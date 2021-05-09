package com.poppytait.cyclingvideosapi.controller;

import com.poppytait.cyclingvideosapi.config.Config;
import com.poppytait.cyclingvideosapi.exception.VideoNotFoundException;
import com.poppytait.cyclingvideosapi.model.Video;
import com.poppytait.cyclingvideosapi.service.IVideoService;
import com.poppytait.cyclingvideosapi.service.IYouTubeService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("videos")
public class VideoController {
    private final IVideoService videoService;
    private final IYouTubeService youTubeService;
    private final Config.YouTubeConfig config;

    public VideoController(IVideoService videoService, IYouTubeService youTubeService, Config.YouTubeConfig config) {
        this.videoService = videoService;
        this.youTubeService = youTubeService;
        this.config = config;
    }

    @PostMapping("/store")
    public void storeVideos() throws IOException {
        videoService.storeVideos();
    }

    @GetMapping("/")
    public List<Video> getVideos() {
       return videoService.getVideos();
    }

    @GetMapping("/{id}")
    public Video getVideo(@PathVariable Integer id) throws VideoNotFoundException {
        return videoService.getVideo(id);
    }

    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable Integer id) throws VideoNotFoundException {
        videoService.deleteVideo(id);
    }


}
