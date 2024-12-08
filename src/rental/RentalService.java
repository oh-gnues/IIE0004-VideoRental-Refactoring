package src.rental;

import src.video.Video;
import src.video.VideoService;
import src.customer.Customer;
import src.customer.CustomerService;

public class RentalService {
    private final CustomerService customerService;
    private final VideoService videoService;

    public RentalService(CustomerService customerService, VideoService videoService) {
        this.customerService = customerService;
        this.videoService = videoService;
    }

    public void rentVideo(String customerName, String videoTitle) {
        Customer customer = customerService.findCustomer(customerName);
        Video video = videoService.findAvailableVideo(videoTitle);

        if (customer != null && video != null) {
            Rental rental = new Rental(video);
            customer.addRental(rental);
            video.setRented(true);
        }
    }

    public void returnVideo(String customerName, String videoTitle) {
        Customer customer = customerService.findCustomer(customerName);

        if (customer != null) {
            Rental rental = customer.getRentals().stream()
                    .filter(r -> r.getVideo().getTitle().equals(videoTitle) && r.getVideo().isRented())
                    .findFirst()
                    .orElse(null);

            if (rental != null) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                customer.removeRental(rental);
            }
        }
    }

    public void clearRentals(String customerName) {
        Customer customer = customerService.findCustomer(customerName);

        if (customer != null) {
            customer.getRentals().clear();
        }
    }
}
