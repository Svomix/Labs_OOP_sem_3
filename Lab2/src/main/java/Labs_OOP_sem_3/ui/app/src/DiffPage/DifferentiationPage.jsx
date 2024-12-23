import React, { useState } from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './DifferentiationPage.css';
import FirstPage from "../FirstPage/FirstPage.jsx"; // Подключаем CSS файл


export default function DifferentiationPage() {
    const [originalFunction, setOriginalFunction] = useState([]);
    const [differentiatedFunction, setDifferentiatedFunction] = useState([]);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [activeModal, setActiveModal] = useState(null);
    const [fileName, setFileName] = useState('');
    const [insertable, setInsertable] = useState(false)
    const [removable, setRemovable] = useState(false)

    const openModal = (modalType) => {
        setModalIsOpen(true);
        setActiveModal(modalType);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        setActiveModal(null);
    };

    const handleDataChange = (newData, ins, rem) => {
        setInsertable(ins)
        setRemovable(rem)
        setOriginalFunction(newData);
    };

    const performDifferentiation = () => {
        // Пример реализации дифференцирования (заглушка)
        ////////
        setDifferentiatedFunction(result);
    };

    const saveFunction = async (table, fileName) => {
        try {
            alert('Функция успешно сохранена!');
        } catch (error) {
            console.error('Ошибка при сохранении функции:', error);
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
    function modalContent() {
        return (
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal} />
        );
    }

    const handleLoad = async () => {
        const selectedFile = prompt('Введите имя файла для загрузки:');
        if (selectedFile) {
            const loadedFunction = await loadFunction(selectedFile);
            if (loadedFunction) {
                setOriginalFunction(loadedFunction);
            } else {
                alert('Файл не найден или произошла ошибка при загрузке.');
            }
        }
    };

    function handleInsert(setTable) {
        const newPoint = { x: table1.length + 1, y: 0 }; // Пример новой точки
        setTable(prev => [...prev, newPoint]);
    }

    function handleRemove(setTable) {
        setTable(prev => prev.slice(0, -1))
    }
    return (
        <div className="differentiation-page-container">
            <div className="buttons-container">
                <Button onClick={() => openModal('create')}>Создать функцию</Button>
                <Button onClick={handleLoad}>Загрузить функцию</Button>
                <Button onClick={() => saveFunction(originalFunction, fileName)}>Сохранить исходную функцию</Button>
                <Button onClick={() => saveFunction(differentiatedFunction, fileName)}>Сохранить результат</Button>
                <Button onClick={performDifferentiation}>Дифференцировать</Button>
            </div>

            <div className="tables-container">
                <div className="table-wrapper">
                    <h3>Исходная функция</h3>
                    {originalFunction.length > 0 && (
                        <table id="originalTable">
                            <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {originalFunction.map((row, index) => (
                                <tr key={index}>
                                    <td>{row.x}</td>
                                    <td>{row.y}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                    {insertable && <Button onClick={() => handleInsert(setOriginalFunction)}>Вставить</Button>}
                    {removable && (
                        <Button onClick={() => handleRemove(setOriginalFunction)}>Удалить</Button>
                    )}
                </div>

                <div className="table-wrapper">
                    <h3>Результат дифференцирования</h3>
                    {differentiatedFunction.length > 0 && (
                        <table id="differentiatedTable">
                            <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {differentiatedFunction.map((row, index) => (
                                <tr key={index}>
                                    <td>{row.x}</td>
                                    <td>{row.y}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>
            </div>
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                {modalContent()}
            </Modal>
        </div>
    );
}