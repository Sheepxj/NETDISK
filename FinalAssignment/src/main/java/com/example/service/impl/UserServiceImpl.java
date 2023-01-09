package com.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final static String URL = "J:\\Learning\\shixun\\netDiskStorge";//J:\Learning\shixun\netDiskStorge
    @Autowired
    UserDao userDao;

    /**
     * 登录
     * @param request 请求体
     * */
    @Override
    public JSONObject login(JSONObject request){
        JSONObject response = new JSONObject();
        String userName = request.getString("userName");
        User user = userDao.findUserByName(userName);
        if (user != null){
            String password = request.getString("password");
            if (user.getPassword().equals(password)){
                Map<String,String> payload = new HashMap<>(1);
                payload.put("userName",userName);
                String token = JWTUtils.getToken(payload);
                response.put("token", token);
                response.put("state", true);
                response.put("msg","登录成功!");
            } else {
                response.put("state", false);
                response.put("msg","密码错误!");
            }
        } else {
            response.put("state", false);
            response.put("msg","不存在该用户!");
        }
        return response;
    }

    /**
     * 注册
     * @param request 请求体
     * */
    @Override
    public JSONObject addUser(JSONObject request){
        JSONObject response = new JSONObject();
        String userName = request.getString("userName");
        if (userDao.findUserByName(userName) == null){
            String password = request.getString("password");
            if (password == null || "".equals(password)){
                password = "123456";//默认密码
            }
            String baseUrl = URL.concat("\\"+userName);
            Integer col = userDao.addUser(userName, password, baseUrl);
            response.put("state", col == 1);
            Map<String,String> payload = new HashMap<>(1);
            payload.put("userName",userName);
            String token = JWTUtils.getToken(payload);
            response.put("token", token);
            response.put("path", baseUrl);
        } else {
            response.put("state", false);
        }
        return response;
    }

    @Override
    public Integer findIdByName(String name) {
        return userDao.findUserIdByName(name);
    }

}
