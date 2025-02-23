import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import ShoppingCart from "./RestaurantMenu";
import "./UserMenuStyle.css";
import { Navbar } from 'react-bootstrap';

const UserMenu = () => {
  const location = useLocation();
  const { restaurantName } = location.state || {};
  const [dishes, setDishes] = useState([]);
  const [quantities, setQuantities] = useState({});
  const [cart, setCart] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    console.log("Restaurant Name:", restaurantName);

    const fetchData = async () => {
      try {
        const response = await fetch(`/api/menu-items/${restaurantName}`);
        const data = await response.json();
        console.log("Fetched data:", data); // Debugging line
        const sortedData = data.sort((a, b) => b.available - a.available);
        setDishes(sortedData);
        const initialQuantities = sortedData.reduce((acc, dish) => {
          acc[dish.idMenuItem] = 1;
          return acc;
        }, {});
        setQuantities(initialQuantities);

        setLoading(false);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    if (restaurantName) {
      fetchData();
    }
  }, [restaurantName]);

  const handleQuantityChange = (dishId, amount) => {
    setQuantities((prevQuantities) => ({
      ...prevQuantities,
      [dishId]: Math.max(1, prevQuantities[dishId] + amount),
    }));
    console.log("Quantities: ", quantities)
  };

  const handleQuantityInputChange = (dishId, value) => {
    if (value === "" || (/^\d+$/.test(value) && parseInt(value, 10) > 0)) {
      if (value.length > 1 && value.startsWith("0")) {
        setErrors((prevErrors) => ({
          ...prevErrors,
          [dishId]: "Please enter a valid quantity (1 or more).",
        }));
      } else {
        setQuantities((prevQuantities) => ({
          ...prevQuantities,
          [dishId]: value,
        }));
        setErrors((prevErrors) => ({
          ...prevErrors,
          [dishId]: "",
        }));
      }
    } else {
      setErrors((prevErrors) => ({
        ...prevErrors,
        [dishId]: "Please enter a valid quantity (1 or more).",
      }));
    }
  };

  const handleQuantityBlur = (dishId) => {
    const newValue = Math.max(1, parseInt(quantities[dishId], 10) || 1);
    setQuantities((prevQuantities) => ({
      ...prevQuantities,
      [dishId]: newValue,
    }));
    setErrors((prevErrors) => ({
      ...prevErrors,
      [dishId]: "",
    }));
  };

  const handleAddToCart = async (dish) => {
    const quantity = parseInt(quantities[dish.idMenuItem], 10);
    const totalItemsInCart = cart.reduce(
      (total, item) => total + item.quantity,
      0
    );

    if (totalItemsInCart + quantity > 100) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        [dish.idMenuItem]: "You cannot add more than 100 items to the cart.",
      }));
      return;
    }

    if (dish.available && quantity > 0) {
      setCart((prevCart) => {
        const existingDish = prevCart.find((item) => item.idMenuItem === dish.idMenuItem);
        if (existingDish) {
          return prevCart.map((item) =>
            item.idMenuItem === dish.idMenuItem
              ? { ...item, quantity: item.quantity + quantity }
              : item
          );
        } else {
          return [...prevCart, { ...dish, quantity }];
        }
      });

      try {
        await fetch("/order/cart", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ id: dish.idMenuItem, quantity }),
        });
        console.log(`Added ${quantity} x ${dish.name} to cart`);
      } catch (error) {
        console.error("Error adding to cart:", error);
      }

      setQuantities((prevQuantities) => ({
        ...prevQuantities,
        [dish.idMenuItem]: 1,
      }));
    } else {
      setErrors((prevErrors) => ({
        ...prevErrors,
        [dish.idMenuItem]: "Please enter a valid quantity (1 or more).",
      }));
    }
  };

  const handleGoToCart = () => {
    console.log("Navigating to cart with items:", cart);
    navigate("/cart", { state: { cartItems: cart } });
  };

  const totalItemsInCart = cart.reduce(
    (total, item) => total + item.quantity,
    0
  );

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Navbar cartCount={totalItemsInCart} handleGoToCart={handleGoToCart} />
      <div className="user-menu">
        <h1 className="restaurant-name">{restaurantName}</h1>
        {dishes.map((dish) => (
          <div
            key={dish.idMenuItem}
            className={`menu-item ${!dish.available ? "unavailable" : ""}`}
          >
            <img
              src={`/images/menu-items/${dish.idMenuItem}.png` || "path/to/default-image.jpg"}
              alt={dish.name}
              className="menu-item-image"
            />
            <div className="menu-item-details">
              <h3 className="dish-name">{dish.name}</h3>
              <p className="dish-description">{dish.description}</p>
              <p className="dish-availability">Available: {console.log(dish.available)}</p> {/* Debugging line */}
            </div>
            <div className="menu-item-controls">
              <p className="dish-price">
                <strong>Price: {dish.price} RON</strong>
              </p>
              <div className="quantity-control">
                <button
                  className="quantity-button"
                  onClick={() => handleQuantityChange(dish.idMenuItem, -1)}
                  disabled={!dish.available}
                >
                  -
                </button>
                <input
                  type="text"
                  value={quantities[dish.idMenuItem]}
                  onInput={(e) =>
                    handleQuantityInputChange(dish.idMenuItem, e.target.value)
                  }
                  onBlur={() => handleQuantityBlur(dish.idMenuItem)}
                  className="quantity-input"
                />
                <button
                  className="quantity-button"
                  onClick={() => handleQuantityChange(dish.idMenuItem, 1)}
                  disabled={!dish.available}
                >
                  +
                </button>
              </div>
              {errors[dish.idMenuItem] && (
                <p className="error-message">{errors[dish.idMenuItem]}</p>
              )}
              <button
                className="add-to-cart"
                onClick={() => handleAddToCart(dish)}
                disabled={!dish.available || quantities[dish.idMenuItem] <= 0}
              >
                Add
              </button>
            </div>
          </div>
        ))}
        <button className="go-to-cart" onClick={handleGoToCart}>
          Go to Cart
        </button>
      </div>
    </div>
  );
};

export default UserMenu;
