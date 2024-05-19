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
  const [isHidden, setIsHidden] = useState(true);

  const trackNewPostPopUp = () => {
    setIsHidden(false);
  }

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    if (e.target.value === 'new-post') {
      trackNewPostPopUp();
      return;
    }

    console.log('app ' + e.target.value)
    setSelectedPost(e.target.value);
    Cookies.set('instagramShortcode', e.target.value);
  }

  const handleTrackNewPost = () => {
    //http://localhost:8080/ig/?ig-url=https://www.instagram.com/p/C6W0p8Art0q/&username=carodej2
    setIsHidden(true);
    const input = document.querySelector('input');
    const url = input?.value;
    const username = Cookies.get('username');
    axios.get(`http://localhost:8080/ig/?ig-url=${url}&username=${username}`, {
      headers: { Authorization: 'Bearer ' + Cookies.get('token') }
    }).then(res => {
      console.log(res.data)
    }).catch(err => {
      console.log(err)
    });
  }

  useEffect(() => {
    if (Cookies.get('token')) {
      setLoggedIn(true)
    } else {
      setLoggedIn(false)
    }

  }, [])


  return (
    <>
      <Navbar selectBoxFunc={handleSelectChange} />
      {loggedIn ?
        <>
          <div className={isHidden ? 'pop-up hidden' : 'pop-up'}>
            <div className='pop-up-content'>
              <h1>Track a new post</h1>
              <input type='text' placeholder='Enter the Instagram URL' />
              <br />
              <button onClick={handleTrackNewPost}>Track</button>
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
