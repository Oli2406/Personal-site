import { useState, useEffect } from "react";

export function useLocalStorage<T>(key: string, initialValue: T) {
    const [value, setValue] = useState<T>(() => {
        try {
            const jsonValue = localStorage.getItem(key);
            return jsonValue != null ? JSON.parse(jsonValue) : initialValue;
        } catch (error) {
            console.error(error);
            return initialValue;
        }
    });

    useEffect(() => {
        try {
            localStorage.setItem(key, JSON.stringify(value));
        } catch (error) {
            console.error("Error writing to localStorage", key, error);
        }
    }, [key, value]);

    return [value, setValue] as const;
}