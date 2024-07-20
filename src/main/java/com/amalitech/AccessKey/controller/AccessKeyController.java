package com.amalitech.AccessKey.controller;

import com.amalitech.AccessKey.dto.AccessKeyCreateSuccessfulResponse;
import com.amalitech.AccessKey.dto.AccessKeyRequest;
import com.amalitech.AccessKey.dto.GenericMessageResponse;
import com.amalitech.AccessKey.dto.GetAccessKeyProjection;
import com.amalitech.AccessKey.service.AccessKeyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/access-keys")
@RequiredArgsConstructor
public class AccessKeyController {
    private final AccessKeyServiceImpl accessKeyService;

    @Operation(
            summary = "Create a new key",
            method = "POST"
    )
    @PostMapping("/new-key")
    public AccessKeyCreateSuccessfulResponse addAccessKey(@RequestBody AccessKeyRequest request,
                                                          Principal principal){
        return accessKeyService.createKey(request,principal,principal.getName());
    }

    @Operation(
            summary = "Get all access keys for user",
            method = "GET"
    )
    @GetMapping("/my-keys")
    public Page<GetAccessKeyProjection> getUserAccessKeys(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "100") int size,
                                                          Principal principal) {
        return accessKeyService.getAllUserKeys(page, size, principal);
    }

    @Operation(
            summary = "Get all access keys for all users",
            method = "GET"
    )
    @GetMapping("/all-keys")
    public Page<GetAccessKeyProjection> getAllAccessKeys(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "100") int size
                                                          ) {
        return accessKeyService.getAllKeys(page, size);
    }

    @Operation(
            summary = "Admin get active access keys for a user",
            method = "GET"
    )
    @GetMapping("/active-key/{email}")
    public GetAccessKeyProjection getActiveUserKey(@PathVariable String email) {
        return accessKeyService.getActiveUserKey(email);
    }

    @Operation(
            summary = "Delete a key",
            method = "DELETE"
    )
    @DeleteMapping("/delete-key/{id}")
    public GenericMessageResponse deleteAccessKey(@PathVariable("id") UUID accessKeyId,
                                                  Principal principal){
        return accessKeyService.deleteKey(accessKeyId,principal);
    }

    @Operation(
            summary = "Revoke an accessKey",
            method = "PATCH"
    )
    @PatchMapping("/revoke-key/{id}")
    public GenericMessageResponse revokeKey(@PathVariable("id") UUID accessKeyId,
                                            Principal principal ){
        return accessKeyService.revokeKey(accessKeyId,principal);
    }


}