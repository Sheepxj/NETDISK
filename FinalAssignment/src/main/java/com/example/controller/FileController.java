package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.service.FileService;
import com.example.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;

@RestController
public class FileController {

    @Autowired
    FileService fileService = new FileServiceImpl();

    /**
     * 新增文件，需要向数据库中添加一条记录 并将文件保存到本地路径下
     * 有三个参数 一个文件 一个文件夹id 一个用户名
     * @param file
     * @param folderId
     * @param userName
     * @return
     * @throws IOException
     */
    @PostMapping("/api/file/{folderId}")
    public JSONObject addFile(@RequestParam("file") MultipartFile file, @PathVariable("folderId") Integer folderId, @RequestParam String userName) throws IOException {
        //服务器端接收文件
        // 获取文件名，包含后缀
        String fileName = file.getOriginalFilename();
        // 保存路径
        String saveFilePath = "J:\\Learning\\shixun\\netDiskStorge\\";//根路径
        // 保存文件
        File saveFile = new File(saveFilePath + userName + "\\" + fileName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject request = new JSONObject();
        request.put("fileName", fileName);
        request.put("folderId", folderId);
        request.put("createTime", new Timestamp(System.currentTimeMillis()));
        request.put("lastModifiedTime", new Timestamp(System.currentTimeMillis()));
        request.put("userName", userName);//TODO 用户名动态
        //数据库中新建数据
        JSONObject a = fileService.addFile(request);
        return a;
    }

    /**
     * 删除某个文件，从文件夹和本地一起删除
     *
     * @param fileId
     * @param fileName
     * @param userName
     * @return
     */
    @PostMapping("/api/file/delete")
    public JSONObject deleteFile(@RequestParam Integer fileId, @RequestParam String fileName, @RequestParam String userName) {
        return fileService.deleteFile(fileId,fileName,userName);
    }

    /**
     * 将文件提供给前端下载 有两个参数 文件名和用户名
     * @param response
     * @param fileName
     * @param userName
     * @return
     * @throws IOException
     */
    @GetMapping("/api/file/download")
    public JSONObject download(HttpServletResponse response, @RequestParam String fileName, @RequestParam String userName) throws IOException {
        fileService.downloadFile(response,fileName,userName);
        return null;
    }
}
