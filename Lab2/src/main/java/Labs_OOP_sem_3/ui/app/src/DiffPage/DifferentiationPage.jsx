import React, {useContext, useState} from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import FirstPage from "../FirstPage/FirstPage.jsx";
import {FactoryContext} from "../FactoryContext.jsx";
import useAuth from "../hock/useAuth.jsx";
import './DifferentiationPage.css'

export default function DifferentiationPage() {
    const [originalFunction, setOriginalFunction] = useState([]);
    const [differentiatedFunction, setDifferentiatedFunction] = useState([]);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [, setActiveModal] = useState(null);
    const {factory} = useContext(FactoryContext);
    const [insertable, setInsertable] = useState(false);
    const [removable, setRemovable] = useState(false);
    const {user} = useAuth();
    const [, setUploadStatus] = useState("");
    const [currentPageOriginal, setCurrentPageOriginal] = useState(1);
    const [currentPageDifferentiated, setCurrentPageDifferentiated] = useState(1);
    const [rowsPerPage] = useState(10);

    const openModal = (modalType) => {
        setModalIsOpen(true);
        setActiveModal(modalType);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        setActiveModal(null);
    };

    const handleDataChange = (newData, ins, rem) => {
        setInsertable(ins);
        setRemovable(rem);
        setOriginalFunction(newData);
    };

    const performDifferentiation = () => {
        if (!areAllCellsFilled(originalFunction)) {
            alert('Не все ячейки исходной функции заполнены');
            return;
        }
        if (!isSorted(originalFunction)) {
            alert('Исходная функция не отсортирована');
            return;
        }

        const postTabArr = {
            arrX: originalFunction.map(item => item.x),
            arrY: originalFunction.map(item => item.y),
            type: factory
        };

        const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
        const url = new URL('http://localhost:8080/points/diff');
        fetch(url, {
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
                const res = data.points
                const tableData = res.map(item => ({
                    x: item.xvalue + '',
                    y: item.yvalue + ''
                }));
                setDifferentiatedFunction(tableData);
            })
            .catch(error => {
                console.error('Error:', error);
            });
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
    const handleInsert = () => {
        const newPoint = {x: '0', y: '0'}; // Пример новой точки
        setOriginalFunction(prev => [...prev, newPoint]);
    };

    const handleRemove = () => {
        setOriginalFunction(prev => prev.slice(0, -1));
    };

    // Функции для переключения страниц
    const goToNextPage = (setCurrentPage, currentPage, totalPages) => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    const goToPreviousPage = (setCurrentPage, currentPage) => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    // Функция для изменения данных в таблице
    const handleInputChange = (index, field, value) => {
        setOriginalFunction(prevData =>
            prevData.map((item, idx) =>
                idx === index + (currentPageOriginal - 1) * rowsPerPage ? {...item, [field]: value} : item
            )
        );
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

    // Функция для проверки заполненности всех ячеек
    const areAllCellsFilled = (tableData) => {
        return tableData.every(item => item.x !== '' && item.y !== '');
    };

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

    return (
        <div className="differentiation-page-container">
            <div className="buttons-container">
                <Button onClick={() => openModal('create')}>Создать функцию</Button>
                <input
                    type="file"
                    id="loadFunction"
                    style={{display: 'none'}}
                    onChange={(e) => handleFileChange(e, setOriginalFunction)}
                />
                <Button onClick={() => document.getElementById('loadFunction').click()}>Загрузить функцию</Button>
                <Button onClick={() => saveFunction(originalFunction)}>Сохранить исходную функцию</Button>
                <Button onClick={performDifferentiation}>Дифференцировать</Button>
                <Button onClick={() => saveFunction(differentiatedFunction)}>Сохранить результат</Button>
            </div>

            <div className="tables-container">
                <div className="table-wrapper">
                    <h3>Исходная функция</h3>
                    {originalFunction.length > 0 && (
                        <>
                            <table id="originalTable">
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {getCurrentRows(originalFunction, currentPageOriginal, rowsPerPage).map((row, index) => (
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
                                {insertable && <Button onClick={handleInsert}>Вставить</Button>}
                                {removable && <Button onClick={handleRemove}>Удалить</Button>}
                            </table>
                            <div className="pagination">
                                <Button
                                    onClick={() => goToPreviousPage(setCurrentPageOriginal, currentPageOriginal)}
                                    disabled={currentPageOriginal === 1}
                                >
                                    Назад
                                </Button>
                                <span>
                                    Страница {currentPageOriginal} из {getTotalPages(originalFunction, rowsPerPage)}
                                </span>
                                <Button
                                    onClick={() => goToNextPage(setCurrentPageOriginal, currentPageOriginal, getTotalPages(originalFunction, rowsPerPage))}
                                    disabled={currentPageOriginal === getTotalPages(originalFunction, rowsPerPage)}
                                >
                                    Вперёд
                                </Button>
                            </div>
                        </>
                    )}
                </div>

                <div className="table-wrapper">
                    <h3>Результат дифференцирования</h3>
                    {differentiatedFunction.length > 0 && (
                        <>
                            <table id="differentiatedTable">
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {getCurrentRows(differentiatedFunction, currentPageDifferentiated, rowsPerPage).map((row, index) => (
                                    <tr key={index}>
                                        <td>{row.x}</td>
                                        <td>{row.y}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                            <div className="pagination">
                                <Button
                                    onClick={() => goToPreviousPage(setCurrentPageDifferentiated, currentPageDifferentiated)}
                                    disabled={currentPageDifferentiated === 1}
                                >
                                    Назад
                                </Button>
                                <span>
                                    Страница {currentPageDifferentiated} из {getTotalPages(differentiatedFunction, rowsPerPage)}
                                </span>
                                <Button
                                    onClick={() => goToNextPage(setCurrentPageDifferentiated, currentPageDifferentiated, getTotalPages(differentiatedFunction, rowsPerPage))}
                                    disabled={currentPageDifferentiated === getTotalPages(differentiatedFunction, rowsPerPage)}
                                >
                                    Вперёд
                                </Button>
                            </div>
                        </>
                    )}
                </div>
            </div>
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                <FirstPage onDataChange={handleDataChange} closeModal={closeModal}/>
            </Modal>
        </div>
    );
}