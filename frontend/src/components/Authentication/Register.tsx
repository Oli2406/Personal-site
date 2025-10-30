import React, {useState} from "react";
import {AuthService} from "../../Service/AuthService.ts";

export default function RegisterPage() {

    const [newUser, setNewUser] = useState({
        username: "",
        password: "",
        role: ""
    })

    const [repeatPassword, setRepeatPassword] = useState({
        password: "",
        repeatPassword: ""
    })

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if(!newUser.username || !newUser.password) return;
        setRepeatPassword({...repeatPassword, password: newUser.password});

        try {
            if(repeatPassword.password === repeatPassword.repeatPassword) {
                await AuthService.register(newUser)
                setNewUser({username: "", password: "", role: "" })
            } else {
                console.error("Password and repeat password don't match")
            }
        } catch (err) {
            console.error("Failed to register user:", err);
        }
    }

    return (
        <div className="flex items-center justify-center mt-4 text-gray-100 px-4">
            <div className="w-full max-w-md bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl shadow-2xl p-8 space-y-6 animate-fade-in-up">
                <h1 className="text-3xl font-bold text-center text-indigo-300">
                    Create an Account
                </h1>

                <form className="space-y-5" onSubmit={handleSubmit}>
                    <div>
                        <label className="block text-sm mb-1">Username</label>
                        <input
                            type="text"
                            placeholder="Enter username"
                            onChange={(e) => setNewUser({...newUser, username: e.target.value})}
                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                        />
                    </div>

                    <div>
                        <label className="block text-sm mb-1">Password</label>
                        <input
                            type="password"
                            placeholder="Enter password"
                            onChange={(e) => setNewUser({...newUser, password: e.target.value})}
                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                        />
                    </div>

                    <div>
                        <label className="block text-sm mb-1">Confirm Password</label>
                        <input
                            type="password"
                            placeholder="Repeat password"
                            onChange={(e) => setRepeatPassword({...repeatPassword, repeatPassword: e.target.value})}
                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                        />
                    </div>

                    <button
                        type="submit"
                        className="w-full py-3 mt-4 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition font-semibold"
                    >
                        Register
                    </button>
                </form>

                <p className="text-center text-sm text-gray-400 mt-4">
                    Already have an account?{" "}
                    <a
                        href="/login"
                        className="text-indigo-400 hover:text-indigo-300 font-medium"
                    >
                        Log in
                    </a>
                </p>
            </div>
        </div>
    );
}
