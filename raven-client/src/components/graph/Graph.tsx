import '../../index-out.css'
import React from 'react'
import './graph.css'

import { Line } from 'react-chartjs-2';


function Graph() {

    const data = {
        labels: ['January', 'February', 'March', 'April', 'May', 'June'],
        datasets: [
          {
            label: 'My First dataset',
            fill: false,
            lineTension: 0.1,
            backgroundColor: 'rgba(75,192,192,0.4)',
            borderColor: 'rgba(75,192,192,1)',
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: 'rgba(75,192,192,1)',
            pointBackgroundColor: '#fff',
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: 'rgba(75,192,192,1)',
            pointHoverBorderColor: 'rgba(220,220,220,1)',
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: [65, 59, 80, 81, 56, 55, 40]
          }
        ]
      };

    return (
        <div className='graph-container'>
            <div className='graph-wrapper'>
                <h1>Přehled v čase</h1>
                <Line className='graph'
                    options={{
                        scales: {
                            y: {
                                beginAtZero: true
                            },
                            x: {
                                beginAtZero: true
                            }
                        
                    }}}
                   
                />
            </div>
        </div>
    )
}

export default Graph
