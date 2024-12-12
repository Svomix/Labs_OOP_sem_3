import FirstPage from "./FirstPage/FirstPage.jsx";
import './index.css'
import Header from "./FirstPage/components/Header/Header.jsx";

export default function App() {
  return (
    <>
        <Header/>
        <main>
            <FirstPage/>
        </main>
    </>
  )
}

