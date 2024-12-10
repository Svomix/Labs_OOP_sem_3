import Button from "./components/Button/Button.jsx";
import {useState} from "react";

export default function FirstPage() {
    const [pointsCount, setPointsCount] = useState();
    const [hasError, setHasError] = useState(true)
    const [tableData, setTableData] = useState([])

    function changePointsCount(event) {
        setPointsCount(event.target.value)
        setHasError(event.target.value < 2)
    }

    function createTable(event) {
        event.preventDefault();
        const data = []
        for (let i = 1; i <= pointsCount; i++) {
            data.push({id: i, x: null, y: null})
        }
        setTableData(data)
    }

    function handleInputChange(id, field, newValue) {
        setTableData(prevData =>
            prevData.map(item =>
                item.id === id ? {...item, [field]: newValue} : item))
    }

    function areAllCellsFilled() {
        return tableData.every(item => item.x !== 0 && item.y !== 0);
    }

    function handleSecondButtonClick() {
        if (areAllCellsFilled()) {
            alert('Все ячейки заполнены!');
        } else {
            alert('Не все ячейки заполнены')
        }
    }

    return (
        <div>
            <section>
                <p3>Создание табулированной функции</p3>
                <form onSubmit={createTable}>
                    <label htmlFor="pointsCount">Количество точек:</label>
                    <input type="number"
                           className="control"
                           id={pointsCount}
                           value={pointsCount}
                           style={{
                               border: !hasError ? null : '1px solid red'
                           }}
                           onChange={changePointsCount}/>
                    <Button onClick={createTable} disabled={hasError} isActive={!hasError}>Создать</Button>
                </form>
                {
                    <table>
                        <thead>
                        <tr>
                            <th>X</th>
                            <th>Y</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tableData.map(item => (
                            <tr key={item.id}>
                                <td>
                                    <input
                                        type="number"
                                        value={item.x}
                                        onChange={(e) => handleInputChange(item.id, 'x', e.target.value)}
                                    />
                                </td>
                                <td>
                                    <input
                                        type="number"
                                        value={item.y}
                                        onChange={(e) => handleInputChange(item.id, 'y', e.target.value)}
                                    />
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                }
                {!hasError && <Button
                    disabled={!areAllCellsFilled()}
                    isActive={areAllCellsFilled()}
                    onClick={handleSecondButtonClick}
                >
                    Подтвердить все ячейки
                </Button>
                }
            </section>
        </div>
    )
}