package com.example.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    JSONObject login(JSONObject request);

    JSONObject addUser(JSONObject request);

    Integer findIdByName(String name);
}
