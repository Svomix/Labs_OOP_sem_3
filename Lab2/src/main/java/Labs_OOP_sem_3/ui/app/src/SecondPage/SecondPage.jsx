import {useState} from "react";
import Button from "../FirstPage/components/Button/Button.jsx";
import FirstPage from "../FirstPage/FirstPage.jsx";
import Modal from 'react-modal'
export default function SecondPage() {
    const [table1, setTable1] = useState([])
    const [table2, setTable2] = useState([])
    const [tableResult, setTableResult] = useState([])
    const [operation, setOperation] = useState('add')
    const [modalIsOpen, setModalIsOpen] = useState(false)
    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    function modalContent1() {
        return (
            <FirstPage onDataChange={handleDataChange} closeModal={closeModal}/>
        )
    }
    function modalContent2() {
        return (
            <FirstPage onDataChange={handleDataChange2} closeModal={closeModal}/>
        )
    }

    const handleDataChange = (newData) => {
        setTable1(newData);
    };
    const handleDataChange2 = (newData) => {
        setTable2(newData);
    };

    const performOperation = () => {
        if (table1 && table2) {
            let result = []
            switch (operation) {
                case 'add' :
                    for (let i = 0; i < table1.length; i++) {
                        result.push([table1[i].x, parseFloat(table1[i].y) + parseFloat(table2[i].y)])
                    }
            }
            setTableResult(result)
        }
    }

    return (
        <>
        <Button onClick={openModal}>Создать функцию</Button>
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} ariaHideApp={false}>{modalContent1} </Modal>
            {table1.length > 0 &&
                <>
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
                                <td>
                                    {row.x}
                                </td>
                                <td>
                                    {row.y}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </>
            }
            <Button onClick={openModal}>Создать функцию</Button>
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} ariaHideApp={false}>{modalContent2}</Modal>
            {table2.length > 0 &&
                <>
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
                                <td>
                                    {row.y}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </>
            }
            <Button onClick={performOperation}>Вычислить</Button>
            {tableResult.length > 0 &&
                <>
                    <table id="dataTable3">
                        <thead>
                        <tr>
                            <th>X</th>
                            <th>Y</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tableResult.map((row) => {
                            console.log(row)
                            return(
                            <tr key={row[0]}>
                                <td>
                                    {row[0]}
                                </td>
                                <td>
                                    {row[1]}
                                </td>
                            </tr>
                        )})}
                        </tbody>
                    </table>
                </>
            }
        </>
    )
}