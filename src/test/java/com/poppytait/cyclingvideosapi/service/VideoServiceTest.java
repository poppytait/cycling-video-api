package com.poppytait.cyclingvideosapi.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.poppytait.cyclingvideosapi.config.Config;
import com.poppytait.cyclingvideosapi.exception.VideoNotFoundException;
import com.poppytait.cyclingvideosapi.model.Video;
import com.poppytait.cyclingvideosapi.repository.IVideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {
    String channelIdOne = "1";
    String channelIdTwo = "2";

    @Mock
    IVideoRepository repository;
    @Mock
    IYouTubeService youTubeService;
    @Spy
    Config.YouTubeConfig config = new Config.YouTubeConfig("0000", channelIdOne, channelIdTwo, Collections.singletonList("pro"));

    @InjectMocks
    VideoService service;

    @Captor
    ArgumentCaptor<List<Video>> videoCaptor;

    Instant instant = Instant.parse("2021-04-04T10:37:30Z");
    DateTime dateTime = DateTime.parseRfc3339("2021-04-04T10:37:30Z");

    Video video1 = new Video(49, "Test Title 1", instant);
    Video video2 = new Video(50, "Test Title 2", instant);

    ResourceId resourceId1 = new ResourceId().setVideoId("1");
    ResourceId resourceId2 = new ResourceId().setVideoId("2");

    SearchResultSnippet snippet1 = new SearchResultSnippet().setTitle("Test Title 1").setPublishedAt(dateTime);
    SearchResultSnippet snippet2 = new SearchResultSnippet().setTitle("Test Title 2").setPublishedAt(dateTime);

    SearchResult result1 = new SearchResult().setId(resourceId1).setSnippet(snippet1);
    SearchResult result2 = new SearchResult().setId(resourceId2).setSnippet(snippet2);


    @Test
    void shouldStoreVideos() throws IOException {
        List<Video> videos = new ArrayList<>();
        videos.add(video1);
        videos.add(video2);

        when(youTubeService.searchChannel(channelIdOne)).thenReturn(Collections.singletonList(result1));
        when(youTubeService.searchChannel(channelIdTwo)).thenReturn(Collections.singletonList(result2));

        service.storeVideos();

        verify(repository).saveAll(videoCaptor.capture());

        List<Video> videoCaptorValue = videoCaptor.getValue();
        assertEquals(videos.get(0).getTitle(), videoCaptorValue.get(0).getTitle());
        assertEquals(videos.get(0).getId(), videoCaptorValue.get(0).getId());
        assertEquals(videos.get(0).getDate(), videoCaptorValue.get(0).getDate());
        assertEquals(videos.get(1).getTitle(), videoCaptorValue.get(1).getTitle());
        assertEquals(videos.get(1).getId(), videoCaptorValue.get(1).getId());
        assertEquals(videos.get(1).getDate(), videoCaptorValue.get(1).getDate());
    }

    @Test
    void getVideo() throws VideoNotFoundException {
        Video video = new Video(1, "Test Title 1", instant);

        when(repository.findById(1)).thenReturn(Optional.of(video));

        Video actualVideo = service.getVideo(1);

        assertEquals(video, actualVideo);
    }

    @Test
    void getVideoThrowsExceptionWhenNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> {
            service.getVideo(1);
        });
    }

    @Test
    void deleteVideo() throws VideoNotFoundException {
        when(repository.existsById(1)).thenReturn(true);

        service.deleteVideo(1);

        verify(repository).deleteById(1);
    }

    @Test
    void deleteVideoThrowsExceptionWhenNotFound() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(VideoNotFoundException.class, () -> {
            service.deleteVideo(1);
        });
    }

}
