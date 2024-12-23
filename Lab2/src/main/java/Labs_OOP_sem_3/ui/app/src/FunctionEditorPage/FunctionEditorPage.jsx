import React, { useState, useEffect, useRef } from "react";
import { Chart, registerables } from "chart.js";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './FunctionEditorPage.css';
import FirstPage from "../FirstPage/FirstPage.jsx"; // Подключаем CSS файл

Chart.register(...registerables); // Регистрируем все необходимые модули для Chart.js

export default function FunctionEditorPage() {
    const [functionData, setFunctionData] = useState([]);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [fileName, setFileName] = useState('');
    const [inputX, setInputX] = useState('');
    const [calculatedY, setCalculatedY] = useState(null);
    const chartRef = useRef(null);
    const chartInstance = useRef(null);
    const {factory} = useContext(FactoryContext);
    const [insertable, setInsertable] = useState(false)
    const [removable, setRemovable] = useState(false)

    function modalContent() {
        return (
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal} />
        );
    }

    // Инициализация графика
    useEffect(() => {
        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        const ctx = document.getElementById('functionChart').getContext('2d');
        chartInstance.current = new Chart(ctx, {
            type: 'line',
            data: {
                labels: functionData.map(point => point.x),
                datasets: [{
                    label: 'Функция',
                    data: functionData.map(point => point.y),
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderWidth: 2,
                    fill: true,
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'X'
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Y'
                        }
                    }
                }
            }
        });
    }, [functionData]);

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    const handleDataChange = (newData) => {
        setFunctionData(newData);
    };

    const saveFunction = async (fileName) => {
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

    const handleLoad = async () => {
        const selectedFile = prompt('Введите имя файла для загрузки:');
        if (selectedFile) {
            const loadedFunction = await loadFunction(selectedFile);
            if (loadedFunction) {
                setFunctionData(loadedFunction);
            } else {
                alert('Файл не найден или произошла ошибка при загрузке.');
            }
        }
    };

    const handleApply = () => {
        const x = parseFloat(inputX);
        if (isNaN(x)) {
            alert('Введите корректное значение X.');
            return;
        }

        // Пример реализации метода apply()
        const applyFunction = (x) => {
            /////////
            return null;
        };

        const y = applyFunction(x);
        if (y !== null) {
            setCalculatedY(y);
        } else {
            alert('Значение X находится вне диапазона функции.');
        }
    };

    return (
        <div className="function-editor-container">
            <div className="buttons-container">
                <Button onClick={openModal}>Создать функцию</Button>
                <Button onClick={handleLoad}>Загрузить функцию</Button>
                <Button onClick={() => saveFunction(fileName)}>Сохранить функцию</Button>
            </div>

            <div className="chart-container">
                <canvas id="functionChart" width="400" height="200"></canvas>
            </div>

            <div className="table-container">
                <h3>Таблица функции</h3>
                <table>
                    <thead>
                    <tr>
                        <th>X</th>
                        <th>Y</th>
                    </tr>
                    </thead>
                    <tbody>
                    {functionData.map((point, index) => (
                        <tr key={index}>
                            <td>{point.x}</td>
                            <td>{point.y}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            <div className="apply-container">
                <h3>Вычислить значение функции в точке X:</h3>
                <input
                    type="text"
                    placeholder="Введите X"
                    value={inputX}
                    onChange={(e) => setInputX(e.target.value)}
                />
                <Button onClick={handleApply}>Вычислить</Button>
                {calculatedY !== null && (
                    <p>Значение функции в точке X: {calculatedY}</p>
                )}
            </div>

            <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                {modalContent()}
            </Modal>
        </div>
    );
}