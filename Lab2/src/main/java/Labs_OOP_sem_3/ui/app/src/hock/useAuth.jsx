import {useContext} from "react";
import {AuthContext} from "../hoc/AuthProvider";

export default function useAuth() {
    return useContext(AuthContext);
}