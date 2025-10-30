import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Skills from './components/Skills/Skills.tsx'
import About from './components/About/About.tsx'
import Layout from './components/Layout/Layout.tsx'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Login from "./components/Authentication/Login.tsx";
import Register from "./components/Authentication/Register.tsx"
import {AuthProvider} from "./contexts/AuthContext.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <AuthProvider>
              <Routes>
                  <Route path="/" element={<Layout />} >
                      <Route index element={<Skills />} />
                      <Route path="about" element={<About />} />
                      <Route path="register" element={<Register />}/>
                      <Route path="login" element={<Login />}/>
                  </Route>
            </Routes>
        </AuthProvider>
      </BrowserRouter>
  </StrictMode>,
)