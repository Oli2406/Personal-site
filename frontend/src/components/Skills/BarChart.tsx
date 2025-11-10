import React from "react";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    CartesianGrid,
    ResponsiveContainer,
} from "recharts";

interface SkillChartProps {
    skills: { name: string; level: number }[];
}

const SkillChart: React.FC<SkillChartProps> = ({ skills }) => (
    <ResponsiveContainer width="100%" height={Math.max(100 + skills.length * 40, 300)}>
        <BarChart
            data={skills}
            layout="vertical"
            margin={{ top: 20, right: 20, left: 40, bottom: 20 }}
        >
            <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.2)" />
            <XAxis type="number" domain={[0, 100]} tick={{ fill: "white" }} />
            <YAxis
                dataKey="name"
                type="category"
                tick={{ fill: "white" }}
                width={100}
            />
            <Tooltip
                contentStyle={{
                    backgroundColor: "rgba(30, 30, 60, 0.9)",
                    borderRadius: "10px",
                    border: "none",
                    color: "#fff",
                }}
            />
            <Bar
                dataKey="level"
                fill="#6366f1"
                radius={[0, 10, 10, 0]}
            />
        </BarChart>
    </ResponsiveContainer>
);

export default SkillChart;
