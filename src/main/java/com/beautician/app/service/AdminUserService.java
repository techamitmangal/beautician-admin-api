package com.beautician.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.beautician.app.shared.dto.AdminUserDto;

public interface AdminUserService extends UserDetailsService {
	AdminUserDto createAdminUser(AdminUserDto adminUser);
	AdminUserDto getAdminUser(String email);
	AdminUserDto getUserByUserId(String userId);
	AdminUserDto updateAdminUserDetails(AdminUserDto adminUserDto);
}
