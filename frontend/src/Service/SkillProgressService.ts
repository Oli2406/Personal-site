export interface SkillProgress {
    id: number;
    name: string;
    description: string;
    progress: number;
}

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

export const SkillProgressService = {
    async create(skillProgress: SkillProgress, token?: string | null): Promise<SkillProgress> {
        const headers: Record<string, string> = { "Content-Type": "application/json" };
        if (token) headers.Authorization = `Bearer ${token}`;

        const response = await fetch(`${API_BASE_URL}/skillProgress`, {
            method: "POST",
            headers,
            body: JSON.stringify(skillProgress),
        });

        if(!response.ok) throw new Error(response.statusText);
        return response.json();
    },

    async getBySkill(skillId: number, token?: string | null): Promise<{ progress: number; createdAt: Date }[]> {
        const headers: Record<string, string> = { "Content-Type": "application/json" };
        if (token) headers.Authorization = `Bearer ${token}`;

        const response = await fetch(`${API_BASE_URL}/skillProgress/${skillId}`, {
            method: "GET",
            headers,
        });

        if (!response.ok) throw new Error(response.statusText);
        return response.json();
    },
}