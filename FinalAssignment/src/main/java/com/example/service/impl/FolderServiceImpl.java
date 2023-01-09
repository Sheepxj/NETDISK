package com.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.FileDao;
import com.example.dao.FolderDao;
import com.example.dao.UserDao;
import com.example.entity.File;
import com.example.entity.Folder;
import com.example.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class FolderServiceImpl implements FolderService {
    @Autowired
    FolderDao folderDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;

    /**
     * 新建文件夹
     *
     * @param request
     * @return
     */
    @Override
    public JSONObject addFolder(JSONObject request) {
        JSONObject response = new JSONObject();
        String userName = request.getString("userName");
        String folderName = request.getString("folderName");
        Integer parentId = request.getInteger("parentId");
        Timestamp createTime = request.getTimestamp("createTime");
        List<String> list = folderDao.findFolderNameByFolderId(parentId);
        for (String s : list) {
            if (s.equals(folderName)) {
                System.out.println(s);
                response.put("state", false);
                response.put("msg", "该文件夹已存在!");
                return response;
            }
        }
        folderDao.addFolder(folderName, userName, createTime, createTime, parentId);
        response.put("state", true);
        response.put("msg", "成功创建文件夹!");
        return response;
    }

    // 作者name获取所有 文件夹 和 文件
    @Override
    public JSONObject getAllByUserName(String userName) {
        List<Folder> folderList = folderDao.findAllFoldersByUserName(userName);
        System.out.println(folderList);
        JSONObject response = new JSONObject();
        JSONArray folders = new JSONArray();
        for (Folder f : folderList) {
            if (f.getParentId() == null) {
                folders.add(getAllFiles(f.getFolderId()));
            }
        }
        response.put("root", folders);
        return response;
    }

    // 获取该文件夹下的 文件 和 文件夹
    @Override
    public JSONObject getFolderContentByFolderId(Integer folderId) {
        JSONObject response = new JSONObject();
        List<Folder> folders = folderDao.findFoldersByParentId(folderId);
        JSONArray folderArray = new JSONArray(folders);
        response.put("childrenFolders", folderArray);
        Folder folder = folderDao.findFolderByFolderId(folderId);
        response.put("folderName", folder.getFolderName());
        response.put("createTime", folder.getCreateTime());
        response.put("lastModifiedTime", folder.getLastModifiedTime());
        List<File> files = fileDao.getFilesByFolderId(folderId);
        JSONArray fileArray = new JSONArray(files);
        response.put("files", fileArray);
        return response;
    }

    @Override
    public boolean createFolder(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        Path pathCreate = Files.createDirectory(path);
        return true;
    }

    @Override
    public JSONObject deleteFolder(Integer folderId) {
        JSONObject response = new JSONObject();
        Integer col = folderDao.deleteFolder(folderId);
        if (col == 1) {
            response.put("state", true);
            response.put("msg", "删除文件夹成功");
        } else {
            response.put("state", false);
            response.put("msg", "删除文件夹失败");
        }
        return response;
    }

    @Override
    public JSONObject getAllNull(String username) {
        JSONObject response = new JSONObject();
        List<Folder> folders= folderDao.getNullByName(username);
        List<File> files =fileDao.getNullByName(username);
        JSONArray folderArray = new JSONArray(folders);
        JSONArray fileArray=new JSONArray(files);
        response.put("folders",folderArray);
        response.put("files",fileArray);
        response.put("state",true);
        return response;
    }

    // 获取所有的 文件夹 以及 文件
    public JSONObject getAllFiles(Integer folderId) {
        List<Folder> folders = folderDao.findFoldersByParentId(folderId);
        Folder folder = folderDao.findFolderByFolderId(folderId);
        JSONObject response = new JSONObject();
        response.put("folderName", folder.getFolderName());
        response.put("createTime", folder.getCreateTime());
        response.put("lastModifiedTime", folder.getLastModifiedTime());
        List<File> files = fileDao.getFilesByFolderId(folderId);
        JSONArray fileArray = new JSONArray(files);
        response.put("files", fileArray);
        if (folders.size() != 0) {
            JSONArray childrenFolders = new JSONArray();
            for (Folder value : folders) {
                JSONObject item = getAllFiles(value.getFolderId());
                childrenFolders.add(item);
            }
            response.put("childrenFolders", childrenFolders);
        }
        return response;
    }
}
