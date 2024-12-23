import Button from "./components/Button/Button.jsx";
import {useContext, useState} from "react";
import {FactoryContext} from "../FactoryContext.jsx";

export default function MainSection({onDataChange, closeModal}) {
    const [pointsCount, setPointsCount] = useState(0);
    const [hasError, setHasError] = useState(true);
    const [tableData, setTableData] = useState([]);
    const {factory} = useContext(FactoryContext)

    function changePointsCount(event) {
        setPointsCount(event.target.value);
        setHasError(event.target.value < 2);
    }

    function createTable(event) {
        event.preventDefault();
        const newTableData = Array.from({length: pointsCount}, (_, index) => {
            return {
                x: tableData[index] ? tableData[index].x : '',
                y: tableData[index] ? tableData[index].y : ''
            };
        });

        setTableData(prevData => {
            if (pointsCount > prevData.length) {
                return [...prevData, ...newTableData.slice(prevData.length)];
            } else {
                return newTableData.slice(0, pointsCount);
            }
        });
    }

    function handleInputChange(index, field, value) {
        setTableData(prevData =>
            prevData.map((item, idx) =>
                idx === index ? {...item, [field]: value} : item
            )
        );
    }

    function areAllCellsFilled() {
        return tableData.every(item => item.x !== '' && item.y !== '');
    }

    async function handleSecondButtonClick() {
        alert('Все ячейки заполнены!');
        closeModal(true);
        onDataChange(tableData);
        const postTabArr = {
            arrX: tableData.map(item => item.x),
            arrY: tableData.map(item => item.y),
            type: factory
        };
        const username = 'igor';
        const password = '12345';
        const authHeader = `Basic ${btoa(`${username}:${password}`)}`;
        fetch('http://localhost:8080/points/table', {
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

            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    return (
        <section style={styles.container}>
            <h3 style={styles.title}>Создание табулированной функции</h3>
            <form onSubmit={createTable} style={styles.form}>
                <label htmlFor="pointsCount" style={styles.label}>
                    Количество точек:
                </label>
                <input
                    type="number"
                    className="control"
                    value={pointsCount}
                    style={{
                        ...styles.input,
                        border: hasError ? '1px solid red' : '1px solid #ccc'
                    }}
                    onChange={changePointsCount}
                />
                <Button
                    onClick={createTable}
                    disabled={hasError}
                    isActive={!hasError}
                    style={styles.button}
                >
                    Создать
                </Button>
            </form>

            {tableData.length > 0 && (
                <>
                    <table id="dataTable" style={styles.table}>
                        <thead>
                        <tr>
                            <th style={styles.tableHeader}>X</th>
                            <th style={styles.tableHeader}>Y</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tableData.map((row, index) => (
                            <tr key={index}>
                                <td
                                    contentEditable
                                    suppressContentEditableWarning
                                    onKeyDown={(e) => {
                                        if (!/[\d]/.test(e.key) && e.key !== 'Backspace' && e.key !== 'Delete') {
                                            e.preventDefault();
                                        }
                                    }}
                                    onBlur={(e) => handleInputChange(index, 'x', e.currentTarget.textContent)}
                                    style={styles.tableCell}
                                >
                                    {row.x}
                                </td>
                                <td
                                    contentEditable
                                    suppressContentEditableWarning
                                    onKeyDown={(e) => {
                                        if (!/[\d]/.test(e.key) && e.key !== 'Backspace' && e.key !== 'Delete') {
                                            e.preventDefault();
                                        }
                                    }}
                                    onBlur={(e) => handleInputChange(index, 'y', e.currentTarget.textContent)}
                                    style={styles.tableCell}
                                >
                                    {row.y}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <Button
                        disabled={!areAllCellsFilled()}
                        isActive={areAllCellsFilled()}
                        onClick={handleSecondButtonClick}
                        style={styles.button}
                    >
                        Подтвердить все ячейки
                    </Button>
                </>
            )}
        </section>
    );
}

// Стили
const styles = {
    container: {
        maxWidth: "800px",
        margin: "0 auto",
        padding: "20px",
        border: "1px solid #ccc",
        borderRadius: "8px",
        boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        backgroundColor: "#f9f9f9",
    },
    title: {
        fontSize: "24px",
        marginBottom: "20px",
        textAlign: "center",
        color: "#333",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        gap: "15px",
        marginBottom: "20px",
    },
    label: {
        fontSize: "16px",
        marginBottom: "5px",
        color: "#555",
    },
    input: {
        padding: "10px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "1px solid #ccc",
    },
    button: {
        padding: "10px",
        fontSize: "16px",
        borderRadius: "4px",
        border: "none",
        backgroundColor: "#007BFF",
        color: "#fff",
        cursor: "pointer",
        transition: "background-color 0.3s",
    },
    table: {
        width: "100%",
        borderCollapse: "collapse",
        marginBottom: "20px",
    },
    tableHeader: {
        backgroundColor: "#007BFF",
        color: "#fff",
        padding: "10px",
        textAlign: "left",
    },
    tableCell: {
        padding: "10px",
        border: "1px solid #ccc",
        textAlign: "left",
    },
};