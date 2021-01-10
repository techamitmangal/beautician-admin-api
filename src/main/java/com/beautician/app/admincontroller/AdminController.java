package com.beautician.app.admincontroller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beautician.app.admincontroller.ui.model.response.CreateAdminUserResponseModel;
import com.beautician.app.service.AdminUserService;
import com.beautician.app.shared.dto.AdminUserDto;
import com.beautician.app.ui.model.request.CreateAdminUserRequestModel;

@RestController
@RequestMapping("admin/api/") //http://localhost:8080/admin/api/createadminuser
public class AdminController {
	
	@Autowired
	AdminUserService adminUserService ;
	
	@GetMapping(path="getuserdetail")
	public String getAdmin() {
		return "Hellow";
	}
	
	@PostMapping(path="createadminuser")
	public CreateAdminUserResponseModel createAdminUser(@RequestBody CreateAdminUserRequestModel adminUser) {
		CreateAdminUserResponseModel createAdminUserResponseModel = new CreateAdminUserResponseModel();
		
		AdminUserDto adminUserDto = new AdminUserDto();
		BeanUtils.copyProperties(adminUser, adminUserDto);
		
		AdminUserDto createdAdminUserDto = adminUserService.createAdminUser(adminUserDto);
		BeanUtils.copyProperties(createdAdminUserDto, createAdminUserResponseModel);
		return createAdminUserResponseModel;
	}
	
	@GetMapping(path="getuser")
	public String getUser() {
		return "Hello World";
	}
	
}
