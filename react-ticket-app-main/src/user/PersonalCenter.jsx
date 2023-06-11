import React, { useState } from 'react'
import { Form, Input, Textarea, Upload, Button, Radio } from 'tdesign-react'
import '../ticket/createTicket/createTicket.css'

// 个人中心的界面，根据用户的编辑状态显示不同的界面元素
export default function PersonalCenter() {
    const userInfo = JSON.parse(window.sessionStorage.getItem("user_info"))
    const [isEditing, setIsEditing] = useState(false)
    const [username, setUsername] = useState(userInfo.username)

    const handleEditClick = () => {
        setIsEditing(true)
    }

    const handleCancelClick = () => {
        setIsEditing(false)
        setUsername(userInfo.username)
    }

    // 阻止默认的表单提交行为，处理提交逻辑
    const handleSubmit = (e) => {
        e.preventDefault()
        console.log("提交")
        window.sessionStorage.setItem(
            "user_info",
            JSON.stringify({ username: username })
        )

        setIsEditing(false)
    }

    const handleUsernameChange = (e) => {
        setUsername(e.target.value)
    }

    return (
        <div>
            <section className="ti-section-wrapper ti-create-wrapper" style={{ height: '100%' }}>
                <form onSubmit={handleSubmit}>
                    <div className="ti-form-basic-container">
                        <div className="ti-form-basic-item">
                            <div className="ti-form-basic-container-title">个人详情</div>
                            {isEditing ? (
                                <div>
                                    <p style={{marginBottom: '10px'}}>用户名: </p>
                                    <input
                                        type="text"
                                        placeholder="请输入内容"
                                        value={username}
                                        onChange={handleUsernameChange}
                                    />
                                </div>
                            ) : (
                                <p>用户名: {username}</p>
                            )}
                        </div>
                    </div>
                    {isEditing ? (
                        <div className="ti-form-submit-container">
                            <div className="ti-form-submit-sub">
                                <Button type="submit" onClick={handleSubmit}>提交</Button>
                                <Button type="reset" theme="default" onClick={handleCancelClick} className="reset">取消</Button>
                            </div>
                        </div>
                    ) : (
                        <Button type="button" onClick={handleEditClick}>修改</Button>
                    )}
                </form>
            </section>
        </div>
    )
}