import './index.css'
import Header from "./FirstPage/components/Header/Header.jsx";
import SelectFactory from "./SelectFactory.jsx";
import SecondPage from "./SecondPage/SecondPage.jsx";
import {Route, Routes} from "react-router";

export default function App() {
  return (
    <>
      <Routes>
        <Route path='/' element={<Header/>}>
            <Route index element={<SecondPage/>}/>
            <Route path='selectFactory' element={<SelectFactory/>}/>
        </Route>
      </Routes>
    </>
  )
}

