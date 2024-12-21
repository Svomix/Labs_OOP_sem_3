import './Header.css'
import {useState} from "react";
import CustomLink from "../Link/CustomLink.jsx";
import {Outlet} from "react-router";


export default function Header() {
    const [now, setNow] = useState(new Date())
    setInterval(() => setNow(new Date()), 1000)

    return (
        <>
            <header>
                <CustomLink to="/">Операции с функциями</CustomLink>
                <CustomLink to="/selectFactory">Настройки</CustomLink>
                <CustomLink to="/diff">Дифференцирование</CustomLink>
                <span>Время сейчас: {now.toLocaleTimeString()}</span>
            </header>

            <main>
                <Outlet/>
            </main>
        </>
    )
}