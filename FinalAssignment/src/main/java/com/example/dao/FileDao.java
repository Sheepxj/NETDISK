package com.example.dao;

import com.example.entity.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface FileDao {
    /**
     * 查找指定folderId的文件夹下的文件
     *
     * @param folderId
     * @return
     */
    @Select("select file_id as fileId, file_name as fileName, create_time as createTime, last_modified_time as lastModifiedTime, folder_id as folderId, user_name as userName from netdisk.file where folder_id = #{folderId}")
    List<File> getFilesByFolderId(Integer folderId);

    /**
     * 按文件id查询文件
     *
     * @param fileId
     * @return
     */
    @Select("select file_id as fileId, file_name as fileName, create_time as createTime, last_modified_time as lastModifiedTime, folder_id as folderId, user_name as userName from netdisk.file where file_id = #{fileId}")
    File getFileById(Integer fileId);

    @Insert("insert into netdisk.file(file_name,folder_id,create_time,last_modified_time,user_name) VALUES (#{fileName},#{folderId},#{createTime},#{lastModifiedTime},#{userName})")
    Integer addFile(String fileName, Integer folderId, Timestamp createTime, Timestamp lastModifiedTime, String userName);

    @Delete("delete from netdisk.file where file_id = #{fileId}")
    Integer deleteFileById(Integer fileId);

    @Select("select file_id as fileId, file_name as fileName, create_time as createTime, last_modified_time as lastModifiedTime, folder_id as folderId, user_name as userName from netdisk.file where user_name = #{userName} and folder_id is null")
    List<File> getNullByName(String userName);
}
