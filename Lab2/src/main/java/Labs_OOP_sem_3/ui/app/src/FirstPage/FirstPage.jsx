import Header from "./components/Header/Header.jsx";
import Button from "./components/Button/Button.jsx";
import {useState} from "react";

export default function FirstPage() {
    //const [pointsCount, setPointsCount] = useState(0);
    //const pointsCount = parseInt(document.getElementById("pointsCount").value);
    //const tableBody = document.getElementById("tableBody");
    return (
        <div>
            <Header/>
            <main>
                <section>
                    <p3>Создание табулированной функции</p3>
                    <label form="pointsCount">Количество точек:</label>
                    <input type="number" className="point-input" value={pointsCount} onChange={changePointsCount}/>
                    <Button>Создать</Button>
                    <table id="dataTable">
                        <thead>
                        <tr>
                            <th>x</th>
                            <th>y</th>
                        </tr>
                        </thead>
                        <tbody id="tableBody"></tbody>
                    </table>
                </section>
            </main>
        </div>
    )
}