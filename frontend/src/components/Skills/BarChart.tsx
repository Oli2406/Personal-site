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

const CustomTooltip = ({ active, payload }: any) => {
    if (!active || !payload || !payload.length) return null;

    const skill = payload[0].payload;
    const currentMinutes = Math.round((skill.level / 100) * skill.targetMinutes);

    return (
        <div
            style={{
                backgroundColor: "rgba(30,30,60,0.9)",
                padding: "10px",
                borderRadius: "10px",
                color: "white"
            }}
        >
            <div><strong>{skill.name}</strong></div>
            <div>Progress: {skill.level}%</div>
            <div>Time: {currentMinutes} / {skill.targetMinutes} min</div>
        </div>
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
                    content={<CustomTooltip />}
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
