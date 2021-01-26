package com.beautician.app.io.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.beautician.app.io.entity.AdminUserEntity;

@Repository
public interface AdminUserRepository extends PagingAndSortingRepository<AdminUserEntity, Long> {
	AdminUserEntity findByEmail(String email);
	AdminUserEntity findByUserId(String userId);
}