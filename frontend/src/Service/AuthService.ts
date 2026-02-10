import type {LoginPayload} from "../contexts/AuthContext.tsx";

export interface LoginRequest {
    username: string;
    password: string;
}

export interface RegisterRequest {
    username: string;
    password: string;
    role: string;
}

export interface User {
    userName: string;
    role: string;
}

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

export const AuthService = {
    async login(loginData: LoginRequest): Promise<LoginPayload> {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(loginData),
        });

        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data: LoginPayload = await response.json();
        return data;
    },

    async register(registerData: RegisterRequest): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(registerData),
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw errorData || { message: "Unknown error occurred." };
        }
    },

    async registerAdmin(registerData: RegisterRequest, token: string): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/admin/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(registerData),
        });

        if (!response.ok) {
            const contentType = response.headers.get("content-type") || "";
            const errorBody = contentType.includes("application/json")
                ? await response.json().catch(() => null)
                : await response.text().catch(() => "");

            throw {
                status: response.status,
                body: errorBody,
                message:
                    (typeof errorBody === "object" && errorBody?.message) ||
                    (typeof errorBody === "string" && errorBody) ||
                    `Request failed with status ${response.status}`,
            };
        }
    },

    async getAllUsers(token: string): Promise<User[]> {
        const headers: Record<string, string> = {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        };

        const response = await fetch(`${API_BASE_URL}/admin`, {
            method: "GET",
            headers
        })

        if (!response.ok) {
            const contentType = response.headers.get("content-type") || "";
            const errorBody = contentType.includes("application/json")
                ? await response.json().catch(() => null)
                : await response.text().catch(() => "");

            throw {
                status: response.status,
                body: errorBody,
                message:
                    (typeof errorBody === "object" && errorBody?.message) ||
                    (typeof errorBody === "string" && errorBody) ||
                    `Request failed with status ${response.status}`,
            };
        }
        const result = await response.json();
        console.log(result)
        return result;
    }
};
