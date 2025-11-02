import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Skills from './components/Skills/Skills.tsx'
import About from './components/About/About.tsx'
import Layout from './components/Layout/Layout.tsx'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Login from "./components/Authentication/Login.tsx";
import Register from "./components/Authentication/Register.tsx"
import Chat from "./components/Chat/Chat.tsx"
import {AuthProvider} from "./contexts/AuthContext.tsx";
import {ToastContainer} from "react-toastify";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <AuthProvider>
              <ToastContainer position="top-right" autoClose={3000} />
              <Routes>
                  <Route path="/" element={<Layout />} >
                      <Route index element={<About />} />
                      <Route path="skills" element={<Skills />} />
                      <Route path="chat" element={<Chat />} />
                      <Route path="register" element={<Register />}/>
                      <Route path="login" element={<Login />}/>
                  </Route>
            </Routes>
        </AuthProvider>
      </BrowserRouter>
  </StrictMode>,
)