import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
// import axios from "axios";
import axios from "../../user/axiosInstance"
import {
  TicketProcess,
  TicketContent,
  TicketBase,
  TicketHistory,
} from "./ticketDetailBase/ticketDetailBase";
// import "../../mock/workorderlistall";

export default function TicketDetail() {
  const { ticketId } = useParams();
  const [data, setData] = useState({});

  // 请求内容
  async function fetchData() {
    try {
      const res = await axios.get(`/process/orderDetails?orderId=${ticketId}`); //接口地址与拦截地址要一致
      console.log('查看', res)
      setData(res.data.data);
    } catch (err) {
      console.log("err", err);
      setData({});
    }
  }
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <TicketProcess data={data} />
      <TicketContent data={data} />
      <TicketBase data={data} />
      <TicketHistory data={data} />
    </>
  );
}
