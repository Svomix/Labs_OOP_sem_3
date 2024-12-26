import {useContext, useState} from "react";
import {FactoryContext} from "../FactoryContext.jsx";
import useAuth from "../hock/useAuth.jsx";

export default function FeedbackSection({onDataChange, closeModal}) {
    const [selectedFunction, setSelectedFunction] = useState("UnitFunction");
    const [points, setPoints] = useState("");
    const [intervalStart, setIntervalStart] = useState("");
    const [intervalEnd, setIntervalEnd] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const {factory} = useContext(FactoryContext);
    const {user} = useAuth();

    async function handleSubmit(event) {
        event.preventDefault();
        setErrorMessage("");

        const numPoints = parseInt(points);
        const start = parseFloat(intervalStart);
        const end = parseFloat(intervalEnd);

        if (start === end) {
            alert('Точки старта и конца отрезка совпадают');
            return;
        }
        if (numPoints >= 1001) {
            alert('Не может быть больше 1001 точки без добавления руками');
            return;
        }


        try {
            const postIntervalArr = {
                start: start,
                end: end,
                numberOfPoints: numPoints,
                typeFunc: selectedFunction,
                typeFabric: factory,
            };

            const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
            const url1 = new URL('http://localhost:8080/points/interval');
            url1.searchParams.append('userName', user.name);
            const response = await fetch(url1, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authHeader,
                },
                body: JSON.stringify(postIntervalArr)
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            const hash = data.hash;

            const url = new URL("http://localhost:8080/points/point");
            url.searchParams.append('hash', hash);

            const getResponse = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authHeader,
                },
            });
            console.log(getResponse)

            if (!getResponse.ok) {
                throw new Error('Network response was not ok');
            }

            const getData = await getResponse.json();
            const tableData = getData.map(item => ({
                x: item.xvalue + '',
                y: item.yvalue + ''
            }));
            onDataChange(tableData, true, true);
            alert("TabulatedFunction создана успешно");
            closeModal(true);
        } catch (error) {
            setErrorMessage(error.message);
        }
    }

    return (
        <div style={styles.container}>
            <h2 style={styles.title}>Создание табулированной функции</h2>
            <form onSubmit={handleSubmit} style={styles.form}>
                <div style={styles.formGroup}>
                    <label htmlFor="functionSelect" style={styles.label}>
                        Выберите функцию:
                    </label>
                    <select
                        id="functionSelect"
                        onChange={(e) => setSelectedFunction(e.target.value)}
                        value={selectedFunction}
                        style={styles.select}
                    >
                        <option value="UnitFunction">Единичная функция</option>
                        <option value="IdentityFunction">Идентичная функция</option>
                        <option value="SqrFunction">Квадратичная функция</option>
                        <option value="ZeroFunction">Нулевая функция</option>
                    </select>
                </div>

                <div style={styles.formGroup}>
                    <label htmlFor="points" style={styles.label}>
                        Количество точек:
                    </label>
                    <input
                        type="number"
                        min="2"
                        value={points}
                        onChange={(e) => setPoints(e.target.value)}
                        required
                        style={styles.input}
                    />
                </div>

                <div style={styles.formGroup}>
                    <label htmlFor="intervalStart" style={styles.label}>
                        Начало интервала:
                    </label>
                    <input
                        type="number"
                        value={intervalStart}
                        onChange={(e) => setIntervalStart(e.target.value)}
                        required
                        style={styles.input}
                    />
                </div>

                <div style={styles.formGroup}>
                    <label htmlFor="intervalEnd" style={styles.label}>
                        Конец интервала:
                    </label>
                    <input
                        type="number"
                        value={intervalEnd}
                        onChange={(e) => setIntervalEnd(e.target.value)}
                        required
                        style={styles.input}
                    />
                </div>

                <button type="submit" style={styles.button}>
                    Создать
                </button>
            </form>
        </div>
    );
}

// Стили
const styles = {
    container: {
        maxWidth: "600px",
        margin: "0 auto",
        padding: "20px",
        border: "1px solid #ccc",
        borderRadius: "8px",
        boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        backgroundColor: "#f9f9f9",
    },
    title: {
        fontSize: "24px",
        marginBottom: "20px",
        textAlign: "center",
        color: "#333",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        gap: "15px",
    },
    formGroup: {
        display: "flex",
        flexDirection: "column",
    },
    label: {
        fontSize: "16px",
        marginBottom: "5px",
        color: "#555",
    },
    select: {
        padding: "10px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "1px solid #ccc",
    },
    input: {
        padding: "10px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "1px solid #ccc",
    },
    button: {
        padding: "10px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "none",
        backgroundColor: "#007BFF",
        color: "#fff",
        cursor: "pointer",
        transition: "background-color 0.3s",
    },
    buttonHover: {
        backgroundColor: "#0056b3",
    },
    error: {
        color: "red",
        fontSize: "14px",
        marginTop: "10px",
        textAlign: "center",
    },
};