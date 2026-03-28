import React from 'react';
import './VinylCollection.css';

const VinylCollection = ({ vinyls, addToCart }) => {
    return (
        <div className="store-page">

            {/* --- HERO BANNER SECTION --- */}
            <section className="hero-banner">
                <div className="hero-content">
                    <h1>Discover the <span className="highlight">Sound</span></h1>
                    <p>Curated vinyl records from every genre and era. Warm analog sound, timeless art.</p>
                </div>
            </section>

            {/* --- VINYL GRID SECTION --- */}
            <section className="collection-section">
                <h2 className="section-title">Our Collection</h2>

                {!vinyls || vinyls.length === 0 ? (
                    <div className="loading-msg">Loading the vault... or no records available.</div>
                ) : (
                    <div className="vinyl-grid">
                        {vinyls.map((vinyl) => (
                            <div className="vinyl-card" key={vinyl.id}>
                                <div className="vinyl-image-container">
                                    <img
                                        src={vinyl.imageUrl || `https://picsum.photos/seed/${vinyl.id}/400/300`}
                                        alt={vinyl.title}
                                        className="vinyl-image"
                                    />
                                </div>

                                <div className="vinyl-info">
                                    <h3 className="vinyl-title">{vinyl.title}</h3>
                                    <p className="vinyl-artist">{vinyl.artist}</p>

                                    <div className="vinyl-footer">
                                        <div className="price-stock">
                                            <span className="vinyl-price">₹{vinyl.price}</span>
                                            <span className={`vinyl-stock ${vinyl.stockQuantity < 5 ? 'low-stock' : ''}`}>
                                                {vinyl.stockQuantity} in stock
                                            </span>
                                        </div>

                                        <button
                                            className="add-to-cart-btn"
                                            onClick={() => addToCart(vinyl)}
                                            disabled={vinyl.stockQuantity === 0}
                                        >
                                            {vinyl.stockQuantity === 0 ? 'Sold Out' : '🛒 Add'}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </section>
        </div>
    );
};

export default VinylCollection;