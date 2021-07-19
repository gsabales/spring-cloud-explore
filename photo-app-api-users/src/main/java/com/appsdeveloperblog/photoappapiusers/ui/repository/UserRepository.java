package com.appsdeveloperblog.photoappapiusers.ui.repository;

import com.appsdeveloperblog.photoappapiusers.ui.data.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
