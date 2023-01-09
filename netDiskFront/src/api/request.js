//axios基础配置
import axios from "axios"

const service = axios.create({
    baseURL: "http://localhost:8300",
    timeout: 5000
})

//请求拦截处理
const requestInterceptor = config => {
    if (localStorage.getItem('token')) {
        config.headers.Authorization = localStorage.getItem('token');
    }
    return config;
}

// 请求错误处理
const requestErrorHandler = error => {
    return Promise.reject(error);
}

// 响应拦截处理
const responseInterceptor = response => {
    return response;
}

// 响应错误处理
const responseErrorHandler = error => {
    return Promise.reject(error);
}

service.interceptors.request.use(requestInterceptor,requestErrorHandler)
service.interceptors.response.use(responseInterceptor,responseErrorHandler)

export default service
