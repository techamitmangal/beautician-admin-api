package com.beautician.app.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beautician.app.io.entity.AdminUserEntity;
import com.beautician.app.io.repositories.AdminUserRepository;
import com.beautician.app.service.AdminUserService;
import com.beautician.app.shared.Utils;
import com.beautician.app.shared.dto.AdminUserDto;

@Service
public class AdminUserServiceImpl implements AdminUserService{

	@Autowired
	Utils utils ;
	
	@Autowired
	AdminUserRepository adminUserRepository; 
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public AdminUserDto createAdminUser(AdminUserDto adminUser) {
		// TODO Auto-generated method stub
		if (adminUserRepository.findByEmail(adminUser.getEmail())!=null) {
			throw new RuntimeException("User Already Exists");
		}
		
		AdminUserEntity adminUserEntity = new AdminUserEntity();
		BeanUtils.copyProperties(adminUser, adminUserEntity);
		adminUserEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(adminUser.getPassword()));
		adminUserEntity.setUserId(utils.generateUserId(30));
		
		AdminUserEntity savedAdminUserEntity = adminUserRepository.save(adminUserEntity);
		AdminUserDto returnValue = new AdminUserDto();
		BeanUtils.copyProperties(savedAdminUserEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AdminUserEntity adminUserEntity = adminUserRepository.findByEmail(email);
		if (adminUserEntity==null) throw new UsernameNotFoundException(email);
		return new User(adminUserEntity.getEmail(),adminUserEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public AdminUserDto getAdminUser(String email) {
		// TODO Auto-generated method stub
		AdminUserEntity adminUserEntity = adminUserRepository.findByEmail(email);
		if (adminUserEntity==null) throw new UsernameNotFoundException(email);
		AdminUserDto returnValue = new AdminUserDto();
		BeanUtils.copyProperties(adminUserEntity, returnValue);
		return returnValue;
	}

	@Override
	public AdminUserDto getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		AdminUserDto adminUserDto = new AdminUserDto();
		AdminUserEntity adminUserEntity = adminUserRepository.findByUserId(userId);
		BeanUtils.copyProperties(adminUserEntity, adminUserDto);		
		return adminUserDto;
	}

	@Override
	public AdminUserDto updateAdminUserDetails(AdminUserDto adminUserDto) {
		AdminUserDto updatedAdminUserdto = new AdminUserDto();
		AdminUserEntity adminUserEntity = adminUserRepository.findByUserId(adminUserDto.getUserId());
		
		if (adminUserEntity==null)
			throw new UsernameNotFoundException(adminUserDto.getUserId());
		
		adminUserEntity.setFirstName(adminUserDto.getFirstName());
		adminUserEntity.setLastName(adminUserDto.getLastName());
		
		AdminUserEntity updatedUserDetails = adminUserRepository.save(adminUserEntity);
		BeanUtils.copyProperties(updatedUserDetails, updatedAdminUserdto);
		
		return updatedAdminUserdto;
	}

	@Override
	public boolean deleteAdminUser(String userId) {
		AdminUserEntity adminUserEntity = adminUserRepository.findByUserId(userId);
		if (adminUserEntity==null)
			throw new UsernameNotFoundException(userId);
		try {
			adminUserRepository.delete(adminUserEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
