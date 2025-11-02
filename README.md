# 🌐 Oliver Kastner — Personal Portfolio

This is my personal portfolio website which will be built to showcase my projects, skills and background.

## 📸 Screenshots

<img width="2560" height="1231" alt="image" src="https://github.com/user-attachments/assets/5c415d7f-a222-47eb-bd4c-501e5c9c2ae5" />
<img width="2542" height="1235" alt="image" src="https://github.com/user-attachments/assets/ffd9304f-a7f1-4ede-9aa5-6ae0ef0742cb" />
<img width="2540" height="1239" alt="image" src="https://github.com/user-attachments/assets/261b0a0a-4de8-47aa-9d1d-e24fc06bef23" />

| Section | Screenshot |
|----------|-------------|
| 💬 **Chatroom Example** | ![Home Screenshot](<img width="2560" height="1231" alt="image" src="https://github.com/user-attachments/assets/5c415d7f-a222-47eb-bd4c-501e5c9c2ae5" />) |
| 💡 **Skill Tracker** | ![Projects Screenshot](<img width="2542" height="1235" alt="image" src="https://github.com/user-attachments/assets/ffd9304f-a7f1-4ede-9aa5-6ae0ef0742cb" />) |
| 👤 **About Section** | ![Chat Screenshot](<img width="2540" height="1239" alt="image" src="https://github.com/user-attachments/assets/261b0a0a-4de8-47aa-9d1d-e24fc06bef23" />) |


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
