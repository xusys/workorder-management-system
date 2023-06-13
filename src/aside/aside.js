import React, { Fragment, useEffect, useState } from 'react';
import { Menu,Badge } from 'tdesign-react';
import { AppIcon, ViewModuleIcon, DashboardIcon, SettingIcon, ViewListIcon } from 'tdesign-icons-react';
import { useNavigate, useLocation } from "react-router-dom";
import './aside.css' 
// import api from '../api';

const { SubMenu, MenuItem } = Menu;

// 根据路径获取对应的菜单值
function getMenuValue(location) {
    // 定义路径与菜单值的映射关系，高亮效果
    let valueList = {
      '/': '0-1',
      '/dashbord/base': '0-1',
      '/personalCenter': '0-3',
      // '/dashbord/details': '0-2',
      '/ticket/create': '1-1',
      '/ticket/todo': '1-2',
      '/ticket/created-by-me': '1-3',
      '/ticket/related-to-me': '1-4',
      '/ticket/all': '1-5',   
      '/ticket/distribute': '1-6',
      '/ticket/alert': '1-7',
      '/ticket/todo/detail': '1-2',    
      '/ticket/created-by-me/detail': '1-3',
      '/ticket/related-to-me/detail': '1-4',
      '/ticket/distribute/detail': '1-6',
      '/ticket/all/detail': '1-5',
      '/admin/approve/employee': '2-1',
      // '/admin/role': '2-2',
      // '/admin/position': '2-3',
      // '/admin/department': '2-4',
      // '/personal': '3'
    };
    return valueList[location];
  }
  
  // 根据路径调整为目标路径
  function changeLocation(location) {
    let valueList = [
      '/ticket/todo/detail',
      '/ticket/created-by-me/detail',
      '/ticket/related-to-me/detail',
      '/ticket/all/detail',
      '/ticket/distribute/detail'
    ];
    for (let i = 0; i < valueList.length; i++) {
      if (location.indexOf(valueList[i]) > -1) {
        return valueList[i];
      }  
    }
  }

// 主要的组件函数，用于渲染侧边栏菜单和处理相关逻辑
// 在组件中，使用了Menu、SubMenu和MenuItem等组件来
// 构建菜单的结构，并使用条件渲染根据权限显示不同的菜单项
  function MultiSide(props) {
    let location = useLocation();
    let pathname = location.pathname;
    // 如果路径包含 'detail'，则将路径转换为目标路径
    if (pathname.indexOf('detail') > -1) {
      pathname = changeLocation(pathname);
    }
    let reflashValue = getMenuValue(pathname);
    
    // 侧边栏工具开启
    const [active, setActive] = useState('0-1');
    const [collapsed, setCollapsed] = useState(true);
    const [expands, setExpands] = useState(['0','1']);
    const [showAdmin, setShowAdmin] = useState(false);
    const [showDistribute, setShowDistribute] = useState(false);
    const [logoContent, setLogoContent] = useState('工单系统');
  
    // 在组件渲染时，获取显示管理菜单和分发工单菜单的状态
    // useEffect(() => {
    //   api.get('/admin/v1/system/menu', { user_id: window.sessionStorage.getItem('user_id') }).then((e) => {
    //     setShowAdmin(e.data.showAdmin);
    //     setShowDistribute(e.data.showDistribute);
    //   });
    // }, []);
  
    // 当目标菜单值改变时，更新激活的菜单项
    useEffect(() => {
      setActive(reflashValue);
    }, [reflashValue]);
  
    // 获取COS桶信息
    // useEffect(() => {
    //   api.get('/tx/v1/cos/bucketInfo').then((e) => {
    //     window.sessionStorage.setItem('Bucket', e.data?.bucket);
    //     window.sessionStorage.setItem('Region', e.data?.region);
    //   });
    // }, []);
  
    // 切换侧边栏收缩状态和LOGO内容
    const clickOper = function() {
      if (!collapsed) {
        setLogoContent(<AppIcon />);
      } else {
        setLogoContent('工单系统');
      }
      setCollapsed(!collapsed);
    };
  
    const navigate = useNavigate();
  
    return (
      <Fragment>
        <Menu
          className='ti-aside-nav'
          value={active}
          collapsed={collapsed}
          logo={logoContent}
          expanded={expands}
          onExpand={(values) => setExpands(values)}
          onChange={(v) => setActive(v)}
          operations={<ViewListIcon className="t-menu__operations-icon" onClick={clickOper} />}
        >
          <SubMenu value="0" title={<span>统计报表</span>} icon={<DashboardIcon />}>
            <MenuItem value="0-1" onClick={() => {navigate("/dashbord/base");}}>工单统计</MenuItem>
            {/* <MenuItem value="0-2" onClick={() => {navigate("/dashbord/details");}}>统计报表</MenuItem> */}
          </SubMenu>
          <SubMenu value="1" title={<span>工单系统</span>} icon={<ViewModuleIcon />}>
            <MenuItem value="1-1" onClick={() => {navigate("/ticket/create");}}>
              <span>工单申请</span>
            </MenuItem>
            <MenuItem value="1-2" onClick={() => {navigate("/ticket/todo");}}>
              <span>我的待办</span>
            </MenuItem>
            <MenuItem value="1-3" onClick={() => {navigate("/ticket/created-by-me");}}>
              <span>我创建的</span>
            </MenuItem>
            <MenuItem value="1-4" onClick={() => {navigate("/ticket/related-to-me");}}>
              <span>我历史的</span>
            </MenuItem>
            <MenuItem value="1-5" onClick={() => {navigate("/ticket/all");}}>
              <span>所有工单</span>
            </MenuItem>
            <MenuItem value="1-7" onClick={() => {navigate("/ticket/alert");}}>
              <span className='span-margin'>我的预警</span>
              {/*<Badge count={alert_count} color="red" />*/}
            </MenuItem>
            {
              showDistribute ?
              <MenuItem value="1-6" onClick={() => {navigate("/ticket/distribute");}}>
                <span>分发工单</span>
              </MenuItem> : <></>
            }
          </SubMenu>
          {
            showAdmin ? 
            <SubMenu value="2" title={<span>系统管理</span>} icon={<SettingIcon />}>
              <MenuItem value="2-1" onClick={() => {navigate("/admin/approve/employee");}}>
                <span>员工审核</span>
              </MenuItem>
              {/* <MenuItem value="2-2" onClick={() => {navigate("/admin/role");}}>
                <span>角色管理</span>
              </MenuItem>
              <MenuItem value="2-3" onClick={() => {navigate("/admin/position");}}>
                <span>岗位管理</span>
              </MenuItem>
              <MenuItem value="2-4" onClick={() => {navigate("/admin/department");}}>
                <span>部门管理</span>
              </MenuItem> */}
            </SubMenu> : ''
          }
        </Menu>
      </Fragment>
    );
  }
  
  export default MultiSide;  