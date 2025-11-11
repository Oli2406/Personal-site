import React, { useEffect, useState } from "react";
import { type Skill, SkillService } from "../../Service/SkillService.ts";
import { useAuth } from "../../contexts/AuthContext.tsx";
import { Navigate } from "react-router-dom";
import BarChart from "./BarChart";
import { SkillProgressService } from "../../Service/SkillProgressService.ts";
import LineChartProgress from "./LineChart";

function Skills() {
    const { token, isLoggedIn } = useAuth();
    const [skills, setSkills] = useState<Skill[]>([]);
    const [selectedSkill, setSelectedSkill] = useState<Skill | null>(null);
    const [progressHistory, setProgressHistory] = useState<
        { progress: number; createdAt: Date }[]
    >([]);
    const [showMenu, setShowMenu] = useState(false);
    const [showUpdateForm, setShowUpdateForm] = useState(false);
    const [newSkill, setNewSkill] = useState({
        id: 0,
        name: "",
        description: "",
        progress: 0,
    });
    const [newProgress, setNewProgress] = useState(0);

    useEffect(() => {
        if (!token) return;
        if (isLoggedIn && token) {
            SkillService.getAll(token)
                .then(setSkills)
                .catch((err) => console.error("Failed to load skills:", err));
        }
    }, []);

    const handleSkillAddition = () => setShowMenu(true);

    const handleCloseMenu = () => setShowMenu(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!newSkill.name || !newSkill.description) return;

        try {
            if (!token) return;
            if (token && isLoggedIn) {
                const created = await SkillService.create(newSkill, token);
                await SkillProgressService.create(newSkill, token);
                setSkills([...skills, created]);
                setNewSkill({ id: 0, name: "", description: "", progress: 0 });
                setShowMenu(false);
            }
        } catch (err) {
            console.error("Failed to create skill:", err);
        }
    };

    const handleSkillClick = async (skill: Skill) => {
        if (!token) return;
        setSelectedSkill(skill);

        try {
            const updates = await SkillProgressService.getBySkill(skill.id, token);
            console.log(updates)

            const formattedUpdates = updates
                .map((u: any) => ({
                    progress: u.progress ?? u.level ?? 0,
                    createdAt: u.createdAt ? new Date(u.createdAt) : new Date(),
                }))
                .sort((a, b) => a.createdAt.getTime() - b.createdAt.getTime());

            setProgressHistory(formattedUpdates);
        } catch (err) {
            console.error("Failed to fetch progress updates:", err);
            setProgressHistory([]);
        }
    };

    const handleAddUpdate = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!selectedSkill || !token) return;

        try {
            await SkillService.create(
                {
                    id: selectedSkill.id,
                    name: selectedSkill.name,
                    description: selectedSkill.description,
                    progress: newProgress,
                },
                token
            );


            const updates = await SkillProgressService.getBySkill(
                selectedSkill.id,
                token
            );
            setProgressHistory(updates);
            setNewProgress(0);
            setShowUpdateForm(false);
        } catch (err) {
            console.error("Failed to add update:", err);
        }
        window.location.reload();
    };

    if (!isLoggedIn) {
        return <Navigate to={"/login"} replace />;
    }

    return (
        <div className="overflow-visible min-h-screen text-gray-100 px-6 py-8">
            <h1 className="text-4xl font-bold mb-8 text-center">My Skills</h1>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-6 shadow-2xl">
                    {skills.length > 0 ? (
                        <BarChart
                            skills={skills.map((s) => ({
                                name: s.name,
                                level: s.progress,
                                onClick: () => handleSkillClick(s),
                            }))}
                        />
                    ) : (
                        <p className="text-gray-300 text-center">
                            No skills to display yet.
                        </p>
                    )}

                    <div className="flex justify-center mt-6">
                        <button
                            className="bg-white/10 border border-white/20 text-gray-100 font-semibold px-6 py-3 rounded-full shadow-2xl hover:bg-white/20 transition-all duration-300"
                            onClick={handleSkillAddition}
                        >
                            + Add Skill
                        </button>
                    </div>
                </div>

                <div className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-6 shadow-2xl">
                    {selectedSkill ? (
                        <>
                            <h2 className="text-2xl font-bold mb-2">
                                {selectedSkill.name}
                            </h2>
                            <p className="text-gray-400 mb-6">
                                {selectedSkill.description}
                            </p>

                            <LineChartProgress
                                data={progressHistory}
                                title="Progress History"
                            />

                            <div className="flex flex-col items-center mt-6">
                                {!showUpdateForm ? (
                                    <button
                                        onClick={() => setShowUpdateForm(true)}
                                        className="bg-white/10 border border-white/20 text-gray-100 font-semibold px-6 py-2 rounded-full hover:bg-white/20 transition-all duration-300"
                                    >
                                        + Add Update
                                    </button>
                                ) : (
                                    <form
                                        onSubmit={handleAddUpdate}
                                        className="flex flex-col items-center gap-3"
                                    >
                                        <input
                                            type="number"
                                            min="0"
                                            max="100"
                                            value={newProgress}
                                            onChange={(e) =>
                                                setNewProgress(Number(e.target.value))
                                            }
                                            placeholder="New progress %"
                                            className="w-32 p-2 rounded-xl bg-white/10 border border-white/20 text-center"
                                        />
                                        <button
                                            type="submit"
                                            className="px-5 py-2 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition"
                                        >
                                            Save Update
                                        </button>
                                    </form>
                                )}
                            </div>
                        </>
                    ) : (
                        <p className="text-gray-400 text-center mt-20">
                            Click a skill to view its progress history.
                        </p>
                    )}
                </div>
            </div>

            {showMenu && (
                <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50 animate-fade-in-up">
                    <form
                        onSubmit={handleSubmit}
                        className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-8 shadow-2xl w-full max-w-md text-gray-100"
                    >
                        <h2 className="text-2xl font-bold mb-6 text-center">
                            Add a New Skill
                        </h2>

                        <div className="space-y-4">
                            <input
                                type="text"
                                placeholder="Skill Name"
                                value={newSkill.name}
                                onChange={(e) =>
                                    setNewSkill({ ...newSkill, name: e.target.value })
                                }
                                className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                            />
                            <input
                                type="text"
                                placeholder="Description"
                                value={newSkill.description}
                                onChange={(e) =>
                                    setNewSkill({ ...newSkill, description: e.target.value })
                                }
                                className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                            />
                            <input
                                type="number"
                                placeholder="Progress %"
                                min="0"
                                max="100"
                                value={newSkill.progress}
                                onChange={(e) =>
                                    setNewSkill({
                                        ...newSkill,
                                        progress: Number(e.target.value),
                                    })
                                }
                                className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                            />
                        </div>

                        <div className="flex justify-end gap-3 mt-6">
                            <button
                                type="button"
                                onClick={handleCloseMenu}
                                className="px-5 py-2 rounded-xl bg-white/10 border border-white/20 hover:bg-white/20 transition"
                            >
                                Cancel
                            </button>
                            <button
                                type="submit"
                                className="px-5 py-2 rounded-xl bg-indigo-600 hover:bg-indigo-700 transition"
                            >
                                Submit
                            </button>
                        </div>
                    </form>
                </div>
            )}
        </div>
    );
}

export default Skills;
