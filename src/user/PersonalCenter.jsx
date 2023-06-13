import React, { useState } from 'react'
import { Form, Input, Textarea, Upload, Button, Radio } from 'tdesign-react'
import '../ticket/createTicket/createTicket.css'
import axios from "../user/axiosInstance"
// import axios from 'axios'
import "../mock/usernamechange"
import './PersonalCenter.css'

const { FormItem } = Form


// 个人中心的界面，根据用户的编辑状态显示不同的界面元素
export default function PersonalCenter() {
    const userInfo = JSON.parse(window.sessionStorage.getItem("user_info"))
    // console.log('userInfo', userInfo)
    const [isEditing, setIsEditing] = useState(false)
    const [username, setUsername] = useState(userInfo.user_name)
    // console.log('username',username)
    const [userid, setUserid] = useState(userInfo.user_id)

    const handleEditClick = () => {
        setIsEditing(true)
    }

    const handleCancelClick = () => {
        setIsEditing(false)
        setUsername(userInfo.user_name)
    }

    // 阻止默认的表单提交行为，处理提交逻辑
    const handleSubmit = (e) => {
        e.preventDefault() // 阻止表单的默认提交行为

        // 更新页面上的用户信息
        const updatedUserInfo = { ...userInfo, user_name: username }
        window.sessionStorage.setItem("user_info", JSON.stringify(updatedUserInfo))

        if (isEditing) {
            // 发送更新用户信息的请求
            console.log('userInfo', updatedUserInfo)
            axios
                .post("/api/v1/updateUser", updatedUserInfo)
                .then((res) => {
                    if (res.data.code === 0) {
                        // 更新成功
                        setIsEditing(false)
                    } else {
                        // 更新失败，处理错误情况
                        console.log("更新失败")
                    }
                })
                .catch((error) => {
                    // 处理请求错误
                    console.log("请求错误", error)
                })
        }
    }


    const handleUsernameChange = (e) => {
        setUsername(e)
    }

    return (
        <div>
            <section className="ti-section-wrapper ti-create-wrapper" style={{ height: '100%' }}>
                <form onSubmit={handleSubmit}>
                    <div className="ti-form-basic-container">
                        <div className="ti-form-basic-item">
                            <div className="ti-form-basic-container-title">个人详情</div>
                            {false ? (
                                <div className="ti-form-basic-item">
                                    <FormItem label="用户名" name="title">
                                        <Input
                                            placeholder="请输入内容"
                                            value={toString(username)}
                                            onChange={(e) => {
                                                handleUsernameChange(e)
                                            }}
                                        />
                                    </FormItem>
                                    <FormItem label="职位" name="title">
                                        <p>{userid}</p>
                                    </FormItem>
                                </div>
                            ) : (
                                <div className="ti-form-basic-item">
                                    <FormItem label="用户名" name="title">
                                        <p>{username}</p>
                                    </FormItem>
                                    <FormItem label="职位" name="title">
                                        <p>{userid}</p>
                                    </FormItem>
                                </div>
                            )}
                        </div>
                    </div>
                    {/* {isEditing ? (
                        <div className="ti-form-submit-container">
                            <div className="ti-form-submit-sub">
                                <Button type="submit" onClick={handleSubmit}>提交</Button>
                                <Button type="reset" theme="default" onClick={handleCancelClick} className="reset">取消</Button>
                            </div>
                        </div>
                    ) : (
                        <Button type="button" onClick={handleEditClick}>修改</Button>
                    )} */}
                </form>
            </section>
        </div >
    )
}