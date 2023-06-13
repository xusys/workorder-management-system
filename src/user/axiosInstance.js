import axios from "axios";
import { Navigate } from "react-router-dom";

const instance = axios.create({
  baseURL: "", // 修改为API基础URL
});

// 添加请求拦截器
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["token"] = `${token}`;
      console.log('token',token)
    } else {
      // 如果没有token，进行页面导航到登录页面
      Navigate("/login");
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 添加响应拦截器
instance.interceptors.response.use(
  (response) => {
    // 处理响应数据
    // const token = response.data.token;
    // window.localStorage.setItem("token", token);
    
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default instance;
