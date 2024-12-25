//import './index.css'
import Header from "./FirstPage/components/Header/Header.jsx";
import SelectFactory from "./SelectFactory.jsx";
import SecondPage from "./SecondPage/SecondPage.jsx";
import {Route, Routes} from "react-router";
import {FactoryProvider} from "./FactoryContext";
import DifferentiationPage from "./DiffPage/DifferentiationPage.jsx";
import FunctionEditorPage from "./FunctionEditorPage/FunctionEditorPage.jsx";
import IntegratePage from "./IntegratePage/IntegratePage.jsx";
import AuthProvider from "./hoc/AuthProvider.jsx";
import LoginPage from "./LoginPage/LoginPage.jsx";
import RegisterPage from "./LoginPage/RegisterPage.jsx";
import RequireAuth from "./hoc/RequireAuth.jsx";
import CompositeFunctionPage from "./CompositeFunctionPage/CompositeFunctionPage.jsx";

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
                        <Route path='selectFactory' element={<RequireAuth>
                            <SelectFactory/>
                        </RequireAuth>}/>
                        <Route path='diff' element={<RequireAuth>
                            <DifferentiationPage/>
                        </RequireAuth>}/>
                        <Route path='graphic' element={<RequireAuth>
                            <FunctionEditorPage/>
                        </RequireAuth>}/>
                        <Route path='integrate' element={<RequireAuth>
                            <IntegratePage/>
                        </RequireAuth>}/>
                        <Route path='composit' element={<RequireAuth>
                            <CompositeFunctionPage/>
                        </RequireAuth>}/>
                        <Route path='login' element={<LoginPage/>}/>
                        <Route path='register' element={<RegisterPage/>}/>
                    </Route>
                </Routes>
            </FactoryProvider>
        </AuthProvider>
    )
}

