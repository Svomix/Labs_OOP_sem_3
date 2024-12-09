import logo from '/logo1.png'
import './Header.css'
import { styled } from "styled-components";
import './Header.css'
import {useState} from "react";

const HeaderContainer = styled.header`
    height: 50px;
    display: flex;
    padding: 0 2rem;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #ccc;
    background: #fafafa;
`

export default function Header() {
    const [now, setNow] = useState(new Date())
    setInterval(() => setNow(new Date()), 1000)

    return (
        <HeaderContainer>
            <span>Время сейчас: {now.toLocaleTimeString()}</span>
        </HeaderContainer>
    )
}