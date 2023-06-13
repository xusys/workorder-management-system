import api from "../../api";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import {
  TicketProcess,
  TicketContent,
  TicketBase,
  TicketHistory,
} from "./ticketDetailBase/ticketDetailBase";
import "../../mock/workorderlistall";

export default function TicketDetail() {
  const { ticketId } = useParams();
  const [data, setData] = useState({});

  // 请求内容
  async function fetchData() {
    try {
      // let {data} = await api.get('/api/v1/dataSource',queryParams)
      const res = await axios.get("/api/v1/dataSource0"); //接口地址与拦截地址要一致

      let data0 = res.data.result.list.filter( (item) => {
        return item.id === ticketId
      })
      //   console.log('data0', data0)
      //   data0.process_list = JSON.parse(data0.process_list);

      setData(data0[0]);
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
