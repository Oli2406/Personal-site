import './App.css'
import {useState} from "react";

function App() {

    const [skills, setSkills] = useState([
        { id: 1, name: "React fluency", description: "Learning hooks", progress: 5 },
        { id: 2, name: "Guitar playing", description: "Learning songs", progress: 25 },
        { id: 3, name: "Snowboarding", description: "Learning to jump", progress: 65 },
    ]);

    const [showMenu, setShowMenu] = useState(false);

    const [newSkill, setNewSkill] = useState({
        id: 0,
        name: "",
        description: "",
        progress: "",
    });

    const handleSkillAddition = () => setShowMenu(true);

    const handleCloseMenu = () => setShowMenu(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if(newSkill === undefined || !newSkill.name || !newSkill.description || !newSkill.progress) return;

        setSkills([...skills, {
            id: Date.now(),
            name: newSkill.name,
            description: newSkill.description,
            progress: Number(newSkill.progress),
        },
        ]);

        setNewSkill({id: -1, name: "", description: "", progress: "0" });
        setShowMenu(false);
    }

    return (
        <div className="min-h-screen w-full overflow-hidden bg-gradient-to-br from-gray-900 via-indigo-900 to-purple-900 text-gray-100 relative">
            <div className="absolute inset-0 bg-[radial-gradient(circle_at_top_left,_#312e81_0%,_transparent_50%),radial-gradient(circle_at_bottom_right,_#9333ea_0%,_transparent_50%)]" />
            <div className="flex flex-col items-center justify-center min-h-screen px-4">
                <h1 className="text-4xl font-bold mb-8 text-center">My Skills</h1>

                <ul className="grid gap-6 w-full max-w-md">
                    {skills.map((skill, index) => (
                        <li
                            key={skill.id}
                            className="relative bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-6 shadow-2xl hover:shadow-indigo-500/30 transition-transform duration-500 hover:-translate-y-1 animate-fade-in-up"
                            style={{animationDelay: `${index * 150}ms`}}
                        >
                            <div className="flex items-center justify-between mb-2">
                                <h2 className="text-2xl font-semibold">{skill.name}</h2>
                                <span className="text-sm text-gray-400">{skill.progress}%</span>
                            </div>
                            <p className="text-gray-300 mb-3">{skill.description}</p>
                            <div className="w-full bg-gray-700 rounded-full h-2.5">
                                <div
                                    className="bg-indigo-500 h-2.5 rounded-full transition-all"
                                    style={{width: `${skill.progress}%`}}
                                />
                            </div>
                        </li>
                    ))}
                    <button
                        className="bg-white/10 backdrop-blur-xl border border-white/20 text-gray-100 font-semibold px-6 py-3 rounded-full shadow-2xl hover:shadow-indigo-500/30 hover:bg-white/20 transition-all duration-300"
                    onClick={handleSkillAddition}>
                        + Add Skill
                    </button>
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
                                        onChange={(e) => setNewSkill({...newSkill, description: e.target.value})}
                                        className="w-full p-3 rounded-xl bg-white/10 border border-white/20 placeholder-gray-400 focus:outline-none focus:border-indigo-400"
                                    />
                                    <input
                                        type="number"
                                        placeholder="Progress %"
                                        min="0"
                                        max="100"
                                        value={newSkill.progress}
                                        onChange={(e) => setNewSkill({...newSkill, progress: e.target.value})}
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
                </ul>
            </div>
        </div>
    );
}

export default App
