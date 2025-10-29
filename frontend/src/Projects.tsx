import { useState, useEffect } from 'react';

export default function Projects(){
    const [repos, setRepos] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(()=>{
        fetch("https://api.github.com/users/Oli2406/repos")
            .then(res => res.json())
            .then(data => {
                setRepos(data);
                setLoading(false);
            })
            .catch(error => {
                console.error(error);
                setLoading(false);
            })
    }, [])

    if(loading){
        return <div className={"text-gray-400 text-center mt-10"}>Loading projects...</div>;
    }

    return(
        <div className="min-h-[60vh] flex flex-col items-center justify-center p-8 text-gray-100">
            <h1 className="text-4xl font-bold text-indigo-300 mb-8">My Public GitHub Projects</h1>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 w-full max-w-5xl">
                {repos.map((repo) => (
                    <a
                        key={repo.id}
                        href={repo.html_url}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-xl p-5 shadow-xl hover:-translate-y-1 hover:shadow-indigo-500/30 transition-transform duration-300"
                    >
                        <h2 className="text-xl font-semibold text-indigo-300">{repo.name}</h2>
                        <p className="text-gray-400 text-sm mt-2 line-clamp-3">
                            {repo.description || "No description provided."}
                        </p>
                    </a>
                ))}
            </div>
        </div>
    );
}