package com.example;

import com.example.dao.FileDao;
import com.example.dao.FolderDao;
import com.example.entity.File;
import com.example.service.FileService;
import com.example.service.FolderService;
import com.example.service.impl.FileServiceImpl;
import com.example.service.impl.FolderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
class FinalAssignmentApplicationTests {
    @Autowired
    FolderService folderService = new FolderServiceImpl();
    @Autowired
    FolderDao folderDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService=new FileServiceImpl();

    @Test
    void contextLoads() {
        System.out.println(folderDao.findFolderNameByFolderId(1));
    }

    @Test
    void addFile(){
        fileDao.addFile("nothing",1,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"小金");
    }

    @Test
    void folderCreate() throws IOException {
        folderService.createFolder("J:\\Learning\\shixun\\netDiskStorge\\小金");
    }

    @Test
    void sqlTest(){
        List<File> files= fileDao.getNullByName("小石");
        System.out.println(files);
    }

    @Test
    void destroyFile(){
        // fileService.destroyFile("接口文档.docx","下载");
    }
}
