import React, { useState } from 'react'
import { Form, Input, Textarea, Upload, Button, Radio } from 'tdesign-react'
import '../layout/layout.css'
const { FormItem } = Form

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

    const handleSubmit = (e) => {
        e.preventDefault()
        // 处理提交逻辑
        console.log("提交")
        window.sessionStorage.setItem(
            "user_info",
            JSON.stringify({ username: username })
        );

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
                                <input
                                    type="text"
                                    placeholder="请输入内容"
                                    value={username}
                                    onChange={handleUsernameChange}
                                />
                            ) : (
                                <p>用户名: {username}</p>
                            )}
                        </div>
                    </div>
                    {isEditing ? (
                        <div className="ti-form-submit-container">
                            <div className="ti-form-submit-sub">
                                <button type="submit" onClick={handleSubmit}>提交</button>
                                <button type="button" onClick={handleCancelClick} className="reset">取消</button>
                            </div>
                        </div>
                    ) : (
                        <button type="button" onClick={handleEditClick}>修改</button>
                    )}
                </form>
            </section>
        </div>
    )
}