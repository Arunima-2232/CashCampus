import React,{useState} from 'react'
import {USER_BASE_URL} from '../config/api.js'
import { useNavigate } from 'react-router-dom';
import '../styles/LoginStyle.css'

export default function Login()
{
    const [popupMessage,setPopupMessage]=useState("")
    const [userDetails,setUserDetails]=useState(
        {
            emailId:"",
            password:""
        }
    )

    const navigate = useNavigate();
    const popupStyle={
        display: popupMessage? 'block':'none'
    }

    function toExpenseManager(){
        navigate("/ExpenseManager");
    }

    function checkValidity(event)
    {
        if(Object.values(userDetails).some(val=>!val))
            setPopupMessage("Please fill all details.")
        else if(userDetails.password.toString().length<5||userDetails.password.toString().length>50)
            setPopupMessage("Password should be in range 5 to 50 characters.")
        else
            userLogin(event)

    }

    function toSignup()
    {
        navigate("/Signup");
    }

    async function userLogin(event)
    {
        try{
            event.preventDefault();
            const response=await USER_BASE_URL.post("/login",userDetails,);
            sessionStorage.setItem("Id",userDetails.emailId)
            sessionStorage.setItem("token",response.data.token)
            toExpenseManager();
        }
        catch(error)
        {
            if(error.status===404)
            {
                setPopupMessage("User not found, please signup.")
                toSignup()
            }
            else if(error.status===401)
            {
                setPopupMessage("Wrong password")
            }
            else
            {
                console.log("Please try again later.")
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
            <p className="subtitle">Login</p>
            <form className="form">
                <fieldset>

                <label htmlFor="userID">Enter Email ID</label>
                <input type="email" name="emailId" id="userID" onChange={handleChange} required/>

                <label htmlFor="userPassword">Enter Password</label>
                <input type="password" name="password" id="userPassword" onChange={handleChange} required/>

                <div className="button-group">
                    <button
                    type="submit"
                    className="main-btn"
                    onClick={toSignup}
                    >
                    Signup
                    </button>

                    <button
                    type="button"
                    className="enabledBtn"
                    onClick={checkValidity}
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
