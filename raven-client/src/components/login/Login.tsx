import '../../index-out.css'
import React from 'react'
import './login.css'
import axios from 'axios';
import Cookies from 'js-cookie';


// eslint-disable-next-line @typescript-eslint/no-explicit-any
function Login(props: any) {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');


    const handleLogin = () => {
        console.log('logging in');
        axios.post('http://localhost:8080/users/login', {
            username: username,
            password: password
        }).then(res => {
            Cookies.set('token', res.data.token);
            Cookies.set('username', res.data.username);
            window.location.reload();
        }).catch(err => {
            console.log(err);
        })
    }

    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }

    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }

    return (
        <>
            <div className='login-form'>
                <div>
                    <label htmlFor="username">Username</label>
                    <br />
                    <input onChange={handleUsernameChange} name="username" type="text"></input>
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <br />
                    <input onChange={handlePasswordChange} name="password" type="password"></input>
                </div>
                <div>
                    <button onClick={handleLogin} className='login-button'>Log in</button>
                </div>
                <div>
                    <p>Or register <button onClick={props.handleRegister} className='nested-button-link'> here </button></p>
                </div>
            </div>
        </>

    )
}

export default Login
