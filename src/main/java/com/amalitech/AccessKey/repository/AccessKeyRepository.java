package com.amalitech.AccessKey.repository;

import com.amalitech.AccessKey.dto.GetAccessKeyProjection;
import com.amalitech.AccessKey.entities.AccessKey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccessKeyRepository extends JpaRepository<AccessKey,UUID> {
    Optional<AccessKey> findByCode(String code);

    @Query("SELECT a FROM AccessKey a WHERE a.school.id = :userId and a.status = com.amalitech.AccessKey.entities.Status.ACTIVE ")
    List<GetAccessKeyProjection> findUserActiveKeys(@Param("userId") UUID userId);

    @Query("SELECT a FROM AccessKey a WHERE a.school.id = :userId")
    Page<GetAccessKeyProjection> findAllUserKeys(@Param("userId") UUID userId, Pageable pageable);

    @Query("select a from AccessKey a")
    Page<GetAccessKeyProjection> findAllAccessKeys(Pageable pageable);

    @Query("SELECT a FROM AccessKey a WHERE a.school.email = :email and a.status = com.amalitech.AccessKey.entities.Status.ACTIVE ")
    Optional <GetAccessKeyProjection> findUserActiveKeysWithEmail(@Param("email") String email);
}
