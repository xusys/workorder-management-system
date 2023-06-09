import {Login, Status} from '../user/login'
import Bind from '../user/bind'
import AdminBind from '../user/adminBind'
import {
  BasicDivider, 
  DashbordBase,
  DashbordDetails,
  MyTodo,
  RelatedToMe,
  CreatedByMe,
  AllTickets,
  Distribute,
  EmployeeApprove,
  RoleAdmin, 
  PositionAdmin,
  DepartmentAdmin,
  Personal
} from '../layout/layout';
import TicketDetail from '../ticket/ticketDetail/ticketDetail';
import CreateTicket from '../ticket/createTicket/createTicket'
import DistributeTicket from '../ticket/ticketDetail/distributeTicket/distributeTicket'
import HandleTicket from '../ticket/ticketDetail/handleTicket/handleTicket'
import EmployeeDetail from "../system/employeeDetail";
import { Navigate } from 'react-router-dom';

const routes = [
  {
    path: "/",
    element: <BasicDivider />,
    children: [
      {
        path: "/",
        element: <DashbordBase />,
      },
      {
        path: "dashbord/base",
        element: <DashbordBase />,
      },
      {
        path: "dashbord/details",
        element: <DashbordDetails />,
      },
      {
        path: "ticket/create",
        element: <CreateTicket />,
      },
      {
        path: "ticket/todo",
        element: <MyTodo />,
      },
      {
        path: "ticket/created-by-me",
        element: <CreatedByMe />,
      },
      {
        path: "ticket/related-to-me",
        element: <RelatedToMe />,
      },
      {
        path: "ticket/all",
        element: <AllTickets />,
      },
      {
        path: "ticket/distribute",
        element: <Distribute />,
      },
      {
        path: "ticket/todo/detail/:ticketId",
        element: <HandleTicket />,
      },
      {
        path: "ticket/created-by-me/detail/:ticketId",
        element: <TicketDetail />,
      },
      {
        path: "ticket/related-to-me/detail/:ticketId",
        element: <TicketDetail />,
      },
      {
        path: "ticket/all/detail/:ticketId",
        element: <TicketDetail />,
      },
      {
        path: "ticket/distribute/detail/:ticketId",
        element: <DistributeTicket />,
      },
      {
        path: "admin/approve/employee",
        element: <EmployeeApprove />,
      },
      {
        path: "admin/approve/employee/detail/:userId",
        element: <EmployeeDetail />,
      },
      {
        path: "admin/role",
        element: <RoleAdmin />,
      },
      {
        path: "admin/position",
        element: <PositionAdmin />,
      },
      {
        path: "admin/department",
        element: <DepartmentAdmin />,
      },
      {
        path: "personal",
        element: <Personal />,
      },
    ],
  },
  {
    path: "login",
    element: <Login />,
  },
  {
    path: "bind",
    element: <Bind />,
  },
  {
    path: "admin-bind",
    element: <AdminBind />,
  },
  {
    path: "status",
    element: <Status />,
  },
  {
    path: "*",
    element: <Navigate to="/about" />,
  },
];

export default routes;
