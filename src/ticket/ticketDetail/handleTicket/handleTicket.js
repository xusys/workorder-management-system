import api from '../../../api'
import { useState,useEffect } from 'react';
import { useParams } from "react-router-dom";
import {TicketContent,TicketBase,TicketHandle,TicketHistory,TicketTask} from '../ticketDetailBase/ticketDetailBase'
// import axios from 'axios';
import axios from "../../../user/axiosInstance"

// 渲染工单详情页面的内容
export default function TicketDetail() {
    const { ticketId } = useParams();
    const  [data,setData] = useState({})
    const [deptData, setDeptData] = useState([]);
    const [employeeData, setEmployeeData] = useState([]);

    // 从服务器获取工单详情数据
    async function fetchData(queryParams) {
        try {
            let {data} = await axios.get()
            data.process_list = JSON.parse(data.process_list)
            setData(data)
        } catch (err) {
            setData({});
        }
    }

    // 从服务器获取部门列表数据
    async function fetchDeptList() {
        try {  
            const res = await axios.get(`http://localhost:8080/process/orderDetails?orderId=${ticketId}`)
            console.log('处理', res)
            setData(res.data.data)
        } catch (err) {
            setData([]);
        }
    }

    // 从服务器获取员工列表数据
    async function fetchEmployeeList() {
        try {  
            const res = await axios.get(`http://localhost:8080/process/assistantPositions`)
            console.log('res1', res)
            setEmployeeData(res.data.data)
        } catch (err) {
            setEmployeeData([{}]);
        }
    }

    useEffect(()=>{
        fetchData({ticket_id:ticketId});
    },[])
    useEffect(()=>{
        fetchDeptList();
    },[])
    useEffect(()=>{
        fetchEmployeeList();
    },[])

    return (
        <>
            <TicketTask data={data} />
            <TicketHandle data={data} employeeData={employeeData}/>
            <TicketContent data={data} />
            <TicketBase data={data} />
            <TicketHistory data={data} />
        </>
    )
}
