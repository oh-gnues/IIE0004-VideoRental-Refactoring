package src.commands;

import src.video.VideoService;

public class ListVideosCommand implements Command {
    private final VideoService videoService;

    public ListVideosCommand(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    public void execute() {
        System.out.println("List of videos") ;

        for ( String videoInfo: videoService.listVideos() ) {
            System.out.println(videoInfo) ;
        }
        System.out.println("End of list") ;
    }
}
