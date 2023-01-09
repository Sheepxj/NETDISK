<template>
  <div class="common-layout" style="height: 100%">
    <el-container style="height: 100%">
      <el-header class="border-b-solid border-b-1 border-opacity-0 bg-white h-20 flex " height="85px"
                 style="border-color: #dcdfe6;padding: 0">
        <div class="w-63 flex justify-center self-center">
          <el-icon :size="50" class="flex flex-1">
            <MessageBox/>
          </el-icon>
        </div>
        <div class="flex p-l-130">
          <p class="text-4xl flex flex-wrap justify-center content-center items-center"
             style="color: #fecaca;letter-spacing: 0.16em;text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;">
            在线网盘</p>
        </div>
      </el-header>
      <el-container>
        <el-aside width="250px">
          <el-menu
              default-active=""
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
              style="height: 100%"
          >
            <el-menu-item index="1">
              <el-icon>
                <icon-menu/>
              </el-icon>
              <span style="font-size: 18px">我的文件</span>
            </el-menu-item>
            <el-menu-item index="2">
              <el-icon>
                <location/>
              </el-icon>
              <span style="font-size: 18px">我的收藏</span>
            </el-menu-item>
            <el-menu-item index="3">
              <el-icon>
                <document/>
              </el-icon>
              <span style="font-size: 18px">我的分享</span>
            </el-menu-item>
            <el-menu-item index="4">
              <el-icon>
                <setting/>
              </el-icon>
              <span style="font-size: 18px">我的网盘</span>
            </el-menu-item>
          </el-menu>

        </el-aside>

        <el-main class="flex">
          <!--          <router-view></router-view>-->
          <div class="flex">
            <div class="w-full h-full">
              <!--      增加和删除按钮以及面包屑-->
              <div class="bg-white flex justify-start content-center flex-wrap m-b-2 m-l-2">
                <el-button type="primary" :icon="Plus" circle @click="dialogVisible=true"/>
                <el-popconfirm title="确定移除该文件?">
                  <template #reference>
                    <el-button type="danger" :icon="Delete" circle/>
                  </template>
                </el-popconfirm>
                <el-button type="info" round @click="download">下载</el-button>
              </div>
              <el-scrollbar height="580px">
                <el-table
                    :data="tableData"
                    style="width: 100%"
                    class="my_table"
                    :row-style="{height: '60px'}"
                    :cell-style="{padding: '0px'}"
                >
                  <el-table-column type="selection" width="55"/>
                  <el-table-column prop="Name" label="文件名" width="180"/>
                  <el-table-column prop="createTime" label="上传时间"/>
                  <el-table-column prop="lastModifiedTime" label="上次修改"/>
                  <el-table-column prop="userName" label="上传者" width="90"/>
                </el-table>
              </el-scrollbar>
            </div>
          </div>
          <el-dialog
              v-model="dialogVisible"
              title="上传你的文件"
              width="30%"
          >
            <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple
                :auto-upload="false"
                :file-list="fileList"
                :on-change="handleChange"
                :http-request="fileUpload"
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text">
                将文件拖拽到此处或者 <em>点击上传</em>
              </div>
              <template #tip>
                <div class="el-upload__tip">
                  请上传不超过200MB的文件
                </div>
              </template>
            </el-upload>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="upload();dialogVisible = false">
                  上传
                </el-button>
              </span>
            </template>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import {useRouter} from "vue-router";
import appMain from "/src/components/main/index.vue"
import service from "/src/api/request.js"
import {ref, onBeforeMount, reactive} from "vue";
import {ElMessage, ElTable} from 'element-plus'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
  Delete,
  Plus
} from '@element-plus/icons-vue'
import { UploadFilled } from '@element-plus/icons-vue'
import * as path from "path";

const dialogVisible = ref(false)
const fileList=reactive([])
function handleChange(file,fileList) { //文件数量改变
  console.log(file);
  console.log(fileList)
}
let file={}
function fileUpload(item){
  this.file=item.file
}
const upload=() =>{ //确认上传
  let param = new FormData();
  param.append("file",this.file)
  param.append("userName",window.localStorage.getItem("username"));

  service.post(
      `/api/file/${1}`,
      param
  ).then(res => {
    console.log(res)
  });
}

interface Files {
  Name: String
  createTime: Date
  lastModifiedTime: Date
  userName: String
}
const tableData = reactive([])

//解决this.$router使用不了的问题
const router = useRouter()

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

onBeforeMount(() => {
  showFolder()
})

const showFolder = () => {
  service.get(
      'api/folderFileGet',
      {
        params: {
          username: window.localStorage.getItem("username")
        }
      }
  ).then((res) => {
    console.log(res);
    const arr = [...res.data.files, ...res.data.folders]
    let file = reactive({});
    for (file of arr) {
      if (file["fileId"]) {
        delete file["fileId"]
        delete file["folderId"]
        let temp = file["fileName"]
        delete file["fileName"]
        file["Name"] = temp
      } else {
        delete file["folderId"]
        delete file["parentId"]
        let temp = file["folderName"]
        delete file["folderName"]
        file["Name"] = temp
      }
      const tdata = {
        Name: file["Name"],
        createTime: file["createTime"],
        lastModifiedTime: file["lastModifiedTime"],
        userName: file["userName"]
      }
      tableData.push(tdata)
    }
    console.log(tableData);
  }).catch((err) => {
    console.log(err);
  })
}
const download=()=>{
  service.post(
      '/api/file/download',
      {
        params:{
          fileName:"A.txt",
          userName:window.localStorage.getItem("username")
        }
      }
  ).then((res)=>{
    console.log(res);
    ElMessage({
      message: "下载成功",
      type: "success"
    })
  }).catch((err)=>{
    console.log(err);
    ElMessage({
      message: "下载失败",
      type: "danger"
    })
  })
}

</script>

<style scoped>

</style>
