package com.amalitech.AccessKey.repository;

import com.amalitech.AccessKey.entities.AccessKey;
import com.amalitech.AccessKey.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepository extends JpaRepository<AccessKey,Long> {

}
