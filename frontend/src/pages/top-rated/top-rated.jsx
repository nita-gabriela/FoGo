import { useNavigate, useLocation } from 'react-router-dom';
import './top-rated.css';
import { useState, useEffect } from 'react';
import Map from '../map/map';

export default function TopRated() {
    const [restaurants, setRestaurants] = useState([]);
    const [categories, setCategories] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [coordinates, setCoordinates] = useState({ latitude: null, longitude: null });
    const [showPopover, setShowPopover] = useState(false);

    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const latitude = queryParams.get('lat');
        const longitude = queryParams.get('lng');

        if (latitude && longitude) {
            setCoordinates({ latitude: parseFloat(latitude), longitude: parseFloat(longitude) });
            const categoryQuery = selectedCategories.map(category => `categories=${category}`).join('&');
            console.log(categoryQuery);
            const url = `/api/restaurants/nearby?${categoryQuery}`;
            console.log(url);
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ latitude: parseFloat(latitude), longitude: parseFloat(longitude) })
            })
            .then(response => response.json())
            .then(data => {
                setRestaurants(data);
            })
            .catch((error) => console.error('Error fetching data:', error));
        }
    }, [location.search, selectedCategories]);

    const navigateToLogin = () => {
        navigate('/login');
    };


    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/categories')
                if(!response.ok) {
                    console.log("Network error");
                }
                const data = await response.json();
                setCategories(data);
            }
            catch (error) {
                console.log(error);
            }
        }
        fetchCategories();
    }, []);

    const handleCategoryChange = (selectedCategory) => {
        setSelectedCategories(previousSelectedCategories => {
            if(previousSelectedCategories.includes(selectedCategory)) {
                return previousSelectedCategories.filter(category => category !== selectedCategory);
            }
            else {
                return [...previousSelectedCategories, selectedCategory];
            }
        });
    }


    const handleRestaurantClick = (restaurantName) => {
        navigate('/menu-items', { state: { restaurantName } });
    };

    const togglePopover = () => {
        setShowPopover(!showPopover);
    };

    return (
        <div className='background' id='background-top-rated'>
            <div className="flex-container">
                <img src="https://trello.com/1/cards/66ab4be9e3b70792bb276db2/attachments/66c49061d805263a8606f8bd/previews/66c49062d805263a8606f997/download/text.png" alt="logo" id='logo'/>

                <div>
                    <button className='pretty-button' onClick={togglePopover}>Map</button>
                    <span> </span>
                    <button className='pretty-button' onClick={() => navigateToLogin()}>
                        Login/Sign Up
                    </button>
                </div>
            </div>
            <div className="category-container">
                <label id="category-label" htmlFor="categories">Category</label>
                <div id="categories">
                    {categories.map(category => (
                        <label key={category.name} className="category">
                            <input
                            className="category-input"
                            type="checkbox"
                            value={category.name}
                            name="categories"
                            onChange={() => {handleCategoryChange(category.name);}}>
                            </input>
                            {category.name}
                        </label>
                    ))}
                </div>
            </div>
            <div className="container">
                {Array.isArray(restaurants) ? (
                    restaurants.map((restaurant, index) => (
                        <div className="column" key={index}>
                            <div className="restaurant-box" onClick={() => handleRestaurantClick(restaurant.name)}>
                               <img src={`/images/restaurants/${restaurant.idRestaurant}.png`} alt={restaurant.name}/>
                                <div className="info">
                                    <span className="name">{restaurant.name}</span>
                                    <span className="rating">{restaurant.rating}</span>
                                </div>
                                <div className="description">
                                    {restaurant.description}
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No restaurants available</p>
                )}
            </div>
            {showPopover && (
                <div>
                    {coordinates.latitude && coordinates.longitude && (
                        <Map restaurants={restaurants} coordinates={coordinates} />
                    )}
                </div>
            )}
        </div>
    );
}
