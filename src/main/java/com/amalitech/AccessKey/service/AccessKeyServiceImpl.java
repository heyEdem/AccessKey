package com.amalitech.AccessKey.service;

import com.amalitech.AccessKey.dto.AccessKeyCreateSuccessfulResponse;
import com.amalitech.AccessKey.dto.AccessKeyRequest;
import com.amalitech.AccessKey.dto.GetAccessKeyProjection;
import com.amalitech.AccessKey.dto.GenericMessageResponse;
import com.amalitech.AccessKey.entities.AccessKey;
import com.amalitech.AccessKey.entities.Status;
import com.amalitech.AccessKey.entities.User;
import com.amalitech.AccessKey.exception.ActiveAccessKeyException;
import com.amalitech.AccessKey.exception.NotFoundException;
import com.amalitech.AccessKey.exception.VerificationFailedException;
import com.amalitech.AccessKey.repository.AccessKeyRepository;
import com.amalitech.AccessKey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccessKeyServiceImpl implements AccessKeyService{
    private static final int OTP_LENGTH = 12;
    private final UserRepository userRepository;
    private final AccessKeyRepository accessKeyRepository;
    private static final String NOT_FOUND_MSG = "User account not found";
    private static final String ACCESS_KEY_NOT_FOUND_MSG = "No access key exists with these credentials";
    private static final String VERIFICATION_FAILED_MESSAGE = "Could not verify this OTP";
    private static final String CREATE_KEY = "Access Key added";
    private static final String REVOKE_SUCCESSFUL ="Access key revoked";
    private static final String DELETE_SUCCESSFUL = "Access key deleted successfully";
    private static final  String ACTIVE_ACCESS_KEY_EXISTS = "You already have an active access key";



    @Override
    public AccessKeyCreateSuccessfulResponse createKey(AccessKeyRequest request, Principal principal, String email) {
        UsernamePasswordAuthenticationToken authenticationToken = getPrincipal((UsernamePasswordAuthenticationToken) principal);

        User user = userRepository.findByEmail(email).orElseThrow(()->new NotFoundException(NOT_FOUND_MSG));

        if (!Objects.equals(user.getEmail(), authenticationToken.getName()))
            throw new VerificationFailedException(VERIFICATION_FAILED_MESSAGE);

        if(!hasActiveKey(user)) {
            throw new ActiveAccessKeyException(ACTIVE_ACCESS_KEY_EXISTS);
        }
        String akey = generator();
        LocalDateTime expirationTime = LocalDateTime.now().plusDays(30);
        AccessKey accessKey = AccessKey.builder()
                .name(request.name())
                .code(akey)
                .expiry(LocalDate.from(expirationTime))
                .school(user)
                .status(Status.ACTIVE)
                .build();
        accessKeyRepository.save(accessKey);
        log.info("created key with code {}",accessKey.getCode());

        return new AccessKeyCreateSuccessfulResponse(CREATE_KEY, accessKey.getCode(), accessKey.getExpiry(),accessKey.getStatus().toString());
    }

    @Override
    public Page<GetAccessKeyProjection> getAllUserKeys(int page, int size, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(()->new NotFoundException(NOT_FOUND_MSG));

        PageRequest pageRequest = PageRequest.of(page, size);
        return accessKeyRepository.findAllUserKeys(user.getId(), pageRequest);
    }

    @Override
    public GenericMessageResponse deleteKey(UUID accessKeyId, Principal principal) {
        getPrincipal((UsernamePasswordAuthenticationToken) principal);

        accessKeyRepository.deleteById(accessKeyId);

        return new GenericMessageResponse(DELETE_SUCCESSFUL);
    }

    @Override
    public GenericMessageResponse revokeKey(UUID accessKeyId, Principal principal) {
        getPrincipal((UsernamePasswordAuthenticationToken) principal);
        AccessKey accessKey = accessKeyRepository.findById(accessKeyId).orElseThrow(()-> new NotFoundException(NOT_FOUND_MSG));
        accessKey.setStatus(Status.REVOKED);
        accessKeyRepository.save(accessKey);
        return new GenericMessageResponse(REVOKE_SUCCESSFUL);

    }

    @Override
    public Page<GetAccessKeyProjection> getAllKeys(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return accessKeyRepository.findAllAccessKeys(pageRequest);
    }

    public boolean hasActiveKey(User user){
        List<GetAccessKeyProjection> keys = accessKeyRepository.findUserActiveKeys(user.getId());
        return keys.isEmpty();
    }

    private UsernamePasswordAuthenticationToken getPrincipal(UsernamePasswordAuthenticationToken principal) {
        return principal;
    }


        public static String generator() {
            SecureRandom random = new SecureRandom();

            StringBuilder key = new StringBuilder();

            for (int i = 0; i < OTP_LENGTH; i++) {
                key.append(random.nextInt(16));
            }
            return key.toString();
        }

}
