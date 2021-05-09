package com.poppytait.cyclingvideosapi.repository;


import com.poppytait.cyclingvideosapi.model.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IVideoRepository extends CrudRepository<Video, Integer> {
    @Override
    List<Video> findAll();
}
