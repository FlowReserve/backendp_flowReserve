package com.flowreserve.demo1.controller.test;


import com.flowreserve.demo1.dto.authentication.AuthLoginRequest;
import com.flowreserve.demo1.dto.authentication.AuthReponse;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.service.user.UserDetailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthentificationController {

    private final UserDetailServiceImpl userDetailService;

    /**
     * Controller que se encarga de manejar el login de usuarios al sistema.
     * @param userRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthReponse>> loginUser(@RequestBody @Valid AuthLoginRequest userRequest) {
        AuthReponse authReponse = userDetailService.loginUser(userRequest);
        return ApiResponseDTO.success("Usuario logueado con éxito", authReponse, HttpStatus.OK);
    }

}
