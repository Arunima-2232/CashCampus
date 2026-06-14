import React, {useState} from 'react'
import {USER_BASE_URL} from '../config/api.js'
import { useNavigate } from 'react-router-dom';
import '../styles/LoginStyle.css'

export default function Signup()
{
    const [popupMessage,setPopupMessage]=useState("")
    const [disable,setDisable]=useState(true)
    const [userDetails,setUserDetails]=useState(
        {
            emailId:"",
            password:"",
            name:"",
            mobileNumber:0,
            role:""
        }
    )

    const navigate = useNavigate();
    const popupStyle={
        display: popupMessage? 'block':'none'
    }

    function toLogin()
    {
        navigate("/");
    }

    function checkValidity(event)
    {
        event.preventDefault();
        if(Object.values(userDetails).some(val=>!val))
            setPopupMessage("Please fill all details.")
        else if(/[a-zA-Z]/.test(userDetails.mobileNumber)||userDetails.mobileNumber.toString().length!==10)
            setPopupMessage("Mobile number should have only 10 numbers")
        else if(/[0-9]/.test(userDetails.name)===true||userDetails.name.toString().length<5||userDetails.name.toString().length>20)
            setPopupMessage("Name should include only 5 to 20 alphabets")
        else if(userDetails.password.toString().length<5||userDetails.password.toString().length>50)
            setPopupMessage("Password should be in range 5 to 50 characters.")
        else
            userRegistration(event)
    }

    async function userRegistration(event)
    {
        try{

            event.preventDefault();
            await USER_BASE_URL.post("/register",userDetails);
            setPopupMessage("Successful Registration! Please proceed to login")
            setDisable(false)
        }
        catch(error)
        {
            if(error.status===409)
            {
                setPopupMessage("User already exists.")
            }
            else
            {
                setPopupMessage("Please try again later.")
            }
        }
    }

    function handleChange(e)
    {
        const {name,value}=e.target;

        setUserDetails(prev => ({
        ...prev,
        [name]: value
        }));
    }

    return(
        <>
        <div id="popup" style={popupStyle}>
            {popupMessage}
            <br/>
            <button 
            onClick={()=>{setPopupMessage("")}}
            >OK</button>
        </div>
        <div className="container">
            <p className="title">ExpenseTracker</p>
            <p className="subtitle">Signup</p>

            <form className="form">
                <fieldset>

                <label htmlFor="userID">Enter Email ID</label>
                <input type="email" name="emailId" id="userID" onChange={handleChange} required/>

                <label htmlFor="userPassword">Enter Password</label>
                <input type="password" name="password" id="userPassword" onChange={handleChange} required/>

                <label htmlFor="userName">Enter Name</label>
                <input type="text" name="name" id="userName" onChange={handleChange} required/>

                <label htmlFor="userNumber">Enter Mobile Number</label>
                <input type="text" name="mobileNumber" id="userNumber" onChange={handleChange} required/>

                <label htmlFor="userRole">Pick Role</label>
                <select id="userRole" name="role" onChange={handleChange} required>
                    <option>Please select a role</option>
                    <option value="user">User</option>
                    <option value="admin">Admin</option>
                </select>

                <div className="button-group">
                    <button
                    type="submit"
                    onClick={checkValidity}
                    className="main-btn">
                    Signup
                    </button>

                    <button
                    type="button"
                    disabled={disable}
                    className={disable?"disabledBtn":"enabledBtn"}
                    onClick={toLogin}
                    >
                    Login
                    </button>
                </div>

                </fieldset>
            </form>
            </div>
        </>
    )
}
