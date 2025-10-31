export interface LoginRequest {
    username: string;
    password: string;
}

export interface RegisterRequest {
    username: string;
    password: string;
    role: string;
}

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const AuthService = {
    async login(loginData: LoginRequest): Promise<string> {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(loginData),
        });

        if (!response.ok) throw new Error(response.statusText);

        const data = await response.json();

        localStorage.setItem("token", data.token);
        localStorage.setItem("username", data.username);

        return data.token;
    },

    async register(registerData: RegisterRequest): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(registerData),
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw errorData || { message: "Unknown error occurred." };
        }
    },
};
