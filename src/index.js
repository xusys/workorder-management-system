import React from 'react';
import './index.css';
import reportWebVitals from './reportWebVitals';
import App from './App'
import { history, HistoryRouter } from './router/history.js';
import 'tdesign-react/es/style/index.css'; // 少量公共样式
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";

const root = createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);

// ReactDOM.render(
//   <React.StrictMode>
//     <HistoryRouter history={history}>
//       <App />
//     </HistoryRouter>
//   </React.StrictMode>,
//   document.getElementById('root')
// );

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();


