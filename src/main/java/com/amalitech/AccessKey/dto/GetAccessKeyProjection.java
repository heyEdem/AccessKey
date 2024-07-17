package com.amalitech.AccessKey.dto;

import com.amalitech.AccessKey.entities.Status;
import com.amalitech.AccessKey.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface GetAccessKeyProjection {

    String getName();

    String getCode();

    LocalDate getExpiry();

    Status getStatus();

    User getUser();

    LocalDateTime getCreatedAt();

}
