package src.commands;

import src.video.VideoService;

import java.util.Scanner;

public class RegisterVideoCommand implements Command {
    private final VideoService videoService;
    private final Scanner scanner;

    public RegisterVideoCommand(VideoService videoService, Scanner scanner) {
        this.videoService = videoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter video title to register: ");
        String title = scanner.next();

        System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
        int videoType = scanner.nextInt();

        System.out.println("Enter price code( 1 for Regular, 2 for New Release ):");
        int priceCode = scanner.nextInt();

        videoService.registerVideo(title, videoType, priceCode);
    }
}
