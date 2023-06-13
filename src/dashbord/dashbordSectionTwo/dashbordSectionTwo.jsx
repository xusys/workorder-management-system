import {React, useState, useEffect} from "react"
import './dashbordSectionTwo.css'
import { DatePicker } from 'tdesign-react'
import ReactECharts from 'echarts-for-react'
// import '../../mock/workorderlistall'
// import axios from "axios"
import axios from "../../user/axiosInstance"

export default function DashbordSectionTwo() {
  
  const [datas, setTotalTicket] = useState([0, 0, 0, 0, 0, 0, 0]);
  // 获取数据进行统计
  const fetchDatatotalTicket = async () => {
    try {
      const response = await axios.get('http://localhost:8080/process/allOrders');
      // const totalTicketCount = response.data.result.list.length;
      var data_count = [0, 0, 0, 0, 0, 0, 0]
      // var day = ['周日','周一', '周二', '周三', '周四', '周五', '周六']
      var data_src = response.data.data;
      console.log(data_src)
      data_src.map((item) => {
        var index = new Date(item.createTime).getDay()
        // console.log(day[index])
        if(index === 0) index = 7
        data_count[index - 1] += 1
      })
      setTotalTicket(data_count);
      // console.log('total_ticket', totalTicketCount);
    } catch (error) {
      // console.log('Error', error);
    }
  };

  useEffect(() => {
    fetchDatatotalTicket();
  }, []);

  // ECharts图表的配置选项
  const option = {
    tooltip: {},
    legend: {
      data: ['工单数']
    },
    xAxis: {
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {},

    
    series: [{
      name: '工单数',
      type: 'bar',
      data: datas,
      itemStyle: {
        color: '#0052d9'
      },
    }]
  }

  return (
    <section className="ti-dashbord-section-two ti-section-wrapper">
      <div className="ti-section-header">
        <div className="ti-section-title-area">
          <span className="ti-title">总工单统计</span>
          <span className="ti-sub-title">（个）</span>
        </div>
        {/* <div className="ti-section-options">
          <DatePicker mode="date" range style={{ width: 240 }}></DatePicker>
        </div> */}
      </div>
      <div className="ti-section-content">
        <ReactECharts
          option={option}
          style={{ height: 400 }}
          opts={{ renderer: 'svg' }} />
      </div>
    </section>
  )
}