package test;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import src.*;
import src.customer.Customer;
import src.customer.CustomerReportData;
import src.customer.CustomerReportFormatter;
import src.rental.Rental;
import src.video.Video;

@DisplayName("비디오 대여 시스템 테스트")
class VideoRentalTest {
    private Customer customer;
    private Video videoRegular;
    private Video videoNewRelease;
    private Rental rentalRegular;
    private Rental rentalNewRelease;
    private CustomerReportFormatter customerReportFormatter;

    @BeforeEach
    void setUp() {
        customer = new Customer("Test Customer");
        videoRegular = new Video("Regular Movie", Video.DVD, Video.REGULAR, new Date());
        videoNewRelease = new Video("New Release", Video.DVD, Video.NEW_RELEASE, new Date());
        rentalRegular = new Rental(videoRegular);
        rentalNewRelease = new Rental(videoNewRelease);
        customerReportFormatter = new CustomerReportFormatter();
    }
    
    private String getFormattedReport(Customer customer) {
        CustomerReportData reportData = customer.generateReportData();
        return customerReportFormatter.formatCustomerReport(reportData);
    }

    @Nested
    @DisplayName("고객 테스트")
    class CustomerTest {
        @Test
        @DisplayName("비디어 대여가 성공적으로 추가되어야 한다")
        void addRentalTest() {
            customer.addRental(rentalRegular);

            assertEquals(1, customer.getRentals().size());
            assertEquals("Regular Movie", customer.getRentals().getFirst().getVideo().getTitle());
        }

        @Test
        @DisplayName("고객 리포트가 올바르게 생성되어야 한다")
        void generateReportTest() {
            customer.addRental(rentalRegular);
            String report = getFormattedReport(customer);

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

    @Nested
    @DisplayName("요금 계산 테스트")
    class PriceCalculationTest {
        @Test
        @DisplayName("일반 비디오의 2일 이내 대여 요금은 기본 요금만 부과되어야 한다")
        void regularVideoBasicFeeTest() {
            customer.addRental(rentalRegular);
            // 2일 이내 대여 시 기본 요금 2만 부과
            setRentalPeriod(rentalRegular, 2);
            String report = getFormattedReport(customer);
            assertTrue(report.contains("Charge: 2.0"));
        }

        @Test
        @DisplayName("일반 비디오의 3일 이상 대여 요금은 추가 요금이 부과되어야 한다")
        void regularVideoExtraFeeTest() {
            customer.addRental(rentalRegular);
            // 4일 대여 시: 기본 2 + (2일 * 1.5) = 5
            setRentalPeriod(rentalRegular, 4);
            String report = getFormattedReport(customer);
            assertTrue(report.contains("Charge: 5.0"));
        }

        @Test
        @DisplayName("신작 비디오는 대여 기간에 따라 요금이 계산되어야 한다")
        void newReleaseVideoFeeTest() {
            customer.addRental(rentalNewRelease);
            // 3일 대여 시: 3일 * 3 = 9
            setRentalPeriod(rentalNewRelease, 3);
            String report = getFormattedReport(customer);
            assertTrue(report.contains("Charge: 9.0"));
        }
    }

    @Nested
    @DisplayName("포인트 계산 테스트")
    class PointCalculationTest {
        @Test
        @DisplayName("일반 비디오 대여 시 기본 포인트가 적립되어야 한다")
        void regularVideoBasicPointTest() {
            customer.addRental(rentalRegular);
            String report = getFormattedReport(customer);
            assertTrue(report.contains("Point: 1"));
        }

        @Test
        @DisplayName("신작 비디오 대여 시 추가 포인트가 적립되어야 한다")
        void newReleaseVideoExtraPointTest() {
            customer.addRental(rentalNewRelease);
            String report = getFormattedReport(customer);
            assertTrue(report.contains("Point: 2")); // 기본 1 + 신작 추가 !
        }

        @Test
        @DisplayName("대여 기간 초과시 포인트 차감이 되어야 한다")
        void lateReturnPointPenaltyTest() {
            // DVD + Regular + 10일 대여 : 1포인트 - min(1, 3)포인트 = 0포인트
            customer.addRental(rentalRegular);
            setRentalPeriod(rentalRegular, 10);
            String report = getFormattedReport(customer);
            assertTrue(report.contains("Point: 0"));

            // VHS + 신작 + 6일 대여 : 2포인트 - 1포인트 = 1포인트
            Video vhsVideo = new Video("VHS Movie", Video.VHS, Video.NEW_RELEASE, new Date());
            Rental rentalVHS = new Rental(vhsVideo);
            customer.addRental(rentalVHS);
            setRentalPeriod(rentalVHS, 6);
            report = getFormattedReport(customer);
            assertTrue(report.contains("Point: 1"));
        }

        @Test
        @DisplayName("누적 포인트에 따라 쿠폰이 발급되어야 한다")
        void couponIssuanceTest() {
            // 포인트가 10점 이상 쌓이는 상황 설정
            List<Video> videos = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                videos.add(new Video("Video " + i, Video.DVD, Video.NEW_RELEASE, new Date()));
                customer.addRental(new Rental(videos.get(i)));
            }

            String report = getFormattedReport(customer);
            assertTrue(report.contains("one free coupon")); // 10포인트 이상

            // 포인트가 30점 이상 쌓이는 상황 설정
            for (int i = 6; i < 20; i++) {
                videos.add(new Video("Video " + i, Video.DVD, Video.NEW_RELEASE, new Date()));
                customer.addRental(new Rental(videos.get(i)));
            }

            report = getFormattedReport(customer);
            assertTrue(report.contains("two free coupon")); // 30포인트 이상
        }
    }

    @Nested
    @DisplayName("VRUI 시스템 테스트")
    class VRUITest {
        private ByteArrayOutputStream testOut;

        @BeforeEach
        void setUp() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @Test
        @DisplayName("고객 등록이 정상적으로 수행되어야 한다")
        void registerCustomerTest() {
            String input = "3\nTestCustomer\n"; // Register customer
            input += "1\n"; // List customers
            input += "0\n"; // Quit
            ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            System.setIn(testIn);

            VRUI.main(new String[]{});

            String output = testOut.toString();
            assertAll(
                    () -> assertTrue(output.contains("List of customers")),
                    () -> assertTrue(output.contains("TestCustomer"))
            );
        }

        @Test
        @DisplayName("비디오 등록이 정상적으로 수행되어야 한다")
        void registerVideoTest() {
            String input = "4\nTestVideo\n1\n1\n"; // Register video
            input += "2\n"; // List videos
            input += "0\n"; // Quit
            ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            System.setIn(testIn);

            VRUI.main(new String[]{});

            String output = testOut.toString();
            assertAll(
                    () -> assertTrue(output.contains("List of videos")),
                    () -> assertTrue(output.contains("TestVideo"))
            );
        }

        @Test
        @DisplayName("비디오 대여가 정상적으로 처리되어야 한다")
        void rentVideoTest() {
            String input = "3\nTestCustomer\n"; // Register customer
            input += "4\nTestVideo\n1\n1\n"; // Register video
            input += "5\nTestCustomer\nTestVideo\n"; // Rent video
            input += "1\n"; // List customers
            input += "0\n"; // Quit
            ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            System.setIn(testIn);

            VRUI.main(new String[]{});

            String output = testOut.toString();
            assertAll(
                    () -> assertTrue(output.contains("List of customers")),
                    () -> assertTrue(output.contains("TestCustomer")),
                    () -> assertTrue(output.contains("TestVideo"))
            );
        }

        @Test
        @DisplayName("비디오 반납이 정상적으로 처리되어야 한다")
        void returnVideoTest() {
            String input = "3\nTestCustomer\n"; // Register customer
            input += "4\nTestVideo\n1\n1\n"; // Register video
            input += "5\nTestCustomer\nTestVideo\n"; // Rent video
            input += "6\nTestCustomer\nTestVideo\n"; // Return video
            input += "1\n"; // List customers
            input += "0\n"; // Quit

            ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            System.setIn(testIn);

            VRUI.main(new String[]{});

            String output = testOut.toString();

            assertAll(
                    () -> assertTrue(output.contains("List of customers")),
                    () -> assertTrue(output.contains("TestCustomer")),
                    () -> assertFalse(output.contains("TestVideo"))
            );
        }

        @Test
        @DisplayName("고객 리포트가 정상적으로 생성되어야 한다")
        void customerReportTest() {
            String input = "3\nTestCustomer\n"; // Register customer
            input += "4\nTestVideo\n1\n1\n"; // Register video
            input += "5\nTestCustomer\nTestVideo\n"; // Rent video
            input += "7\nTestCustomer\n"; // Show customer report
            input += "0\n"; // Quit

            ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            System.setIn(testIn);

            VRUI.main(new String[]{});

            String output = testOut.toString();
            assertAll(
                    () -> assertTrue(output.contains("Customer Report for TestCustomer")),
                    () -> assertTrue(output.contains("TestVideo"))
            );
        }

        @Test
        @DisplayName("대여 목록 삭제가 정상적으로 처리되어야 한다")
        void clearRentalsTest() {
            String input = "3\nTestCustomer\n"; // Register customer
            input += "4\nTestVideo\n1\n1\n"; // Register video
            input += "5\nTestCustomer\nTestVideo\n"; // Rent video
            input += "8\nTestCustomer\n"; // Show customer and clear rentals
            input += "1\n"; // List customers
            input += "0\n"; // Quit

            ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            System.setIn(testIn);

            VRUI.main(new String[]{});

            String output = testOut.toString();
            assertAll(
                    () -> assertTrue(output.contains("List of customers")),
                    () -> assertTrue(output.contains("Name: TestCustomer\tRentals: 0"))
            );
        }

        @AfterEach
        void restoreStreams() {
            System.setIn(System.in);
            System.setOut(System.out);
        }
    }

    // 테스트 헬퍼 메소드
    private void setRentalPeriod(Rental rental, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(rental.getRentDate());
        cal.add(Calendar.DATE, -days+1);
        rental.setRentDate(cal.getTime());
    }
}