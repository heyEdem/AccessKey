package com.amalitech.AccessKey.dto;

import com.amalitech.AccessKey.entities.Roles;
import lombok.Builder;



import java.util.List;

@Builder
public record NormalUserResponseDto(
        String token,

        String name,

        String email,

        Roles role

) {
}
