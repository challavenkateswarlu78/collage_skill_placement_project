import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";

function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError("");
        setLoading(true);

        try {
            const response = await api.post("/api/auth/login", {
                username: username,
                password: password,
            });

            console.log("Login response:", response.data);

            const token = response.data.token;
const userId = response.data.userId;

if (!token) {
    throw new Error("Token not received from server");
}

if (!userId) {
    throw new Error("User ID not received from server");
}

login(token, userId);

            navigate("/dashboard");
        } catch (err) {
            console.error("Login error:", err);

            if (err.response) {
                console.error("Status:", err.response.status);
                console.error("Response:", err.response.data);
            }

            setError(
                err.response?.data?.message ||
                "Login failed. Please check your username and password."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
    <div className="login-page">

        <div className="login-card">

            <div className="login-header">
                <h1>
                    College Skill Placement Portal
                </h1>

                <p>
                    Student Login
                </p>
            </div>

            <form
                className="login-form"
                onSubmit={handleSubmit}
            >

                <div className="login-form-group">
                    <label>
                        Username
                    </label>

                    <input
                        type="text"
                        value={username}
                        onChange={(e) =>
                            setUsername(e.target.value)
                        }
                        placeholder="Enter username"
                        required
                    />
                </div>

                <div className="login-form-group">
                    <label>
                        Password
                    </label>

                    <input
                        type="password"
                        value={password}
                        onChange={(e) =>
                            setPassword(e.target.value)
                        }
                        placeholder="Enter password"
                        required
                    />
                </div>

                {error && (
                    <p className="login-error">
                        {error}
                    </p>
                )}

                <button
                    className="login-button"
                    type="submit"
                    disabled={loading}
                >
                    {loading
                        ? "Logging in..."
                        : "Login"}
                </button>

            </form>

        </div>

    </div>
);
}

export default Login;