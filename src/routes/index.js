import {Login, Status} from '../user/login'
import PersonalCenter from '../user/PersonalCenter'
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
  Personal,
  MyAlert
} from '../layout/layout';
import TicketDetail from '../ticket/ticketDetail/ticketDetail';
import CreateTicket from '../ticket/createTicket/createTicket'
import HandleTicket from '../ticket/ticketDetail/handleTicket/handleTicket'

// 路由表
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
        path: "personalCenter",
        element: <PersonalCenter />,
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
        path: "ticket/alert",
        element: <MyAlert />,
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
        path: "ticket/alert/detail/:ticketId",
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
        path: "admin/approve/employee",
        element: <EmployeeApprove />,
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
    path: "status",
    element: <Status />,
  },
];

export default routes;
