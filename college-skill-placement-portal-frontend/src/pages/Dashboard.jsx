import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";

function Dashboard() {
    const { logout } = useAuth();

    const [student, setStudent] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchStudent = async () => {
            try {
                const response = await api.get("/api/student/me");

                console.log("Student data:", response.data);

                setStudent(response.data);
            } catch (err) {
                console.error("Student fetch error:", err);

                setError(
                    err.response?.data?.message ||
                    "Failed to load student information."
                );
            } finally {
                setLoading(false);
            }
        };

        fetchStudent();
    }, []);

    if (loading) {
        return <h2>Loading dashboard...</h2>;
    }

    return (
        <div>
            <h1>Student Dashboard</h1>

            {error && (
                <p>{error}</p>
            )}

            {student && (
                <div>
                    <h2>Student Information</h2>

                    <p>
                        <strong>Name:</strong>{" "}
                        {student.name}
                    </p>

                    <p>
                        <strong>Email:</strong>{" "}
                        {student.email}
                    </p>

                    <p>
                        <strong>Roll Number:</strong>{" "}
                        {student.rollNumber}
                    </p>

                    <p>
                        <strong>Department:</strong>{" "}
                        {student.department}
                    </p>

                    <p>
                        <strong>Year:</strong>{" "}
                        {student.year}
                    </p>

                    <p>
                        <strong>CGPA:</strong>{" "}
                        {student.cgpa}
                    </p>
                </div>
            )}

            <button onClick={logout}>
                Logout
            </button>
        </div>
    );
}

export default Dashboard;