import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";
import Navbar from "../components/Navbar";


function Dashboard() {
    const { logout } = useAuth();

    const [student, setStudent] = useState(null);
    const [summary, setSummary] = useState(null);
    const [skills, setSkills] = useState([]);
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchStudent = async () => {
            try {
                const response = await api.get("/api/student/me");

                console.log("Student data:", response.data);

                setStudent(response.data);

                const studentId = response.data.id;

const summaryResponse =
    await api.get(
        `/api/job-applications/student/${studentId}/summary`
    );

setSummary(summaryResponse.data);
const skillsResponse =
    await api.get(
        `/api/students/${studentId}/skills`
    );

setSkills(skillsResponse.data);

const notificationsResponse =
    await api.get(
        `/api/notifications/user/${localStorage.getItem("userId")}`
    );

setNotifications(
    notificationsResponse.data
);

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
            <Navbar />
            
           
{summary && (
    <div>
        <h2>Quick Statistics</h2>

        <div className="stats-grid">

            <div className="stat-card">
                <h3>Total Applications</h3>
                <p>{summary.totalApplications}</p>
            </div>

            <div className="stat-card">
                <h3>Applied</h3>
                <p>{summary.applied}</p>
            </div>

            <div className="stat-card">
                <h3>Shortlisted</h3>
                <p>{summary.shortlisted}</p>
            </div>

            <div className="stat-card">
                <h3>Interviews</h3>
                <p>{summary.interview}</p>
            </div>

            <div className="stat-card">
                <h3>Selected</h3>
                <p>{summary.selected}</p>
            </div>

            <div className="stat-card">
                <h3>Rejected</h3>
                <p>{summary.rejected}</p>
            </div>

            <div className="stat-card">
                <h3>Total Skills</h3>
                <p>{skills.length}</p>
            </div>

        </div>
    </div>
)}

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
            {summary && (
    <div>
        <h2>Application Summary</h2>

        <p>
            <strong>Total Applications:</strong>{" "}
            {summary.totalApplications}
        </p>

        <p>
            <strong>Applied:</strong>{" "}
            {summary.applied}
        </p>

        <p>
            <strong>Shortlisted:</strong>{" "}
            {summary.shortlisted}
        </p>

        <p>
            <strong>Interview:</strong>{" "}
            {summary.interview}
        </p>

        <p>
            <strong>Selected:</strong>{" "}
            {summary.selected}
        </p>

        <p>
            <strong>Rejected:</strong>{" "}
            {summary.rejected}
        </p>
    </div>
)}
{skills.length > 0 && (
    <div>
        <h2>My Skills</h2>

        {skills.map((studentSkill) => (
            <div key={studentSkill.id}>
                <p>
                    <strong>
                        {studentSkill.skill?.name ||
                            "Skill"}
                    </strong>
                    :{" "}
                    {studentSkill.skillLevel}%
                </p>
            </div>
        ))}
    </div>
)}

{notifications.length > 0 && (
    <div>
        <h2>Recent Notifications</h2>

        {notifications
            .slice(0, 3)
            .map((notification) => (
                <div key={notification.id}>
                    <h3>
                        {notification.title}
                    </h3>

                    <p>
                        {notification.message}
                    </p>

                    <p>
                        <strong>Status:</strong>{" "}
                        {notification.read
                            ? "Read"
                            : "Unread"}
                    </p>

                    <p>
                        <strong>Date:</strong>{" "}
                        {notification.createdAt
                            ? new Date(
                                  notification.createdAt
                              ).toLocaleString()
                            : "N/A"}
                    </p>

                    <hr />
                </div>
            ))}
    </div>
)}

            <button onClick={logout}>
                Logout
            </button>
        </div>
    );
}

export default Dashboard;