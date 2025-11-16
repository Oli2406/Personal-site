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

interface Skill {
    name: string;
    level: number;
    targetMinutes: number;
}

interface SkillChartProps {
    skills: Skill[];
    onSkillClick?: (skillName: string) => void;
    selectedSkillName?: string;
}

const SkillLabel = ({
                        x,
                        y,
                        payload,
                        onSkillClick,
                        selected,
                    }: any) => {
    return (
        <text
            x={x}
            y={y}
            dy={5}
            textAnchor="end"
            fill={selected ? "#a5b4fc" : "white"}
            fontSize={14}
            style={{ cursor: "pointer" }}
            onClick={() => onSkillClick && onSkillClick(payload.value)}
        >
            {payload.value}
        </text>
    );
};

const SkillChart: React.FC<SkillChartProps> = ({
                                                   skills,
                                                   onSkillClick,
                                                   selectedSkillName,
                                               }) => {
    return (
        <ResponsiveContainer
            width="100%"
            height={Math.max(100 + skills.length * 40, 300)}
        >
            <BarChart
                data={skills}
                layout="vertical"
                margin={{ top: 20, right: 20, left: 120, bottom: 20 }}
            >
                <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.2)" />

                <XAxis type="number" domain={[0, 100]} tick={{ fill: "white" }} />

                <YAxis
                    dataKey="name"
                    type="category"
                    width={120}
                    tick={(props) => (
                        <SkillLabel
                            {...props}
                            onSkillClick={onSkillClick}
                            selected={selectedSkillName === props.payload.value}
                        />
                    )}
                />

                <Tooltip
                    contentStyle={{
                        backgroundColor: "rgba(30,30,60,0.9)",
                        borderRadius: "10px",
                        border: "none",
                        color: "#fff",
                    }}
                />

                <Bar
                    dataKey="level"
                    fill="#6366f1"
                    radius={[0, 10, 10, 0]}
                    activeBar={false}>
                </Bar>

            </BarChart>
        </ResponsiveContainer>
    );
};

export default SkillChart;
