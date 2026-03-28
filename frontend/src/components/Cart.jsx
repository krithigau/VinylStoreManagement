import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Cart.css';

const Cart = ({ isOpen, onClose, cartItems, removeFromCart }) => {
    const navigate = useNavigate();

    const total = cartItems.reduce((acc, item) => {
        const price = item.vinyl?.price || 0;
        return acc + (price * item.quantity);
    }, 0);

    const handleProceedToCheckout = () => {
        onClose(); // Close the sidebar first
        navigate('/checkout'); // Redirect to checkout page
    };

    // We don't return null anymore because the CSS handles visibility
    // using the 'open' class for a smooth slide-in animation.
    return (
        <div className={`cart-overlay ${isOpen ? 'open' : ''}`} onClick={onClose}>
            <div className="cart-panel" onClick={(e) => e.stopPropagation()}>
                <div className="cart-header">
                    <h2>Your Cart</h2>
                    <button className="close-btn" onClick={onClose}>&times;</button>
                </div>

                <div className="cart-content">
                    {cartItems.length === 0 ? (
                        <p className="empty-msg">Cart is empty</p>
                    ) : (
                        cartItems.map((item) => (
                            <div key={item.id} className="cart-item">
                                <div className="cart-item-info">
                                    <h4>{item.vinyl?.title}</h4>
                                    <p>₹{item.vinyl?.price} x {item.quantity}</p>
                                </div>
                                <button
                                    className="remove-item"
                                    onClick={() => removeFromCart(item.id)}
                                >
                                    🗑️
                                </button>
                            </div>
                        ))
                    )}
                </div>

                <div className="cart-footer">
                    <div className="cart-total">
                        <span>Total:</span>
                        <span>₹{total.toFixed(2)}</span>
                    </div>
                    <button
                        className="checkout-btn"
                        disabled={cartItems.length === 0}
                        onClick={handleProceedToCheckout}
                    >
                        Proceed to Checkout
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Cart;