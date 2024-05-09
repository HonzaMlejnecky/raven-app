import './App.css'
import './index-out.css'
import Navbar from './components/navbar/Navbar';

import { useEffect, useState } from 'react';
import Cookies from 'js-cookie';
import UnloggedMain from './components/unlogged-main/UnloggedMain';
import Main from './components/main/Main';

function App() {

  const [loggedIn, setLoggedIn] = useState(false)

  useEffect(() => {
    if (Cookies.get('token')) {
      setLoggedIn(true)
    } else {
      setLoggedIn(false)
    }

  }, [])


  return (
    <>
      <Navbar />
      {loggedIn ?
        <>
          <Main />
        </>
        :
        <>
          <UnloggedMain />
        </>}


    </>
  )
}

export default App
