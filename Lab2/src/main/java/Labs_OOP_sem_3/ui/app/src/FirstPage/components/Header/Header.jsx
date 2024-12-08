import logo from '/logo1.png'
import './Header.css'

export default function Header() {
    const now = new Date()
    const name = 'result'
    return (
        <header>
            <img src={logo} alt={name}/>
            <span>Время сейчас: {now.toLocaleTimeString()}</span>
        </header>
    )
}