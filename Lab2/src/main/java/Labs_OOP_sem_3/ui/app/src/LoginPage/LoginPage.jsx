import {Navigate, useLocation, useNavigate} from "react-router-dom";
import useAuth from "../hock/useAuth";

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

        try {
            const username = 'igor';
            const password = '12345';
            const authHeader = `Basic ${btoa(`${username}:${password}`)}`;
            const response = await fetch('http://localhost:8080/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user),
            });

            if (response.ok) {
                return response.json();
            } else {
                alert('Ошибка входа. Проверьте логин и пароль.');
            }
        } catch (error) {
            console.error('Ошибка при входе:', error);
            alert('Произошла ошибка при входе.');
        }

        signin(user, () => navigate(fromPage, { replace: true }));
    };

    return (
        <div>
            <h1>Login Page</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Name: <input name='username' />
                </label>
                <label>
                    Password: <input name='password' type='password' />
                </label>
                <button type='submit'>Login</button>
            </form>
            <p>
                Нет аккаунта? <a href='/register'>Зарегистрируйтесь</a>
            </p>
        </div>
    );
}