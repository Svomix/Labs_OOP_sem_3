import {useState} from "react";

export default function FeedbackSection({onDataChange}) {
    const [selectedFunction, setSelectedFunction] = useState("");
    const [points, setPoints] = useState("");
    const [intervalStart, setIntervalStart] = useState("");
    const [intervalEnd, setIntervalEnd] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    function handleSubmit(event)  {
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
            alert("TabulatedFunction создана успешно")
            onDataChange(table)
            window.location.reload()
        } catch (error) {
            setErrorMessage(error.message);
        }
    }

    return (
        <>
        <form onSubmit={handleSubmit}>
            <label htmlFor="functionSelect">Выберите функцию:</label>
            <select id="functionSelect" onChange={(e) => setSelectedFunction(e.target.value)} value={selectedFunction}>
                <option value="UnitFunction">Единичная функция</option>
                <option value="IdentityFunction">Идентичная фунция</option>
                <option value="SqrFunction">Квадратичная функция</option>
                <option value="ZeroFunction">Нулевая функция</option>
            </select>

            <label htmlFor="points">Количество точек:</label>
            <input type="number" min="1" value={points} onChange={(e) => setPoints(e.target.value)} required />

            <label htmlFor="intervalStart">Начало интервала:</label>
            <input type="number" value={intervalStart} onChange={(e) => setIntervalStart(e.target.value)} required />

            <label htmlFor="intervalEnd">Конец интервала:</label>
            <input type="number" value={intervalEnd} onChange={(e) => setIntervalEnd(e.target.value)} required />

            <button type="submit">Создать</button>
        </form>
        {errorMessage && <div className="error">{errorMessage}</div>}
            </>
    )
}
