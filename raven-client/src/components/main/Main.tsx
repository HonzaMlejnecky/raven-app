/* eslint-disable @typescript-eslint/no-unused-vars */
import { useEffect, useState } from 'react'
import '../../index-out.css'
import Card from '../card/Card'
import Graph from '../graph/Graph'
import axios from 'axios'
import Cookies from 'js-cookie'
import './main.css'
import IgPostCard from '../ig-post-card/IgPostCard'
import React from 'react'


function Main(props: { selectedPost: string }) {

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const [data, setData] = useState({} as any);
  const [timelineData, setTimelineData] = useState([] as any);
  const [timelineCommentsData, setTimelineCommentsData] = useState([] as any);

  // obtain data from the server
  useEffect(() => {
    axios.post(`http://localhost:8080/ig/?ig-shortcode=${props.selectedPost}`, {}, {
      headers: { Authorization: 'Bearer ' + Cookies.get('token') }
    })
      .then(res => {
        console.log(res.data)
        setData(res.data)
        setTimelineData(res.data.postTimelineDTOS)
        setTimelineCommentsData(res.data.postTimelineCommentsDTOS)
      })
      .catch(err => {
        console.log(err)
      })

  }, [props.selectedPost])

  const combineData = (): object[] => {
    const combinedData: object[] = [] as object[];

    timelineData.forEach((element: { numOfLikes: number, datetime: string }) => {
      element.datetime = convertDate(Date.parse(element.datetime));
      combinedData.push(element)
    })

    timelineCommentsData.forEach((element: { numOfComments: number, datetime: string }) => {
      // supply the data to the exisitng array
      combinedData.forEach(existing => {
        if (existing.datetime === convertDate(Date.parse(element.datetime))) {
          existing.numOfComments = element.numOfComments;
        }
      });
    })


    // sort the array by date
    combinedData.sort((a, b) => {
      return Date.parse(a.datetime) - Date.parse(b.datetime)
    })


    return combinedData
  }

  const convertDate = (date: number): string | null => {
    return new Date(date).toLocaleDateString("en-US")
  };


  return (
    <main>
      <div className="p-6">
        <div className="grid-template">
          <Card className="element-1" title={"Number of comments"} value={data.numberOfCommentsTotal} />
          <Card className="element-2" title={"Number of likes"} value={data.numOfLikesTotal} />
          <Card className="element-3" title={"Number of obtained updates"} value={timelineData.length} />
          <Card className="element-4" title={"Last updated at"} value={convertDate(Date.parse(data.createdAt))} />
          <Card className="element-5" title={"Posted by"} value={data.posterUsername} />
          <IgPostCard shortcode={props.selectedPost} />
        </div>
      </div>

      <div className='card-graph'>
        <Graph data={combineData()} />
      </div>
      <div>

      </div>
    </main>
  )
}

export default Main
