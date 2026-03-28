import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Checkout.css';

const Checkout = ({ cartItems, setCart }) => {
    const navigate = useNavigate();
    const [isProcessing, setIsProcessing] = useState(false);

    const total = cartItems.reduce((acc, item) => acc + (item.vinyl.price * item.quantity), 0);

    const handleConfirmOrder = async () => {
        setIsProcessing(true);
        const token = localStorage.getItem('token');
        const cartId = localStorage.getItem('cartId');

        try {
            // Here you would typically call an endpoint like /api/orders/create
            // For now, we'll simulate the success and clear the cart
            setTimeout(() => {
                alert("Order Placed Successfully! Your vinyls are on the way.");
                setCart([]); // Clear frontend state
                navigate('/'); // Go back to collection
            }, 1500);
        } catch (error) {
            console.error("Checkout failed:", error);
            setIsProcessing(false);
        }
    };

    return (
        <div className="checkout-container">
            <h1>Order Summary</h1>
            <div className="checkout-card">
                <div className="order-items">
                    {cartItems.map((item) => (
                        <div key={item.id} className="summary-item">
                            <span>{item.vinyl.title} x {item.quantity}</span>
                            <span>₹{item.vinyl.price * item.quantity}</span>
                        </div>
                    ))}
                </div>
                <hr />
                <div className="summary-total">
                    <h3>Total Amount</h3>
                    <h3>₹{total.toFixed(2)}</h3>
                </div>
                <button
                    className="confirm-btn"
                    onClick={handleConfirmOrder}
                    disabled={isProcessing || cartItems.length === 0}
                >
                    {isProcessing ? "Processing..." : "Confirm Purchase"}
                </button>
            </div>
        </div>
    );
};

export default Checkout;