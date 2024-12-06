package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import src.*;

@DisplayName("비디오 대여 시스템 테스트")
class VideoRentalTest {
    private Customer customer;
    private Video videoRegular;
    private Video videoNewRelease;
    private Rental rentalRegular;
    private Rental rentalNewRelease;

    @BeforeEach
    void setUp() {
        customer = new Customer("Test Customer");
        videoRegular = new Video("Regular Movie", Video.DVD, Video.REGULAR, new Date());
        videoNewRelease = new Video("New Release", Video.DVD, Video.NEW_RELEASE, new Date());
        rentalRegular = new Rental(videoRegular);
        rentalNewRelease = new Rental(videoNewRelease);
    }

    @Nested
    @DisplayName("고객 테스트")
    class CustomerTest {
        @Test
        @DisplayName("비디어 대여가 성공적으로 추가되어야 한다")
        void addRentalTest() {
            customer.addRental(rentalRegular);

            assertEquals(1, customer.getRentals().size());
            assertEquals("Regular Movie", customer.getRentals().get(0).getVideo().getTitle());
        }

        @Test
        @DisplayName("고객 리포트가 올바르게 생성되어야 한다")
        void generateReportTest() {
            customer.addRental(rentalRegular);
            String report = customer.getReport();

            assertAll(
                    () -> assertTrue(report.contains("Customer Report for Test Customer")),
                    () -> assertTrue(report.contains("Regular Movie")),
                    () -> assertTrue(report.contains("Charge:"))
            );
        }
    }

    @Nested
    @DisplayName("비디오 테스트")
    class VideoTest {
        @Test
        @DisplayName("비디오 대여 상태가 올바르게 변경되어야 한다")
        void rentalStatusTest() {
            assertFalse(videoRegular.isRented());

            videoRegular.setRented(true);

            assertTrue(videoRegular.isRented());
        }

        @Test
        @DisplayName("비디오 타입에 따른 연체 패널티가 올바르게 계산되어야 한다")
        void lateReturnPenaltyTest() {
            Video vhsVideo = new Video("VHS Movie", Video.VHS, Video.REGULAR, new Date());
            Video cdVideo = new Video("CD Movie", Video.CD, Video.REGULAR, new Date());
            Video dvdVideo = new Video("DVD Movie", Video.DVD, Video.REGULAR, new Date());

            assertAll(
                    () -> assertEquals(1, vhsVideo.getLateReturnPointPenalty()),
                    () -> assertEquals(2, cdVideo.getLateReturnPointPenalty()),
                    () -> assertEquals(3, dvdVideo.getLateReturnPointPenalty())
            );
        }
    }

    @Nested
    @DisplayName("대여 테스트")
    class RentalTest {
        @Test
        @DisplayName("비디오 반납이 올바르게 처리되어야 한다")
        void returnVideoTest() {
            rentalRegular.returnVideo();

            assertAll(
                    () -> assertEquals(1, rentalRegular.getStatus()),
                    () -> assertNotNull(rentalRegular.getReturnDate())
            );
        }

        @Test
        @DisplayName("대여 기간 제한이 비디오 타입에 따라 올바르게 계산되어야 한다")
        void daysRentedLimitTest() {
            Video vhsVideo = new Video("VHS Movie", Video.VHS, Video.REGULAR, new Date());
            Video cdVideo = new Video("CD Movie", Video.CD, Video.REGULAR, new Date());
            Video dvdVideo = new Video("DVD Movie", Video.DVD, Video.REGULAR, new Date());

            Rental vhsRental = new Rental(vhsVideo);
            Rental cdRental = new Rental(cdVideo);
            Rental dvdRental = new Rental(dvdVideo);

            assertAll(
                    () -> assertEquals(5, vhsRental.getDaysRentedLimit()),
                    () -> assertEquals(3, cdRental.getDaysRentedLimit()),
                    () -> assertEquals(2, dvdRental.getDaysRentedLimit())
            );
        }
    }
}
