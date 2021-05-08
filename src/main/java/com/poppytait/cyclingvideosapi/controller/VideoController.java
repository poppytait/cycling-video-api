package com.poppytait.cyclingvideosapi.controller;

import com.google.api.services.youtube.model.SearchResult;
import com.poppytait.cyclingvideosapi.config.Config;
import com.poppytait.cyclingvideosapi.service.IVideoService;
import com.poppytait.cyclingvideosapi.service.IYouTubeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<SearchResult> getPlaylistItems() throws IOException {
       return youTubeService.searchChannel(config.getGcnChannelId());
    }

}
