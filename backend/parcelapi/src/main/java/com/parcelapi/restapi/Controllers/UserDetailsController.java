package com.parcelapi.restapi.Controllers;

import javax.validation.Valid;

import com.parcelapi.restapi.Constants.Constants;
import com.parcelapi.restapi.Dto.CreateUserDetailsDTO;
import com.parcelapi.restapi.Dto.UpdateUserDetailsDTO;
import com.parcelapi.restapi.Models.UserDetails;
import com.parcelapi.restapi.Services.Impl.UserServiceImpl;
import com.parcelapi.restapi.Utility.ApiResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2. User Details Controller", description = "Handle all user related operations")
@RestController
@RequestMapping(path = Constants.REQUEST_MAPPING.USER)
public class UserDetailsController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ModelMapper modelMapper;

    @SecurityRequirements
    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<UpdateUserDetailsDTO>> registerUser(@Valid @RequestBody CreateUserDetailsDTO userDetails) {
        UserDetails saveUserObj = userService.registerUser(modelMapper.map(userDetails, UserDetails.class));
        return new ResponseEntity<>(new ApiResponse<UpdateUserDetailsDTO>(true, modelMapper.map(saveUserObj, UpdateUserDetailsDTO.class)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUserInfo/{userId}")
    public ResponseEntity<ApiResponse<UpdateUserDetailsDTO>> getUserById(@PathVariable("userId") long userId) {
        UserDetails userObj = userService.GetUserDetails(userId);
        if(userObj != null) {
            return new ResponseEntity<>(new ApiResponse<UpdateUserDetailsDTO>(true, modelMapper.map(userObj, UpdateUserDetailsDTO.class)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<UpdateUserDetailsDTO>(false, null, "Cannot find the user!"), HttpStatus.OK);
        }
        
    }

    @PutMapping(value = "/updateInfo")
    public ResponseEntity<ApiResponse<String>> updateUserInfo(@RequestBody UpdateUserDetailsDTO userDetails) {
        UserDetails saveUserObj = userService.UpdateUserInfo(modelMapper.map(userDetails, UserDetails.class));
        if(saveUserObj != null) {
            return new ResponseEntity<>(new ApiResponse<String>(true, "Successfully update user! User ID : " + saveUserObj.getUserId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<String>(false, null, "Error happen in user update!"), HttpStatus.OK);
        }
    }
}
