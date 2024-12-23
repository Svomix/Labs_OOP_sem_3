import React, {useState} from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import Modal from 'react-modal';
import './/IntegratePage.css'
import FirstPage from "../FirstPage/FirstPage.jsx";


export default function IntegratePage() {
    const [originalFunction, setOriginalFunction] = useState([]);
    const [threadCount, setThreadCount] = useState('')
    const [integratedFunction, setIntegratedFunction] = useState([]);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [activeModal, setActiveModal] = useState(null);
    const [fileName, setFileName] = useState('');

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

    const performIntegration = () => {
        const thread = parseInt(threadCount)
        // Пример реализации дифференцирования (заглушка)
        ////////
        setIntegratedFunction(result);
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
                {x: 1, y: 2},
                {x: 2, y: 4},
                {x: 3, y: 6},
            ];
        } catch (error) {
            console.error('Ошибка при загрузке функции:', error);
            return null;
        }
    };

    function modalContent() {
        return (
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal}/>
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

    return (
        <div className="differentiation-page-container">
            <div className="buttons-container">
                <Button onClick={() => openModal('create')}>Создать функцию</Button>
                <Button onClick={handleLoad}>Загрузить функцию</Button>
                <Button onClick={() => saveFunction(originalFunction, fileName)}>Сохранить исходную функцию</Button>
                <Button onClick={() => saveFunction(integratedFunction, fileName)}>Сохранить результат</Button>
                <Button onClick={performIntegration}>Интегрировать</Button>
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
                </div>

                <div className="table-wrapper">
                    <h3>Результат интегрирования</h3>
                    {integratedFunction.length > 0 && (
                        <table id="differentiatedTable">
                            <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                            </tr>
                            </thead>
                            <tbody>
                            {integratedFunction.map((row, index) => (
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
            <label htmlFor="thread">
                Количество потоков:
            </label>
            <input
                type="number"
                min="1"
                value={threadCount}
                onChange={(e) => setThreadCount(e.target.value)}
                required
            />
        </div>
    );
}