import { useState } from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import FirstPage from "../FirstPage/FirstPage.jsx";
import Modal from 'react-modal';
import './SecondPage.css'; // Подключаем CSS файл
import { saveAs } from 'file-saver'; // Импорт библиотеки для сохранения файлов

export default function SecondPage() {
    const [table1, setTable1] = useState([]);
    const [table2, setTable2] = useState([]);
    const [tableResult, setTableResult] = useState([]);
    const [operation, setOperation] = useState('+');
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [activeModal, setActiveModal] = useState(null);
    const [insertable1, setInsertable1] = useState(false);
    const [insertable2, setInsertable2] = useState(false);
    const [removable1, setRemovable1] = useState(false);
    const [removable2, setRemovable2] = useState(false);

    // Состояние для пагинации
    const [currentPage1, setCurrentPage1] = useState(1);
    const [currentPage2, setCurrentPage2] = useState(1);
    const [currentPageResult, setCurrentPageResult] = useState(1);
    const [rowsPerPage] = useState(5); // Количество строк на странице

    const [uploadStatus, setUploadStatus] = useState("");

    const handleFileChange = async (e, setTable) => {
        const selectedFile = e.target.files[0];
        if (!selectedFile) {
            alert("Выберите файл для загрузки.");
            return;
        }
        console.log(selectedFile)

        // Проверка расширения файла
        const allowedExtensions = [".json", ".xml"];
        const fileExtension = selectedFile.name.split(".").pop().toLowerCase();
        if (!allowedExtensions.includes("." + fileExtension)) {
            alert("Файл должен быть в формате JSON или XML.");
            return;
        }

        const formData = new FormData();
        formData.append("file", selectedFile); // Ключ "file" должен совпадать с тем, что ожидает сервер

        console.log(formData)
        try {
            const response = await fetch("http://localhost:5000/upload", {
                method: "POST",
                body: formData,
            });

            if (response.ok) {
                const result = await response.json();
                setUploadStatus("Файл успешно загружен: " + result.message);

                // Преобразуем данные в таблицу и обновляем состояние
                const tableData = result.data; // Предполагаем, что сервер возвращает данные в формате [{ x: 1, y: 2 }, ...]
                setTable(tableData);
            } else {
                setUploadStatus("Ошибка при загрузке файла.");
            }
        } catch (error) {
            console.error("Ошибка:", error);
            setUploadStatus("Ошибка при загрузке файла.");
        }
    };

    const saveFunction = (table, format = 'json') => {
        if (table.length === 0) {
            alert("Таблица пуста. Нет данных для сохранения.");
            return;
        }

        let data, fileName, mimeType;

        if (format === 'json') {
            // Преобразуем таблицу в JSON
            data = JSON.stringify(table, null, 2);
            fileName = "table.json";
            mimeType = "application/json";
        } else if (format === 'xml') {
            // Преобразуем таблицу в XML
            const xmlData = table.map((row) => `<point><x>${row.x}</x><y>${row.y}</y></point>`).join("");
            data = `<data>${xmlData}</data>`;
            fileName = "table.xml";
            mimeType = "application/xml";
        } else {
            alert("Неподдерживаемый формат файла.");
            return;
        }

        // Создаем Blob и сохраняем файл
        const blob = new Blob([data], { type: mimeType });
        saveAs(blob, fileName);
    };

    const openModal = (modalType) => {
        setModalIsOpen(true);
        setActiveModal(modalType);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        setActiveModal(null);
    };

    function modalContent1() {
        return (
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal} />
        );
    }

    function modalContent2() {
        return (
            <FirstPage onDataChange={handleDataChange2} closeModal={closeModal} />
        );
    }

    const handleDataChange = (newData, ins, rem) => {
        setInsertable1(ins);
        setRemovable1(rem);
        setTable1(newData);
    };

    const handleDataChange2 = (newData, ins, rem) => {
        setInsertable2(ins);
        setRemovable2(rem);
        setTable2(newData);
    };

    const performOperation = (e) => {
        e.preventDefault();
        if (!areAllCellsFilled(table1)) {
            alert('Не все ячейки функции 1 заполнены');
            return;
        }
        if (!areAllCellsFilled(table2)) {
            alert('Не все ячейки функции 2 заполнены');
            return;
        }
        if (table1.length <= 1) {
            alert('Длина таблицы 1 меньше 2. Добавьте новые строки');
            return;
        }
        if (table2.length <= 1) {
            alert('Длина таблицы 2 меньше 2. Добавьте новые строки');
            return;
        }
        if (!isSorted(table1)) {
            alert('Функция 1 не отсортирована');
            return;
        }
        if (!isSorted(table2)) {
            alert('Функция 2 не отсортирована');
            return;
        }

        let result = [];
        setTableResult([]); // Принудительно обновляем состояние перед выполнением операции

        switch (operation) {
            case '+': {
                result = performAddition(table1, table2);
                break;
            }
            case '-': {
                result = performSubtraction(table1, table2);
                break;
            }
            case '*': {
                result = performMultiplication(table1, table2);
                break;
            }
            case '/': {
                result = performDivision(table1, table2);
                break;
            }
            default:
                break;
        }

        setTableResult(result); // Обновляем состояние результата
    };

    const performAddition = (table1, table2) => {
        let result = [];
        let i = 0, j = 0;

        while (i < table1.length || j < table2.length) {
            let res = [];

            if (i < table1.length && j < table2.length) {
                if (table1[i].x === table2[j].x) {
                    res = [table1[i].x, '' + (parseFloat(table1[i].y) + parseFloat(table2[j].y))];
                    i++;
                    j++;
                } else if (table1[i].x < table2[j].x) {
                    res = [table1[i].x, table1[i].y];
                    i++;
                } else {
                    res = [table2[j].x, table2[j].y];
                    j++;
                }
            } else if (i < table1.length) {
                res = [table1[i].x, table1[i].y];
                i++;
            } else if (j < table2.length) {
                res = [table2[j].x, table2[j].y];
                j++;
            }

            result.push(res);
        }

        return result;
    };

    const performSubtraction = (table1, table2) => {
        let result = [];
        let i = 0, j = 0;

        while (i < table1.length || j < table2.length) {
            let res = [];

            if (i < table1.length && j < table2.length) {
                if (table1[i].x === table2[j].x) {
                    res = [table1[i].x, '' + (parseFloat(table1[i].y) - parseFloat(table2[j].y))];
                    i++;
                    j++;
                } else if (table1[i].x < table2[j].x) {
                    res = [table1[i].x, table1[i].y];
                    i++;
                } else {
                    res = [table2[j].x, '-' + table2[j].y];
                    j++;
                }
            } else if (i < table1.length) {
                res = [table1[i].x, table1[i].y];
                i++;
            } else if (j < table2.length) {
                res = [table2[j].x, '-' + table2[j].y];
                j++;
            }

            result.push(res);
        }

        return result;
    };

    const performMultiplication = (table1, table2) => {
        if (table1.length !== table2.length) {
            alert('Разные длины функций');
            return [];
        }

        let result = [];
        let i = 0, j = 0;

        while (i < table1.length && j < table2.length) {
            if (table1[i].x === table2[j].x) {
                result.push([table1[i].x, '' + (parseFloat(table1[i].y) * parseFloat(table2[j].y))]);
                i++;
                j++;
            } else {
                alert('У функций различаются x');
                return [];
            }
        }

        return result;
    };

    const performDivision = (table1, table2) => {
        if (table1.length !== table2.length) {
            alert('Разные длины функций');
            return [];
        }

        let result = [];
        let i = 0, j = 0;

        while (i < table1.length && j < table2.length) {
            if (table1[i].x === table2[j].x) {
                const y2 = parseFloat(table2[j].y);
                if (y2 === 0) {
                    alert('Деление на ноль');
                    return [];
                }
                result.push([table1[i].x, '' + (parseFloat(table1[i].y) / y2)]);
                i++;
                j++;
            } else {
                alert('У функций различаются x');
                return [];
            }
        }

        return result;
    };



    const handleInsert = (setTable) => {
        const newPoint = { x: '0', y: '0' }; // Пример новой точки
        setTable(prev => [...prev, newPoint]);
    };

    const handleRemove = (setTable) => {
        setTable(prev => prev.slice(0, -1));
    };

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

    const handleInputChange1 = (index, field, value) => {
        setTable1(prevData =>
            prevData.map((item, idx) =>
                idx === index + (currentPage1 - 1) * rowsPerPage ? { ...item, [field]: value } : item
            )
        );
    };

    const handleInputChange2 = (index, field, value) => {
        setTable2(prevData =>
            prevData.map((item, idx) =>
                idx === index + (currentPage1 - 1) * rowsPerPage ? { ...item, [field]: value } : item
            )
        );
    };

    const getCurrentRows = (table, currentPage, rowsPerPage) => {
        const indexOfLastRow = currentPage * rowsPerPage;
        const indexOfFirstRow = indexOfLastRow - rowsPerPage;
        return table.slice(indexOfFirstRow, indexOfLastRow);
    };

    const getTotalPages = (table, rowsPerPage) => {
        return Math.ceil(table.length / rowsPerPage);
    };

    const isValidInput = (key, value) => {
        if (key === 'Backspace' || key === 'Delete') {
            return true;
        }
        return /^-?\d*\.?\d*$/.test(value);
    };

    const areAllCellsFilled = (tableData) => {
        return tableData.every(item => item.x !== '' && item.y !== '');
    };

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
        <div className="second-page-container">
            <div className="buttons-container">
                <Button onClick={() => openModal('modal1')}>Создать функцию для таблицы 1</Button>
                <Button onClick={() => saveFunction(table1)}>Сохранить функцию 1</Button>
                <input
                    type="file"
                    id="loadFunction1"
                    style={{ display: 'none' }}
                    onChange={(e) => handleFileChange(e, setTable1)}
                />
                <Button onClick={() => document.getElementById('loadFunction1').click()}>Загрузить функцию 1</Button>
                <Button onClick={() => openModal('modal2')}>Создать функцию для таблицы 2</Button>
                <Button onClick={() => saveFunction(table2)}>Сохранить функцию 2</Button>
                <input
                    type="file"
                    id="loadFunction2"
                    style={{ display: 'none' }}
                    onChange={(e) => handleFileChange(e, setTable2)}
                />
                <Button onClick={() => document.getElementById('loadFunction2').click()}>Загрузить функцию 2</Button>
                <Button onClick={() => saveFunction(tableResult)}>Сохранить результат</Button>
            </div>

            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} className="modal-content">
                {activeModal === 'modal1' ? modalContent1() : modalContent2()}
            </Modal>

            <div className="tables-container">
                <div className="table-wrapper">
                    {table1.length > 0 && (
                        <>
                            <table id="dataTable1">
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {getCurrentRows(table1, currentPage1, rowsPerPage).map((row, index) => (
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
                                            onBlur={(e) => handleInputChange1(index, 'x', e.currentTarget.textContent)}
                                        >{row.x}</td>
                                        <td
                                            contentEditable
                                            suppressContentEditableWarning
                                            onKeyDown={(e) => {
                                                const value = e.currentTarget.textContent + e.key;
                                                if (!isValidInput(e.key, value)) {
                                                    e.preventDefault();
                                                }
                                            }}
                                            onBlur={(e) => handleInputChange1(index, 'y', e.currentTarget.textContent)}
                                        >{row.y}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                            <div className="pagination">
                                <Button
                                    onClick={() => goToPreviousPage(setCurrentPage1, currentPage1)}
                                    disabled={currentPage1 === 1}
                                >
                                    Назад
                                </Button>
                                <span>
                                    Страница {currentPage1} из {getTotalPages(table1, rowsPerPage)}
                                </span>
                                <Button
                                    onClick={() => goToNextPage(setCurrentPage1, currentPage1, getTotalPages(table1, rowsPerPage))}
                                    disabled={currentPage1 === getTotalPages(table1, rowsPerPage)}
                                >
                                    Вперёд
                                </Button>
                            </div>
                        </>
                    )}
                    {insertable1 && <Button onClick={() => handleInsert(setTable1)}>Вставить</Button>}
                    {removable1 && (
                        <Button onClick={() => handleRemove(setTable1)}>Удалить</Button>
                    )}
                </div>

                <div className="operation-container">
                    <label htmlFor="operationSelect">Операция:</label>
                    <select id="operationSelect" onChange={(e) => setOperation(e.target.value)} value={operation}>
                        <option value="+">+</option>
                        <option value="-">-</option>
                        <option value="*">*</option>
                        <option value="/">/</option>
                    </select>
                    <Button onClick={performOperation}>Вычислить</Button>
                </div>

                <div className="table-wrapper">
                    {table2.length > 0 && (
                        <>
                            <table id="dataTable2">
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {getCurrentRows(table2, currentPage2, rowsPerPage).map((row, index) => (
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
                                            onBlur={(e) => handleInputChange2(index, 'x', e.currentTarget.textContent)}
                                        >{row.x}</td>
                                        <td
                                            contentEditable
                                            suppressContentEditableWarning
                                            onKeyDown={(e) => {
                                                const value = e.currentTarget.textContent + e.key;
                                                if (!isValidInput(e.key, value)) {
                                                    e.preventDefault();
                                                }
                                            }}
                                            onBlur={(e) => handleInputChange2(index, 'y', e.currentTarget.textContent)}
                                        >{row.y}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                            <div className="pagination">
                                <Button
                                    onClick={() => goToPreviousPage(setCurrentPage2, currentPage2)}
                                    disabled={currentPage2 === 1}
                                >
                                    Назад
                                </Button>
                                <span>
                                    Страница {currentPage2} из {getTotalPages(table2, rowsPerPage)}
                                </span>
                                <Button
                                    onClick={() => goToNextPage(setCurrentPage2, currentPage2, getTotalPages(table2, rowsPerPage))}
                                    disabled={currentPage2 === getTotalPages(table2, rowsPerPage)}
                                >
                                    Вперёд
                                </Button>
                            </div>
                        </>
                    )}
                    {insertable2 && <Button onClick={() => handleInsert(setTable2)}>Вставить</Button>}
                    {removable2 && (
                        <Button onClick={() => handleRemove(setTable2)}>Удалить</Button>
                    )}
                </div>

                <div className="table-wrapper">
                    {tableResult.length > 0 && (
                        <>
                            <table id="dataTable3">
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {getCurrentRows(tableResult, currentPageResult, rowsPerPage).map((row, index) => (
                                    <tr key={index}>
                                        <td>{row[0]}</td>
                                        <td>{row[1]}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                            <div className="pagination">
                                <Button
                                    onClick={() => goToPreviousPage(setCurrentPageResult, currentPageResult)}
                                    disabled={currentPageResult === 1}
                                >
                                    Назад
                                </Button>
                                <span>
                                    Страница {currentPageResult} из {getTotalPages(tableResult, rowsPerPage)}
                                </span>
                                <Button
                                    onClick={() => goToNextPage(setCurrentPageResult, currentPageResult, getTotalPages(tableResult, rowsPerPage))}
                                    disabled={currentPageResult === getTotalPages(tableResult, rowsPerPage)}
                                >
                                    Вперёд
                                </Button>
                            </div>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
}