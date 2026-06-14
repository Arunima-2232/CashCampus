//import axios from 'axios';
import {MAIN_BASE_URL} from '../config/api';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import '../styles/ExpenseManagerStyle.css'

export default function ExpenseManager() {

  const userId=sessionStorage.getItem("Id")

  const [income,setIncome]=useState(0)
  const [expenditure,setExpenditure]=useState(0)
  const [popupMessage,setPopupMessage]=useState("")
  const [expense,setExpense]=useState({
    id:0,
    category:"",
    amount:0,
    title:"",
    description:"",
    type:""
  })
  const [searchValue,setSearchValue]=useState("")
  const [typeValue,setTypeValue]=useState("")
  const [entries,setEntry]=useState([])
  const [isVisible,setVisibility]=useState(true)

      const popupStyle={
        display: popupMessage? 'block':'none'
    }

    const navigate = useNavigate();
    const token=sessionStorage.getItem("token")

    function toLogin()
    {
        navigate("/");
        sessionStorage.clear();
    }

  async function loadExpenses(){
    try{
      const response = await MAIN_BASE_URL.post(
      "/get",
      {},
      {
        headers: {
          Authorization: `${token}`
        }
      }
    );
      setEntry(response.data.reverse());

      let totalIncome=0
      let totalExpenditure=0
      response.data.forEach((e)=>
      {
        if(e.type==="Income")
          totalIncome+=e.amount
        else
          totalExpenditure+=e.amount
      })
      setIncome(totalIncome)
      setExpenditure(totalExpenditure)
    }catch(error)
    {
      setPopupMessage(error.response.data.message)
    }
  };

  useEffect(()=>{
  loadExpenses();
},[]);

  //filtering
  async function filter(event)
  {
    if(!event.target.value)
    {
      loadExpenses();
      setTypeValue("")
      return;
    }
    const userRequest={"type":event.target.value}
    setTypeValue(userRequest.type)
    const response = await MAIN_BASE_URL.post(
          "/byType",
          userRequest,
          {
            headers: {
              Authorization: `${token}`
            }
        }
      );
    setEntry(response.data.reverse());
  }

  //search
  async function searchForExpense(event)
  {
    if(searchValue)
    {
      try{
        event.preventDefault();
        const userRequest={"date":searchValue}
        const response = await MAIN_BASE_URL.post(
            "/byDate",
            userRequest,
            {
              headers: {
                Authorization: `${token}`
              }
          }
        );
        setEntry(response.data.reverse());
    }
    catch(error)
    {
      setPopupMessage(error.response.data.message)
    }
  }
  }

  //refresh
  function refreshPage(){
    setSearchValue("");
    setTypeValue("")
    loadExpenses();
  }

  //delete
  async function deleteExpense(uId)
  {
    try{
      const userRequest={"id":uId};
      const response = await MAIN_BASE_URL.post(
          "/delete",
          userRequest,
          {
            headers: {
              Authorization: `${token}`
            }
        }
      );
      setExpense({
      id:uId,
      category:"",
      amount:0,
      title:"",
      description:"",
      type:""
    })
      loadExpenses();
    }
    catch(error)
    {
      setPopupMessage(error.response.data.message)
    }
  }

  //update
  async function updateExpense(e)
  {
    if(isVisible)
    {
      try{
        if(checkValidity)
        {
        setExpense({
          id:e.id,
          category:e.category,
          amount:e.amount,
          title:e.title,
          description:e.description,
          type:e.type
        });
          setVisibility(false);
      }
    }
      catch(error)
      {
        setPopupMessage(error.response.data.message)
      }
    }
    else
    {
      try{
        if(checkValidity())
        {
        const response = await MAIN_BASE_URL.post(
            "/update",
            expense,
            {
              headers: {
                Authorization: `${token}`
              }
          }
        );
        setExpense({
        id: e.id,
        category:"",
        amount:0,
        title:"",
        description:"",
        type:""
      })
        setVisibility(true);
        loadExpenses();
        }
    }
    catch(error)
    {
      setPopupMessage(error.response.data.message)
    }
    // else
    //   setPopupMessage("Recheck details")
    }
  }

  function checkValidity()
    {
        if(Object.values(expense).some(val=>!val)&&expense.id!==0)
        {
            setPopupMessage("Please fill all details.")
        }
        else if(/^[0-9]+$/.test(expense.amount)===false||expense.amount<1.0||expense.amount>100000.0)
            setPopupMessage("Amount should be in range 1.0-100000.0")
        else if(expense.title.length<3||expense.title.length>50||/^[A-Za-z]+$/.test(expense.title)===false)
          setPopupMessage("Title should include 3 to 50 alphabets")
        else if(expense.category.length<3||expense.category.length>50||/^[A-Za-z]+$/.test(expense.category)===false)
          setPopupMessage("Category should include 3 to 50 alphabets")
        else if(/^[A-Za-z]+$/.test(expense.description)===false)
          setPopupMessage("Descrption should include only alphabets")
        else if(expense.type.length<6||expense.type.length>7)
          setPopupMessage("Select an expense type")
        else
          return true;
        return false;
    }

  //add entry
    async function addExpense()
      {
      try{
        if(checkValidity)
        {
        refreshPage();
        const response = await MAIN_BASE_URL.post(
          "/add",
          expense,
          {
            headers: {
              Authorization: `${token}`
            }
        }
      );
        setExpense({
        category:"",
        amount:0,
        title:"",
        description:"",
        type:""
      })
        loadExpenses();
    }
      }
      catch(error)
      {
        setPopupMessage(error.response.data.message)
      }
    }

  function handleChange(e)
  {
    const {name,value}=e.target;

    setExpense(prev => ({
      ...prev,
      [name]: value
    }));
  }

  return (
    <>
    <div id="popup" style={popupStyle}>
            {popupMessage}
            <br/>
            <button 
            onClick={()=>{setPopupMessage("")}}
            >OK</button>
        </div>
    <div id="app">
    <div id="header">
        <p style={{fontSize: 36}}>ExpenseTracker</p>
         <p> Expense Manager</p>
         <div id="enterNew">

            {isVisible&&(<button onClick={addExpense} id="addEntry">Add Entry</button>)}
            {!isVisible&&(<button onClick={()=>updateExpense(expense)} id="updateEntry">Update Entry</button>)}

            <input type='text' placeholder="Enter category of expense" onChange={(e)=>{handleChange(e)}}
            value={expense.category}
            name="category">
            </input>

            <select onChange={(e)=>{handleChange(e)}} value={expense.type} name="type">
            <option value=" ">Please select a type</option>
            <option value="Income">Income</option>
            <option value="Expense">Expenditure</option>
            </select>

            <input type='text' placeholder="Enter title of expense" onChange={(e)=>{handleChange(e)}}
            value={expense.title}
            name="title"></input>
            
            <input type='text' placeholder="Enter description of expense" onChange={(e)=>{handleChange(e)}}
            value={expense.description}
            name="description">
            </input>

            <input type='text' onChange={(e)=>{handleChange(e)}}
            value={expense.amount} placeholder="Enter amount of expense"
            name="amount"></input>
            <button id="logout" onClick={toLogin}>Logout</button>
         </div>
    </div>
      
        <div id="container">
              {/* <div id="logout-button">
      Logout
    </div> */}
            <h3>Total {income>expenditure?"income":"expenditure"}: ₹{Math.abs(income-expenditure)}</h3>
            <div id="showAmount">
                <div id="income">
                    <p>My Income:<br/>
                    ₹{income}</p><br/><br/>
                    
                </div>
                <div id="expense">
                    <p>My Expenditure:<br/>
                    ₹{expenditure}</p><br/><br/>
                </div>
            </div>
            <select style={{marginLeft: "20px"}} id="typeOfExpense"
            onChange={filter} value={typeValue}
            >
                <option value="">All Entries</option>
                <option value="Income">Income</option>
                <option value="Expense">Expenditure</option>
            </select>
            <input type="date" id="search" onChange={e => setSearchValue(e.target.value)} value={searchValue}></input>
            <button 
            onClick={searchForExpense} 
            id="searchBtn">Search</button>
            <button 
            onClick={refreshPage} 
            id="searchBtn" style={{marginLeft:"10px"}}>Refresh</button>
            <div id="viewExpense">
            {
              entries?.map((e,index)=>(
                <div key={index} id="singleEntry">
                  <button id="deleteButton" onClick={()=>deleteExpense(e.id)}>delete</button>
                  <button id="updateButton" onClick={()=>{updateExpense(e)}}>update</button><br/>
                  <div  id="singleContent">
                  <div style={{left:"0", margin: "auto 0", textAlign:"center"}}>
                    <div style={{fontSize: 25}}>{e.category}</div><br/><br/>
                    <div style={{fontSize: 14}}>{e.title}: {e.description}</div>
                    <div style={{fontSize: 14}}>{e.date.split("-").reverse().join("-")}</div>
                  </div>
                    <div style={{textAlign: "center",fontSize: "35px", marginTop: "40px", marginBottom: "10px"}}>
                      {e.type}<br/>
                      ₹{e.amount}
                    </div>
                </div>
                </div>
                ))
            }
            </div>
        </div>
        </div>
    </>
  )
}
