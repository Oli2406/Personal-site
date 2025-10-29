export interface Skill {
    id: number;
    name: string;
    description: string;
    progress: number;
}

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const SkillService = {

    async getAll(): Promise<Skill[]> {
        const response = await fetch(`${API_BASE_URL}/skills`);
        if(!response.ok) throw new Error(response.statusText);
        return response.json();
    },

    async create(skill: Skill): Promise<Skill> {
        const response = await fetch(`${API_BASE_URL}/skills`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(skill),
        });
        if(!response.ok) throw new Error(response.statusText);
        return response.json();
    },

    async delete(id: number): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/skills/${id}`, {
            method: "DELETE",
        });
        if(!response.ok) throw new Error(response.statusText);
    }
}