import { useState } from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import FirstPage from "../FirstPage/FirstPage.jsx";
import Modal from 'react-modal';

export default function SecondPage() {
    const [table1, setTable1] = useState([]);
    const [table2, setTable2] = useState([]);
    const [tableResult, setTableResult] = useState([]);
    const [operation, setOperation] = useState('+');
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [activeModal, setActiveModal] = useState(null);

    const openModal = (modalType) => {
        setModalIsOpen(true);
        setActiveModal(modalType);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        setActiveModal(null);
    };

    function modalContent1() {
        return <FirstPage onDataChange={handleDataChange} closeModal={closeModal} />;
    }

    function modalContent2() {
        return <FirstPage onDataChange={handleDataChange2} closeModal={closeModal} />;
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

    return (
        <div style={styles.container}>
            <Button onClick={() => openModal('modal1')} style={styles.button}>
                Создать функцию для таблицы 1
            </Button>
            <Button onClick={() => openModal('modal2')} style={styles.button}>
                Создать функцию для таблицы 2
            </Button>

            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} style={styles.modal}>
                {activeModal === 'modal1' ? modalContent1() : modalContent2()}
            </Modal>

            <div style={styles.grid}>
                {table1.length > 0 && (
                    <table id="dataTable1" style={styles.table}>
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

                <div style={styles.operation}>
                    <label htmlFor="operationSelect">Выберите операцию:</label>
                    <select
                        id="operationSelect"
                        onChange={(e) => setOperation(e.target.value)}
                        value={operation}
                        style={styles.select}
                    >
                        <option value="+">+</option>
                        <option value="-">-</option>
                        <option value="*">*</option>
                        <option value="/">/</option>
                    </select>
                </div>

                {table2.length > 0 && (
                    <table id="dataTable2" style={styles.table}>
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

                <Button onClick={performOperation} style={styles.calculateButton}>
                    Вычислить
                </Button>

                {tableResult.length > 0 && (
                    <table id="dataTable3" style={styles.table}>
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
    );
}

// Стили
const styles = {
    container: {
        maxWidth: "1200px",
        margin: "0 auto",
        padding: "20px",
    },
    button: {
        margin: "10px 0",
        padding: "10px 20px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "none",
        backgroundColor: "#007BFF",
        color: "#fff",
        cursor: "pointer",
        transition: "background-color 0.3s",
    },
    modal: {
        content: {
            width: "60%",
            maxWidth: "600px",
            margin: "auto",
            padding: "20px",
            borderRadius: "8px",
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        },
    },
    grid: {
        display: "grid",
        gridTemplateColumns: "1fr auto 1fr auto 1fr",
        gap: "20px",
        alignItems: "start",
    },
    table: {
        width: "100%",
        borderCollapse: "collapse",
        marginBottom: "20px",
    },
    operation: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
    },
    select: {
        padding: "10px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "1px solid #ccc",
    },
    calculateButton: {
        gridColumn: "2 / 3",
        alignSelf: "center",
        padding: "10px 20px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "none",
        backgroundColor: "#28a745",
        color: "#fff",
        cursor: "pointer",
        transition: "background-color 0.3s",
    },
};