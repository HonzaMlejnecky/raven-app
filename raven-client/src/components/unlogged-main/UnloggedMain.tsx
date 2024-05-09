import '../../index-out.css'
import React from 'react'
import './unloggedMain.css'
import Login from '../login/Login';
import Register from '../register/Register';

function UnloggedMain() {

    const [showLogin, setShowLogin] = React.useState(false);
    const [showRegister, setShowRegister] = React.useState(false);

    const handleLogin = () => {
        setShowLogin(true);
        setShowRegister(false);
    }

    const handleRegister = () => {
        setShowRegister(true);
        setShowLogin(false);
    }

    return (
        <>
            {
                showLogin ? <Login handleRegister={handleRegister}/> : <> </>
            }
            {
                showRegister ? <Register handleLogin={handleLogin}/> : <> </>
            }
            {
                !showRegister && !showLogin ? <main className='unloged-main-wrapper'>
                    <h1 className='unlogged-h1'>You are not logged in</h1>
                    <div className='login-button-container'>
                        <button onClick={handleLogin} className='login-button'>Log in</button>
                    </div>
                    <div className='split-info-container'>
                        <hr />
                        Or create an account
                        <hr />
                    </div>
                    <div>
                        <button onClick={handleRegister} className='login-button'>Register</button>
                    </div>
                </main>
                : <> </>
            }
        </>
    )
}

export default UnloggedMain
