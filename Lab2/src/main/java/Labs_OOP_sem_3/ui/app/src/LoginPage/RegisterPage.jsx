import {Navigate, useNavigate} from "react-router-dom";

export default function RegisterPage() {
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        const form = event.target;
        const user = {
            username: form.username.value,
            password: form.password.value,
            email: form.email.value,
        };

        try {
            const response = await fetch('/api/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user),
            });

            if (response.ok) {
                alert('Регистрация прошла успешно!');
                navigate('/login'); // Перенаправляем на страницу входа
            } else {
                alert('Ошибка регистрации. Попробуйте снова.');
            }
        } catch (error) {
            console.error('Ошибка при регистрации:', error);
            alert('Произошла ошибка при регистрации.');
        }
    };

    return (
        <div>
            <h1>Register Page</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Name: <input name='username' />
                </label>
                <label>
                    Password: <input name='password' type='password' />
                </label>
                <button type='submit'>Register</button>
            </form>
            <p>
                Уже есть аккаунт? <a href='/login'>Войдите</a>
            </p>
        </div>
    );
}