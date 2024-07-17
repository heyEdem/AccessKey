package com.amalitech.AccessKey.service;

import com.amalitech.AccessKey.dto.AccessKeyCreateSuccessfulResponse;
import com.amalitech.AccessKey.dto.AccessKeyRequest;
import com.amalitech.AccessKey.dto.GenericMessageResponse;
import com.amalitech.AccessKey.dto.GetAccessKeyProjection;
import com.amalitech.AccessKey.entities.AccessKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.UUID;

public interface AccessKeyService {
    AccessKeyCreateSuccessfulResponse createKey (AccessKeyRequest request, Principal principal, String email);
    Page<GetAccessKeyProjection> getAllUserKeys (int page, int size, String email);
    GenericMessageResponse deleteKey (UUID accessKeyId, Principal principal);
    GenericMessageResponse revokeKey(UUID accessKeyId, Principal principal);
    Page<GetAccessKeyProjection> getAllKeys(int page, int size);
}
