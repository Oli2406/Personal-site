export interface Skill {
    id: number;
    name: string;
    description: string;
    progress: number;
    targetMinutes: number;
}

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

export const SkillService = {
    async getAll(token?: string | null): Promise<Skill[]> {
        const headers: Record<string, string> = { "Content-Type": "application/json" };
        if (token) headers.Authorization = `Bearer ${token}`;

        const response = await fetch(`${API_BASE_URL}/skills`, { headers });

        if (!response.ok) throw new Error(response.statusText);

        const data = await response.json();
        console.log("Received skills:", data);

        return data;
    },

    async create(skill: Skill, token?: string | null): Promise<Skill> {
        const headers: Record<string, string> = { "Content-Type": "application/json" };
        if (token) headers.Authorization = `Bearer ${token}`;

        const response = await fetch(`${API_BASE_URL}/skills`, {
            method: "POST",
            headers,
            body: JSON.stringify(skill),
        });

        if (!response.ok) throw new Error(response.statusText);
        return response.json();
    },

    async delete(id: number, token?: string | null): Promise<void> {
        const headers: Record<string, string> = { "Content-Type": "application/json" };
        if (token) headers.Authorization = `Bearer ${token}`;

        const response = await fetch(`${API_BASE_URL}/skills/${id}`, {
            method: "DELETE",
            headers,
        });

        if (!response.ok) throw new Error(response.statusText);
    },
};
