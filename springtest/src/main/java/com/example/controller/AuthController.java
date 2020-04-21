package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.Model.LoginRequest;
import com.example.Model.RegisterRequest;
import com.example.Model.UserTest;
import com.example.Service.AuthService;
import com.example.util.MiscUtil;
import com.example.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * login 登录
     * 
     * @param authRequest
     * @param bindingResult
     * @return ResponseEntity<Result>
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Result> login(@Valid @RequestBody LoginRequest authRequest, BindingResult bindingResult)
            throws AuthenticationException {

        if (bindingResult.hasErrors()) {
            Result res = MiscUtil.getValidateError(bindingResult);
            return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final String token = authService.login(authRequest.getAccount(), authRequest.getPassword());

        // Return the token
        Result res = new Result(200, "ok");
        res.putData("token", token);
        return ResponseEntity.ok(res);
    }

    /**
     * refresh 刷新token
     * 
     * @param request
     * @return ResponseEntity<Result>
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Result> refresh(HttpServletRequest request, @RequestParam String token)
            throws AuthenticationException {

        Result res = new Result(200, "ok");

        String refreshedToken = authService.refresh(token);

        if (refreshedToken == null) {
            res.setStatus(400);
            res.setMessage("无效token");
            return new ResponseEntity<Result>(res, HttpStatus.BAD_REQUEST);
        }

        res.putData("token", token);
        return ResponseEntity.ok(res);
    }

    // 注册用户
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Result> register(@Valid @RequestBody RegisterRequest register, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            Result res1 = MiscUtil.getValidateError(bindingResult);

            return new ResponseEntity<Result>(res1, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        UserTest userTest = new UserTest();
        userTest.setNickname(register.getNickname());
        userTest.setMobile(register.getMobile());
        userTest.setPassword(register.getPassword());

        userTest = authService.register(userTest);

        Result res = new Result(200, "ok");
        return ResponseEntity.ok(res);
    }
}