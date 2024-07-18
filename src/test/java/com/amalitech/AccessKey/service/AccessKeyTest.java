package com.amalitech.AccessKey.service;

import com.amalitech.AccessKey.dto.AccessKeyCreateSuccessfulResponse;
import com.amalitech.AccessKey.dto.AccessKeyRequest;
import com.amalitech.AccessKey.entities.AccessKey;
import com.amalitech.AccessKey.entities.User;
import com.amalitech.AccessKey.repository.AccessKeyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccessKeyTest {
    @Mock
    private AccessKeyRepository accessKeyRepository;

    @Mock
    private AccessKeyServiceImpl accessKeyService;
    private User user;
    private String email;
    private String code;
    private LocalDate expiry;
    private AccessKey accessKey;

    private Principal principal;
//    @BeforeEach
//    void setUp(){
//        email = "test@user.com";
//        code = "1234567812345678";
//        expiry = LocalDate.now().plusDays(30);
//    }

    @Test
    void should_create_new_key (){
        String code = "1234567812345678";
        AccessKeyRequest requestDto = AccessKeyRequest.builder()
                .name("test")
                .build();

//        AccessKeyCreateSuccessfulResponse expectedResponse = new AccessKeyCreateSuccessfulResponse("New access key added",code,expiry);
//        when(accessKeyRepository.findByCode(request.))
        AccessKeyCreateSuccessfulResponse response = AccessKeyCreateSuccessfulResponse.builder()
                .code(code)
                .expiry(expiry)
                .message("New access key added")
                .build();


        when(accessKeyService.hasActiveKey(user)).thenReturn(true);
        AccessKeyCreateSuccessfulResponse request = accessKeyService.createKey(requestDto,principal,email);


        assertNotNull(response);
        assertEquals(response.code(),request.code());
        assertEquals(response.expiry(),request.expiry());

        verify(accessKeyService,times(1)).hasActiveKey(user);
    }
}
