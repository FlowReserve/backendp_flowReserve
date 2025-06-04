package com.flowreserve.demo1.controller.test;


import com.flowreserve.demo1.dto.authentication.AuthLoginRequest;
import com.flowreserve.demo1.dto.authentication.AuthReponse;
import com.flowreserve.demo1.service.user.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {

@Autowired
    private UserDetailServiceImpl userDetailService;
@PostMapping("log-in")
    public ResponseEntity <AuthReponse>login(@RequestBody @Valid AuthLoginRequest userRequest){
    return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

}
