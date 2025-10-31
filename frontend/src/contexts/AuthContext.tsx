import React, { createContext, useContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

interface AuthContextType {
    token: string | null;
    username: string | null;
    isLoggedIn: boolean;
    login: (newToken: string) => void;
    logout: () => void;
    getLoggedInUsername: () => string;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [token, setToken] = useState<string | null>(() => localStorage.getItem("token"));
    const [username, setUsername] = useState<string | null>(() => localStorage.getItem("username"));

    const navigate = useNavigate();

    const login = (newToken: string) => {
        setToken(newToken);
        setUsername(localStorage.getItem("username"));
    };

    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        setToken(null);
        setUsername(null);
        navigate("/");
    };

    const getLoggedInUsername = ():string => {
        return(localStorage.getItem("username")) || "";
    }

    useEffect(() => {
        const handleStorageChange = () => {
            setToken(localStorage.getItem("token"));
            setUsername(localStorage.getItem("username"));
        };
        window.addEventListener("storage", handleStorageChange);
        return () => window.removeEventListener("storage", handleStorageChange);
    }, []);

    const isLoggedIn = Boolean(token);

    return (
        <AuthContext.Provider value={{ token, username, isLoggedIn, login, logout, getLoggedInUsername }}>
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
