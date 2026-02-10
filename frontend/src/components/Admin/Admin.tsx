import React, {useEffect, useState} from "react";
import {AuthService, type User} from "../../Service/AuthService.ts";
import {useAuth} from "../../contexts/AuthContext.tsx";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

export function Admin() {

    const notifySuccess = (message:string) => toast.success(message);
    const notifyError = (message: string) => toast.error(message);

    const navigate = useNavigate();

    const { token } = useAuth();
    const [ showMenu, setShowMenu ] = useState(false);
    const [ user, setUser ] = useState({
        username: "",
        password: "",
        role: ""
    });

    const [ allUsers, setAllUsers ] = useState<User[]>([]);

    useEffect(() => {
        if(!token) return;
        if(token) {
            AuthService.getAllUsers(token)
                .then(setAllUsers)
                .catch((err) => console.error("Failed to load users:", err));
        }
    }, []);

    const handleUserCreation = () => setShowMenu(true);

    const handleUserClick = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!token) return;
        setUser(user);

        const username = user.username.trim();
        const password = user.password.trim();
        const role = user.role;
        if (!username || !password || !role) {
            notifyError("Please enter username and password");
            return;
        }

        try {
            await AuthService.registerAdmin({ username, password, role }, token);
            setUser({username: "", password: "", role: "" })
            setShowMenu(false);
            notifySuccess("User successfully created")
            navigate("/admin");

        } catch (err: any) {
            console.error("Registration error:", err);
            notifyError(err.errors.toString().substring(1, err.errors.length - 1))
        }
    }

    return (
        <div className="flex flex-col items-center justify-center mt-4 text-gray-100 px-4 gap-4">
            <button className="py-3 px-4 mt-4 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition font-semibold"
                    onClick={handleUserCreation}>
                + Create new user
            </button>
            <div
                className="w-full max-w-md bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-6 shadow-2xl">

                <h2 className="text-lg font-semibold mb-4 text-center">
                    Registered Users
                </h2>

                {allUsers.length <= 0 ? (
                    <p className="text-center opacity-70"> No users found</p>
                ) : (
                    <ul className="space-y-2">
                        {allUsers.map((user, index) => (
                            <li
                                key={index}
                                className="flex justify-between items-center bg-white/5 hover:bg-white/10 transition p-3 rounded-xl border border-white/10">
                                <span className="font-medium">{user.userName}</span>
                                <span className="text-sm opacity-70">{user.role}</span>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
            {showMenu && (
                <div
                    className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50 animate-fade-in-up">
                    <form
                        onSubmit={handleUserClick}
                        className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-8 shadow-2xl w-full max-w-md text-gray-100">

                        <div className="space-y-4">
                            <input
                                type="text"
                                placeholder="Username"
                                value={user.username}
                                onChange={(e) =>
                                    setUser({...user, username: e.target.value})
                                }
                                className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                            />
                            <input
                                type="password"
                                placeholder="Password"
                                value={user.password}
                                onChange={(e) =>
                                    setUser({...user, password: e.target.value})
                                }
                                className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                            />
                            <label className="flex items-center gap-3 select-none">
                                <input
                                    type="checkbox"
                                    checked={user.role === "ADMIN"}
                                    onChange={(e) =>
                                        setUser({
                                            ...user,
                                            role: e.target.checked ? "ADMIN" : "USER",
                                        })
                                    }
                                />
                                <span>Make this user Admin</span>
                            </label>
                        </div>
                        <button
                            type="submit"
                            className="w-full py-3 mt-4 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition font-semibold"
                        >
                            Register user
                        </button>
                    </form>
                </div>
            )}
        </div>
    );
}