import { useState } from "react";

export default function FeedbackSection({ onDataChange, closeModal }) {
    const [selectedFunction, setSelectedFunction] = useState("");
    const [points, setPoints] = useState("");
    const [intervalStart, setIntervalStart] = useState("");
    const [intervalEnd, setIntervalEnd] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    function handleSubmit(event) {
        event.preventDefault();
        setErrorMessage("");

        const numPoints = parseInt(points);
        const start = parseFloat(intervalStart);
        const end = parseFloat(intervalEnd);

        if (numPoints <= 1) {
            setErrorMessage("Неверное количество точек разбиения. Число должно быть >=2");
            return;
        }

        const chosenFunction = selectedFunction;
        try {
            alert("TabulatedFunction создана успешно");
            closeModal(true);
            onDataChange(table);
            ///////////////////////
            window.location.reload();
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
                        min="1"
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
            {errorMessage && (
                <div style={styles.error}>{errorMessage}</div>
            )}
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