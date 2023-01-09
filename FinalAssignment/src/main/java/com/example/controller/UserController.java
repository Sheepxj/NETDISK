package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.service.FolderService;
import com.example.service.UserService;
import com.example.service.impl.FolderServiceImpl;
import com.example.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class UserController {
    //注入
    @Autowired
    UserService userService = new UserServiceImpl();
    @Autowired
    FolderService folderService = new FolderServiceImpl();

    /**
     * request 里面有两个参数 username和password
     * 登录
     */
    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject request) {
        //打印请求体
        log.info(String.valueOf(request));
        return userService.login(request);
    }

    /**
     * request 包含两个参数 username和password
     * 注册
     * 并创建存储文件的文件夹，该用户上传的所有文件会存储在这个文件夹里面
     */
    @PostMapping("/signup")
    public JSONObject signup(@RequestBody JSONObject request) throws IOException {
        JSONObject response = userService.addUser(request);
        //创建用户文件夹
        if (response.getBooleanValue("state")) {
            String path = response.getString("path");
            folderService.createFolder(path);
        }
        return response;
    }

}
