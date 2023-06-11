import { Form, Input, Button } from "tdesign-react";
import { DesktopIcon, LockOnIcon, CheckCircleIcon } from "tdesign-icons-react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Register from "../user/register";
import React, { useRef, useState, forwardRef } from "react";
import "./user.css";
import api from "../api";
import "../mock/index";
const { FormItem } = Form;

export const Login = function () {
  window.localStorage.removeItem("token");
  window.sessionStorage.removeItem("user_id");
  window.sessionStorage.removeItem("user_info");

  window.localStorage.setItem("token", "mock-token");
  window.sessionStorage.setItem("user_id", "mock-user-id");
  window.sessionStorage.setItem(
    "user_info",
    JSON.stringify({ username: "mock-username" })
  );

  return (
    <div className="ti-login-wrapper">
      <LoginContent></LoginContent>
    </div>
  );
};

export const Status = function () {
  return (
    <div className="ti-login-wrapper">
      <div className="ti-status-content">
        <CheckCircleIcon
          size="76px"
          style={{ color: "var(--td-success-color)" }}
        />
        <p>你的信息已提交</p>
        <p>审核中，请耐心等待</p>
      </div>
    </div>
  );
};

function LoginContent() {
  const [select, setSelect] = useState(0);
  const formRef = useRef();
  const navigate = useNavigate();
  const tabList = [
    {
      id: 0,
      item: "登录",
    },
    {
      id: 1,
      item: "注册",
    },
  ];
  // form规则
  const rules = {
    username: [
      { required: true, message: "必填", type: "error" },
      { min: 4, message: "用户名不少于4个字符", type: "error" },
    ],
    password: [
      { required: true, message: "必填", type: "error" },
      { min: 6, message: "密码不少于6个字符", type: "error" },
    ],
  };

  const onSubmit = (e) => {
    if (e.validateResult === true) {
      let params = formRef.current.getFieldsValue(true);
      // api
      //   .post("/user/login", params)
      //   .then((data) => {
      //     api.message.success("登录成功");
      //     if (data.data.user_info && data.data.user_info.job_num) {

      //       window.localStorage.setItem("token", `bearer ${data.data.token}`);
      //       window.sessionStorage.setItem(
      //         "user_id",
      //         `${data.data.user_info.user_id}`
      //       );
      //       window.sessionStorage.setItem(
      //         "user_info",
      //         `${JSON.stringify(data.data.user_info)}`
      //       );
      //       navigate("/");
      //     } else {
      //       if (data.data.user_id) {
      //         window.localStorage.setItem("token", `bearer ${data.data.token}`);
      //         window.sessionStorage.setItem("user_id", `${data.data.user_id}`);
      //         navigate("/bind");
      //       } else {
      //         console.log("审核中");
      //         navigate("/status");
      //       }
      //     }
      //   })
      //   .catch((err) => {
      //     if (err.code === 1001) {
      //       api.message.error("账号或密码错误");
      //     } else if (err.code === 1002) {
      //       api.message.error("对不起，您没有管理站权限");
      //     } else {
      //       api.message.error("登录失败");
      //     }
      //   });
      axios
        .get("/api/v1/dataSource") //接口地址与拦截地址要一致
        .then((res) => {
          // console.log('res', res)
          navigate("/");
        });
    }
  };

  let content;
  const login = (
    <Form
      ref={formRef}
      rules={rules}
      className="ti-login-form"
      onSubmit={onSubmit}
      colon={true}
      labelWidth={0}
    >
      <FormItem name="username" className="ti-login-input-wrapper">
        <Input
          className="ti-login-input"
          size="large"
          clearable={true}
          prefixIcon={<DesktopIcon />}
          placeholder="请输入用户名"
        />
      </FormItem>
      <FormItem name="password" className="ti-login-input-wrapper">
        <Input
          className="ti-login-input"
          size="large"
          type="password"
          prefixIcon={<LockOnIcon />}
          clearable
          placeholder="请输入密码"
        />
      </FormItem>
      <FormItem statusIcon={false}>
        <Button size="large" theme="primary" type="submit" block>
          登录
        </Button>
      </FormItem>
    </Form>
  );

  const register = (
    <Register
      changeTab={(select) => {
        changeTab(select);
      }}
    />
  );

  if (select === 0) {
    content = login;
  } else if (select === 1) {
    content = register;
  }

  function changeTab(e) {
    setSelect(e);
  }

  return (
    <div className="ti-login-content">
      <Tab
        value={select}
        options={tabList}
        onTabChange={(e) => {
          changeTab(e);
        }}
      />
      {content}
      <div className="ti-admin-bind-area">
        <span
          onClick={() => {
            navigate("/admin-bind");
          }}
        >{`添加管理员 >`}</span>
      </div>
    </div>
  );
}

const Tab = (props) => {
  const tabGroup = props.options.map((i, index) => {
    let classList = "ti-tab-login";
    if (props.value === index) {
      classList += " ti-tab-login-active";
    }
    return (
      <div
        key={i.id}
        className={classList}
        onClick={() => {
          props.onTabChange(index);
        }}
      >
        {i.item}
      </div>
    );
  });
  return <div className="ti-login-tab">{tabGroup}</div>;
};