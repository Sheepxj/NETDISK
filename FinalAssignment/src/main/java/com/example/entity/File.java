package com.example.entity;

import lombok.Data;
import lombok.ToString;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@ToString
public class File {

    Integer fileId;
    String fileName;
    Integer folderId;
    Timestamp createTime;
    Timestamp lastModifiedTime;
    String userName;
}
