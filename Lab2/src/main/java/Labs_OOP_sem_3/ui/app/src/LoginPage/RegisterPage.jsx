import {useLocation, useNavigate} from "react-router-dom";

export default function RegisterPage() {
    const navigate = useNavigate();
    const location = useLocation();

    const fromPage = location.state?.from?.pathname || '/';

    const handleSubmit = async (event) => {
        event.preventDefault();

        const form = event.target;
        const user = {
            username: form.username.value,
            password: form.password.value,
        };

        try {
            const response = await fetch('http://localhost:8080/users/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user),
            });

            if (response.ok) {
                alert('Регистрация прошла успешно');
                navigate(fromPage, {replace: true});
            } else {
                alert('Ошибка входа. Проверьте логин и пароль.');
            }
        } catch (error) {
            console.error('Ошибка при входе:', error);
            alert('Произошла ошибка при входе.');
        }
    };

    return (
        <div>
            <h1>Register Page</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Name: <input name='username'/>
                </label>
                <label>
                    Password: <input name='password' type='password'/>
                </label>
                <button type='submit'>Register</button>
            </form>
            <p>
                Уже есть аккаунт? <a href='/login'>Войдите</a>
            </p>
        </div>
    );
}