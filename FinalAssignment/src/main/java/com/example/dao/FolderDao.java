package com.example.dao;

import com.example.entity.Folder;
import org.apache.ibatis.annotations.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface FolderDao {
    /**
     * 通过 user_name 查找所有文件夹
     */
    @Select("select folder_id as folderId, folder_name as folderName, create_time as createTime, last_modified_time as lastModifiedTime, parent_id as parentId, user_name as userName from netdisk.folder where user_name = #{userName}")
    List<Folder> findAllFoldersByUserName(String userName);
    /**
     * 通过 folder_id 查找文件夹
     */
    @Select("select folder_id as folderId, folder_name as folderName, create_time as createTime, last_modified_time as lastModifiedTime, parent_id as parentId from netdisk.folder where folder_id = #{folderId}")
    Folder findFolderByFolderId(Integer folderId);
    /**
     * 通过 parent_id 查找此文件夹下的所有文件夹名称
     */
    @Select("select folder_name as folderName from netdisk.folder where parent_id = #{parentId}")
    List<String> findFolderNameByFolderId(Integer parentId);
    /**
     * 通过 parent_id 查找文件夹
     */
    @Select("select folder_id as folderId, folder_name as folderName, create_time as createTime, last_modified_time as lastModifiedTime, parent_id as parentId from netdisk.folder where parent_id = #{parentId}")
    List<Folder> findFoldersByParentId(Integer parentId);
    /**
     * 新增 文件夹
     */
    @Insert("insert into netdisk.folder(folder_name, user_name, create_time, last_modified_time, parent_id) values (#{folderName},#{userName},#{createTime},#{lastModifiedTime},#{parentId})")
    Integer addFolder(String folderName, String userName, Timestamp createTime, Timestamp lastModifiedTime, Integer parentId);

    /**
     * 删除 文件夹
     * @param folderId
     * @return
     */
    @Delete("delete from netdisk.folder where folder_id = #{folderId}")
    Integer deleteFolder(Integer folderId);

    @Select("select folder_id as folderId, folder_name as folderName, create_time as createTime, last_modified_time as lastModifiedTime, parent_id as parentId, user_name as userName from netdisk.folder where user_name = #{userName} and parent_id is null")
    List<Folder> getNullByName(String userName);
}
