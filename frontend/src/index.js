import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import Register from "./pages/Autentification/Register";
import Homepage from "./pages/homepage/homepage";
import TopRated from "./pages/top-rated/top-rated";
import Login from "./pages/Autentification/Login";
import reportWebVitals from "./reportWebVitals";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import UserMenu from "./pages/UserMenu/UserMenu";
import Cart from "./pages/Cart/Cart";

const router = createBrowserRouter([
   {
     path: "/",
     element: <Homepage />,
   },
   {
     path: "/login",
     element: <Login />,
   },
  {
    path: "/menu-items",
    element: <UserMenu />,
  },
  {
    path: "/top-rated",
    element: <TopRated />,
  },
  {
    path: "/Register",
    element: <Register />,
  },
  {
    path: "/Cart",
    element: <Cart />,
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
