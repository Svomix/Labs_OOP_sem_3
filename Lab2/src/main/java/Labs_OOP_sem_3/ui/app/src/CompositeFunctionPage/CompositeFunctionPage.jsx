import React, {useContext, useState} from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './CompositeFunctionPage.css';
import useAuth from "../hock/useAuth.jsx";
import FirstPage from "../FirstPage/FirstPage.jsx";
import {FactoryContext} from "../FactoryContext.jsx";

export default function CompositeFunctionPage() {
    const [functions, setFunctions] = useState([]); // Список функций
    const [selectedFunctions, setSelectedFunctions] = useState([]); // Выбранные функции
    const [newFunctionName, setNewFunctionName] = useState(''); // Имя новой функции
    const [modalFunctionIsOpen, setModalFunctionIsOpen] = useState(false); // Состояние модального окна для создания функции
    const [modalTableIsOpen, setModalTableIsOpen] = useState(false); // Состояние модального окна для таблицы
    const [tables, setTables] = useState([]); // Таблицы для отправки данных
    const [confirmedTables, setConfirmedTables] = useState([]); // Подтвержденные таблицы
    const [activeTableIndex, setActiveTableIndex] = useState(null); // Индекс активной таблицы
    const {user} = useAuth();
    const {factory} = useContext(FactoryContext);

    // Состояние для пагинации
    const [currentPage, setCurrentPage] = useState(1);
    const [rowsPerPage] = useState(5); // Количество строк на странице


    const fetchFunctions = async () => {
        try {
            const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
            const url = new URL('http://localhost:8080/points/get_comp');
            url.searchParams.append('userName', user.name);
            fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authHeader,
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Success:', data);
                    setFunctions(data); // Предполагаем, что данные приходят в формате массива функций
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        } catch (error) {
            console.error('Ошибка при получении списка функций:', error);
        }
    };

    const openFunctionModal = () => {
        if (tables.length > 0 && tables.length !== confirmedTables.length) {
            alert('Не все таблицы подтверждены. Пожалуйста, подтвердите все таблицы перед созданием новой функции.');
            return;
        }
        fetchFunctions()
        setModalFunctionIsOpen(true);
    };

    const closeFunctionModal = () => {
        setModalFunctionIsOpen(false);
    };

    const openTableModal = (index) => {
        setActiveTableIndex(index); // Устанавливаем индекс активной таблицы
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

    const handleFunctionRemove = (func) => {
        setSelectedFunctions(selectedFunctions.filter(f => f !== func));
    };

    const validateFunctionName = (name) => {
        if (!name) {
            return "Имя функции не может быть пустым.";
        }
        if (functions.some(func => func.name === name)) {
            return "Функция с таким именем уже существует.";
        }
        if (name.startsWith("Таблица")) {
            return "Имя функции не может начинаться с 'Таблица'.";
        }
        return null;
    };

    const handleCreateCompositeFunction = async () => {
        const validationError = validateFunctionName(newFunctionName);
        if (validationError) {
            alert(validationError);
            return;
        }
        console.log(selectedFunctions)
        const func = selectedFunctions.map(item => item.name) //Массив названий функций
        console.log(func)
        const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
        const url = new URL('http://localhost:8080/points/comp_create');
        url.searchParams.append('username', user.name);
        url.searchParams.append('name', newFunctionName);
        const postData = {
            name: newFunctionName,
            functions: selectedFunctions.map(f => f), // Используем ID функций
        };
        console.log(postData)
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authHeader,
            },
            body: JSON.stringify(postData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                setFunctions([...functions, data]); // Добавляем новую функцию в список
                setSelectedFunctions([]); // Очищаем выбранные функции
                setNewFunctionName(''); // Очищаем поле имени
                closeFunctionModal(); // Закрываем модальное окно
            })
            .catch(error => {
                console.error('Error:', error);
            });
        setModalFunctionIsOpen(false)
    };

    const handleAddTable = () => {
        setTables([...tables, []]); // Добавляем новую таблицу
    };

    const handleDataChange = (data) => {
        const newTables = [...tables];
        newTables[activeTableIndex] = data; // Обновляем конкретную таблицу
        setTables(newTables);
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

    const handleConfirmTable = (index) => {
        const tableData = tables[index];

        // Проверка заполненности ячеек
        if (!areAllCellsFilled(tableData)) {
            alert('Не все ячейки таблицы заполнены.');
            return;
        }

        // Проверка сортировки
        if (!isSorted(tableData)) {
            alert('Таблица не отсортирована по значениям X.');
            return;
        }

        const postTabArr = {
            arrX: tableData.map(point => point.x),
            arrY: tableData.map(point => point.y),
            type: factory,
            name: `таблица ${index + 1}`,
            composite: "part"
        };
        const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
        const url = new URL("http://localhost:8080/points/comp_table");
        url.searchParams.append('userName', user.name);
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
                console.log('Success:', data);
                setConfirmedTables([...confirmedTables, index]); // Добавляем индекс подтвержденной таблицы
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    // Функции для пагинации
    const goToNextPage = () => {
        if (currentPage < getTotalPages(tables[activeTableIndex], rowsPerPage)) {
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

    // Функция для изменения данных в таблице
    const handleInputChange = (index, field, value) => {
        const newTable = [...tables[activeTableIndex]];
        newTable[index][field] = value;
        const newTables = [...tables];
        newTables[activeTableIndex] = newTable;
        setTables(newTables);
    };

    // Функция для удаления последней строки из таблицы выбранных функций
    const removeLastRowFromSelectedFunctions = () => {
        if (selectedFunctions.length > 0) {
            const newSelectedFunctions = [...selectedFunctions];
            newSelectedFunctions.pop();
            setSelectedFunctions(newSelectedFunctions);
        }
    };

    // Функция для изменения имени функции в таблице выбранных функций
    const handleFunctionNameChange = (index, newName) => {
        const updatedFunctions = [...selectedFunctions];
        updatedFunctions[index].name = newName;
        setSelectedFunctions(updatedFunctions);
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
                        {
                            functions.map((func, index) => (
                                <li key={index}>
                                    <label>
                                        <input
                                            type="checkbox"
                                            checked={selectedFunctions.includes(func)} // Проверяем, выбрана ли функция
                                            onChange={() => handleFunctionSelect(func)} // Обрабатываем выбор
                                        />
                                        {func} {/* Отображаем имя функции */}
                                    </label>
                                </li>
                            ))
                        }
                    </ul>
                </div>

                <div className="selected-functions">
                    <h3>Выбранные функции:</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Имя функции</th>
                        </tr>
                        </thead>
                        <tbody>
                        {selectedFunctions.map((func, index) => (
                            <tr key={index}>
                                <td>
                                    {func}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <div className="pagination">
                        <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1}>
                            Назад
                        </Button>
                        <span>
                            Страница {currentPage} из {Math.ceil(selectedFunctions.length / rowsPerPage)}
                        </span>
                        <Button
                            onClick={() => setCurrentPage(currentPage + 1)}
                            disabled={currentPage === Math.ceil(selectedFunctions.length / rowsPerPage)}
                        >
                            Вперёд
                        </Button>
                    </div>
                    <div className="row-buttons">
                        <Button onClick={removeLastRowFromSelectedFunctions}>Удалить последнюю строку</Button>
                    </div>
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
                    !confirmedTables.includes(index) && (
                        <div key={index} className="table-wrapper">
                            <h3>Таблица {index + 1}</h3>
                            <Button onClick={() => openTableModal(index)}>Создать функцию</Button>
                            <Button onClick={() => handleConfirmTable(index)}>Подтвердить таблицу</Button>
                            <Modal isOpen={modalTableIsOpen} onRequestClose={closeTableModal}>
                                <FirstPage onDataChange={handleDataChange} closeModal={closeTableModal}/>
                            </Modal>
                            <table>
                                <thead>
                                <tr>
                                    <th>X</th>
                                    <th>Y</th>
                                </tr>
                                </thead>
                                <tbody>
                                {getCurrentRows(table, currentPage, rowsPerPage).map((row, idx) => (
                                    <tr key={idx}>
                                        <td
                                            contentEditable
                                            suppressContentEditableWarning
                                            onBlur={(e) => handleInputChange(idx, 'x', e.currentTarget.textContent)}
                                        >
                                            {row.x}
                                        </td>
                                        <td
                                            contentEditable
                                            suppressContentEditableWarning
                                            onBlur={(e) => handleInputChange(idx, 'y', e.currentTarget.textContent)}
                                        >
                                            {row.y}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                            <div className="pagination">
                                <Button onClick={goToPreviousPage} disabled={currentPage === 1}>
                                    Назад
                                </Button>
                                <span>
                                    Страница {currentPage} из {getTotalPages(table, rowsPerPage)}
                                </span>
                                <Button onClick={goToNextPage}
                                        disabled={currentPage === getTotalPages(table, rowsPerPage)}>
                                    Вперёд
                                </Button>
                            </div>
                        </div>
                    )
                ))}
            </div>
        </div>
    );
}