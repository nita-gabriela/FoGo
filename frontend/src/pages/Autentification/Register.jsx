import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Register.css";

const Register = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handler = (setField, fieldName, validate) => {
    return function (event) {
      const value = event.target.value;
      setField(value);
      setErrors((prevErrors) => {
        const newErrors = { ...prevErrors };
        delete newErrors[fieldName];
        return newErrors;
      });
      if (validate) {
        const error = validate(value);
        if (error) {
          setErrors((prevErrors) => ({
            ...prevErrors,
            [fieldName]: error,
          }));
        }
      }
    };
  };

  const validateName = (name) => {
    return /^[a-zA-Z]{2,}([ -][a-zA-Z]{2,})+$/.test(name)
      ? ""
      : "Enter a valid name";
  };

  const validateEmail = (email) => {
    return /^[a-zA-Z0-9._]+(?<!\.)@[a-zA-Z]+\.[a-zA-Z]+$/.test(email)
      ? ""
      : "Enter a valid email";
  };

  const validatePassword = (password) => {
    return password.length >= 8 &&
      password.length <= 12 &&
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d@$!%#^<>*?&.,/{}\s]{8,12}$/.test(
        password
      )
      ? ""
      : "The password must contain between 8 and 12 characters, including at least one uppercase letter, one lowercase letter, and one number";
  };

  const validatePhoneNumber = (phoneNumber) => {
    return /^\d{10}$/.test(phoneNumber)
      ? ""
      : "The phone number must have 10 digits.";
  };

  const getFieldValue = (object, fieldName) => {
    return object[fieldName];
  };

  const fields = [
    {
      label: "Name",
      type: "text",
      value: name,
      id: "name",
      handler: handler(setName, "name", validateName),
      error: "name",
    },
    {
      label: "Email",
      type: "text",
      value: email,
      id: "email",
      handler: handler(setEmail, "email", validateEmail),
      error: "email",
    },
    {
      label: "Password",
      type: "password",
      value: password,
      id: "password",
      handler: handler(setPassword, "password", validatePassword),
      error: "password",
    },
    {
      label: "Confirm Password",
      type: "password",
      value: confirmPassword,
      id: "confirmPassword",
      handler: handler(setConfirmPassword, "confirmPassword", validatePassword),
      error: "confirmPassword",
    },
    {
      label: "Phone Number",
      type: "text",
      value: phoneNumber,
      id: "phoneNumber",
      handler: handler(setPhoneNumber, "phoneNumber", validatePhoneNumber),
      error: "phoneNumber",
    },
  ];
  const handleSubmit = async (e) => {
    e.preventDefault();

    const newErrors = {};
    const nameError = validateName(name);
    const emailError = validateEmail(email);
    const passwordError = validatePassword(password);
    const phoneNumberError = validatePhoneNumber(phoneNumber);

    if (nameError) {
      newErrors.name = nameError;
    }

    if (emailError) {
      newErrors.email = emailError;
    }

    if (password !== confirmPassword) {
      newErrors.password = "The passwords aren't the same";
    }
    if (passwordError) {
      newErrors.password = passwordError;
    }
    if (phoneNumberError) {
      newErrors.phoneNumber = phoneNumberError;
    }
    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      try {
        const response = await fetch("/api/register", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email,
            password,
            name,
            phoneNumber,
          }),
        });
        const data = await response.json();

        if (data.error) {
          const serverErrors = {};
          if (data.error.includes("Email is already in use!")) {
            serverErrors.email = "This email is already registered.";
          } else if (data.error.includes("Phone number already in use!")) {
            serverErrors.phoneNumber = "Phone number is already in use!";
          }
          setErrors(serverErrors);
        } else {
          navigate("/login");
        }
      } catch (error) {
        console.error(error);
      }
    }
  };
  return (
    <div id="body-register">
      <h1 className="navbar-register">Register</h1>
      <div className="container-register">
        <form className="Register" onSubmit={handleSubmit}>
          {fields.map(({ label, type, value, id, handler, error }) => (
            <div key={label}>
              <label id="label-register">{label}</label>
              <input
                className="input-register"
                type={type}
                value={value}
                id={id}
                onChange={handler}
              />
              {getFieldValue(errors, error) && (
                <div className="error">{getFieldValue(errors, error)}</div>
              )}
            </div>
          ))}
          <div className="button-register">
            <button id="button-register">Submit</button>
          </div>
          <p className="center-link-register">
            <a
              className="register-a"
              href="#"
              onClick={() => navigate("/Login")}
            >
              Do you have an account?
            </a>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Register;
