package com.poppytait.cyclingvideosapi.service;

import com.google.api.services.youtube.model.SearchResult;
import com.poppytait.cyclingvideosapi.config.Config;
import com.poppytait.cyclingvideosapi.exception.VideoNotFoundException;
import com.poppytait.cyclingvideosapi.model.ProjectIdAndTitle;
import com.poppytait.cyclingvideosapi.model.Video;
import com.poppytait.cyclingvideosapi.repository.IVideoRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService implements IVideoService {
    private final IYouTubeService youTubeService;
    private final Config.YouTubeConfig config;
    private final IVideoRepository repository;

    public VideoService(IYouTubeService youTubeService, Config.YouTubeConfig config, IVideoRepository repository) {
        this.youTubeService = youTubeService;
        this.config = config;
        this.repository = repository;
    }

    @Override
    public void storeVideos() throws IOException {
        List<SearchResult> gcnResults = youTubeService.searchChannel(config.getGcnChannelId());
        List<SearchResult> mbnResults = youTubeService.searchChannel(config.getMbnChannelId());
        List<SearchResult> allResults = new ArrayList<>(gcnResults);
        allResults.addAll(mbnResults);

        List<Video> videos = allResults.stream().map(result -> buildVideo(result)).collect(Collectors.toList());

        repository.saveAll(videos);
    }

    @Override
    public List<Video> getVideos() {
        return repository.findAll();
    }

    @Override
    public Video getVideo(Integer id) throws VideoNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
    }

    @Override
    public void deleteVideo(Integer id) throws VideoNotFoundException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new VideoNotFoundException(id);
        }
    }

    @Override
    public List<ProjectIdAndTitle> search(String query) {
        return repository.findByTitleContaining(query);
    }

    private Video buildVideo(SearchResult result) {
        int id = result.getId().getVideoId().hashCode();
        String title = result.getSnippet().getTitle();
        Instant publishedAt = Instant.parse(result.getSnippet().getPublishedAt().toStringRfc3339());

        return new Video(id, title, publishedAt);
    }
}

