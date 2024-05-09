import '../../index-out.css'
import React from 'react'
import Cookies from 'js-cookie'


function Navbar() {

    const [username, setUsername] = React.useState(Cookies.get('username'));

    const handleLogout = () => {
        Cookies.remove('token');
        Cookies.remove('username');
        setUsername('');
        window.location.reload();
    }

    return (
        <nav className='navbar'>
            <div className="flex justify-between items-center h-16 bg-white text-black relative shadow-sm " role="navigation">
                <div className="pl-16 font-bold	">
                    <a href="/" className="p-8">
                        /Raven/
                    </a>
                </div>
                <div className='place-content-center'>
                    {
                        username ? <div >
                            <a href="/profile" className="font-bold	pr-4">{username}</a>
                            <button onClick={handleLogout} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-3 rounded-full">Logout</button>
                        </div> : <></>
                    }
                </div>
                <div className="pr-16 md:block hidden">
                    <a href="/manual" className="p-8">User Manual</a>
                    <a href="/about" className="p-8">About</a>
                </div>
            </div>
        </nav>
    )
}

export default Navbar
