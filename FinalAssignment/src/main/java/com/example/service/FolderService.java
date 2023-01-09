package com.example.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FolderService {
    JSONObject addFolder(JSONObject request);

    JSONObject getAllByUserName(String userName);

    JSONObject getFolderContentByFolderId(Integer folderId);

    boolean createFolder(String folderPath) throws IOException;

    JSONObject deleteFolder(Integer folderId);

    JSONObject getAllNull(String username);
}
