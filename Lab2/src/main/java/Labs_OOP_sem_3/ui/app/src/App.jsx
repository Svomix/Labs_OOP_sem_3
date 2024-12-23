import './index.css'
import Header from "./FirstPage/components/Header/Header.jsx";
import SelectFactory from "./SelectFactory.jsx";
import SecondPage from "./SecondPage/SecondPage.jsx";
import {Route, Routes} from "react-router";
import { FactoryProvider } from "./FactoryContext";
import DifferentiationPage from "./DiffPage/DifferentiationPage.jsx";
import FunctionEditorPage from "./FunctionEditorPage/FunctionEditorPage.jsx";
import IntegratePage from "./IntegratePage/IntegratePage.jsx";

export default function App() {
  return (
    <FactoryProvider>
      <Routes>
        <Route path='/' element={<Header/>}>
            <Route index element={<SecondPage/>}/>
            <Route path='selectFactory' element={<SelectFactory/>}/>
            <Route path='diff' element={<DifferentiationPage/>}/>
            <Route path='graphic' element={<FunctionEditorPage/>}/>
            <Route path='integrate' element={<IntegratePage/>}/>
        </Route>
      </Routes>
    </FactoryProvider>
  )
}

