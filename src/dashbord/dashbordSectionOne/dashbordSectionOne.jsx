import React, { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom";
import axios from "../../user/axiosInstance"
// import "../../mock/workorderlistall"
// import "../../mock/workorderlistalert"
// import "../../mock/workorderlistcreate"
// import "../../mock/workorderlistrelatedtome"
// import "../../mock/workorderlisttodo"
// import axios from "axios"
// import api from "../../api"
import TiCard from '../../tiCard/tiCard'
import './dashbordSectionOne.css'

// 渲染一个包含多个数据的区块
export default function DashbordSectionOne() {
  
  const navigate = useNavigate();
  const [createTicket, setCreateTicket] = useState(0)
  const [alertTicket, setAlertTicket] = useState(0)
  const [todoTicket, setToDoTicket] = useState(0)
  const [totalTicket, setTotalTicket] = useState(0)

  const fetchDataTotalTicket = async () => {
    try {
      const response = await axios.get('/process/allOrders')
      // console.log(response)

      var totalTicketCount = 0
      response.data.data.map((item) => {
        totalTicketCount += 1
      })
      setTotalTicket(totalTicketCount)
    } catch (error) {
    if (error.message === "No token available") {
      // 进行页面导航到登录页面
      navigate("/login");
    } else if (error.message === "Token expired") {
      // 进行页面导航到登录页面或其他处理
      navigate("/login");
    } else if (error.message === "Network error") {
      // 进行页面导航到错误页面或其他处理
      navigate("/login");
    }
      
    }
  }

  const fetchDataCreateTicket = async () => {
    try {
      const response = await axios.get('/process/myOrder')
      console.log(response)

      var createTicketCount = 0
      response.data.data.map((item) => {
        createTicketCount += 1
      })
      setCreateTicket(createTicketCount)
    } catch (error) {
    if (error.message === "No token available") {
      // 进行页面导航到登录页面
      navigate("/login");
    } else if (error.message === "Token expired") {
      // 进行页面导航到登录页面或其他处理
      navigate("/login");
    } else if (error.message === "Network error") {
      // 进行页面导航到错误页面或其他处理
      navigate("/login");
    }
      
    }
  }
  
  const fetchDataAlertTicket = async () => {
    try {
      const response = await axios.get('/process/myWarningTask')
      // console.log(response)

      var alertTicketCount = 0
      response.data.data.map((item) => {
        alertTicketCount += 1
      })
      setAlertTicket(alertTicketCount)
    } catch (error) {
    if (error.message === "No token available") {
      // 进行页面导航到登录页面
      navigate("/login");
    } else if (error.message === "Token expired") {
      // 进行页面导航到登录页面或其他处理
      navigate("/login");
    } else if (error.message === "Network error") {
      // 进行页面导航到错误页面或其他处理
      navigate("/login");
    }
      
    }
  }
  
  const fetchDataToDoTicket = async () => {
    try {
      const response = await axios.get('/process/myCommision')

      var todoTicketCount = 0
      response.data.data.map((item) => {
        todoTicketCount += 1
      })
      setToDoTicket(todoTicketCount)
    } catch (error) {
    if (error.message === "No token available") {
      // 进行页面导航到登录页面
      navigate("/login");
    } else if (error.message === "Token expired") {
      // 进行页面导航到登录页面或其他处理
      navigate("/login");
    } else if (error.message === "Network error") {
      // 进行页面导航到错误页面或其他处理
      navigate("/login");
    }
      
    }
  }
  

  useEffect(() => {
    fetchDataTotalTicket()
    fetchDataCreateTicket()
    fetchDataAlertTicket()
    fetchDataToDoTicket()
  }, [])
  

  var dates = [
    { title: '我的预警', number: alertTicket, remark: '自上周以来下降10%', mainColor: true },
    { title: '总工单数', number: totalTicket, remark: '自上周以来上升25%' },
    { title: '我创建的', number: createTicket, remark: '自上周以来上升15%' },
    { title: '我的待办', number: todoTicket, remark: '自上周以来上升12%' }
  ]

  return (
    <section className="ti-dashbord-section-one" >
      {dates.map((date, index) => (
        <div key={index} className='ti-dashbord-section-one-block'>
          <TiCard
            title={date.title}
            number={date.number}
            // remark={date.remark}
            mainColor={date.mainColor}
          />
        </div>
      ))}
    </section>
  )
}