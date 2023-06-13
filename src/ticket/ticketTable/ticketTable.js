import { useState, useEffect, Fragment } from "react";
import { Table, DialogPlugin } from "tdesign-react";
import { Edit1Icon } from "tdesign-icons-react";
import { Link, useLocation } from "react-router-dom";
import axios from "axios";
import api from "../../api";
import "../../mock/workorderlistall";

// 展示工单信息的表格组件，并提供了相关操作和分页功能
export default function TicketTable(props) {
  // const user_id = window.sessionStorage.getItem('user_id')
  const { queryParams } = props;
  const { flog } = queryParams;
  const { datalist } = props;
  const [isLoading, setIsloading] = useState(false);
  const [data, setData] = useState([]);
  const cur = 1;
  const pgs = 5;
  const tle = 5;
  const [current, setCurrent] = useState(cur);
  const [pageSize, setPageSize] = useState(pgs);
  const [totle, setTotle] = useState(tle);
  const location = useLocation();
  const ticketPath = location.pathname;

  const [totalTicket, setTotalTicket] = useState(0);

  let tableData = [];

  // const fetchDataTotalTicket = async () => {
  //   try {
  //     const response = await axios.get('/api/v1/dataSource0')
  //     console.log('response',response.data.result.list);
  //     response.data.result.list.map((item) => {
  //       tableData.push({
  //         id: item.id,
  //         title: item.title,
  //         status: item.status === 0 ? '创建' : item.status === 1 ? '进行中' : '完成',
  //         current_handler: item.current_handler_name,
  //         dept: item.dept_name,
  //         create_time: item.create_time
  //       })
  //     })
  //     // setTotalTicket(totalTicketCount)
  //     setData(tableData)
  //   } catch (error) {
  //     // console.log('Error', error)
  //   }
  // }

  // 用于配置工单表格的列信息
  const columns = [
    {
      align: "center",
      ellipsis: true, // 内容超出，是否显示省略号
      colKey: "id",
      title: "ID",
      width: "7%",
    },
    {
      colKey: "title",
      ellipsis: true,
      title: "标题",
      width: "20%",
    },
    {
      align: "center",
      ellipsis: true,
      colKey: "status",
      title: "当前状态",
      width: "12%",
    },
    {
      align: "center",
      ellipsis: true,
      colKey: "current_handler",
      title: "当前处理人",
      width: "13%",
    },
    {
      align: "center",
      ellipsis: true,
      colKey: "dept",
      title: "所属部门",
      width: "12%",
    },
    {
      align: "center",
      ellipsis: true,
      colKey: "create_time",
      title: "创建时间",
      width: "21%",
    },
    {
      align: "center",
      ellipsis: true,
      colKey: "operation",
      title: "操作",
      // 自定义列的单元格内容，处理、删除或查看工单的链接
      cell: ({ col, row }) => {
        if (queryParams.current_handler) {
          return (
            <span>
              <Edit1Icon></Edit1Icon>
              <Link
                className="ti-ticket-link"
                to={`${ticketPath}/detail/${row["id"]}`}
              >
                处理
              </Link>
            </span>
          );
        } else if (queryParams.creator_id) {
          if (row["status"] === "创建") {
            return (
              <>
                {/* <span
                  onClick={() => deleteTicket(row["id"])}
                  style={{ fontSize: "13px", marginRight: "2px" }}
                >
                  <Edit1Icon></Edit1Icon>
                  <a>删除</a>
                </span> */}
                <span style={{ fontSize: "13px" }}>
                  <Edit1Icon></Edit1Icon>
                  <Link
                    className="ti-ticket-link"
                    to={`${ticketPath}/detail/${row["id"]}`}
                  >
                    查看
                  </Link>
                </span>
              </>
            );
          }
        }
        return (
          <span>
            <Edit1Icon></Edit1Icon>
            <Link
              className="ti-ticket-link"
              to={`${ticketPath}/detail/${row["id"]}`}
            >
              查看
            </Link>
          </span>
        );
      },
      width: "15%",
    },
  ];

  // 用于删除工单，弹出确认对话框
  const deleteTicket = (id) => {
    api.dialog.confirm({
      msg: "是否删除该工单",
      cancelBtn: true,
      confirm: [
        () => {
          api
            .post("/admin/v1/ticket/delete", { ticket_id: id })
            .then((data) => {
              api.message.success("删除成功", 2 * 1000);
              fetchData(queryParams, { current, pageSize });
            })
            .catch((err) => {
              api.message.error("删除失败", 2 * 1000);
              console.log(err);
              fetchData(queryParams, { current, pageSize });
            });
        },
      ],
    });
  };

  // 分页数据变化
  async function rehandleChange(pageInfo) {
    const { current, pageSize } = pageInfo;
    setCurrent(current);
    setPageSize(pageSize);
    await fetchData(queryParams, pageInfo);
  }

  // 根据查询参数和分页信息发送请求获取工单数据
  async function fetchData(queryParams, pageInfo) {
    setIsloading(true);
    try {
      queryParams.pageIndex = pageInfo.current;
      queryParams.pageSize = pageInfo.pageSize;

      // let {data} = await api.post('/admin/v1/ticket/list',queryParams)
      axios
        .get("/api/v1/dataSource" + flog) //接口地址与拦截地址要一致
        .then((res) => {
          // console.log('res', res.data.result.list)
          res.data.result.list.map((item) => {
            tableData.push({
              id: item.id,
              title: item.title,
              status:
                item.status === 0
                  ? "创建"
                  : item.status === 1
                  ? "进行中"
                  : "完成",
              current_handler: item.current_handler_name,
              dept: item.dept_name,
              create_time: item.create_time,
            });
          });
        })
        .catch((error) => {
          // 处理请求错误
          console.log("请求错误", error);
        });

      // data.list.map((item)=>{
      //   tableData.push({
      //     id: item.id,
      //     title: item.title,
      //     status: item.status === 0 ? '创建' : item.status === 1 ? '进行中' : '完成',
      //     current_handler: item.current_handler_name,
      //     dept: item.dept_name,
      //     create_time: item.create_time
      //   })
      // })
      setData(tableData);
      setTotle(data.totle);
      setIsloading(false);
    } catch (err) {
      setData([]);
      setIsloading(false);
    }
  }

  // 更新当前页码和每页显示数量，初始化时调用一次
  async function update() {
    setCurrent(cur);
    setPageSize(pgs);
    await fetchData(queryParams, { current: cur, pageSize: pgs });
  }

  // 组件渲染完成后监听 queryParams 的变化
  useEffect(() => {
    update();
    // fetchDataTotalTicket()
  }, [datalist]);

  return (
    <Fragment>
      <Table
        stripe={true}
        bordered
        data={data}
        columns={columns}
        rowKey="id"
        loading={isLoading}
        pagination={{
          current,
          pageSize,
          total: totle,
          showSizer: true,
          visibleWithOnePage: true,
          onChange(pageInfo) {
            rehandleChange(pageInfo);
          },
        }}
      />
    </Fragment>
  );
}
