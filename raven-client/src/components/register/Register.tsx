import axios from 'axios'
import '../../index-out.css'
import './register.css'
import { useState } from 'react'


// eslint-disable-next-line @typescript-eslint/no-explicit-any
function Register(props: any) {

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordVerif, setPasswordVerif] = useState('');
    
    const handleRegister = () => {
        axios.post('http://localhost:8080/users/register', {
            username: username,
            email: email,
            password: password,
            passwordVerif: passwordVerif
        }).then(response => {
            console.log(response)
            props.handleLogin();
        }).catch(err => {
            console.log(err)
        });
    }
    
    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }
    
    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    }
    
    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }
    
    const handlePasswordVerifChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPasswordVerif(e.target.value);
    }

    return (
        <div className='register-form'>
            <div>
                <label htmlFor="username">Username</label>
                <br />
                <input onChange={handleUsernameChange} name="username" type="text"></input>
            </div>
            <div>
                <label htmlFor="email">Email</label>
                <br />
                <input onChange={handleEmailChange} name="email" type="text"></input>
            </div>
            <div>
                <label htmlFor="password">Password</label>
                <br />
                <input onChange={handlePasswordChange} name="password" type="password"></input>
            </div>
            <div>
                <label htmlFor="passwordVerif">Password Verification</label>
                <br />
                <input onChange={handlePasswordVerifChange} name="passwordVerif" type="password"></input>
            </div>
            <div>
                <button onClick={handleRegister} className='register-button'>Register</button>
            </div>
            <div>
                <p>Or login <button onClick={props.handleLogin} className='nested-button-link'> here </button></p>
            </div>
        </div>
    )
}

export default Register
