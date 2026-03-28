// src/api.js

const BASE_URL = 'http://localhost:8080/api';

export const loginUser = async (username, password) => {
    const response = await fetch(`${BASE_URL}/users/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });

    if (!response.ok) {
        throw new Error('Login failed');
    }

    const data = await response.json();
    console.log("Login successful, Role:", data.role);
    return data;
};

export const fetchVinyls = async () => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${BASE_URL}/vinyls`, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) throw new Error("Failed to fetch vinyls");
    return response.json();
};

export const addToCartApi = async (cartId, vinylId, quantity = 1) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${BASE_URL}/cart/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
            cartId: parseInt(cartId),
            vinylId: vinylId,
            quantity: quantity
        })
    });
    if (!response.ok) throw new Error("Failed to add to cart");
    return response.json();
};