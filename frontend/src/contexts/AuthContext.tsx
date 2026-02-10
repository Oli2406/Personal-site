import React, { createContext, useContext, useState, useEffect } from "react";
import {Navigate, Outlet, useNavigate} from "react-router-dom";

interface AuthContextType {
    token: string | null;
    username: string | null;
    role: string | null;
    userId: number | null;
    isLoggedIn: boolean;
    login: (data: LoginPayload) => void;
    logout: () => void;
    getLoggedInUsername: () => string;
}

export interface LoginPayload {
    token: string;
    username: string;
    role: string;
    id: number;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [token, setToken] = useState(() => localStorage.getItem("token"));
    const [username, setUsername] = useState(() => localStorage.getItem("username"));
    const [role, setRole] = useState(() => localStorage.getItem("role"));
    const [userId, setUserId] = useState<number | null>(() => {
        const id = localStorage.getItem("userId");
        return id ? parseInt(id) : null;
    });

    const navigate = useNavigate();

    const login = (data: LoginPayload) => {
        localStorage.setItem("token", data.token);
        localStorage.setItem("username", data.username);
        localStorage.setItem("role", data.role);
        localStorage.setItem("userId", data.id.toString());

        setToken(data.token);
        setUsername(data.username);
        setRole(data.role);
        setUserId(data.id);
    };

    const logout = () => {
        localStorage.clear();
        setToken(null);
        setUsername(null);
        setRole(null);
        setUserId(null);
        navigate("/");
    };

    const getLoggedInUsername = (): string => username || "";

    useEffect(() => {
        const handleStorageChange = () => {
            setToken(localStorage.getItem("token"));
            setUsername(localStorage.getItem("username"));
            setRole(localStorage.getItem("role"));
            const id = localStorage.getItem("userId");
            setUserId(id ? parseInt(id) : null);
        };
        window.addEventListener("storage", handleStorageChange);
        return () => window.removeEventListener("storage", handleStorageChange);
    }, []);

    const isLoggedIn = Boolean(token);

    return (
        <AuthContext.Provider
            value={{ token, username, role, userId, isLoggedIn, login, logout, getLoggedInUsername }}
        >
            {children}
        </AuthContext.Provider>
    );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) throw new Error("useAuth must be used within an AuthProvider");
    return context;
};

export const AdminRoute = () => {
    const { isLoggedIn, role } = useAuth();

    if(!isLoggedIn) return <Navigate to="/login" replace />;
    if(role !== "ADMIN") return <Navigate to="/" />;

    return <Outlet/>;
}
