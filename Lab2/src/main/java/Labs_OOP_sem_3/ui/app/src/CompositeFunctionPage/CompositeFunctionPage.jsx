import React, { useState } from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './CompositeFunctionPage.css'; // Подключаем CSS файл

export default function CompositeFunctionPage() {
    const [selectedFunctions, setSelectedFunctions] = useState([]);
    const [newFunctionName, setNewFunctionName] = useState('');
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    const handleFunctionSelect = (func) => {
        if (!selectedFunctions.includes(func)) {
            setSelectedFunctions([...selectedFunctions, func]);
        }
    };

    const handleFunctionRemove = (func) => {
        setSelectedFunctions(selectedFunctions.filter(f => f !== func));
    };

    const handleCreateCompositeFunction = () => {
        if (selectedFunctions.length === 0) {
            alert('Выберите хотя бы одну функцию для создания сложной функции.');
            return;
        }

        if (!newFunctionName) {
            alert('Введите имя для новой функции.');
            return;
        }

        // Создаем новую сложную функцию
        const newCompositeFunction = {
            name: newFunctionName,
            functions: selectedFunctions,
        };
        
        // Очищаем состояние
        setSelectedFunctions([]);
        setNewFunctionName('');
        closeModal();
    };

    return (
        <div className="composite-function-container">
            <h2>Создание сложной функции</h2>
            <div className="buttons-container">
                <Button onClick={openModal}>Создать новую функцию</Button>
            </div>

            <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                <h2>Создание сложной функции</h2>
                <div className="function-list">
                    <h3>Доступные функции:</h3>
                    <ul>
                        {functions.map((func, index) => (
                            <li key={index}>
                                <label>
                                    <input
                                        type="checkbox"
                                        checked={selectedFunctions.includes(func)}
                                        onChange={() => handleFunctionSelect(func)}
                                    />
                                    {func.name}
                                </label>
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="selected-functions">
                    <h3>Выбранные функции:</h3>
                    <ul>
                        {selectedFunctions.map((func, index) => (
                            <li key={index}>
                                {func.name}
                                <Button onClick={() => handleFunctionRemove(func)}>Удалить</Button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="function-name">
                    <h3>Название новой функции:</h3>
                    <input
                        type="text"
                        placeholder="Введите название"
                        value={newFunctionName}
                        onChange={(e) => setNewFunctionName(e.target.value)}
                    />
                </div>

                <Button onClick={handleCreateCompositeFunction}>Создать функцию</Button>
            </Modal>
        </div>
    );
}