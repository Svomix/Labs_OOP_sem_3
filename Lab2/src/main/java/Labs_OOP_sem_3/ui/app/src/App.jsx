import './index.css'
import Header from "./FirstPage/components/Header/Header.jsx";
import SelectFactory from "./SelectFactory.jsx";
import SecondPage from "./SecondPage/SecondPage.jsx";
import {Route, Routes} from "react-router";
import {FactoryProvider} from "./FactoryContext";
import DifferentiationPage from "./DiffPage/DifferentiationPage.jsx";
import FunctionEditorPage from "./FunctionEditorPage/FunctionEditorPage.jsx";
import IntegratePage from "./IntegratePage/IntegratePage.jsx";
import CompositeFunctionPage from "./CompositeFunctionPage/CompositeFunctionPage.jsx";
import AuthProvider from "./hoc/AuthProvider.jsx";
import LoginPage from "./LoginPage/LoginPage.jsx";
import RegisterPage from "./LoginPage/RegisterPage.jsx";
import RequireAuth from "./hoc/RequireAuth.jsx";

export default function App() {
    return (
        <AuthProvider>
            <FactoryProvider>
                <Routes>
                    <Route path='/' element={<Header/>}>
                        <Route index element={<RequireAuth>
                            <SecondPage/>
                        </RequireAuth>
                        }/>
                        <Route path='selectFactory' element={<SelectFactory/>}/>
                        <Route path='diff' element={<DifferentiationPage/>}/>
                        <Route path='graphic' element={<FunctionEditorPage/>}/>
                        <Route path='integrate' element={<IntegratePage/>}/>
                        <Route path='composit' element={<CompositeFunctionPage/>}/>
                        <Route path='login' element={<LoginPage/>}/>
                        <Route path='register' element={<RegisterPage/>}/>
                    </Route>
                </Routes>
            </FactoryProvider>
        </AuthProvider>
    )
}

