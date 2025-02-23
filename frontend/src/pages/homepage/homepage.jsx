import './homepage.css';
import './city-input/city-input.css';
import './top-bar/top-bar.css';
import '../../shared/styles/styles.css';
import '../../shared/pretty-button/pretty-button.css';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function Homepage() {
  const navigate = useNavigate();
  const [location, setLocation] = useState('');

  const navigateToLogin = () => {
    navigate('/login');
  };

  const navigateToTopRated = async (e) => {
      if (e.key === 'Enter') {
        try {
          const response = await fetch('/api/location', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(location),
          });

          if (response.ok) {
            const coordinates = await response.json();
            navigate(`/top-rated?lat=${coordinates[0]}&lng=${coordinates[1]}`);
            console.log("Coordinates:", coordinates);
          } else {
            console.error('Failed to fetch coordinates');
          }
        } catch (error) {
          console.error('Error:', error);
        }
      }
  };


  return (
    <div className='background'>
      <div className="flex-container">
        <img src="https://trello.com/1/cards/66ab4be9e3b70792bb276db2/attachments/66c49061d805263a8606f8bd/previews/66c49062d805263a8606f997/download/text.png" alt="logo" id='logo'/>
        <div>
          <button className='pretty-button' onClick={() => navigateToLogin()}>
            Login/Sign Up
          </button>
        </div>
      </div>
      <div>
        <h1 id='homepage-title'>FoGo, a food ordering site</h1>
      </div>
      <div id='input-container'>
        <input
          type="text"
          placeholder="Enter your location..."
          id='city-input'
          value={location}
          onChange={(e) => setLocation(e.target.value)}
          onKeyPress={navigateToTopRated}
        />
      </div>
    </div>
  );
}
