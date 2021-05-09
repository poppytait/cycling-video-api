package com.poppytait.cyclingvideosapi.controller;

import com.poppytait.cyclingvideosapi.exception.VideoNotFoundException;
import com.poppytait.cyclingvideosapi.model.ProjectIdAndTitle;
import com.poppytait.cyclingvideosapi.model.Video;
import com.poppytait.cyclingvideosapi.service.IVideoService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("videos")
public class VideoController {
    private final IVideoService videoService;

    public VideoController(IVideoService videoService) {
        this.videoService = videoService;
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

    @GetMapping("/search")
    public List<ProjectIdAndTitle> search(@RequestParam String q) {
        return videoService.search(q);
    }
}
