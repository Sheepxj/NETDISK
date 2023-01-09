package com.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.FileDao;
import com.example.entity.File;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private final static String URL = "J:\\Learning\\shixun\\netDiskStorge\\";//根路径
    @Autowired
    FileDao fileDao;

    /**
     * 按文件夹id获取文件夹下所有文件 参数id是文件夹id
     *
     * @param id
     * @return
     */
    @Override
    public List<File> getFileByFolderId(Integer id) {
        List<File> fileList;
        fileList = fileDao.getFilesByFolderId(id);
        return fileList;
    }

    /**
     * 新增文件
     * @param request
     * @return
     */
    @Override
    public JSONObject addFile(JSONObject request) {
        JSONObject response = new JSONObject();
        String fileName = request.getString("fileName");
        Integer folderId = request.getInteger("folderId");
        Timestamp createTime = request.getTimestamp("createTime");
        Timestamp lastModifiedTime = request.getTimestamp("lastModifiedTime");
        String userName = request.getString("userName");
        Integer col = fileDao.addFile(fileName, folderId, createTime, lastModifiedTime, userName);
        if (col == 1) {
            response.put("state", true);
            response.put("msg", "新增文件成功");
        } else {
            response.put("state", false);
            response.put("msg", "新增文件失败");
        }
        return response;
    }

    /**
     * 删除文件
     * @param fileId
     * @param fileName
     * @param userName
     * @return
     */
    @Override
    public JSONObject deleteFile(Integer fileId, String fileName, String userName) {
        JSONObject response = new JSONObject();
        File file = fileDao.getFileById(fileId);
        String username = file.getUserName();//获取文件名
        if (userName == null) {
            userName = username;
        }
        //删除数据库中数据
        Integer col = fileDao.deleteFileById(fileId);
        if (col == 1) {
            //删除本地文件
            destroyFile(fileName, userName);
            response.put("state", true);
            response.put("msg", "删除文件成功");
        } else {
            response.put("state", false);
            response.put("msg", "删除文件失败");
        }
        return response;
    }

    /**
     * 下载文件
     * @param response
     * @param fileName
     * @param userName
     * @throws IOException
     */
    @Override
    public void downloadFile(HttpServletResponse response, String fileName, String userName) throws IOException {
        //文件路径
        String path = URL+userName+"\\"+fileName;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new java.io.File(path)));
        //转码，免得文件名中文乱码
        path = URLEncoder.encode(path,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        //设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 创建输出流
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        // 关闭输出流
        out.close();
    }

    public boolean destroyFile(String fileName, String userName) {
        java.io.File file = new java.io.File(URL + userName + "\\" + fileName);
        if (file.delete()) {
            System.out.println(file.getName() + " 文件已被删除！");
            return true;
        } else {
            System.out.println("文件删除失败！");
            return false;
        }
    }
}
