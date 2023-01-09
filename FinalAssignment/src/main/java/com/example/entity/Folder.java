package com.example.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Folder {
    String folderName;
    Integer folderId;
    Timestamp createTime;
    Timestamp lastModifiedTime;
    Integer parentId;
    String userName;
}
