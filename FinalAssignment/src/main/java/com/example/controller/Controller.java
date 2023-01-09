package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.service.FileService;
import com.example.service.FolderService;
import com.example.service.UserService;
import com.example.service.impl.FileServiceImpl;
import com.example.service.impl.FolderServiceImpl;
import com.example.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@Slf4j
public class Controller {
    @Autowired
    UserService userService = new UserServiceImpl();
    @Autowired
    FolderService folderService = new FolderServiceImpl();
    @Autowired
    FileService fileService = new FileServiceImpl();

    /**
     * 查看所有文件和子文件夹
     *
     * @param userName 用户名
     */
    @GetMapping("/api/file")
    public JSONObject getAllFiles(@RequestParam("userName") String userName) {
        return folderService.getAllByUserName(userName);
    }

    /**
     * 查询用户的最高文件夹和文件 只有一个username
     * @param username
     * @return
     */
    @GetMapping("/api/folderFileGet")
    public JSONObject getAllNull(@RequestParam String username){
        return folderService.getAllNull(username);
    }

    /**
     * 查看该文件夹下的所有文件
     *
     * @param folderId 文件夹id
     */
    @GetMapping("/api/folder/{folderId}")
    public JSONObject getFolderContentByFolderId(@PathVariable("folderId") Integer folderId) {
        return folderService.getFolderContentByFolderId(folderId);
    }

    /**
     * 新增文件夹
     *
     * @param request 请求体包括 userName folderName parentId createTime
     */
    @PostMapping("/api/folder")
    public JSONObject addFolder(@RequestBody JSONObject request) {
        return folderService.addFolder(request);
    }

    /**
     * 删除文件夹 如果下方没有文件则删除，有文件禁止删除
     * @param folderId
     * @return
     */
    @PostMapping("/api/folder/delete/{folderId}")
    public JSONObject deleteFolder(@PathVariable Integer folderId){
        return folderService.deleteFolder(folderId);
    }
}
