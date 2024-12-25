import {useState} from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import FirstPage from "../FirstPage/FirstPage.jsx";
import Modal from 'react-modal';
import './SecondPage.css'; // Подключаем CSS файл

export default function SecondPage() {
    const [table1, setTable1] = useState([]);
    const [table2, setTable2] = useState([]);
    const [tableResult, setTableResult] = useState([]);
    const [operation, setOperation] = useState('+');
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [activeModal, setActiveModal] = useState(null);
    const [saveModalIsOpen, setSaveModalIsOpen] = useState(false);
    const [loadModalIsOpen, setLoadModalIsOpen] = useState(false);
    const [fileName, setFileName] = useState('');
    const [insertable1, setInsertable1] = useState(false);
    const [insertable2, setInsertable2] = useState(false);
    const [removable1, setRemovable1] = useState(false);
    const [removable2, setRemovable2] = useState(false);

    // Состояние для пагинации
    const [currentPage1, setCurrentPage1] = useState(1);
    const [currentPage2, setCurrentPage2] = useState(1);
    const [currentPageResult, setCurrentPageResult] = useState(1);
    const [rowsPerPage] = useState(5); // Количество строк на странице

    const openModal = (modalType) => {
        setModalIsOpen(true);
        setActiveModal(modalType);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        setActiveModal(null);
    };

    const openSaveModal = () => {
        setSaveModalIsOpen(true);
    };

    const closeSaveModal = () => {
        setSaveModalIsOpen(false);
    };

    const openLoadModal = () => {
        setLoadModalIsOpen(true);
    };

    const closeLoadModal = () => {
        setLoadModalIsOpen(false);
    };

    function modalContent1() {
        return (
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal}/>
        );
    }

    function modalContent2() {
        return (
            <FirstPage onDataChange={handleDataChange2} closeModal={closeModal}/>
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
        if (table1.length !== 0 && table2.length !== 0) {
            let result = [];
            setTableResult([]); // Принудительно обновляем состояние перед выполнением операции

            switch (operation) {
                case '+': {
                    let i = 0, j = 0;

                    while (i < table1.length || j < table2.length) {
                        let res = [];

                        if (i < table1.length && j < table2.length) {
                            if (table1[i].x === table2[j].x) {
                                // Если значения x совпадают, складываем y
                                res = [table1[i].x, '' + (parseFloat(table1[i].y) + parseFloat(table2[j].y))];
                                i++;
                                j++;
                            } else if (table1[i].x < table2[j].x) {
                                // Если x из table1 меньше, добавляем строку из table1
                                res = [table1[i].x, table1[i].y];
                                i++;
                            } else {
                                // Если x из table2 меньше, добавляем строку из table2
                                res = [table2[j].x, table2[j].y];
                                j++;
                            }
                        } else if (i < table1.length) {
                            // Если table2 закончилась, добавляем оставшиеся строки из table1
                            res = [table1[i].x, table1[i].y];
                            i++;
                        } else if (j < table2.length) {
                            // Если table1 закончилась, добавляем оставшиеся строки из table2
                            res = [table2[j].x, table2[j].y];
                            j++;
                        }

                        result.push(res);
                    }
                    break;
                }
                case '-': {
                    let i = 0;
                    let j = 0;

                    while (i < table1.length || j < table2.length) {
                        let res = [];

                        if (i < table1.length && j < table2.length) {
                            if (table1[i].x === table2[j].x) {
                                // Если значения x совпадают, вычитаем y
                                res = [table1[i].x, '' + (parseFloat(table1[i].y) - parseFloat(table2[j].y))];
                                i++;
                                j++;
                            } else if (table1[i].x < table2[j].x) {
                                // Если x из table1 меньше, добавляем строку из table1
                                res = [table1[i].x, table1[i].y];
                                i++;
                            } else {
                                // Если x из table2 меньше, добавляем строку из table2 с отрицательным y
                                res = [table2[j].x, '-' + table2[j].y];
                                j++;
                            }
                        } else if (i < table1.length) {
                            // Если table2 закончилась, добавляем оставшиеся строки из table1
                            res = [table1[i].x, table1[i].y];
                            i++;
                        } else if (j < table2.length) {
                            // Если table1 закончилась, добавляем оставшиеся строки из table2 с отрицательным y
                            res = [table2[j].x, '-' + table2[j].y];
                            j++;
                        }

                        result.push(res);
                    }
                    break;
                }
                case '*': {
                    if (table1.length !== table2.length) {
                        alert('Разные длины функций');
                        return;
                    }
                    let i = 0;
                    let j = 0;

                    while (i < table1.length || j < table2.length) {
                        let res = [];

                        if (i < table1.length && j < table2.length) {
                            if (table1[i].x === table2[j].x) {
                                // Если значения x совпадают, умножаем y
                                res = [table1[i].x, '' + (parseFloat(table1[i].y) * parseFloat(table2[j].y))];
                                i++;
                                j++;
                            } else {
                                alert('У функций различаются x');
                                return;
                            }
                        }
                        result.push(res);
                    }
                    break;
                }
                case '/': {
                    if (table1.length !== table2.length) {
                        alert('Разные длины функций');
                        return;
                    }
                    let i = 0;
                    let j = 0;

                    while (i < table1.length || j < table2.length) {
                        let res = [];

                        if (i < table1.length && j < table2.length) {
                            if (table1[i].x === table2[j].x) {
                                // Если значения x совпадают, делим y
                                const y2 = parseFloat(table2[j].y);
                                if (y2 === 0) {
                                    alert('Деление на ноль');
                                    return;
                                }
                                res = [table1[i].x, '' + (parseFloat(table1[i].y) / y2)];
                                i++;
                                j++;
                            } else {
                                alert('У функций различаются x');
                                return;
                            }
                        }
                        result.push(res);
                    }
                    break;
                }
            }
            setTableResult(result); // Обновляем состояние результата
        } else {
            alert('Не создана одна или обе функции');
        }
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
            return [
                {x: 1, y: 2},
                {x: 2, y: 4},
                {x: 3, y: 6},
            ];
        } catch (error) {
            console.error('Ошибка при загрузке функции:', error);
        }
    };

    const handleLoad = async (tableSetter) => {
        openLoadModal();
        const selectedFile = prompt('Введите имя файла для загрузки:');
        if (selectedFile) {
            const loadedFunction = await loadFunction(selectedFile);
            tableSetter(loadedFunction);
        }
        closeLoadModal();
    };

    const handleInsert = (setTable) => {
        const newPoint = {x: table1.length + 1, y: 0}; // Пример новой точки
        setTable(prev => [...prev, newPoint]);
    };

    const handleRemove = (setTable) => {
        setTable(prev => prev.slice(0, -1));
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

    return (
        <div className="second-page-container">
            <div className="buttons-container">
                <Button onClick={() => openModal('modal1')}>Создать функцию для таблицы 1</Button>
                <Button onClick={() => saveFunction(table1)}>Сохранить функцию 1</Button>
                <Button onClick={() => handleLoad(setTable1)}>Загрузить функцию 1</Button>
                <Button onClick={() => openModal('modal2')}>Создать функцию для таблицы 2</Button>
                <Button onClick={() => saveFunction(table2)}>Сохранить функцию 2</Button>
                <Button onClick={() => handleLoad(setTable2)}>Загрузить функцию 2</Button>
                <Button onClick={() => saveFunction(tableResult)}>Сохранить результат</Button>
            </div>

            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} className="modal-content">
                {activeModal === 'modal1' ? modalContent1() : modalContent2()}
            </Modal>

            <Modal isOpen={saveModalIsOpen} onRequestClose={closeSaveModal} className="modal-content">
                <h2>Сохранить функцию</h2>
                <input
                    type="text"
                    placeholder="Введите имя файла"
                    value={fileName}
                    onChange={(e) => setFileName(e.target.value)}
                />
                <Button onClick={() => {
                    saveFunction(table1, fileName);
                    closeSaveModal();
                }}>Сохранить</Button>
            </Modal>

            <Modal isOpen={loadModalIsOpen} onRequestClose={closeLoadModal} className="modal-content">
                <h2>Загрузить функцию</h2>
                <input
                    type="text"
                    placeholder="Введите имя файла"
                />
                <Button onClick={() => {
                    handleLoad(setTable1);
                    closeLoadModal();
                }}>Загрузить</Button>
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
                                        <td>{row.x}</td>
                                        <td>{row.y}</td>
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
                                        <td>{row.x}</td>
                                        <td>{row.y}</td>
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