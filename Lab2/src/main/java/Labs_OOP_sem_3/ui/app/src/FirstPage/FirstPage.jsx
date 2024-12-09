import Header from "./components/Header/Header.jsx";
import Button from "./components/Button/Button.jsx";
import {useState} from "react";

export default function FirstPage() {
    const [pointsCount, setPointsCount] = useState();
    const [hasError, setHasError] = useState(true)

    function changePointsCount(event){
        setPointsCount(event.target.value)
        setHasError(event.target.value < 2)
    }
    return (
        <div>
            <Header/>
            <main>
                <section>
                    <p3>Создание табулированной функции</p3>
                    <form>
                        <label htmlFor="pointsCount">Количество точек:</label>
                        <input type="number"
                               className="control"
                               value={pointsCount}
                               style={{
                                   border: !hasError ? null : '1px solid red'
                               }}
                               onChange={changePointsCount}/>
                        <Button disabled={hasError} isActive={!hasError}>Создать</Button>
                    </form>
                </section>
            </main>
        </div>
    )
}