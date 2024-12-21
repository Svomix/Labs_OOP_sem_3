import React, { useContext } from "react";
import { FactoryContext } from "./FactoryContext";

export default function SelectFactory() {
    const { updateFactory } = useContext(FactoryContext);

    function handleSubmit(event) {
        event.preventDefault();
        alert("Фабрика изменена успешно");
    }

    return (
        <section>
            <form onSubmit={handleSubmit}>
                <label htmlFor="factorySelect">Выберите функцию:</label>
                <select
                    id="factorySelect"
                    onChange={(e) => updateFactory(e.target.value)}
                >
                    <option value="ArrayTabulatedFunctionFactory">
                        Фабрика на основе массива
                    </option>
                    <option value="LinkedListTabulatedFunctionFactory">
                        Фабрика на основе листа
                    </option>
                </select>
                <button type="submit">Применить</button>
            </form>
        </section>
    );
}