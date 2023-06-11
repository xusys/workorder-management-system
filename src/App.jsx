import React from "react";
import "./App.css";

import { useRoutes } from 'react-router-dom'
import routes from './routes'

export default function App() {
  // 根据路由表生成对应路由规则
  const element = useRoutes(routes)

  return (
    <div className="App" style={{minHeight: '100vh'}}>
    {/* 注册路由 */}
    {element}
    </div>
  );
}