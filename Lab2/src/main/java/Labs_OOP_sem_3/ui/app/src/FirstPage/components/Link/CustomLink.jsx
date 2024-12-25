import {Link} from "react-router-dom";
import '../Header/Header.css'

export default function CustomLink({children, to, ...props}) {
    //const match = useMatch(to);
    return (
        <Link to={to}
              {...props}>
            {children}
        </Link>
    )
}