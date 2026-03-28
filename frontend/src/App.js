import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import Register from './components/Register';
import VinylCollection from './components/VinylCollection';
import Login from './components/Login';
import AdminDashboard from './components/AdminDashboard';
import Cart from './components/Cart';
import Checkout from './components/Checkout';
import { fetchVinyls as fetchVinylsFromApi } from './api';
import './App.css';

function App() {
  const [vinyls, setVinyls] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token'));
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole') || 'ROLE_CUSTOMER');
  const [cart, setCart] = useState([]);
  const [isCartOpen, setIsCartOpen] = useState(false);

  const fetchVinylsData = async () => {
    try {
      const data = await fetchVinylsFromApi();
      setVinyls(data);
    } catch (err) { console.error(err); }
  };

  const fetchCartData = () => {
    const currentCartId = localStorage.getItem('cartId');
    const token = localStorage.getItem('token');
    if (currentCartId && token && currentCartId !== "undefined") {
      fetch(`http://localhost:8080/api/cart/${currentCartId}`, {
        headers: { 'Authorization': `Bearer ${token}` }
      })
      .then(res => res.json())
      .then(data => setCart(data.items || []))
      .catch(err => console.error(err));
    }
  };

  useEffect(() => {
    fetchVinylsData();
    if (isLoggedIn) fetchCartData();
  }, [isLoggedIn]);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
    const role = localStorage.getItem('userRole');
    setUserRole(role || 'ROLE_CUSTOMER');
  };

  const logout = () => {
    localStorage.clear();
    setIsLoggedIn(false);
    setUserRole('ROLE_CUSTOMER');
    setCart([]);
    window.location.href = '/login';
  };

  const removeFromCart = async (itemId) => {
    const token = localStorage.getItem('token');
    try {
      const response = await fetch(`http://localhost:8080/api/cart/remove/${itemId}`, {
        method: 'DELETE',
        headers: { 'Authorization': `Bearer ${token}` }
      });
      if (response.ok) {
        setCart(cart.filter(item => item.id !== itemId));
        fetchVinylsData();
      }
    } catch (error) { console.error("Failed to delete item:", error); }
  };

  const addToCart = async (vinyl) => {
    const currentCartId = localStorage.getItem('cartId');
    const token = localStorage.getItem('token');
    try {
      const response = await fetch(`http://localhost:8080/api/cart/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
        body: JSON.stringify({ cartId: parseInt(currentCartId), vinylId: vinyl.id, quantity: 1 })
      });
      if (response.ok) {
        fetchCartData();
        await fetchVinylsData();
      }
    } catch (error) { console.error(error); }
  };

  return (
    <Router>
      <div className="App">
        {isLoggedIn && (
          <header className="main-header">
            <Link to="/" className="logo-link">💿 <span className="logo-text">VinylVault</span></Link>
            <nav className="top-nav">

              {/* If Admin, show Admin link. If Customer, show Cart button. */}
              {userRole === 'ROLE_ADMIN' ? (
                <Link to="/admin" className="nav-item admin-link">⚙️ Admin Dashboard</Link>
              ) : (
                <button className="cart-btn" onClick={() => setIsCartOpen(true)}>
                  🛒 Cart ({cart.reduce((acc, item) => acc + (item.quantity || 0), 0)})
                </button>
              )}

              <button className="logout-btn" onClick={logout}>Logout</button>
            </nav>
          </header>
        )}

        {/* Only render the Cart component if the user is NOT an admin */}
        {userRole !== 'ROLE_ADMIN' && (
          <Cart
              isOpen={isCartOpen}
              onClose={() => setIsCartOpen(false)}
              cartItems={cart}
              removeFromCart={removeFromCart}
          />
        )}

        <main>
          <Routes>
            <Route path="/login" element={isLoggedIn ? <Navigate to="/" /> : <Login onLoginSuccess={handleLoginSuccess} />} />
            <Route path="/register" element={<Register />} />
            <Route path="/" element={isLoggedIn ? <VinylCollection vinyls={vinyls} addToCart={addToCart} /> : <Navigate to="/login" />} />
            <Route path="/admin" element={isLoggedIn && userRole === 'ROLE_ADMIN' ? <AdminDashboard /> : <Navigate to="/" />} />
            <Route path="/checkout" element={isLoggedIn ? <Checkout cartItems={cart} setCart={setCart} /> : <Navigate to="/login" />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;