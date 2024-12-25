import React, { useContext, useState } from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './IntegratePage.css'; // Подключаем CSS файл
import FirstPage from "../FirstPage/FirstPage.jsx";
import { FactoryContext } from "../FactoryContext.jsx";
import useAuth from "../hock/useAuth.jsx";

export default function IntegratePage() {
    const [originalFunction, setOriginalFunction] = useState([]);
    const [threadCount, setThreadCount] = useState('0');
    const [integratedFunction, setIntegratedFunction] = useState(0);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [activeModal, setActiveModal] = useState(null);
    const [fileName, setFileName] = useState('');
    const { factory } = useContext(FactoryContext);
    const { user } = useAuth();

    // Состояние для пагинации
    const [currentPage, setCurrentPage] = useState(1);
    const [rowsPerPage] = useState(5); // Количество строк на странице

    const openModal = (modalType) => {
        setModalIsOpen(true);
        setActiveModal(modalType);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        setActiveModal(null);
    };

    const handleDataChange = (newData) => {
        setOriginalFunction(newData);
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

    const performIntegration = () => {
        if (originalFunction.length === 0) {
            alert('Невозможно посчитатть интеграл из ничего');
            return;
        }
        if (!areAllCellsFilled(originalFunction)) {
            alert('Не все ячейки исходной функции заполнены');
            return;
        }
        if (!isSorted(originalFunction)) {
            alert('Исходная функция не отсортирована');
            return;
        }
        if (parseInt(threadCount) < 1){
            alert('Невозможно использовать меньше 1 потока');
            return;
        }
        if (parseInt(threadCount) > 32){
            alert('Невозможно использовать больше 32 потоков');
            return;
        }

        const thread = threadCount;
        const postTabArr = {
            arrX: originalFunction.map(item => item.x),
            arrY: originalFunction.map(item => item.y),
            type: factory
        };
        const authHeader = `Basic ${btoa(`${user.name}:${user.password}`)}`;
        const url = new URL('http://localhost:8080/points/integr');
        url.searchParams.append('th', thread);
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
                setIntegratedFunction(data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
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

    // Функции для переключения страниц
    const goToNextPage = () => {
        if (currentPage < getTotalPages(originalFunction, rowsPerPage)) {
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
        setOriginalFunction(prevData =>
            prevData.map((item, idx) =>
                idx === index + (currentPage - 1) * rowsPerPage ? { ...item, [field]: value } : item
            )
        );
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

    return (
        <div className="integrate-page-container">
            <div className="buttons-container">
                <Button onClick={() => openModal('create')}>Создать функцию</Button>
                <Button onClick={handleLoad}>Загрузить функцию</Button>
                <Button onClick={() => saveFunction(originalFunction, fileName)}>Сохранить исходную функцию</Button>
                <Button onClick={performIntegration}>Интегрировать</Button>
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
                                {getCurrentRows(originalFunction, currentPage, rowsPerPage).map((row, index) => (
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
                                    Страница {currentPage} из {getTotalPages(originalFunction, rowsPerPage)}
                                </span>
                                <Button
                                    onClick={goToNextPage}
                                    disabled={currentPage === getTotalPages(originalFunction, rowsPerPage)}
                                >
                                    Вперёд
                                </Button>
                            </div>
                        </>
                    )}
                </div>

                <div className="table-wrapper">
                    <h3>Результат интегрирования</h3>
                    {integratedFunction}
                </div>
            </div>
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                {modalContent()}
            </Modal>
            <label htmlFor="thread">
                Количество потоков:
            </label>
            <input
                type="number"
                min="1"
                max="32"
                value={threadCount}
                onChange={(e) => setThreadCount(e.target.value)}
                required
            />
        </div>
    );
}