import { SocialIcon } from 'react-social-icons'
import Projects from '../Projects/Projects.tsx'
import { useState } from "react";

export function About() {

    const WEATHER_KEY = import.meta.env.VITE_WEATHER_API_KEY || "";

    const city = "Vienna";

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [weather, setWeather] = useState<any>(null);
    const [showMenu, setShowMenu] = useState(false);

    const handleShowMenu = () => setShowMenu(true)
    const handleCloseMenu = () => setShowMenu(false)


    const fetchWeather = async ()=> {
        setLoading(true);
        setError(null);
        try{
            const res = await fetch(
                `https://api.weatherapi.com/v1/current.json?key=${WEATHER_KEY}&q=${city}&aqi=no`
            );

            if(!res.ok) throw new Error("City not found or API error");
            const data = await res.json();
            setWeather(data);
            handleShowMenu();
        } catch (error) {
            const err = error as Error;
            setError(err.message);
            setWeather(null);
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-[80vh] px-6 text-gray-100">
            <div
                className="max-w-4xl w-full bg-white/10 backdrop-blur-lg border border-white/20 rounded-2xl shadow-2xl p-8 space-y-6 animate-fade-in-up mt-4">
                <h1 className="text-4xl font-bold text-center text-indigo-300 mb-4">
                    About Me
                </h1>

                <div className="flex flex-col md:flex-row items-center md:items-start gap-10">
                    <div className="flex-shrink-0 flex flex-col items-center md:items-center">
                        <img
                            src="/selfie.jpeg"
                            alt="Oliver Kastner"
                            className="w-40 h-40 rounded-full border-4 border-indigo-400/50 shadow-lg object-cover hover:scale-105 transition-transform duration-300"
                        />
                        <div className="flex justify-center gap-4 mt-8">
                            <SocialIcon
                                url={"https://github.com/Oli2406"}
                                className="w-10 h-10 bg-white/10 rounded-full transition-transform hover:scale-110"
                                fgColor="#fff"
                                bgColor="transparent"
                            />
                            <SocialIcon
                                url={"https://www.linkedin.com/in/oliver-kastner-507a45250/"}
                                className="w-10 h-10 bg-white/10 rounded-full transition-transform hover:scale-110"
                                fgColor="#fff"
                                bgColor="transparent"
                            />
                        </div>
                    </div>

                    <div className="text-center md:text-left space-y-4">
                        <p className="text-gray-300 leading-relaxed text-lg">
                            Hello, my name is Oliver Kastner. I am a Software Developer from Vienna Austria.
                            I have a passion for web development and data visualization. Currently I am in taking the
                            last course of my bachelors in Media Informatics and Visual Computing at TU Wien. This
                            website acts as a personal project to improve my web development skills. You can find the
                            Github Links to my other projects below.
                        </p>

                        <p className="text-gray-300 leading-relaxed text-lg">
                            For this project the technologies I utilized are:
                        </p>

                        <div className="flex flex-wrap gap-3 mt-6 justify-center md:justify-start">
                          <span className="px-4 py-2 bg-indigo-500/30 rounded-full border border-indigo-400/30 text-sm font-medium">
                            React & TypeScript
                          </span>
                            <span
                                className="px-4 py-2 bg-purple-500/30 rounded-full border border-purple-400/30 text-sm font-medium">
                                Tailwind CSS
                              </span>
                            <span
                                className="px-4 py-2 bg-green-500/30 rounded-full border border-green-400/30 text-sm font-medium">
                                Java & Spring Boot
                              </span>
                            <span
                                className="px-4 py-2 bg-blue-500/30 rounded-full border border-blue-400/30 text-sm font-medium">
                                PostgreSQL
                              </span>
                            <span
                                className="px-4 py-2 bg-pink-500/30 rounded-full border border-pink-400/30 text-sm font-medium">
                                Git & Docker
                                </span>
                        </div>
                    </div>
                </div>
            </div>
            <Projects/>
            <button className="flex align-middle px-4 py-2 rounded-lg bg-indigo-500 hover:bg-indigo-600 transition mb-4"
            onClick={fetchWeather}>
                How is the weather in your hometown? ☀️
            </button>
            {loading && <p>Loading...</p>}
            {error && <p style={{color: "red"}}>⚠️ {error}</p>}
            {showMenu && weather && (
                <div
                    className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50 animate-fade-in-up">
                    <div
                        className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-8 shadow-2xl w-full max-w-md text-gray-100 relative">
                        <button
                            onClick={() => {
                                handleCloseMenu()
                            }}
                            className="absolute top-3 right-3 text-gray-400 hover:text-white text-lg font-semibold transition-colors duration-200"
                            aria-label="close menu"
                        >
                            ✕
                        </button>
                        <h2
                            className="text-2xl font-bold mb-4 text-center">
                            Weather in {weather.location.name}
                        </h2>

                        <div
                            className={"flex flex-col items-center space-y-2"}>
                            <img
                                src={weather.current.condition.icon}
                                alt={weather.current.condition.text}
                                className={"w-16 h-16"}
                            />
                            <p className={"text-3xl font-bold text-indigo-300"}>
                                {weather.current.temp_c}°C
                            </p>
                            <p className={"text-lg text-gray-300"}>
                                {weather.current.condition.text}
                            </p>
                            <p className="text-sm text-gray-400">
                                Humidity: {weather.current.humidity}%
                            </p>
                            <p className="text-sm text-gray-400">
                                Wind: {weather.current.wind_kph} km/h
                            </p>
                        </div>
                    </div>
                </div>
            )}

        </div>
    );
}

export default About;