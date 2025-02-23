import React from 'react';
import { FaShoppingCart } from 'react-icons/fa';
import './NavbarStyle.css';

const ShoppingCart = ({ cartCount, handleGoToCart }) => {
  return (
    <nav className="navbar">
      <h1 className="navbar-title">Restaurant Menu</h1>
      <div className="cart-icon" onClick={handleGoToCart}>
        <FaShoppingCart />
        <span className="cart-count">{cartCount}</span>
      </div>
    </nav>
  );
};

export default ShoppingCart;
