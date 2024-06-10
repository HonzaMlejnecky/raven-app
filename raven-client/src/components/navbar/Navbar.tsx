import '../../index-out.css'
import React, { useEffect } from 'react'
import Cookies from 'js-cookie'
import axios from 'axios';
import './navbar.css'

function Navbar(props) {

    const [username, setUsername] = React.useState(Cookies.get('username'));
    const [trackedPosts, setTrackedPosts] = React.useState({} as any);
    const [selectedPost, setSelectedPost] = React.useState('' as string);

    const handleLogout = () => {
        Cookies.remove('token');
        Cookies.remove('username');
        setUsername('');
        window.location.reload();
    }

    useEffect(() => {
        axios.get('http://localhost:8080/ig/get-tracked-posts?', {
            headers: { Authorization: 'Bearer ' + Cookies.get('token') },
            params: {
                username: Cookies.get('username')
            }
        }).then(res => {
            console.log(res.data)
            setTrackedPosts(res.data)
        }).catch(err => {
            console.log(err)
        });

        setSelectedPost(props.selectedPost);
        console.log('navbar ' + props.selectedPost)

    }, [username])



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
                <div>
                    {
                        username ?
                            <div>
                                <select className='selectbox' onChange={props.selectBoxFunc}>
                                    {
                                        trackedPosts.postShortDTOs &&
                                        trackedPosts.postShortDTOs.map((post: { posterUsername: string, shortcode: string, description: string }) => {
                                            return <option key={post.shortcode} value={post.shortcode}>{post.posterUsername}</option>
                                        })
                                    }
                                </select>
                            </div>
                            : <></>
                    }
                </div>
                <div>
                    {
                        username ? <button onClick={props.updatePostFunc} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-3 rounded-full">
                            Update Post
                        </button> : <></>
                    }

                </div>
                <div>
                    {
                        username ? <button onClick={props.trackNewPost} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-3 rounded-full">
                            Track New Post
                        </button> : <></>
                    }

                </div>
                <div></div>
            </div>
        </nav>
    )
}

export default Navbar
