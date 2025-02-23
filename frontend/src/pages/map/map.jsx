import React from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import './map.css';

const customIcon = new L.Icon({
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
    shadowSize: [41, 41],
});

const userIcon = new L.Icon({
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
    shadowSize: [41, 41],
    className: 'custom-user-icon',
});


const Map = ({ restaurants, coordinates }) => {
    const center = [coordinates.latitude, coordinates.longitude];

    return (
        <MapContainer className="map-container" center={center} zoom={13} minZoom={12} maxZoom={17} >
            <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
            <Marker position={center} icon={userIcon}>
                <Popup>
                    <strong>You are here</strong>
                </Popup>
            </Marker>
            {restaurants.map((restaurant, id) => (
                <Marker
                    key={id}
                    position={[restaurant.latitude, restaurant.longitude]}
                    icon={customIcon}
                >
                    <Popup>
                        <strong>{restaurant.name}</strong><br />
                        <div>Rating: {restaurant.rating}</div>
                    </Popup>
                </Marker>
            ))}
        </MapContainer>
    );
};

export default Map;
