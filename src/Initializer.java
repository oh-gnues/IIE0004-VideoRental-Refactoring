package src;

import src.customer.Customer;
import src.customer.CustomerService;
import src.rental.RentalService;
import src.video.Video;
import src.video.VideoService;

public class Initializer {
    private final CustomerService customerService;
    private final VideoService videoService;
    private final RentalService rentalService;

    public Initializer(CustomerService customerService, VideoService videoService, RentalService rentalService) {
        this.customerService = customerService;
        this.videoService = videoService;
        this.rentalService = rentalService;
    }

    public void initialize() {
        initDefaultCustomers();
        initDefaultVideos();
        initDefaultRentals();
    }

    private void initDefaultCustomers() {
        customerService.registerCustomer("James");
        customerService.registerCustomer("Brown");
    }

    private void initDefaultVideos() {
        videoService.registerVideo("v1", Video.CD, Video.REGULAR);
        videoService.registerVideo("v2", Video.DVD, Video.NEW_RELEASE);
    }

    private void initDefaultRentals() {
        Customer james = customerService.findCustomer("James");
        Video v1 = videoService.findAvailableVideo("v1");
        Video v2 = videoService.findAvailableVideo("v2");

        if (james != null && v1 != null && v2 != null) {
            rentalService.rentVideo(james.getName(), v1.getTitle());
            rentalService.rentVideo(james.getName(), v2.getTitle());
        }
    }
}
