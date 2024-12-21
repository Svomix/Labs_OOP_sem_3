import React, { createContext, useState } from "react";

// Создаем контекст
export const FactoryContext = createContext();

// Создаем провайдер
export const FactoryProvider = ({ children }) => {
    const [factory, setFactory] = useState("ArrayTabulatedFunctionFactory");

    // Функция для обновления factory
    const updateFactory = (newFactory) => {
        setFactory(newFactory);
    };

    return (
        <FactoryContext.Provider value={{ factory, updateFactory }}>
            {children}
        </FactoryContext.Provider>
    );
};