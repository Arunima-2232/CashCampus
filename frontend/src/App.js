import React from 'react';
import {BrowserRouter, Routes, Route} from 'react-router-dom'

import ExpenseManager from './components/ExpenseManager';
import Login from './components/Login';
import Signup from './components/Signup';

export default function App()
{
  return(
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/Signup" element={<Signup/>}/>
        <Route path="/ExpenseManager" element={<ExpenseManager/>}/>
      </Routes>
    </BrowserRouter>
  )
}
