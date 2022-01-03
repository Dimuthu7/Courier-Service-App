package com.parcelapi.restapi.Controllers;

import javax.validation.Valid;

import com.parcelapi.restapi.Constants.Constants;
import com.parcelapi.restapi.Dto.LoginUserDTO;
import com.parcelapi.restapi.Dto.ViewLoginDTO;
import com.parcelapi.restapi.Services.AuthService;
import com.parcelapi.restapi.Services.Impl.UserServiceImpl;
import com.parcelapi.restapi.Utility.ApiResponse;
import com.parcelapi.restapi.Utility.JWTUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1. Authentication Controller", description = "Handle all authentication related operations")
@RestController
@RequestMapping(path = Constants.REQUEST_MAPPING.AUTH)
public class AuthController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserServiceImpl userService;

    @SecurityRequirements
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse<ViewLoginDTO>> AuthenticateUser(@Valid @RequestBody LoginUserDTO loginUser)
            throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getUserPassword()));
        final UserDetails userDetails = authService.loadUserByUsername(loginUser.getUserName());
        com.parcelapi.restapi.Models.UserDetails dbUser = userService.GetUserDetailsByName(loginUser.getUserName());
        final String token = jwtUtility.generateToken(userDetails);

        return new ResponseEntity<>(
                new ApiResponse<ViewLoginDTO>(true,
                        new ViewLoginDTO(dbUser.getUserId(), dbUser.getUserName(), dbUser.getUserType(), token, dbUser.getUserStatus())),
                HttpStatus.OK);
    }
}
