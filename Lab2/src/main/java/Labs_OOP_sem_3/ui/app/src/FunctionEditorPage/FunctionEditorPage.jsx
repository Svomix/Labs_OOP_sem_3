import React, {useContext, useRef, useState} from "react";
import {Chart, registerables} from "chart.js";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './FunctionEditorPage.css'; // Подключаем CSS файл
import FirstPage from "../FirstPage/FirstPage.jsx";
import {FactoryContext} from "../FactoryContext.jsx";
import useAuth from "../hock/useAuth.jsx";

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
    const {user} = useAuth();

    // Состояние для пагинации
    const [currentPage, setCurrentPage] = useState(1);
    const [rowsPerPage] = useState(5); // Количество строк на странице

    function modalContent() {
        return (
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal}/>
        );
    }

    // Функция для проверки сортировки таблицы
    const isSorted = (tableData) => {
        for (let i = 1; i < tableData.length; i++) {
            const currentX = parseFloat(tableData[i].x);
            const previousX = parseFloat(tableData[i - 1].x);

            if (isNaN(currentX) || isNaN(previousX)) {
                return false;
            }

            if (currentX <= previousX) {
                return false;
            }
        }
        return true;
    };

    // Функция для проверки заполненности всех ячеек
    const areAllCellsFilled = (tableData) => {
        return tableData.every(item => item.x !== '' && item.y !== '');
    };

    // Функция для обновления графика
    const updateChart = () => {
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
    };

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    const handleDataChange = (newData) => {
        setFunctionData(newData);
    };

    const handleFileChange = async (e, setTable) => {
        const selectedFile = e.target.files[0];
        if (!selectedFile) {
            alert("Выберите файл для загрузки.");
            return;
        }

        // Проверка расширения файла
        const allowedExtensions = [".json", ".xml"];
        const fileExtension = selectedFile.name.split(".").pop().toLowerCase();
        if (!allowedExtensions.includes("." + fileExtension)) {
            alert("Файл должен быть в формате JSON или XML.");
            return;
        }

        const formData = new FormData();
        formData.append("file", selectedFile);
        const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
        try {
            const response = await fetch('http://localhost:8080/points/upload', {
                method: "POST",
                headers: {
                    'Authorization': authHeader,
                },
                body: formData,
            });

            if (response.ok) {
                const result = await response.json();
                setUploadStatus("Файл успешно загружен: " + result.message);
                const tableData = result.x.map((xValue, index) => ({
                    x: xValue + '',
                    y: result.y[index] + '',
                }));
                setTable(tableData);
            } else {
                throw new Error("Ошибка при загрузке файла.");
            }
        } catch (error) {
            alert(error);
            setUploadStatus("Ошибка при загрузке файла.");
        }
    };

    const saveFunction = (table) => {
        if (table.length === 0) {
            alert("Таблица пуста. Нет данных для сохранения.");
            return;
        }
        if (table.length === 1) {
            alert('Нельзя сохранить таблицу с длинной меньше 1');
            return;
        }
        if (!areAllCellsFilled(table)) {
            alert('Все ячейки должны быть заполнены');
            return;
        }
        if (!isSorted(table)) {
            alert('Таблица не отсортирована по x');
            return;
        }
        let fileName = prompt("Введите название файла с расширением (например, data.xml или data.json):");
        let data, mimeType;
        const format = fileName.split('.').pop().toLowerCase();

        if (format === 'json') {
            // Преобразуем таблицу в JSON
            data = JSON.stringify(table, null, 2);
            mimeType = "application/json";
        } else if (format === 'xml') {
            // Преобразуем таблицу в XML
            const xmlData = table.map((row) => `<point><x>${row.x}</x><y>${row.y}</y></point>`).join("");
            data = `<data>${xmlData}</data>`;
            mimeType = "application/xml";
        } else {
            alert("Неподдерживаемый формат файла.");
            return;
        }

        const blob = new Blob([data], {type: mimeType});
        saveAs(blob, fileName);
    };

    const handleApply = () => {
        const x = parseFloat(inputX);
        if (isNaN(x)) {
            alert('Введите корректное значение X.');
            return;
        }
        if (functionData.length === 0) {
            alert("Таблица пуста. Нельзя посчитать значение");
            return;
        }
        if (functionData.length === 1) {
            alert('Нельзя посчитать значение функции с 1 точкой');
            return;
        }
        if (!areAllCellsFilled(functionData)) {
            alert('Все ячейки должны быть заполнены');
            return;
        }
        if (!isSorted(functionData)) {
            alert('Таблица не отсортирована по x');
            return;
        }

        const applyFunction = (x) => {
            const postTabArr = {
                arrX: functionData.map(item => item.x),
                arrY: functionData.map(item => item.y),
                type: factory
            };
            const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
            const url = new URL('http://localhost:8080/points/apply');
            url.searchParams.append('xVal', x);
            const result = fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authHeader,
                },
                body: JSON.stringify(postTabArr)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    setCalculatedY(data);
                    console.log('Success:', data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        };

        const y = applyFunction(x);
        if (y !== null) {
            setCalculatedY(y);
        } else {
            alert('Значение X находится вне диапазона функции.');
        }
    };

    const handleInsert = () => {
        const newPoint = {x: '0', y: '0'}; // Пример новой точки
        setFunctionData(prev => [...prev, newPoint]);
    };

    const handleRemove = () => {
        setFunctionData(prev => prev.slice(0, -1));
    };

    // Функция для подтверждения таблицы
    const handleConfirmTable = () => {
        if (functionData.length === 0) {
            alert("Таблица пуста. Нельзя создать такую функцию");
            return;
        }
        if (functionData.length === 1) {
            alert('Нельзя посчитать функцию с 1 точкой');
            return;
        }
        if (!areAllCellsFilled(functionData)) {
            alert('Не все ячейки исходной функции заполнены');
            return;
        }
        if (!isSorted(functionData)) {
            alert('Исходная функция не отсортирована');
            return;
        }
        updateChart();
        alert('Таблица подтверждена, график обновлен.');
    };

    // Функции для переключения страниц
    const goToNextPage = () => {
        if (currentPage < getTotalPages(functionData, rowsPerPage)) {
            setCurrentPage(currentPage + 1);
        }
    };

    const goToPreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    // Вычисление данных для текущей страницы
    const getCurrentRows = (table, currentPage, rowsPerPage) => {
        const indexOfLastRow = currentPage * rowsPerPage;
        const indexOfFirstRow = indexOfLastRow - rowsPerPage;
        return table.slice(indexOfFirstRow, indexOfLastRow);
    };

    // Вычисление общего количества страниц
    const getTotalPages = (table, rowsPerPage) => {
        return Math.ceil(table.length / rowsPerPage);
    };

    // Функция для проверки допустимости ввода
    const isValidInput = (key, value) => {
        // Разрешаем Backspace и Delete
        if (key === 'Backspace' || key === 'Delete') {
            return true;
        }
        // Разрешаем цифры, точку и минус
        return /^-?\d*\.?\d*$/.test(value);
    };

    // Функция для изменения данных в таблице
    const handleInputChange = (index, field, value) => {
        setFunctionData(prevData =>
            prevData.map((item, idx) =>
                idx === index + (currentPage - 1) * rowsPerPage ? {...item, [field]: value} : item
            )
        );
    };

    return (
        <div className="function-editor-container">
            <div className="buttons-container">
                <Button onClick={openModal}>Создать функцию</Button>
                <input
                    type="file"
                    id="loadFunction"
                    style={{display: 'none'}}
                    onChange={(e) => handleFileChange(e, setFunctionData)}
                />
                <Button onClick={() => document.getElementById('loadFunction').click()}>Загрузить функцию</Button>
                <Button onClick={() => saveFunction(functionData)}>Сохранить функцию</Button>
                <Button onClick={handleConfirmTable}>Подтвердить таблицу</Button>
            </div>

            <div className="chart-container">
                <canvas id="functionChart" width="400" height="200"></canvas>
            </div>

            <div className="table-container">
                <h3>Таблица функции</h3>
                {functionData.length > 0 && (
                    <>
                        <table>
                        <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {getCurrentRows(functionData, currentPage, rowsPerPage).map((row, index) => (
                                <tr key={index}>
                                    <td
                                        contentEditable
                                        suppressContentEditableWarning
                                        onKeyDown={(e) => {
                                            const value = e.currentTarget.textContent + e.key;
                                            if (!isValidInput(e.key, value)) {
                                                e.preventDefault();
                                            }
                                        }}
                                        onBlur={(e) => handleInputChange(index, 'x', e.currentTarget.textContent)}
                                    >
                                        {row.x}
                                    </td>
                                    <td
                                        contentEditable
                                        suppressContentEditableWarning
                                        onKeyDown={(e) => {
                                            const value = e.currentTarget.textContent + e.key;
                                            if (!isValidInput(e.key, value)) {
                                                e.preventDefault();
                                            }
                                        }}
                                        onBlur={(e) => handleInputChange(index, 'y', e.currentTarget.textContent)}
                                    >
                                        {row.y}
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                        <div className="pagination">
                            <Button
                                onClick={goToPreviousPage}
                                disabled={currentPage === 1}
                            >
                                Назад
                            </Button>
                            <span>
                                Страница {currentPage} из {getTotalPages(functionData, rowsPerPage)}
                            </span>
                            <Button
                                onClick={goToNextPage}
                                disabled={currentPage === getTotalPages(functionData, rowsPerPage)}
                            >
                                Вперёд
                            </Button>
                        </div>
                    </>
                )}
                {<Button onClick={handleInsert}>Вставить</Button>}
                {<Button onClick={handleRemove}>Удалить</Button>}
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