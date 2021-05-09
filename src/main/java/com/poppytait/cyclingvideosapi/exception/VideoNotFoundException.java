package com.poppytait.cyclingvideosapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VideoNotFoundException extends Exception {
    public VideoNotFoundException(Integer id) {
        super("Video with id " + id + " not found");
    }
}
