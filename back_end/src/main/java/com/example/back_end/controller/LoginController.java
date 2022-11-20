package com.example.back_end.controller;

import com.example.back_end.common.Result;
import com.example.back_end.entity.User;
import com.example.back_end.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "SwaggerController")
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/hello")
    public String toHello(){
        return "Hello, world";
    }

    /**
     * 登录接口
     * @param user
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "test1 方法", notes = "hello Swagger测试方法--login")
    public Result login(@RequestBody User user){
//        System.out.println("尝试登录");
        return loginService.login(user);
    }

    @GetMapping("")
    public Result logout(){
        return loginService.logout();
    }


}
