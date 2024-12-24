import React, { useState, useEffect } from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './CompositeFunctionPage.css';
import TableComponent from "./TableComponent.jsx";
import useAuth from "../hock/useAuth.jsx";
import FirstPage from "../FirstPage/FirstPage.jsx";

export default function CompositeFunctionPage() {
    const [functions, setFunctions] = useState([]); // Список функций
    const [selectedFunctions, setSelectedFunctions] = useState([]); // Выбранные функции
    const [newFunctionName, setNewFunctionName] = useState(''); // Имя новой функции
    const [modalFunctionIsOpen, setModalFunctionIsOpen] = useState(false); // Состояние модального окна для создания функции
    const [modalTableIsOpen, setModalTableIsOpen] = useState(false); // Состояние модального окна для таблицы
    const [tables, setTables] = useState([[]]); // Таблицы для отправки данных
    const [table, setTable] = useState([]);
    const { user } = useAuth();

    // Fetch запрос для получения списка функций
    useEffect(() => {
        fetchFunctions();
    }, []);

    const fetchFunctions = async () => {
        try {
            // const response = await fetch('/api/functions'); // Запрос к backend
            // const data = await response.json();
            // setFunctions(data);
        } catch (error) {
            console.error('Ошибка при получении списка функций:', error);
        }
    };

    const openFunctionModal = () => {
        setModalFunctionIsOpen(true);
    };

    const closeFunctionModal = () => {
        setModalFunctionIsOpen(false);
    };

    const openTableModal = () => {
        setModalTableIsOpen(true);
    };

    const closeTableModal = () => {
        setModalTableIsOpen(false);
    };

    const handleFunctionSelect = (func) => {
        if (!selectedFunctions.includes(func)) {
            setSelectedFunctions([...selectedFunctions, func]);
        }
    };

    const loadFunction = async (fileName) => {
        try {
            // Здесь должен быть код для загрузки функции из файла
            // Например, используя fetch или axios
            // Пример:
            // const response = await fetch(`/api/loadFunction?fileName=${fileName}`);
            // const data = await response.json();
            // return data;

            // Временная заглушка для тестирования
            return [
                { x: 1, y: 2 },
                { x: 2, y: 4 },
                { x: 3, y: 6 },
            ];
        } catch (error) {
            console.error('Ошибка при загрузке функции:', error);
            return null;
        }
    };

    const handleLoad = async () => {
        const selectedFile = prompt('Введите имя файла для загрузки:');
        if (selectedFile) {
            const loadedFunction = await loadFunction(selectedFile);
            if (loadedFunction) {
                setTable(loadedFunction);
            } else {
                alert('Файл не найден или произошла ошибка при загрузке.');
            }
        }
    };

    const handleFunctionRemove = (func) => {
        setSelectedFunctions(selectedFunctions.filter(f => f !== func));
    };

    const handleCreateCompositeFunction = async () => {
        if (!newFunctionName) {
            alert('Введите имя для новой функции.');
            return;
        }

        // Создаем новую сложную функцию
        const newCompositeFunction = {
            name: newFunctionName,
            functions: selectedFunctions.map(f => f.id), // Используем ID функций
        };

        try {
            // Отправляем новую функцию на backend
            const response = await fetch('/api/functions', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newCompositeFunction),
            });

            if (response.ok) {
                alert('Функция успешно создана!');
                await fetchFunctions(); // Обновляем список функций
                setSelectedFunctions([]);
                setNewFunctionName('');
                closeFunctionModal();
            } else {
                alert('Ошибка при создании функции.');
            }
        } catch (error) {
            console.error('Ошибка при создании функции:', error);
        }
    };

    const handleAddTable = () => {
        setTables([...tables, []]); // Добавляем новую таблицу
    };

    const handleDataChange = (data) => {
        const newTables = [...tables];
        newTables[newTables.length - 1] = data;
        setTables(newTables);
        setTable(data);
    };

    const handleConfirmTable = (index) => {
        // Сохраняем данные таблицы (например, отправляем на сервер)
        const confirmedTable = tables[index];
        console.log('Таблица подтверждена:', confirmedTable);

        // Удаляем таблицу из списка
        const newTables = tables.filter((_, i) => i !== index);
        setTables(newTables);
    };

    return (
        <div className="composite-function-container">
            <h2>Создание сложной функции</h2>
            <div className="buttons-container">
                <Button onClick={openFunctionModal}>Создать новую функцию</Button>
                <Button onClick={handleAddTable}>Добавить таблицу</Button>
            </div>

            {/* Модальное окно для создания функции */}
            <Modal isOpen={modalFunctionIsOpen} onRequestClose={closeFunctionModal}>
                <h2>Создание сложной функции</h2>
                <div className="function-list">
                    <h3>Доступные функции:</h3>
                    <ul>
                        {functions.map((func) => (
                            <li key={func.id}>
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
                        {selectedFunctions.map((func) => (
                            <li key={func.id}>
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

            {/* Таблицы */}
            <div className="tables-container">
                {tables.map((table, index) => (
                    <div key={index} className="table-wrapper">
                        <h3>Таблица {index + 1}</h3>
                        <Button onClick={openTableModal}>Создать функцию</Button>
                        <Button onClick={handleLoad}>Загрузить функцию</Button>
                        <Button onClick={() => handleConfirmTable(index)}>Подтвердить таблицу</Button>
                        <Modal isOpen={modalTableIsOpen} onRequestClose={closeTableModal}>
                            <FirstPage onDataChange={handleDataChange} closeModal={closeTableModal} />
                        </Modal>
                        <table>
                            <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {table.map((point, index) => (
                                <tr key={index}>
                                    <td>{point.x}</td>
                                    <td>{point.y}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                ))}
            </div>
        </div>
    );
}