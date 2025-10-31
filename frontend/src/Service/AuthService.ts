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
    async login(login: login): Promise<string> {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(login),
        });
        if(!response.ok) throw new Error(response.statusText);

        const data = await response.json()
        return data.token;
    },

    async register(register: register): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(register),
        });
        if(!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw errorData || {message: "Unknown error occurred."};
        }
    },
}