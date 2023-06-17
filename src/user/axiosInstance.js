import axios from "axios";
// import { Navigate } from "react-router-dom";

const instance = axios.create({
  baseURL: "http://localhost:8080", // 修改为API基础URL
});

// 添加请求拦截器
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["token"] = `${token}`;
      console.log('token',token)
    } else {
      // 如果没有token，抛出一个特定的错误
      throw new Error("No token available");
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
    console.log('响应拦截器',response);
    if (response.data.code === 0){
      // 如果没有token，抛出一个特定的错误
      throw new Error("Token expired");
    }
    // 处理响应数据
    // const token = response.data.token;
    // window.localStorage.setItem("token", token);
    
    return response;
  },
  (error) => {
    console.log('响应拦截器error',error);
    // 处理其他错误，抛出一个特定的错误
    // throw new Error("Network error");
    // alert('请重新登录！');
    window.location.href = '/login';
  }
);

export default instance;
