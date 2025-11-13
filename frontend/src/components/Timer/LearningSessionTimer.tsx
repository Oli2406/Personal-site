import React, { useEffect, useState } from "react";

interface LearningSessionTimerProps {
    onCancel: () => void;
    onFinish: (minutes: number) => void;
}

const LearningSessionTimer: React.FC<LearningSessionTimerProps> = ({
                                                                       onCancel,
                                                                       onFinish,
                                                                   }) => {
    const [seconds, setSeconds] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            setSeconds((s) => s + 1);
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    const formatTime = (time: number) => {
        const mins = Math.floor(time / 60);
        const secs = time % 60;
        return `${mins.toString().padStart(2, "0")}:${secs
            .toString()
            .padStart(2, "0")}`;
    };

    const handleFinish = () => {
        const minutes = Math.round(seconds / 60);
        onFinish(minutes);
    };

    return (
        <div className="fixed inset-0 flex items-center justify-center z-50">
            <div className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-8 shadow-2xl w-full max-w-md relative text-gray-100">
                <button
                    onClick={onCancel}
                    className="absolute top-3 right-3 text-gray-300 hover:text-white transition"
                >
                    ✕
                </button>

                <h2 className="text-2xl font-bold text-center mb-4">
                    Learning Session
                </h2>

                <div className="text-6xl font-semibold text-center mb-6">
                    {formatTime(seconds)}
                </div>

                <div className="flex justify-center">
                    <button
                        onClick={handleFinish}
                        className="px-5 py-2 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition"
                    >
                        Finish Session
                    </button>
                </div>
            </div>
        </div>
    );
};

export default LearningSessionTimer;
