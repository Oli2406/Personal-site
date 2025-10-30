export default function LoginPage() {
    return (
        <div className="flex items-center justify-center mt-4 text-gray-100 px-4">
            <div className="w-full max-w-md bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl shadow-2xl p-8 space-y-6 animate-fade-in-up">
                <h1 className="text-3xl font-bold text-center text-indigo-300">
                    Log in to your account
                </h1>

                <form className="space-y-5">
                    <div>
                        <label className="block text-sm mb-1">Username</label>
                        <input
                            type="text"
                            placeholder="Enter username"
                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                        />
                    </div>

                    <div>
                        <label className="block text-sm mb-1">Password</label>
                        <input
                            type="password"
                            placeholder="Enter password"
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
