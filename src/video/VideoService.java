package src.video;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoService {
    private final List<Video> videos;

    public VideoService() {
        videos = new ArrayList<>();
    }

    public List<String> listVideos() {
        List<String> videoInfo = new ArrayList<>();
        for (Video video : videos) {
            videoInfo.add("Title: " + video.getTitle() + ", Price Code: " + video.getPriceCode());
        }
        return videoInfo;
    }

    public void registerVideo(String title, int videoType, int priceCode) {
        videos.add(new Video(title, videoType, priceCode, new Date()));
    }

    public Video findAvailableVideo(String title) {
        return videos.stream()
                .filter(video -> video.getTitle().equals(title) && !video.isRented())
                .findFirst()
                .orElse(null);
    }
}
