import React, { useState } from "react";
import "./Cart.css";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";

const ProcessingPopup = () => (
  <div className="popup">
    <div className="popup-content">
      <p>Payment is being processed...</p>
      <div className="spinner"></div>
    </div>
  </div>
);

const ThankYouPopup = () => (
  <div className="popup">
    <div className="popup-content">
      <p>Thank you!</p>
    </div>
  </div>
);

const EmptyCart = ({ onBackToRestaurants }) => (
  <div className="empty-cart">
    <div className="empty-cart-message">
      <p className="empty-cart-text">The shopping cart is empty.</p>
      <img src="empty_cart.png" className="empty-cart-image"></img>
      <button
        className="empty-cart-button pretty-button addProducts-button"
        onClick={onBackToRestaurants}
      >
        Back to restaurants
      </button>
    </div>
  </div>
);

const Cart = () => {
  //const [products, setProducts] = useState();
  const [products, setProducts] = useState([
      { id: 1, name: "Caesar Salad", price: 20.0, image: "salata.png" },
      { id: 2, name: "Tiramisu", price: 25.0, image: "tiramisu.png" },

    ]);
  const [quantities, setQuantities] = useState(Array(products.length).fill(1));
  const [isProcessing, setIsProcessing] = useState(false);
  const [showThankYou, setShowThankYou] = useState(false);
  const [address, setAddress] = useState("");
  const [addressError, setAddressError] = useState("");
  const [quantityErrors, setQuantityErrors] = useState(
    Array(products.length).fill("")
  );
  const navigate = useNavigate();

  const handleIncrement = (index) => {
    const newQuantities = [...quantities];
    const newQuantityErrors = [...quantityErrors];
    if (newQuantities[index] < 100) {
      newQuantities[index] += 1;
      newQuantityErrors[index] = "";
    }
    setQuantities(newQuantities);
    setQuantityErrors(newQuantityErrors);
  };

  const handleDecrement = (index) => {
    const newQuantities = [...quantities];
    const newQuantityErrors = [...quantityErrors];
    if (newQuantities[index] > 1) {
      newQuantities[index] -= 1;
      newQuantityErrors[index] = "";
    }
    setQuantities(newQuantities);
    setQuantityErrors(newQuantityErrors);
  };

  const handlePay = () => {
    let hasErrors = false;

    if (address.trim() === "") {
      setAddressError("Please enter an address.");
      hasErrors = true;
    }

    quantityErrors.forEach((error) => {
      if (error !== "") {
        hasErrors = true;
      }
    });

    if (hasErrors) {
      return;
    }
    console.log(products, quantities);
    setIsProcessing(true);
    setTimeout(() => {
      setIsProcessing(false);
      setShowThankYou(true);
      setTimeout(() => {
        setShowThankYou(false);
        handleBackToRestaurants();
      }, 3000);
    }, 3000);
  };

  const handleAddProducts = () => {
    navigate("/menu-items");
  };
  const handleRemove = (id) => {
    const newProducts = products.filter((product) => product.id !== id);
    const newQuantityErrors = quantityErrors.filter(
      (_, index) => products[index].id !== id
    );
    const newQuantities = quantities.filter(
      (_, index) => products[index].id !== id
    );
    setProducts(newProducts);
    setQuantities(newQuantities);
    setQuantityErrors(newQuantityErrors);
  };

  const handleBackToRestaurants = () => {
    navigate("/top-rated");
  };

  const handleQuantityChange = (index, value) => {
    const newQuantities = [...quantities];
    const newQuantityErrors = [...quantityErrors];
    if (value === "") {
      newQuantities[index] = "";
    } else {
      const numericValue = parseInt(value, 10);
      if (!isNaN(numericValue) && numericValue >= 1 && numericValue <= 100) {
        newQuantities[index] = numericValue;
        newQuantityErrors[index] = "";
      } else {
        newQuantityErrors[index] = "Please enter a number between 1 and 100.";
      }
    }
    setQuantities(newQuantities);
    setQuantityErrors(newQuantityErrors);
  };

  const totalPrice = products
    .reduce((acc, product, index) => acc + product.price * quantities[index], 0)
    .toFixed(2);

  if (products.length === 0) {
    return <EmptyCart onBackToRestaurants={handleBackToRestaurants} />;
  }

  return (
    <div>
      <nav className="cart-navbar">Cart</nav>
      <div id="cart-container">
        <div
          id="cart-blur"
          className={isProcessing || showThankYou ? "blur" : ""}
        >
          <div id="product-container">
            {products.map((product, index) => (
              <div className="product" key={product.id}>
                <div className="product-details">
                  <img className="menu-item-image" src={product.image} />
                  <div className="name-quantity-error">
                    <div className="name-quantity">
                      <div className="product-name">{product.name}</div>
                      <div className="quantity">
                        <div className="quantity-controls">
                          <button
                            className="quantity-button pretty-button"
                            onClick={() => handleDecrement(index)}
                          >
                            -
                          </button>
                          <input
                            className="quantity-input"
                            type="text"
                            value={quantities[index]}
                            onChange={(e) =>
                              handleQuantityChange(index, e.target.value)
                            }
                            onBlur={(e) => {
                              if (e.target.value === "") {
                                handleQuantityChange(index, "1");
                              }
                            }}
                          />
                          <button
                            className="quantity-button pretty-button"
                            onClick={() => handleIncrement(index)}
                          >
                            +
                          </button>
                        </div>

                        <button
                          className={`remove-button pretty-button ${
                            quantities[index] === 1 ? "move-up" : ""
                          }`}
                          onClick={() => handleRemove(product.id)}
                        >
                          <FontAwesomeIcon icon={faTrash} />
                        </button>
                      </div>
                    </div>
                    {quantityErrors[index] && (
                      <div className="error-message error">
                        {quantityErrors[index]}
                      </div>
                    )}
                  </div>
                </div>
                <div className="product-info">
                  <div className="product-price">
                    Price: {quantities[index]}x{product.price} Ron=
                    {(product.price * quantities[index]).toFixed(2)}Ron
                  </div>
                </div>
              </div>
            ))}
          </div>
          <div id="add-button">
            <button
              className="add-products-button pretty-button"
              onClick={handleAddProducts}
            >
              Add products
            </button>
          </div>
          <div id="address-container">
            <div id="address-label">Address</div>
            <input
              type="text"
              id="address-input"
              placeholder="Enter your address"
              value={address}
              onChange={(e) => {
                setAddress(e.target.value);
                setAddressError("");
              }}
            />
          </div>
          {addressError && (
            <div className="error-message error">{addressError}</div>
          )}

          <div id="total-container">
            <div id="total-label">TOTAL: {totalPrice} Ron</div>
            <button
              id="pay-button"
              className="pretty-button"
              onClick={handlePay}
              disabled={isProcessing || showThankYou}
            >
              Pay
            </button>
          </div>
        </div>
        {isProcessing && <ProcessingPopup />}
        {showThankYou && <ThankYouPopup />}
      </div>
    </div>
  );
};

export default Cart;
