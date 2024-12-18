import Button from "./components/Button/Button.jsx";
import {useState} from "react";

export default function MainSection({onDataChange, closeModal}) {
    const [pointsCount, setPointsCount] = useState(0);
    const [hasError, setHasError] = useState(true)
    const [tableData, setTableData] = useState([])

    function changePointsCount(event) {
        setPointsCount(event.target.value)
        setHasError(event.target.value < 2)
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
            prevData.map(((item, idx) =>
                idx === index ? {...item, [field]: value} : item)))
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
            arrY: tableData.map(item => item.y)
        };
        const credentials =  ('igor:12345')
        const auth = { "Authorization" : 'Basic KGlnb3I6MTIzNCk=' }
        fetch('http://localhost:8080/points', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': auth,
            },
            mode:'no-cors',
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
        <section>
            <p3>Создание табулированной функции</p3>
            <form onSubmit={createTable}>
                <label htmlFor="pointsCount">Количество точек:</label>
                <input type="number"
                       className="control"
                       value={pointsCount}
                       style={{
                           border: !hasError ? null : '1px solid red'
                       }}
                       onChange={changePointsCount}/>
                <Button onClick={createTable} disabled={hasError} isActive={!hasError}>Создать</Button>
            </form>

            {tableData.length > 0 &&
                <>
                    <table id="dataTable">
                        <thead>
                        <tr>
                            <th>X</th>
                            <th>Y</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tableData.map((row, index) => (
                            <tr key={index}>
                                <td contentEditable
                                    suppressContentEditableWarning
                                    onKeyDown={(e) => {
                                        if (!/[\d]/.test(e.key) && e.key !== 'Backspace' && e.key !== 'Delete') {
                                            e.preventDefault();
                                        }
                                    }}
                                    onBlur={(e) => handleInputChange(index, 'x', e.currentTarget.textContent)}>
                                    {row.x}
                                </td>
                                <td contentEditable
                                    suppressContentEditableWarning
                                    onKeyDown={(e) => {
                                        if (!/[\d]/.test(e.key) && e.key !== 'Backspace' && e.key !== 'Delete') {
                                            e.preventDefault();
                                        }
                                    }}
                                    onBlur={(e) => handleInputChange(index, 'y', e.currentTarget.textContent)}>
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
                    >
                        Подтвердить все ячейки
                    </Button>
                </>
            }
        </section>
    )
}