package com.poppytait.cyclingvideosapi.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.poppytait.cyclingvideosapi.config.Config;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class YouTubeService implements IYouTubeService {
    private static final String PART = "snippet";
    private static final String TYPE = "video";
    private static final long MAX_RESULTS = 100L;

    private final YouTube youTube;
    private final Config.YouTubeConfig config;
    private final String searchTerms;

    public YouTubeService(Config.YouTubeConfig config, YouTube youTube) {
        this.config = config;
        this.searchTerms = String.join("|", config.getSearchTerms());
        this.youTube = youTube;
    }

    @Override
    public List<SearchResult> searchChannel(String channelID) throws IOException {
        YouTube.Search.List request = youTube.search()
                .list(Collections.singletonList(PART));

        SearchListResponse response = request.setChannelId(channelID).setMaxResults(MAX_RESULTS)
                .setQ(searchTerms)
                .setType(Collections.singletonList(TYPE))
                .setKey(config.getApiKey())
                .execute();

        return response.getItems();
    }
}


