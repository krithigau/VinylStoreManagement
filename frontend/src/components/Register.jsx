import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css'; // Reuse your login styles for consistency

const Register = () => {
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/users/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                alert("Registration Successful! Please login.");
                navigate('/login');
            } else {
                setMessage("Username already exists or invalid data.");
            }
        } catch (err) {
            setMessage("Server error. Try again later.");
        }
    };

    return (
        <div className="login-page">
            <div className="login-card">
                <h2>Create Account</h2>
                <form className="login-form" onSubmit={handleSubmit}>
                    {message && <div className="error-banner">{message}</div>}
                    <div className="input-group">
                        <input type="text" name="username" placeholder="Username" onChange={handleChange} required />
                    </div>
                    <div className="input-group">
                        <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
                    </div>
                    <div className="input-group">
                        <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
                    </div>
                    <button type="submit" className="login-btn">Register</button>
                    <p onClick={() => navigate('/login')} style={{cursor:'pointer', marginTop:'10px'}}>
                        Already have an account? Login
                    </p>
                </form>
            </div>
        </div>
    );
};

export default Register;