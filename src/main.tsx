import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import About from './About.tsx'
import Layout from './Layout.tsx'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Layout />}>
                  <Route index element={<App />} />
                  <Route path="about" element={<About />} />
              </Route>
        </Routes>
      </BrowserRouter>
  </StrictMode>,
)
