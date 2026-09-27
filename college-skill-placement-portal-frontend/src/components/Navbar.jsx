import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
    const { logout } = useAuth();

    return (
        <nav className="navbar">
            <div className="navbar-brand">
            College Skill Placement Portal
            </div>
            <Link to="/dashboard">
                Dashboard
            </Link>

            {" | "}

            <Link to="/profile">
                Profile
            </Link>

            {" | "}

            <Link to="/skills">
                Skills
            </Link>

            {" | "}

            <Link to="/jobs">
                Jobs
            </Link>

            {" | "}

            <Link to="/applications">
                Applications
            </Link>

            {" | "}

            <Link to="/notifications">
                Notifications
            </Link>

            {" | "}

            <button onClick={logout}>
                Logout
            </button>
        </nav>
    );
}

export default Navbar;