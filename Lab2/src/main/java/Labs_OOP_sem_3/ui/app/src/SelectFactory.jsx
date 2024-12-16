import {useState} from "react";

export default function SelectFactory() {
    const [factory, setFactory] = useState('ArrayTabulatedFunctionFactory')

    function handleSubmit(event) {
        alert("Фабрика изменена успешно");
        window.location.reload()
    }


    return (
        <section>
            <form onSubmit={handleSubmit}>
                <label htmlFor="factorySelect">Выберите функцию:</label>
                <select id="factorySelect" onChange={(e) => setFactory(e.target.value)}
                        value={factory}>
                    <option value="ArrayTabulatedFunctionFactory">Фабрика на основе массива</option>
                    <option value="LinkedListTabulatedFunctionFactory">Фабрика на основе листа</option>
                </select>
                <button type="submit">Применить</button>
            </form>
        </section>
    )
}