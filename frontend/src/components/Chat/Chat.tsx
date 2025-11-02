import { useAuth } from "../../contexts/AuthContext.tsx";
import React, { useEffect, useRef, useState } from "react";
import { Navigate } from "react-router-dom";

interface Message {
    sender: string;
    content: string;
    timestamp: string;
}

interface Room {
    code: string;
    participants: string[];
    messages: Message[];
}

export default function Chat() {
    const { token, username, isLoggedIn } = useAuth();

    const [rooms, setRooms] = useState<Room[]>([]);
    const [currentRoom, setCurrentRoom] = useState<Room | null>(null);
    const [newRoomCode, setNewRoomCode] = useState("");
    const [message, setMessage] = useState("");

    const socketsRef = useRef<Record<string, WebSocket>>({});
    const messagesEndRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        const storedRooms = JSON.parse(localStorage.getItem("joinedRooms") || "[]");
        setRooms(
            storedRooms.map((code: string) => ({
                code,
                participants: [],
                messages: [],
            }))
        );
    }, []);

    useEffect(() => {
        return () => {
            Object.values(socketsRef.current).forEach((ws) => ws.close());
        };
    }, []);

    const joinRoom = (code: string) => {
        if (!token) {
            console.error("Missing token — cannot join room.");
            return;
        }

        if (socketsRef.current[code]?.readyState === WebSocket.OPEN) {
            const existingRoom = rooms.find((r) => r.code === code);
            setCurrentRoom(existingRoom || { code, participants: [], messages: [] });
            return;
        }

        const joinedRooms = JSON.parse(localStorage.getItem("joinedRooms") || "[]");
        if (!joinedRooms.includes(code)) {
            joinedRooms.push(code);
            localStorage.setItem("joinedRooms", JSON.stringify(joinedRooms));
        }

        const ws = new WebSocket(
            `ws://localhost:8080/ws/chat?room=${code}&token=${encodeURIComponent(token)}`
        );
        socketsRef.current[code] = ws;

        setRooms((prev) => {
            if (!prev.find((r) => r.code === code)) {
                return [...prev, { code, participants: [], messages: [] }];
            }
            return prev;
        });

        ws.onopen = () => console.log(`✅ Connected to room: ${code}`);

        ws.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);

                if (data.type === "ROOM_INFO") {
                    setRooms((prev) =>
                        prev.map((r) =>
                            r.code === code
                                ? {
                                    ...r,
                                    participants: data.data?.participants || [],
                                    messages: data.data?.messages || [],
                                }
                                : r
                        )
                    );
                } else if (data.type === "NEW_MESSAGE") {
                    setRooms((prev) =>
                        prev.map((r) =>
                            r.code === code
                                ? { ...r, messages: [...r.messages, data.message] }
                                : r
                        )
                    );
                } else if (data.type === "PARTICIPANTS_UPDATE") {
                    setRooms((prev) =>
                        prev.map((r) =>
                            r.code === code
                                ? { ...r, participants: data.participants }
                                : r
                        )
                    );
                }
            } catch (err) {
                console.error("Error parsing WebSocket message:", err);
            }
        };

        ws.onclose = (e) => {
            console.warn(`⚠️ Disconnected from ${code} (${e.code}): ${e.reason || "No reason"}`);
        };

        ws.onerror = (err) => console.error("WebSocket error:", err);

        setCurrentRoom({ code, participants: [], messages: [] });
    };

    const handleJoinRoom = (e: React.FormEvent) => {
        e.preventDefault();
        const code = newRoomCode.trim();
        if (code) {
            joinRoom(code);
            setNewRoomCode("");
        }
    };

    const handleSendMessage = (e: React.FormEvent) => {
        e.preventDefault();
        if (!currentRoom || !message.trim()) return;

        const socket = socketsRef.current[currentRoom.code];
        if (!socket || socket.readyState !== WebSocket.OPEN) {
            console.warn("Cannot send — WebSocket not open.");
            return;
        }

        const msg = {
            type: "MESSAGE",
            room: currentRoom.code,
            sender: username,
            content: message.trim(),
        };

        socket.send(JSON.stringify(msg));
        setMessage("");
    };

    if (!isLoggedIn || !token) {
        return <Navigate to="/login" replace />;
    }

    const currentRoomData =
        rooms.find((r) => r.code === currentRoom?.code) || currentRoom;

    return (
        <div className="flex h-[80vh] gap-4 p-4 text-gray-200">
            <div className="w-1/4 bg-white/10 backdrop-blur-md border border-white/10 rounded-2xl p-4 space-y-4">
                <h2 className="text-xl font-semibold text-indigo-300">Chat Rooms</h2>

                <form onSubmit={handleJoinRoom} className="flex gap-2">
                    <input
                        type="text"
                        placeholder="Enter room code"
                        value={newRoomCode}
                        onChange={(e) => setNewRoomCode(e.target.value)}
                        className="w-full p-2 bg-white/10 rounded-xl border border-white/20 focus:outline-none"
                    />
                    <button
                        type="submit"
                        className="px-3 py-2 bg-indigo-600 rounded-xl hover:bg-indigo-700"
                    >
                        Join
                    </button>
                </form>

                <div className="space-y-2 overflow-y-auto max-h-[50vh]">
                    {rooms.map((room) => (
                        <button
                            key={room.code}
                            onClick={() => joinRoom(room.code)}
                            className={`w-full text-left p-2 rounded-xl ${
                                currentRoom?.code === room.code
                                    ? "bg-indigo-700"
                                    : "bg-white/10 hover:bg-white/20"
                            }`}
                        >
                            {room.code}
                        </button>
                    ))}
                </div>
            </div>

            <div className="flex-1 bg-white/10 backdrop-blur-md border border-white/10 rounded-2xl flex flex-col">
                {currentRoomData ? (
                    <>
                        <div className="border-b border-white/10 p-3 flex justify-between">
                            <h2 className="text-xl font-semibold text-indigo-300">
                                Room: {currentRoomData.code}
                            </h2>
                            <p className="text-sm text-gray-400">
                                Participants: {(currentRoomData.participants ?? []).join(", ") || "None"}
                            </p>
                        </div>

                        <div className="flex-1 overflow-y-auto p-4 space-y-2">
                            {currentRoomData.messages.map((msg, i) => (
                                <div
                                    key={i}
                                    className={`p-3 rounded-xl max-w-[70%] ${
                                        msg.sender === username
                                            ? "ml-auto bg-indigo-600"
                                            : "bg-white/20"
                                    }`}
                                >
                                    <p className="text-sm text-gray-200">
                                        <strong>{msg.sender}</strong>
                                    </p>
                                    <p>{msg.content}</p>
                                    <p className="text-xs text-gray-400 text-right">
                                        {new Date(msg.timestamp).toLocaleTimeString()}
                                    </p>
                                </div>
                            ))}
                            <div ref={messagesEndRef}></div>
                        </div>

                        <form
                            onSubmit={handleSendMessage}
                            className="p-3 border-t border-white/10 flex gap-2"
                        >
                            <input
                                type="text"
                                placeholder="Type your message..."
                                value={message}
                                onChange={(e) => setMessage(e.target.value)}
                                className="flex-1 p-2 rounded-xl bg-white/10 border border-white/20 focus:outline-none"
                            />
                            <button
                                type="submit"
                                className="px-4 py-2 bg-indigo-600 rounded-xl hover:bg-indigo-700"
                            >
                                Send
                            </button>
                        </form>
                    </>
                ) : (
                    <div className="flex-1 flex items-center justify-center text-gray-400">
                        Join a room to start chatting 💬
                    </div>
                )}
            </div>
        </div>
    );
}
