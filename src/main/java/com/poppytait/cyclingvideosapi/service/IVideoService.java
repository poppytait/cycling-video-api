package com.poppytait.cyclingvideosapi.service;

import com.poppytait.cyclingvideosapi.exception.VideoNotFoundException;
import com.poppytait.cyclingvideosapi.model.Video;

import java.io.IOException;
import java.util.List;

public interface IVideoService {
    void storeVideos() throws IOException;
    List<Video> getVideos();
    Video getVideo(Integer id) throws VideoNotFoundException;
    void deleteVideo(Integer id) throws VideoNotFoundException;
}
