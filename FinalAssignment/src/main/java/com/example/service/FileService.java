package com.example.service;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.File;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    List<File> getFileByFolderId(Integer id);

    JSONObject addFile(JSONObject request);

    JSONObject deleteFile(Integer fileId, String fileName, String userName);

    void downloadFile(HttpServletResponse response,String fileName,String userName) throws IOException;
}
