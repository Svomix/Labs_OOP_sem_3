import {Navigate, useLocation} from "react-router-dom";
import useAuth from "../hock/useAuth";

export default function RequireAuth({children}) {
    const location = useLocation();
    const {user} = useAuth();

    if(!user)
        return <Navigate to='/login' state={{from: location}} />

    return children;
}