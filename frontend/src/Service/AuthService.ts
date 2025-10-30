export interface login {
    username: string;
    password: string;
}

export interface register {
    username: string;
    password: string;
    role: string;
}

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const AuthService = {
    async login(login: login): Promise<login> {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(login),
        });
        if(!response.ok) throw new Error(response.statusText);
        return response.json();
    },

    async register(register: register): Promise<register> {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(register),
        });
        if(!response.ok) throw new Error(response.statusText);
        return response.json();
    }
}