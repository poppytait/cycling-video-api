package com.poppytait.cyclingvideosapi.service;

import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

public interface IYouTubeService {
    List<SearchResult> searchChannel(String channelID) throws IOException;
}
