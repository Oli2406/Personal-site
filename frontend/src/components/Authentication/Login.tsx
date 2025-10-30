import React, { useState } from "react";
import {AuthService} from "../../Service/AuthService.ts";
import {useAuth} from "../../contexts/AuthContext.tsx";
import {useNavigate} from "react-router-dom";

export default function LoginPage() {

    const { login } = useAuth();

    const navigate = useNavigate();

    const [newUser, setNewUser] = useState({
        username: "",
        password: "",
    })

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if(!newUser.username || !newUser.password) return;

        try {
            const token = await AuthService.login(newUser);
            setNewUser({username: "", password: ""});
            login(token);
            navigate("/");
        } catch (err) {
            console.error("Failed to log in user: ", err);
        }
    }

    return (
        <div className="flex items-center justify-center mt-4 text-gray-100 px-4">
            <div className="w-full max-w-md bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl shadow-2xl p-8 space-y-6 animate-fade-in-up">
                <h1 className="text-3xl font-bold text-center text-indigo-300">
                    Log in to your account
                </h1>

                <form className="space-y-5" onSubmit={handleSubmit}>
                    <div>
                        <label className="block text-sm mb-1">Username</label>
                        <input
                            type="text"
                            placeholder="Enter username"
                            onChange={ (e) => setNewUser({...newUser, username: e.target.value})}
                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                        />
                    </div>

                    <div>
                        <label className="block text-sm mb-1">Password</label>
                        <input
                            type="password"
                            placeholder="Enter password"
                            onChange={ (e) => setNewUser({...newUser, password: e.target.value})}
                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full py-3 mt-4 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition font-semibold"
                    >
                        Log in
                    </button>
                </form>

                <p className="text-center text-sm text-gray-400 mt-4">
                    Create a new account?{" "}
                    <a
                        href="/register"
                        className="text-indigo-400 hover:text-indigo-300 font-medium"
                    >
                        Register
                    </a>
                </p>
            </div>
        </div>
    );
}
