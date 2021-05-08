package com.poppytait.cyclingvideosapi.repository;


import com.poppytait.cyclingvideosapi.model.Video;
import org.springframework.data.repository.CrudRepository;

public interface IVideoRepository extends CrudRepository<Video, Long> {
}
