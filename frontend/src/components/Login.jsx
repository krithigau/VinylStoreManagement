import React, { useState } from 'react';
import { loginUser } from '../api';
import './Login.css';
import { useNavigate } from 'react-router-dom';
const Login = ({ onLoginSuccess }) => {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [error, setError] = useState('');

    const handleChange = (e) => {
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };
const [isLoading, setIsLoading] = useState(false); //  state
const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
        const data = await loginUser(credentials.username, credentials.password);

        if (data.token) {
            // 1. Save the Security Token
            localStorage.setItem('token', data.token);

            // 2. Save the User ID (Useful for profile pages)
            localStorage.setItem('userId', data.userId);

            // 3. Save the REAL Cart ID (This fixes the 400 error when adding to cart!)
            localStorage.setItem('cartId', data.cartId);
            // Inside your login success logic
            localStorage.setItem('userRole', data.role); // Ensure your backend sends 'role'
            console.log("Logged in as:", data.role);
            onLoginSuccess();
        } else {
            setError('Incorrect username or password');
        }
    } catch (err) {
        // Detailed error for debugging
        console.error("Login Error:", err);
        setError('Server connection failed. Is Spring Boot running?');
    }
};

    return (
        <div className="login-page">
            <div className="login-overlay"></div>
            <div className="login-card">
                <div className="login-header">
                    <div className="vinyl-logo">💿</div>
                    <h1>Resonance</h1>
                    <p>Vinyl Vault</p>
                </div>

                <form className="login-form" onSubmit={handleSubmit}>
                    <h2>Welcome Back</h2>
                    {error && <div className="error-banner">{error}</div>}

                    <div className="input-group">
                        <input
                            type="text"
                            name="username"
                            value={credentials.username}
                            placeholder="Username"
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <input
                            type="password"
                            name="password"
                            value={credentials.password}
                            placeholder="Password"
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <button type="submit" className="login-btn" disabled={isLoading}>
                        {isLoading ? 'Signing In...' : 'Sign In'}
                    </button>
                    <p style={{marginTop: '15px'}}>
                        New to Resonance? <span onClick={() => navigate('/register')} style={{color: '#e65100', cursor: 'pointer', fontWeight: 'bold'}}>Create an account</span>
                    </p>
                </form>
            </div>
        </div>
    );
};

export default Login;