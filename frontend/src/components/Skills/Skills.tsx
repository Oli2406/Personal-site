import React, {useEffect, useState} from "react";
import {type Skill, SkillService} from "../../Service/SkillService.ts";
import {useAuth} from "../../contexts/AuthContext.tsx";
import {Navigate} from "react-router-dom";
import BarChart from "./BarChart";

function Skills() {

    const {token, isLoggedIn} = useAuth();
    const [skills, setSkills] = useState<Skill[]>([]);
    const [showMenu, setShowMenu] = useState(false);
    const [newSkill, setNewSkill] = useState({
        id: 0,
        name: "",
        description: "",
        progress: 0,
    });

    useEffect(() => {
        if(!token) return;
        if(isLoggedIn && token) {
            SkillService.getAll(token)
                .then(setSkills)
                .catch((err) => console.error("Failed to load skills:", err));
        }
    }, []);

    /*const handleSkillDeletion = async (id: number) => {
        try {
            if(!token) return;
            if(token && isLoggedIn) {
                await SkillService.delete(id, token);
                if(skills !== undefined){
                    setSkills(skills.filter((s) => s.id !== id));
                }
            }
        } catch(err) {
            console.error("Failed to delete skills:", err);
        }
    }*/

    const handleSkillAddition = () => setShowMenu(true);

    const handleCloseMenu = () => setShowMenu(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!newSkill.name || !newSkill.description) return;

        try {
            if(!token) return;
            if(token && isLoggedIn) {
                const created = await SkillService.create(newSkill, token);
                setSkills([...skills, created]);
                setNewSkill({ id: 0, name: "", description: "", progress: 0 });
                setShowMenu(false);
            }
        } catch (err) {
            console.error("Failed to create skill:", err);
        }
    };

    if(!isLoggedIn) {
        return <Navigate to={"/login"} replace/>
    }

    return (
        <div className="overflow-visible">
            <div className="flex flex-col items-center justify-center px-4 mt-4">

                {isLoggedIn ? (
                    <h1 className="text-4xl font-bold mb-8 text-center">
                        My Skills
                    </h1>
                ) : (
                    <h1 className="text-4xl font-bold mb-8 text-center">
                        Log in to view your skills
                    </h1>
                )}


                    <div
                        className="w-full max-w-3xl bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-6 shadow-2xl mt-6">
                        {skills.length > 0 ? (
                            <div
                                className="w-full max-w-3xl bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-6 shadow-2xl">
                                <BarChart
                                    skills={skills.map((s) => ({
                                        name: s.name,
                                        level: s.progress,
                                    }))}
                                />
                            </div>
                        ) : (
                            <p className="text-gray-300 text-center">No skills to display yet.</p>
                        )}

                        {isLoggedIn && (
                            <div className="flex justify-center mt-6">
                                    <button
                                        className="bg-white/10 backdrop-blur-xl border border-white/20 text-gray-100 font-semibold px-6 py-3 rounded-full shadow-2xl hover:shadow-indigo-500/30 hover:bg-white/20 transition-all duration-300"
                                        onClick={handleSkillAddition}
                                    >
                                        + Add Skill
                                    </button>
                                </div>
                            )}
                        </div>

                        {showMenu && (
                            <div
                                className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50 animate-fade-in-up">
                                <form
                                    onSubmit={handleSubmit}
                                    className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-8 shadow-2xl w-full max-w-md text-gray-100"
                                >
                                    <h2 className="text-2xl font-bold mb-6 text-center">Add a New Skill</h2>

                                    <div className="space-y-4">
                                        <input
                                            type="text"
                                            placeholder="Skill Name"
                                            value={newSkill.name}
                                            onChange={(e) => setNewSkill({...newSkill, name: e.target.value})}
                                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                                        />
                                        <input
                                            type="text"
                                            placeholder="Description"
                                            value={newSkill.description}
                                            onChange={(e) => setNewSkill({
                                                ...newSkill,
                                                description: e.target.value
                                            })}
                                            className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                                        />
                                        <input
                                            type="number"
                                            placeholder="Progress %"
                                            min="0"
                                            max="100"
                                            value={newSkill.progress}
                                            onChange={(e) => setNewSkill({
                                                ...newSkill,
                                                progress: Number(e.target.value)
                                            })}
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
                    </div>
                    );
                }

export default Skills
