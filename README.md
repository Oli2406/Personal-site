# 🌐 Oliver Kastner — Personal Portfolio

This is my personal portfolio website which will be built to showcase my projects, skills and background.

## 📸 Screenshots

| Section | Screenshot |
|----------|-------------|
| 💬 **Chatroom Example** | ![Home Screenshot](./showcaseScreenshots/chatroom_example.png) |
| 💡 **Skill Tracker** | ![Projects Screenshot](./showcaseScreenshots/skills_example.png) |
| 👤 **About Section** | ![Chat Screenshot](./showcaseScreenshots/about_example.png) |


---

## 🧩 Features

Here are some of the main features included (or planned):

| Category | Description |
|-----------|--------------|
| 💼 **Portfolio Showcase** | Highlight selected personal and professional projects with details and links. |
| 👤 **About Section** | Present personal background, education, and experience. |
| 💡 **Skill Tracker** | Create and track your personal goals and progress in them. |
| ⚡ **Real-time Chat (WIP)** | Interactive group-chatroom powered by WebSockets. |
| 🧠 **Authentication** | Secure login system using JWT-based authentication. |
| 🗃️ **Database Integration** | PostgreSQL backend with persistent storage for messages, accounts, chatrooms and skills. |
| 🎨 **Modern UI** | Built with TailwindCSS for a sleek and responsive design. |

--- 

## ☸️ Run with Docker Desktop & Kubernetes

If you have **Docker Desktop** with **Kubernetes enabled**, you can deploy the entire stack and automatically rebuild it with a single click.

### 🔹 Requirements
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) with Kubernetes enabled  
- [Git](https://git-scm.com/) (for Git Bash)

### 🔹 Quick Start

1. **Double-click** the file:
   ```bash
   redeploy.bat
   ```

   This script will:
   - Rebuild all Docker images (`frontend`, `backend`, `postgres`)
   - Recreate running containers
   - Restart Kubernetes deployments
   - Wait until the rollout completes successfully

2. Wait for the terminal to display:
   ```
   ✅ Redeployment complete!
   ```

3. Open your browser:
   ```
   http://frontend.localhost
   ```

NOTE: This is still a work in progress and not all features work while deployed.

---

## 🧠 Run Locally

If you wish to run the stack locally follow these steps:

## 🔹 Requirements
- [Java 21+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/install.html)
- [Node.js 10+ and npm](https://nodejs.org/)

---

## ⚙️ 1. Run the Backend

1. Open a terminal in the backend folder:
   ```bash
   cd backend
   ```
2. Build and start the Spring Boot application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

The backend will automatically connect to the configured PostgreSQL database  
and start on **http://localhost:8080**.

---

## 🎨 2. Run the Frontend

1. Open a new terminal in the frontend folder:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

The frontend will be available at **http://localhost:5202** and will automatically connect to the backend.

---

✅ You now have both servers running locally:
- **Frontend:** http://localhost:5202  
- **Backend:** http://localhost:8080  

---

## ⚙️ Technologies Used

| Layer | Stack |
|-------|--------|
| **Frontend** | React • TypeScript • Vite • TailwindCSS|
| **Backend** | Spring Boot • Java 21 • JPA |
| **Database** | PostgreSQL 16 |
| **DevOps** | Docker • Git |
---

This project is currently under active development
