import Button from "./components/Button/Button.jsx";
import {useContext, useState} from "react";
import {FactoryContext} from "../FactoryContext.jsx";
import useAuth from "../hock/useAuth.jsx";

export default function MainSection({onDataChange, closeModal}) {
    const [pointsCount, setPointsCount] = useState(0);
    const [hasError, setHasError] = useState(true);
    const [tableData, setTableData] = useState([]);
    const {factory} = useContext(FactoryContext);
    const {user} = useAuth();

    const [currentPage, setCurrentPage] = useState(1);
    const [rowsPerPage] = useState(5); // Количество ячеек на странице

    function changePointsCount(event) {
        setPointsCount(event.target.value);
        setHasError(event.target.value < 2);
    }

    function createTable(event) {
        event.preventDefault();
        if (pointsCount >= 1001) {
            alert('Не может быть больше 1001 точки без добавления руками');
            return;
        }
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

    function isSorted() {
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
    }

    async function handleSecondButtonClick() {
        if (!isSorted()) {
            alert('Функция не отсортирована');
            return;
        }
        alert('Функция подтверждена');
        closeModal(true);
        onDataChange(tableData);
    }

    const indexOfLastRow = currentPage * rowsPerPage;
    const indexOfFirstRow = indexOfLastRow - rowsPerPage;
    const currentRows = tableData.slice(indexOfFirstRow, indexOfLastRow);

    const totalPages = Math.ceil(tableData.length / rowsPerPage);

    const goToNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    const goToPreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
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
        <section style={styles.container}>
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
                        {currentRows.map((row, index) => (
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
                                    onBlur={(e) => handleInputChange(indexOfFirstRow + index, 'x', e.currentTarget.textContent)}
                                    style={styles.tableCell}
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
                                    onBlur={(e) => handleInputChange(indexOfFirstRow + index, 'y', e.currentTarget.textContent)}
                                    style={styles.tableCell}
                                >
                                    {row.y}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    {/* Пагинация */}
                    <div style={styles.pagination}>
                        <Button
                            onClick={goToPreviousPage}
                            disabled={currentPage === 1}
                        >
                            Назад
                        </Button>
                        <span style={styles.pageInfo}>
                            Страница {currentPage} из {totalPages}
                        </span>
                        <Button
                            onClick={goToNextPage}
                            disabled={currentPage === totalPages}
                        >
                            Вперёд
                        </Button>
                    </div>

                    <Button
                        disabled={!areAllCellsFilled()}
                        isActive={areAllCellsFilled()}
                        onClick={handleSecondButtonClick}
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
    pagination: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        gap: "10px",
        marginBottom: "20px",
    },
    pageInfo: {
        fontSize: "16px",
        color: "#555",
    },
};