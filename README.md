# 🌐 Oliver Kastner — Personal Portfolio

This is my personal portfolio website which will be built to showcase my projects, skills and background.

## 🐳 Run with Docker

You can run the entire stack (frontend, backend, and database) with a single command through docker.

### 🔹 Requirements
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)

### 🔹 Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Oli2406/Personal-site.git
   cd Personal-site
    ```

2. **Build and start all services (must be in the project root)**
   ```bash
   docker compose up --build
   ```

3. **Access the application**
   - 🌐 **Frontend (React App):** [http://localhost:5173](http://localhost:5173)  
   - ⚙️ **Backend (Spring Boot API):** [http://localhost:8080](http://localhost:8080)  
   - 🐘 **PostgreSQL Database:** exposed on port `5433`

4. **Stop all containers**
   ```bash
   docker compose down
   ```

## ⚙️ Technologies Used

| Layer | Stack |
|-------|--------|
| **Frontend** | React • TypeScript • Vite • TailwindCSS|
| **Backend** | Spring Boot • Java 21 • JPA |
| **Database** | PostgreSQL 16 |
| **DevOps** | Docker • Git |
---

This project is currently under active development
