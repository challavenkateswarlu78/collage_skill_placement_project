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

            if (!token) {
                throw new Error("Token not received from server");
            }

            login(token);

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
        <div>
            <h1>College Skill Placement Portal</h1>

            <h2>Login</h2>

            <form onSubmit={handleSubmit}>
                <div>
                    <label>Username</label>

                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <label>Password</label>

                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                {error && (
                    <p>{error}</p>
                )}

                <button
                    type="submit"
                    disabled={loading}
                >
                    {loading ? "Logging in..." : "Login"}
                </button>
            </form>
        </div>
    );
}

export default Login;