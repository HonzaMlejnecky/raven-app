import '../../index-out.css'
import './register.css'

// eslint-disable-next-line @typescript-eslint/no-explicit-any
function Register(props: any) {

    return (
        <div className='register-form'>
            <div>
                <label htmlFor="username">Username</label>
                <br />
                <input name="username" type="text"></input>
            </div>
            <div>
                <label htmlFor="email">Email</label>
                <br />
                <input name="email" type="text"></input>
            </div>
            <div>
                <label htmlFor="password">Password</label>
                <br />
                <input name="password" type="password"></input>
            </div>
            <div>
                <label htmlFor="passwordVerif">Password Verification</label>
                <br />
                <input name="passwordVerif" type="password"></input>
            </div>
            <div>
                <button className='register-button'>Register</button>
            </div>
            <div>
                <p>Or login <button onClick={props.handleLogin} className='nested-button-link'> here </button></p>
            </div>
        </div>
    )
}

export default Register
