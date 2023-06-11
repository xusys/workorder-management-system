import api from '../../api'
import { useState,useEffect } from 'react';
import { useParams } from "react-router-dom";
import axios from 'axios';
import {TicketProcess,TicketContent,TicketBase,TicketHistory} from './ticketDetailBase/ticketDetailBase'


export default function TicketDetail() {
    const { ticketId } = useParams();
    const  [data,setData] = useState({})
    // 请求内容
    async function fetchData(queryParams) {
        try {
            // let {data} = await api.get('/api/v1/dataSource',queryParams)
            axios.get('/api/v1/dataSource')//接口地址与拦截地址要一致
            .then((res)=>{
                // console.log('res', res)
                data = res.data.result.list
                })
            data.process_list = JSON.parse(data.process_list)
            setData(data)
        } catch (err) {
            console.log('err',err)
            setData({});
        }
    }
    useEffect(()=>{
        fetchData({ticket_id:ticketId});
    },[])
    return (
        <>
            <TicketProcess data={data} />
            <TicketContent data={data} />
            <TicketBase data={data} />
            <TicketHistory data={data} />
        </>
    )
}

