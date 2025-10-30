import { Outlet, NavLink } from "react-router-dom";

export default function Layout() {
    return (
        <div
            className="min-h-screen flex flex-col bg-gradient-to-br from-gray-900 via-indigo-900 to-purple-900 text-gray-100">
            <header
                className="sticky top-0 z-50 w-full flex items-center justify-between bg-gray-900 backdrop-blur-md border-b border-white/20 shadow-lg px-8 py-4 text-2xl font-semibold">
                <h1>🌟 My First React App</h1>
                <div className="flex items-center gap-4 text-sm">
                    <button className="px-4 py-2 rounded-lg bg-indigo-500 hover:bg-indigo-600 transition">
                        <NavLink to="/">Skills</NavLink>
                    </button>
                    <button className="px-4 py-2 rounded-lg bg-pink-500 hover:bg-pink-600 transition">
                        <NavLink to="/about">About</NavLink>
                    </button>
                    <button className="px-4 py-2 rounded-lg bg-amber-500 hover:bg-amber-600 transition">
                        <NavLink to="/register">Register</NavLink>
                    </button>
                </div>
            </header>
            <main className="flex-1">
                <Outlet/>
            </main>
        </div>
    )
}

