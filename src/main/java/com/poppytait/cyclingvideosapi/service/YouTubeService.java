package com.poppytait.cyclingvideosapi.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.poppytait.cyclingvideosapi.config.Config;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class YouTubeService implements IYouTubeService {
    private static final String APPLICATION_NAME = "Cycling Videos API";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String PART = "snippet";
    private static final String TYPE = "video";
    private static final long MAX_RESULTS = 100L;

    private final YouTube youTube;
    private final Config.YouTubeConfig config;
    private final String searchTerms;

    public YouTubeService(Config.YouTubeConfig config) throws GeneralSecurityException, IOException {
        this.config = config;
        this.searchTerms = String.join("|", config.getSearchTerms());
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        this.youTube = new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
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


