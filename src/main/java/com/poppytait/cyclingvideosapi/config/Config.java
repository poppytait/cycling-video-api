package com.poppytait.cyclingvideosapi.config;

import com.poppytait.cyclingvideosapi.util.ResourceReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class Config {
    @Value("${YOUTUBE_API_KEY}")
    private String apiKey;

    @Value("${global_cycling_network_channel_id}")
    private String gcnChannelId;

    @Value("${global_mountain_biking_network_channel_id}")
    private String mbnChannelId;

    @Value("classpath:search_filter")
    private Resource searchTermsFile;

    @Bean
    public YouTubeConfig youTubeConfig() {
        String asString = ResourceReader.asString(searchTermsFile);
        List<String> searchTerms = asString.lines().map(term -> "\"" + term + "\"").collect(Collectors.toList());
        return new YouTubeConfig(apiKey, gcnChannelId, mbnChannelId, searchTerms);
    }

    public static class YouTubeConfig {
        private final String apiKey;
        private final String gcnChannelId;
        private final String mbnChannelId;
        private final List<String> searchTerms;

        public YouTubeConfig(String apiKey, String gcnChannelId, String mbnChannelId, List<String> searchTerms) {
            this.apiKey = apiKey;
            this.gcnChannelId = gcnChannelId;
            this.mbnChannelId = mbnChannelId;
            this.searchTerms = searchTerms;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getGcnChannelId() {
            return gcnChannelId;
        }

        public String getMbnChannelId() {
            return mbnChannelId;
        }

        public List<String> getSearchTerms() {
            return searchTerms;
        }
    }
}
