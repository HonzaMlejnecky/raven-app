import '../../index-out.css'
import React from 'react'
import './card.css'


function Card(props: any) {

    return (
        <div className="card bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black">
            <div className="flex justify-between">
                <div>
                    <div className="flex items-center mb-6">
                        <div className="text-2xl font-semibold">{props.value}</div>
                    </div>
                    <div className="text-sm font-medium text-gray-400">{props.title}</div>
                </div>
            </div>
        </div>
    )
}

export default Card
