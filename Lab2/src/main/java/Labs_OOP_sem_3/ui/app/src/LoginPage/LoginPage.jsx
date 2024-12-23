import {Navigate, useLocation, useNavigate} from "react-router-dom";
import useAuth from "../hock/useAuth";

export default function LoginPage() {
    const navigate = useNavigate();
    const location = useLocation();
    const { signin } = useAuth();

    const fromPage = location.state?.from?.pathname || '/';

    const handleSubmit = (event) => {
        event.preventDefault();
        const form = event.target;
        const user = {
            username: form.username.value,
            password: form.password.value,
        };

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