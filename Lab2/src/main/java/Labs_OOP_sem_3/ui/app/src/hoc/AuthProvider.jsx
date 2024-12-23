import { createContext, useState } from "react";

export const AuthContext = createContext(null);

export default function AuthProvider({ children }) {
    const [user, setUser] = useState(null);

    // Функция для входа
    const signin = async (newUser, cb) => {
        try {
            const response = await fetch('/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newUser),
            });

            if (response.ok) {
                const data = await response.json();
                setUser(data.user); // Сохраняем данные пользователя
                cb(); // Вызываем колбэк для перенаправления
            } else {
                alert('Ошибка входа. Проверьте логин и пароль.');
            }
        } catch (error) {
            console.error('Ошибка при входе:', error);
            alert('Произошла ошибка при входе.');
        }
    };

    // Функция для выхода
    const signout = (cb) => {
        setUser(null);
        cb();
    };

    const value = { user, signin, signout };
    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}