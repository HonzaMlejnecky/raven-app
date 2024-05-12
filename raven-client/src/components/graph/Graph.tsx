import '../../index-out.css'
import React from 'react'
import './graph.css'

import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';


function Graph(props: {data: []}) {

    const data2: [] = props.data;
    console.log(data2)


    return <>

        <LineChart width={1100} height={250} data={data2}
            margin={{ top: 5, right: 20, left: 20, bottom: 5 }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="datetime" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="numOfLikes" stroke="green" />
            <Line type="monotone" dataKey="numOfComments" stroke="blue" />
        </LineChart>
        <p>
            Graph of the number of likes and comments
        </p>
    </>
}

export default Graph
