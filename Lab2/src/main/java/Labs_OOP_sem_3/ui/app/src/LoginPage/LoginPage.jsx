import { useLocation, useNavigate } from "react-router-dom";
import useAuth from "../hock/useAuth";
import './LoginPage.css'; // Подключаем CSS файл

export default function LoginPage() {
    const navigate = useNavigate();
    const location = useLocation();
    const { signin } = useAuth();

    const fromPage = location.state?.from?.pathname || '/';

    const handleSubmit = async (event) => {
        event.preventDefault();
        const form = event.target;
        const user = {
            name: form.username.value,
            password: form.password.value,
        };
        signin(user, () => navigate(fromPage, { replace: true }));
    };

    return (
        <div className="login-page">
            <h1>Login Page</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input name='username' />
                </label>
                <label>
                    Password:
                    <input name='password' type='password' />
                </label>
                <button type='submit'>Login</button>
            </form>
            <p>
                Нет аккаунта? <a href='/register'>Зарегистрируйтесь</a>
            </p>
        </div>
    );
}