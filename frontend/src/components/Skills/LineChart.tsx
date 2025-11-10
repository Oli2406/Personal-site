import React, { useEffect } from "react";
import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    Tooltip,
    CartesianGrid,
    ResponsiveContainer,
} from "recharts";

export interface ProgressPoint {
    progress: number;
    createdAt: string;
}

interface LineChartProgressProps {
    data: ProgressPoint[];
    title?: string;
    color?: string;
}

const LineChartProgress: React.FC<LineChartProgressProps> = ({
                                                                 data,
                                                                 title = "Progress Over Time",
                                                                 color = "#6366f1",
                                                             }) => {
    // 🔁 trigger a resize after render (important in grids/flex)
    useEffect(() => {
        const timeout = setTimeout(() => {
            window.dispatchEvent(new Event("resize"));
        }, 200);
        return () => clearTimeout(timeout);
    }, [data]);

    if (!data || data.length === 0) {
        return (
            <div className="text-gray-400 text-center h-48 flex items-center justify-center">
                No progress updates yet.
            </div>
        );
    }

    return (
        <div className="w-full min-w-[300px] min-h-[300px]">
            <h3 className="text-xl font-semibold text-gray-200 mb-4 text-center">
                {title}
            </h3>

            <div className="relative w-full h-[300px]">
                <ResponsiveContainer width="100%" height="100%">
                    <LineChart data={data}>
                        <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.2)" />
                        <XAxis
                            dataKey="createdAt"
                            tick={{ fill: "white" }}
                            style={{ fontSize: "0.8rem" }}
                        />
                        <YAxis tick={{ fill: "white" }} domain={[0, 100]} />
                        <Tooltip
                            contentStyle={{
                                backgroundColor: "rgba(30,30,60,0.9)",
                                borderRadius: "10px",
                                color: "#fff",
                            }}
                            labelStyle={{ color: "#a5b4fc" }}
                        />
                        <Line
                            type="monotone"
                            dataKey="progress"
                            stroke={color}
                            strokeWidth={2}
                            dot={{ fill: color, strokeWidth: 1 }}
                            activeDot={{ r: 6 }}
                        />
                    </LineChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
};

export default LineChartProgress;
