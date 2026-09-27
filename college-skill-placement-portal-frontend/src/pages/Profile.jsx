import { useEffect, useState } from "react";
import api from "../services/api";

function Profile() {
    const [student, setStudent] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const response = await api.get("/api/student/me");
                setStudent(response.data);
            } catch (err) {
                console.error("Profile fetch error:", err);

                setError(
                    err.response?.data?.message ||
                    "Failed to load profile."
                );
            } finally {
                setLoading(false);
            }
        };

        fetchProfile();
    }, []);

    if (loading) {
        return <h2>Loading profile...</h2>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h1>My Profile</h1>

            {student && (
                <div>
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
        </div>
    );
}

export default Profile;