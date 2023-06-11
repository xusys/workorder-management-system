import React from "react"
import './dashbordSectionThree.css'
import TableBasic from './timeSpentTable/timeSpentTable'

export default function DashbordSectionThree() {
    return (
        <section className="ti-dashbord-section-three ti-section-wrapper">
            <div className="ti-section-header">
                <div className="ti-section-title-area">
                    <span className="ti-title">工单处理耗时排行榜</span>
                </div>
                <div className="ti-section-options">

                </div>
            </div>
            <div className="ti-section-content">
                <TableBasic></TableBasic>
            </div>
        </section>
    )
}