import React, {useLayoutEffect, useState} from 'react';
import { createBrowserHistory } from 'history';
import { Router } from 'react-router-dom';

// 创建一个浏览器历史记录对象
export const history = createBrowserHistory();

// 路由管理和导航功能，用于处理路由相关的操作并渲染子组件
export const HistoryRouter = ({ basename, children, history }) => {
  const [state, setState] = useState({
    action: history.action,
    location: history.location
  });

  useLayoutEffect(() => history.listen(setState), [history]);

  return (
    <Router
      basename={basename}
      children={children}
      location={state.location}
      navigationType={state.action}
      navigator={history}
    />
  );
}