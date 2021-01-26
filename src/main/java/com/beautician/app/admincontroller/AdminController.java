package com.beautician.app.admincontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beautician.app.admincontroller.ui.model.response.CreateAdminUserResponseModel;
import com.beautician.app.exceptions.AdminUserServiceException;
import com.beautician.app.service.AdminUserService;
import com.beautician.app.shared.dto.AdminUserDto;
import com.beautician.app.ui.model.request.CreateAdminUserReqModel;
import com.beautician.app.ui.model.request.UpdateUserDetailsReqModel;
import com.beautician.app.ui.model.response.AdminUserResModel;
import com.beautician.app.ui.model.response.ErrorMessages;
import com.beautician.app.ui.model.response.GenericMessageResModel;

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
	public CreateAdminUserResponseModel createAdminUser(@RequestBody CreateAdminUserReqModel adminUser) {
		CreateAdminUserResponseModel createAdminUserResponseModel = new CreateAdminUserResponseModel();
		
		AdminUserDto adminUserDto = new AdminUserDto();
		BeanUtils.copyProperties(adminUser, adminUserDto);
		
		AdminUserDto createdAdminUserDto = adminUserService.createAdminUser(adminUserDto);
		BeanUtils.copyProperties(createdAdminUserDto, createAdminUserResponseModel);
		return createAdminUserResponseModel;
	}
	
	@GetMapping(path="getuser")
	public AdminUserResModel getUser(@RequestParam String id, String name) throws Exception{
		if (id.isEmpty() || id==null || id.length()==0 || id.equals("\"\"")) 
			throw new AdminUserServiceException("Null pointer exception") ;
		
		AdminUserDto adminUserDto = adminUserService.getUserByUserId(id) ;
		AdminUserResModel responseModel = new AdminUserResModel();
		BeanUtils.copyProperties(adminUserDto, responseModel);
		return responseModel;
	}
	
	@PostMapping(path="updateuserdetails")
	public AdminUserResModel UpdateUserDetails(@RequestBody UpdateUserDetailsReqModel updateUserDetailsReqModel) {
		if (updateUserDetailsReqModel.getUserId().isEmpty() || updateUserDetailsReqModel.getUserId()==null) 
			throw new AdminUserServiceException("User Id missing") ;
		
		AdminUserResModel adminUserResModel = new AdminUserResModel();
		
		AdminUserDto adminUserDto = new AdminUserDto();
		BeanUtils.copyProperties(updateUserDetailsReqModel, adminUserDto);
		
		AdminUserDto updatedAdminUserDto = adminUserService.updateAdminUserDetails(adminUserDto);
		BeanUtils.copyProperties(updatedAdminUserDto, adminUserResModel);
		
		return adminUserResModel;
	}
	
	@GetMapping(path="deleteuser")
	public GenericMessageResModel DeleteUser(@RequestParam String id) {
		if (id.isEmpty() || id==null || id.length()==0 || id.equals("\"\"")) 
			throw new AdminUserServiceException("User Id missing") ;
		GenericMessageResModel genericMessageResModel = new GenericMessageResModel();
		boolean isUserDeleted = adminUserService.deleteAdminUser(id);
		if (isUserDeleted) {
			genericMessageResModel.setMessage("User deleted successfully");
		} else {
			genericMessageResModel.setMessage("User didn't find in our record");
		}
		return genericMessageResModel;
	}
	
	@GetMapping(path="getusers")
	public List<AdminUserResModel> GetUsers(@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="limit", defaultValue="25") int limit) {
		List<AdminUserResModel> adminUserResModelList = new ArrayList<AdminUserResModel>();
		List<AdminUserDto> adminUserDtoList = adminUserService.getAdminUserListService(page, limit) ;
		for (AdminUserDto adminUserDto : adminUserDtoList) {
			AdminUserResModel adminUserRes = new AdminUserResModel();
			BeanUtils.copyProperties(adminUserDto, adminUserRes);
			adminUserResModelList.add(adminUserRes);
		}
		return adminUserResModelList ;
	}
	
}