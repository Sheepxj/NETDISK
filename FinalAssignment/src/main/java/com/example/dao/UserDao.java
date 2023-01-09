package com.example.dao;

import com.example.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserDao {
    @Select("select user_name as userName, password, base_url as baseUrl from netdisk.user where user_name = #{userName}")
    User findUserByName(String userName);

    @Insert("insert into netdisk.user(user_name, password, base_url) VALUES (#{userName},#{password},#{baseUrl})")
    Integer addUser(String userName, String password, String baseUrl);

    @Select("select user_id as userId from netdisk.user where user_name =#{userName}")
    Integer findUserIdByName(String userName);
}
