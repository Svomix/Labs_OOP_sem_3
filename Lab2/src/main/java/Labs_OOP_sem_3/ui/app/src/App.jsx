import FirstPage from "./FirstPage/FirstPage.jsx";
import './index.css'
import Header from "./FirstPage/components/Header/Header.jsx";
import SelectFactory from "./SelectFactory.jsx";

export default function App() {
  return (
    <>
        <Header/>
        <main>
            <FirstPage/>
            <SelectFactory/>
        </main>
    </>
  )
}

