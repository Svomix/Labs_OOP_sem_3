import { useState } from "react";
import CustomLink from "../Link/CustomLink.jsx";
import { Outlet } from "react-router";
import './Header.css'; // Подключаем CSS файл

export default function Header() {
    const [now, setNow] = useState(new Date());
    setInterval(() => setNow(new Date()), 1000);

    return (
        <>
            <header>
                <CustomLink to="/">Операции с функциями</CustomLink>
                <CustomLink to="/selectFactory">Настройки</CustomLink>
                <CustomLink to="/diff">Дифференцирование</CustomLink>
                <CustomLink to="/graphic">График</CustomLink>
                <CustomLink to="/integrate">Интегрирование</CustomLink>
                <CustomLink to="/composit">Сложные функции</CustomLink>
                <span>Время сейчас: {now.toLocaleTimeString()}</span>
            </header>

            <main>
                <Outlet />
            </main>
        </>
    );
}