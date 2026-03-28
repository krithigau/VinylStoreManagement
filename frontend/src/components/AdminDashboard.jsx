import React, { useState, useEffect } from 'react';
import './AdminDashboard.css';

const AdminDashboard = () => {
    const [inventory, setInventory] = useState([]);

    // UI State: Controls what screen the admin is looking at ('TABLE', 'ADD', or 'EDIT')
    const [viewMode, setViewMode] = useState('TABLE');

    // The single state object used for both Adding and Editing
    const [formData, setFormData] = useState({
        title: '', artist: '', price: '', stockQuantity: '', imageUrl: ''
    });

    // Keeps track of which ID we are currently editing
    const [editId, setEditId] = useState(null);

    // --- FETCH LOGIC ---
    const fetchInventory = async () => {
        const token = localStorage.getItem('token');
        try {
            const res = await fetch("http://localhost:8080/api/vinyls", {
                headers: { "Authorization": `Bearer ${token}` }
            });
            const data = await res.json();
            setInventory(data);
        } catch (err) { console.error("Failed to fetch inventory", err); }
    };

    useEffect(() => { fetchInventory(); }, []);

    // --- BUTTON CLICK HANDLERS ---
    const handleAddClick = () => {
        setFormData({ title: '', artist: '', price: '', stockQuantity: '', imageUrl: '' });
        setViewMode('ADD');
    };

    const handleEditClick = (vinyl) => {
        setFormData({
            title: vinyl.title,
            artist: vinyl.artist,
            price: vinyl.price,
            stockQuantity: vinyl.stockQuantity,
            imageUrl: vinyl.imageUrl || ''
        });
        setEditId(vinyl.id);
        setViewMode('EDIT');
    };

    const handleCancel = () => {
        setViewMode('TABLE');
        setEditId(null);
    };

    // --- DELETE LOGIC ---
    const handleDelete = async (id) => {
        if (!window.confirm("Are you sure you want to delete this record?")) return;

        const token = localStorage.getItem('token');
        try {
            const res = await fetch(`http://localhost:8080/api/vinyls/${id}`, {
                method: "DELETE",
                headers: { "Authorization": `Bearer ${token}` }
            });
            if (res.ok) {
                fetchInventory(); // Refresh table after delete
            } else {
                alert("Failed to delete. Check backend logs.");
            }
        } catch (err) { console.error("Delete error", err); }
    };

    // --- SUBMIT LOGIC (Handles both ADD and EDIT) ---
    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');

        const isEditing = viewMode === 'EDIT';
        const url = isEditing
            ? `http://localhost:8080/api/vinyls/${editId}`
            : `http://localhost:8080/api/vinyls`;
        const method = isEditing ? "PUT" : "POST";

        try {
            const res = await fetch(url, {
                method: method,
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(formData),
            });

            if (res.ok) {
                fetchInventory();
                setViewMode('TABLE'); // Go back to table view
            } else {
                alert(`Failed to ${isEditing ? 'update' : 'add'} vinyl.`);
            }
        } catch (err) { console.error("Submit error", err); }
    };

    return (
        <div className="admin-page">
            {/* HEADER SECTION */}
            <div className="admin-header">
                <div>
                    <h1>Inventory Management</h1>
                    <p>Manage your vinyl collection</p>
                </div>
                {viewMode === 'TABLE' && (
                    <button className="add-btn" onClick={handleAddClick}>+ Add Vinyl</button>
                )}
            </div>

            {/* DYNAMIC VIEW RENDERING */}
            {viewMode === 'TABLE' ? (
                /* ---------------- TABLE VIEW ---------------- */
                <div className="admin-card">
                    <table className="admin-table">
                        <thead>
                            <tr>
                                <th>Cover</th>
                                <th>Title</th>
                                <th>Artist</th>
                                <th>Price</th>
                                <th>Stock</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {inventory.map(item => (
                                <tr key={item.id}>
                                    <td>
                                        {/* Auto-generates image if imageUrl is empty */}
                                        <img src={item.imageUrl || `https://picsum.photos/seed/${item.id}/50/50`} alt="cover" className="table-img" />
                                    </td>
                                    <td><strong>{item.title}</strong></td>
                                    <td>{item.artist}</td>
                                    <td>₹{item.price}</td>
                                    <td>{item.stockQuantity}</td>
                                    <td className="action-cell">
                                        <button className="icon-btn edit-btn" onClick={() => handleEditClick(item)}>✏️</button>
                                        <button className="icon-btn delete-btn" onClick={() => handleDelete(item.id)}>🗑️</button>
                                    </td>
                                </tr>
                            ))}
                            {inventory.length === 0 && (
                                <tr><td colSpan="6" style={{textAlign: 'center', padding: '20px'}}>No records found. Add one!</td></tr>
                            )}
                        </tbody>
                    </table>
                </div>
            ) : (
                /* ---------------- FORM VIEW (ADD / EDIT) ---------------- */
                <div className="admin-card form-card">
                    <h3>{viewMode === 'EDIT' ? 'Edit Vinyl' : 'Add New Vinyl'}</h3>
                    <form onSubmit={handleSubmit} className="admin-form">

                        <div className="form-row">
                            <div className="form-group">
                                <label>Title</label>
                                <input value={formData.title} onChange={e => setFormData({...formData, title: e.target.value})} required />
                            </div>
                            <div className="form-group">
                                <label>Artist</label>
                                <input value={formData.artist} onChange={e => setFormData({...formData, artist: e.target.value})} required />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label>Price (₹)</label>
                                <input type="number" step="0.01" value={formData.price} onChange={e => setFormData({...formData, price: e.target.value})} required />
                            </div>
                            <div className="form-group">
                                <label>Stock Quantity</label>
                                <input type="number" value={formData.stockQuantity} onChange={e => setFormData({...formData, stockQuantity: e.target.value})} required />
                            </div>
                        </div>

                        <div className="form-group full-width">
                            <label>Cover Image URL (Optional)</label>
                            <input
                                value={formData.imageUrl}
                                onChange={e => setFormData({...formData, imageUrl: e.target.value})}
                                placeholder="Leave blank for auto-generated image, or paste an image link"
                            />
                        </div>

                        <div className="form-actions">
                            <button type="submit" className="save-btn">
                                {viewMode === 'EDIT' ? 'Update Record' : 'Save Record'}
                            </button>
                            <button type="button" className="cancel-btn" onClick={handleCancel}>Cancel</button>
                        </div>
                    </form>
                </div>
            )}
        </div>
    );
};

export default AdminDashboard;