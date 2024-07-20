package com.amalitech.AccessKey.dto;

import com.amalitech.AccessKey.entities.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface GetAccessKeyProjection {

    String getName();

    String getCode();

    LocalDate getExpiry();

    Status getStatus();

    LocalDateTime getCreatedAt();

}
