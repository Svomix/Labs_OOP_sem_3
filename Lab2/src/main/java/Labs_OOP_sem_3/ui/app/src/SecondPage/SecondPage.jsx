import { useState } from "react";
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
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal} />
        );
    }

    function modalContent2() {
        return (
            <FirstPage onDataChange={handleDataChange2} closeModal={closeModal} />
        );
    }

    const handleDataChange = (newData) => {
        setTable1(newData);
    };

    const handleDataChange2 = (newData) => {
        setTable2(newData);
    };

    const performOperation = (e) => {
        e.preventDefault();
        if (table1 && table2) {
            let result = [];
            switch (operation) {
                case '+':
                    for (let i = 0; i < table1.length; i++) {
                        result.push([table1[i].x, parseFloat(table1[i].y) + parseFloat(table2[i].y)]);
                    }
                    break;
                case '-':
                    for (let i = 0; i < table1.length; i++) {
                        result.push([table1[i].x, parseFloat(table1[i].y) - parseFloat(table2[i].y)]);
                    }
                    break;
                case '*':
                    for (let i = 0; i < table1.length; i++) {
                        result.push([table1[i].x, parseFloat(table1[i].y) * parseFloat(table2[i].y)]);
                    }
                    break;
                case '/':
                    for (let i = 0; i < table1.length; i++) {
                        result.push([table1[i].x, parseFloat(table1[i].y) / parseFloat(table2[i].y)]);
                    }
                    break;
            }
            setTableResult(result);
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
            return [{ x: 1, y: 2 },
                { x: 2, y: 4 },
                { x: 3, y: 6 },]
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

    return (
        <div className="second-page-container">
            <div className="buttons-container">
                <Button onClick={() => openModal('modal1')}>Создать функцию для таблицы 1</Button>
                <Button onClick={() => openModal('modal2')}>Создать функцию для таблицы 2</Button>
                <Button onClick={() => saveFunction(table1)}>Сохранить функцию 1</Button>
                <Button onClick={() => handleLoad(setTable1)}>Загрузить функцию 1</Button>
                <Button onClick={() => saveFunction(table2)}>Сохранить функцию 2</Button>
                <Button onClick={() => handleLoad(setTable2)}>Загрузить функцию 2</Button>
                <Button onClick={() => saveFunction(tableResult)}>Сохранить результат</Button>
            </div>
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
                {activeModal === 'modal1' ? modalContent1() : modalContent2()}
            </Modal>

            <Modal isOpen={saveModalIsOpen} onRequestClose={closeSaveModal}>
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

            <Modal isOpen={loadModalIsOpen} onRequestClose={closeLoadModal}>
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
                        <table id="dataTable1">
                            <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {table1.map((row, index) => (
                                <tr key={index}>
                                    <td>{row.x}</td>
                                    <td>{row.y}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
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
                        <table id="dataTable2">
                            <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {table2.map((row, index) => (
                                <tr key={index}>
                                    <td>{row.x}</td>
                                    <td>{row.y}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>

                <div className="table-wrapper">
                    {tableResult.length > 0 && (
                        <table id="dataTable3">
                            <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {tableResult.map((row) => (
                                <tr key={row[0]}>
                                    <td>{row[0]}</td>
                                    <td>{row[1]}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>
            </div>
        </div>
    );
}