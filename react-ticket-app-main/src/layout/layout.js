import { Fragment, useState } from "react";
import { Layout, Dialog, Breadcrumb, Dropdown, Button } from "tdesign-react";
import { UserCircleIcon, ChevronDownIcon } from "tdesign-icons-react";
import {
  Link,
  useLocation,
  Outlet,
  useNavigate,
  Navigate,
} from "react-router-dom";
import MultiSide from "../aside/aside";
import DashbordSectionOne from "../dashbord/dashbordSectionOne/dashbordSectionOne";
import DashbordSectionTwo from "../dashbord/dashbordSectionTwo/dashbordSectionTwo";
import DashbordSectionThree from "../dashbord/dashbordSectionThree/dashbordSectionThree";
import TicketList from "../ticket/ticketList/ticketList";
import ApproveTable from "../system/approveTable";

import "./layout.css";
import api from "../api";

const { BreadcrumbItem } = Breadcrumb;

const { Header, Content, Footer, Aside } = Layout;

// 根据用户登录状态和权限，渲染工作台页面的基本布局，包括侧边栏、头部导航栏、内容区域和页脚
export function BasicDivider() {
  const user_id = window.sessionStorage.getItem("user_id");
  const token = window.localStorage.getItem("token");
  const user_info = window.sessionStorage.getItem("user_info");
  if (!user_id || !token || !user_info) {
    return <Navigate to="/login" />;
  } else {
    const name = JSON.parse(user_info).name;
    return (
      <Layout>
        <Aside width="auto" style={{ fontSize: 0 }}>
          <MultiSide />
        </Aside>
        <Layout className="ti-flex">
          <Header id="ti-header">
            <BreadcrumbTicket />
            <div className="ti-reference">
              <BasicDropdown name={name} />
            </div>
          </Header>
          <div className="ti-content-and-footer-wrapper">
            <Content style={{ padding: 24 }}>
              <Outlet />
            </Content>
            {/* <Footer>Copyright @ 2019-2021 Tencent. All Rights Reserved</Footer> */}
          </div>
        </Layout>
      </Layout>
    );
  }
}

// 根据当前页面的路径，生成面包屑导航的内容
const breadcrumbNameMap = {
  "/personalCenter":"个人中心",
  "/ticket/create": "工单申请",
  "/ticket/distribute": "分发工单",
  "/ticket/distribute/detail": "工单详情",
  "/ticket/all": "所有工单",
  "/ticket/all/detail": "工单详情",
  "/ticket/todo": "我的待办",
  "/ticket/todo/detail": "工单详情",
  "/ticket/related-to-me": "我相关的",
  "/ticket/related-to-me/detail": "工单详情",
  "/ticket/created-by-me": "我创建的",
  "/ticket/created-by-me/detail": "工单详情",
  "/admin/approve/employee": "员工审核",
};

function BreadcrumbTicket() {
  const location = useLocation();
  const pathSnippets = location.pathname.split("/").filter((i) => i);
  const extraBreadcrumbItems = pathSnippets.map((item, index) => {
    const url = `/${pathSnippets.slice(0, index + 1).join("/")}`;
    if (!breadcrumbNameMap[url]) return null;
    if (item === "detail")
      return (
        <BreadcrumbItem key={url}>{breadcrumbNameMap[url]}</BreadcrumbItem>
      );
    if (pathSnippets.length === index + 1)
      return (
        <BreadcrumbItem key={url}>{breadcrumbNameMap[url]}</BreadcrumbItem>
      );
    return (
      <BreadcrumbItem key={url}>
        <Link className="ti-ticket-link" to={url}>
          {breadcrumbNameMap[url]}
        </Link>
      </BreadcrumbItem>
    );
  });
  const breadcrumbItems = [
    <BreadcrumbItem key="/">
      <Link className="ti-ticket-link" to="/">
        首页
      </Link>
    </BreadcrumbItem>,
  ].concat(extraBreadcrumbItems);
  return <Breadcrumb>{breadcrumbItems}</Breadcrumb>;
}

// 渲染一个下拉菜单组件，根据传入的用户信息展示个人中心和退出登录选项
function BasicDropdown(props) {
  const navigate = useNavigate();
  const options = [
    {
      content: "个人中心",
      value: 1,
    },
    {
      content: "退出登录",
      value: 2,
    },
  ];
  const clickHandler = (data) => {
    if (data.value === 1) {
      // 执行跳转到个人中心页面的操作
      navigate("/personalCenter");
    } else if (data.value === 2) {
      // 执行弹出确认对话框并在确认后导航到登录页面的操作
      api.dialog.confirm({
        title: "系统消息",
        msg: "确定退出登录？",
        confirm: [
          () => {
            navigate("/login");
          },
        ],
      });
    }
  };

  return (
    <Dropdown options={options} onClick={clickHandler} trigger={"click"}>
      <Button variant="text">
        <span
          style={{
            display: "inline-flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <UserCircleIcon size="20px" />
          <span style={{ margin: "0 10px" }}>{props.name}</span>
          <ChevronDownIcon />
        </span>
      </Button>
    </Dropdown>
  );
}

// 渲染工作台页面的基本布局，包括三个区域
export function DashbordBase() {
  const user_id = window.sessionStorage.getItem("user_id");
  return (
    <Fragment>
      <DashbordSectionOne />
      <DashbordSectionTwo />
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <DashbordSectionThree />
        <DashbordSectionThree />
      </div>
    </Fragment>
  );
}

// 渲染工作台详情页面
export function DashbordDetails() {
  const user_id = window.sessionStorage.getItem("user_id");
  return <Fragment>DashbordDetails</Fragment>;
}

// 渲染一个警告提示框组件，根据传入的参数展示提示内容，并提供关闭和确认操作
function WarningAlert(props) {
  const navigate = useNavigate();
  const { show, content } = props;
  const [visibleWarning, setVisibleWarning] = useState(show);
  const onCloseWarning = () => {
    setVisibleWarning(false);
    navigate("/login");
  };
  return (
    <Dialog
      header="系统消息"
      body={content}
      cancelBtn={false}
      visible={visibleWarning}
      onClose={onCloseWarning}
      onConfirm={onCloseWarning}
    ></Dialog>
  );
}

// 渲染我的待办工单列表或显示重新登录警告，根据用户登录状态和权限
export function MyTodo() {
  const user_id = window.sessionStorage.getItem("user_id");
  let content;
  if (!user_id) {
    content = <WarningAlert show={true} content={"请重新登录"} />;
  } else {
    content = (
      <TicketList
        pageParmas={{
          type: 2,
          status: 1,
          current_handler: user_id,
        }}
        page="todo"
      />
    );
  }
  return content;
}

// 渲染我的提醒工单列表或显示重新登录警告
export function MyAlert() {
  const user_id = window.sessionStorage.getItem("user_id");
  let content;
  if (!user_id) {
    content = <WarningAlert show={true} content={"请重新登录"} />;
  } else {
    content = (
      <TicketList
        pageParmas={{
          type: 2,
          status: 1,
          current_handler: user_id,
        }}
        page="todo"
      />
    );
  }
  return content;
}

// 渲染与我相关的工单列表或显示重新登录警告
export function RelatedToMe() {
  const user_id = window.sessionStorage.getItem("user_id");
  let content;
  if (!user_id) {
    content = <WarningAlert show={true} content={"请重新登录"} />;
  } else {
    content = (
      <TicketList
        pageParmas={{
          type: 2,
          relative_staff: user_id,
        }}
      />
    );
  }
  return content;
}

// 渲染我创建的工单列表或显示重新登录警告
export function CreatedByMe() {
  const user_id = window.sessionStorage.getItem("user_id");
  let content;
  if (!user_id) {
    content = <WarningAlert show={true} content={"请重新登录"} />;
  } else {
    content = (
      <TicketList
        pageParmas={{
          type: 2,
          creator_id: user_id,
        }}
      />
    );
  }
  return content;
}

// 渲染所有工单列表
export function AllTickets() {
  const user_id = window.sessionStorage.getItem("user_id");
  
  return (
    <Fragment>
      <TicketList
        pageParmas={{
          type: 1,
        }}
      />
    </Fragment>
  );
}

// 渲染待分发工单列表或显示重新登录警告
export function Distribute() {
  const user_id = window.sessionStorage.getItem("user_id");
  let content;
  if (!user_id) {
    content = <WarningAlert show={true} content={"请重新登录"} />;
  } else {
    content = (
      <TicketList
        pageParmas={{
          type: 2,
          status: 0,
        }}
        page="distribute"
      />
    );
  }
  return content;
}

// 渲染员工审核页面或显示重新登录警告
export function EmployeeApprove() {
  const user_id = window.sessionStorage.getItem("user_id");
  let content;
  if (!user_id) {
    content = <WarningAlert show={true} content={"请重新登录"} />;
  } else {
    content = <ApproveTable></ApproveTable>;
  }
  return content;
}

// 渲染角色管理页面
export function RoleAdmin() {
  const user_id = window.sessionStorage.getItem("user_id");
  return <Fragment>RoleAdmin</Fragment>;
}

// 渲染职位管理页面
export function PositionAdmin() {
  const user_id = window.sessionStorage.getItem("user_id");
  return <Fragment>PositionAdmin</Fragment>;
}

// 渲染部门管理页面
export function DepartmentAdmin() {
  const user_id = window.sessionStorage.getItem("user_id");
  return <Fragment>DepartmentAdmin</Fragment>;
}

// 渲染个人中心页面
export function Personal() {
  const user_id = window.sessionStorage.getItem("user_id");
  return <Fragment>Personal</Fragment>;
}
