import './App.css'
import './index-out.css'
import Navbar from './components/navbar/Navbar';

import { useEffect, useState } from 'react';
import Cookies from 'js-cookie';
import UnloggedMain from './components/unlogged-main/UnloggedMain';
import Main from './components/main/Main';
import axios from 'axios';

function App() {

  const [loggedIn, setLoggedIn] = useState(false)
  const [selectedPost, setSelectedPost] = useState('' as string)
  const [trackedPosts, setTrackedPosts] = useState({} as any);
  const [isHidden, setIsHidden] = useState(true);
  const [isLoading, setIsLoading] = useState(false);

  const trackNewPostPopUp = () => {
    setIsHidden(false);
  }

  const cancelPopUp = () => {
    setIsHidden(true);
  }

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedPost(e.target.value);
    Cookies.set('instagramShortcode', e.target.value);
  }

  const updateSelectedPost = () => {
    const username = Cookies.get('username');
    setIsLoading(true);
    axios.get(`http://localhost:8080/ig/?ig-url=/p/${Cookies.get("instagramShortcode")}&username=${username}`, {
      headers: { Authorization: 'Bearer ' + Cookies.get('token') }
    }).then(response => {
      axios.get('http://localhost:8080/ig/get-tracked-posts?', {
        headers: { Authorization: 'Bearer ' + Cookies.get('token') },
        params: {
          username: Cookies.get('username')
        }
      }).then(res => {
        setTrackedPosts(res.data)
        setIsLoading(false);
      }).catch(err => {
        console.log(err)
        setIsLoading(false);
      });
    }).catch(err => {
      console.log(err)
      setIsLoading(false);
    });
  }

  const handleTrackNewPost = () => {
    setIsHidden(true);
    setIsLoading(true);
    const input = document.querySelector('input');
    const url = input?.value;
    const username = Cookies.get('username');
    axios.get(`http://localhost:8080/ig/?ig-url=${url}&username=${username}`, {
      headers: { Authorization: 'Bearer ' + Cookies.get('token') }
    }).then(res => {
      console.log(res.data);
      setIsLoading(false);
    }).catch(err => {
      console.log(err);
      setIsLoading(false);
    });
  }

  useEffect(() => {
    if (Cookies.get('token')) {
      setLoggedIn(true)
    } else {
      setLoggedIn(false)
    }

    setIsLoading(true);

    axios.get('http://localhost:8080/ig/get-tracked-posts?', {
      headers: { Authorization: 'Bearer ' + Cookies.get('token') },
      params: {
        username: Cookies.get('username')
      }
    }).then(res => {
      console.log(res.data)
      setTrackedPosts(res.data)
      setSelectedPost(res.data.postShortDTOs[0].shortcode);
      setIsLoading(false);
    }).catch(err => {
      console.log(err)
    });

    if (!trackedPosts) {
      trackNewPostPopUp();
    }


  }, [])


  return (
    <>
      <Navbar updatePostFunc={updateSelectedPost} isLoading={isLoading} trackNewPost={trackNewPostPopUp} selectBoxFunc={handleSelectChange} selectedPost={selectedPost} />
      {loggedIn ?
        <>
          {
              isLoading ? <div className='loading-overlay'>
                        <div className='loader'></div>
              </div> : <></>
          }
          <div className={isHidden ? 'pop-up hidden' : 'pop-up'}>
            <div className='pop-up-content'>
              <h1>Track a new post</h1>
              <input type='text' placeholder='Enter the Instagram URL' />
              <br />
              <div className='button-wrapper'>
                <button onClick={handleTrackNewPost}>Track</button>
                <button className='cancel-button' onClick={cancelPopUp}>Cancel</button>
              </div>
            </div>
          </div>
          <Main selectedPost={selectedPost} />
        </>
        :
        <>
          <UnloggedMain />
        </>}


    </>
  )
}

export default App
