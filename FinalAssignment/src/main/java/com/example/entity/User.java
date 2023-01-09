package com.example.entity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User {
    Integer userId;
    String userName;
    String password;
    String baseUrl;
}
