import React from "react"
import './tiCard.css'

export default function TiCard(props) {

    let cardFigure
    if (props.figure) {
        cardFigure = <div className="ti-card-figure"></div>
    }
    
    return (
        <div className={`ti-card-container ${props.mainColor ? 'mian-card-container' : null}`}>
            <p className="ti-card-title">
                {props.title}
            </p>
            <p className="ti-card-number">
                {props.number}
            </p>
            <p className="ti-card-remark">
                {props.remark}
            </p>
            {cardFigure}
        </div>
    )
}