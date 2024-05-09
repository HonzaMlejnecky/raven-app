import { useEffect, useState } from 'react'
import '../../index-out.css'
import Card from '../card/Card'
import Graph from '../graph/Graph'
import axios from 'axios'
import Cookies from 'js-cookie'

// eslint-disable-next-line @typescript-eslint/no-explicit-any
function Main() {

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const [data, setData] = useState({} as any);
    const [timelineData, setTimelineData] = useState([] as any);

    // obtain data from the server
    useEffect(() => {
        axios.post('http://localhost:8080/ig/?ig-shortcode=C6W0p8Art0q', {} ,{
           headers: { Authorization: 'Bearer ' + Cookies.get('token') }
        })
            .then(res => {
                console.log(res.data)
                setData(res.data)
                setTimelineData(res.data.postTimelineDTOS)
                console.log(res.data.postTimelineDTOS)
            })
            .catch(err => {
                console.log(err)
            })

    }, [])

    const convertDate = (date: number):string | null => {
        return new Date(date).toLocaleDateString()
    };


    return (
        <main>
          <div className="p-6">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              <Card title={"Number of comments"} value={data.numberOfCommentsTotal}/>
              <Card title={"Number of likes"} value={data.numOfLikesTotal}/>
              <Card title={"Number of obtained updates"} value={timelineData.length} />
              <Card title={"Last updated at"} value={convertDate(Date.parse(data.createdAt))} />
              <Card title={"Posted by"} value={data.posterUsername} />
            </div>
          </div>
          <div>
            <p>View on instagram - <a href={'https://instagram.com/p/C6W0p8Art0q'}>post</a></p>
          </div>
          <div>
          

          </div>
        </main>
    )
}

/*
<Graph data={timelineData}/>
*/

export default Main
