import FirstPage from "./FirstPage/FirstPage.jsx";
import './index.css'
import Header from "./FirstPage/components/Header/Header.jsx";
import SelectFactory from "./SelectFactory.jsx";
import SecondPage from "./SecondPage/SecondPage.jsx";

export default function App() {
  return (
    <>
        <Header/>
        <main>
            <SecondPage/>
            <SelectFactory/>
        </main>
    </>
  )
}

