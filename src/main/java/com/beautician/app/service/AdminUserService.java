package com.beautician.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.beautician.app.shared.dto.AdminUserDto;

public interface AdminUserService extends UserDetailsService {
	AdminUserDto createAdminUser(AdminUserDto adminUser);
	AdminUserDto getAdminUser(String email);
	List<AdminUserDto> getAdminUserListService(int page, int limit);
	AdminUserDto getUserByUserId(String userId);
	AdminUserDto updateAdminUserDetails(AdminUserDto adminUserDto);
	boolean deleteAdminUser(String userId);
}