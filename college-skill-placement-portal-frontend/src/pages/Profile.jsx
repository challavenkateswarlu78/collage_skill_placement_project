import { useEffect, useState } from "react";
import api from "../services/api";
import Navbar from "../components/Navbar";

function Profile() {
    const [student, setStudent] = useState(null);
const [loading, setLoading] = useState(true);
const [editing, setEditing] = useState(false);
const [saving, setSaving] = useState(false);
const [error, setError] = useState("");
const [message, setMessage] = useState("");

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
    
    const handleChange = (e) => {
    const { name, value } = e.target;

    setStudent((current) => ({
        ...current,
        [name]: value,
    }));
};

const handleSave = async () => {
    setSaving(true);
    setError("");
    setMessage("");

    try {
        const response = await api.put(
            `/api/students/${student.id}`,
            {
                name: student.name,
                email: student.email,
                rollNumber: student.rollNumber,
                department: student.department,
                year: Number(student.year),
                cgpa: Number(student.cgpa),
            }
        );

        setStudent(response.data);
        setEditing(false);

        setMessage(
            "Profile updated successfully."
        );
    } catch (err) {
        console.error(
            "Profile update error:",
            err
        );

        setError(
            err.response?.data?.message ||
            "Failed to update profile."
        );
    } finally {
        setSaving(false);
    }
};

const profileFields = [
    student?.name,
    student?.email,
    student?.rollNumber,
    student?.department,
    student?.year,
    student?.cgpa,
];

const completedFields = profileFields.filter(
    (field) =>
        field !== null &&
        field !== undefined &&
        field !== ""
).length;

const profileCompletion = Math.round(
    (completedFields / profileFields.length) * 100
);

    if (loading) {
        return <h2>Loading profile...</h2>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h1>My Profile</h1>
            <Navbar />

            {student && (
    <div className="profile-completion-card">
        <h2>Profile Completion</h2>

        <p>
            {profileCompletion}% Complete
        </p>

        <div
            style={{
                width: "300px",
                height: "20px",
                border: "1px solid #000",
                borderRadius: "5px",
                overflow: "hidden",
            }}
        >
            <div
                style={{
                    width: `${profileCompletion}%`,
                    height: "100%",
                    backgroundColor: "green",
                }}
            />
        </div>

        <p>
            Completed fields:{" "}
            {completedFields} /{" "}
            {profileFields.length}
        </p>
    </div>
)}

            {message && (
    <p>{message}</p>
)}

{error && (
    <p>{error}</p>
)}



            {student && (
    <div className="profile-card">
        <p>
            <strong>Name:</strong>{" "}
            {editing ? (
                <input
                    type="text"
                    name="name"
                    value={student.name || ""}
                    onChange={handleChange}
                />
            ) : (
                student.name
            )}
        </p>

        <p>
            <strong>Email:</strong>{" "}
            {editing ? (
                <input
                    type="email"
                    name="email"
                    value={student.email || ""}
                    onChange={handleChange}
                />
            ) : (
                student.email
            )}
        </p>

        <p>
            <strong>Roll Number:</strong>{" "}
            {editing ? (
                <input
                    type="text"
                    name="rollNumber"
                    value={student.rollNumber || ""}
                    onChange={handleChange}
                />
            ) : (
                student.rollNumber
            )}
        </p>

        <p>
            <strong>Department:</strong>{" "}
            {editing ? (
                <input
                    type="text"
                    name="department"
                    value={student.department || ""}
                    onChange={handleChange}
                />
            ) : (
                student.department
            )}
        </p>

        <p>
            <strong>Year:</strong>{" "}
            {editing ? (
                <input
                    type="number"
                    name="year"
                    min="1"
                    max="4"
                    value={student.year || ""}
                    onChange={handleChange}
                />
            ) : (
                student.year
            )}
        </p>

        <p>
            <strong>CGPA:</strong>{" "}
            {editing ? (
                <input
                    type="number"
                    name="cgpa"
                    min="0"
                    max="10"
                    step="0.01"
                    value={student.cgpa || ""}
                    onChange={handleChange}
                />
            ) : (
                student.cgpa
            )}
        </p>

        {editing ? (
            <div>
                <button
                    onClick={handleSave}
                    disabled={saving}
                >
                    {saving
                        ? "Saving..."
                        : "Save Changes"}
                </button>

                <button
                    onClick={() => setEditing(false)}
                    disabled={saving}
                >
                    Cancel
                </button>
            </div>
        ) : (
            <button
                onClick={() => {
                    setMessage("");
                    setError("");
                    setEditing(true);
                }}
            >
                Edit Profile
            </button>
        )}
    </div>
)}
        </div>
    );
}

export default Profile;