import { useEffect, useState } from "react";
import api from "../services/api";
import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
function Applications() {
    const [applications, setApplications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [summary, setSummary] = useState(null);
    const [selectedStatus, setSelectedStatus] = useState("ALL");

    const fetchApplicationsByStatus = async (
    studentId,
    status
) => {
    try {
        setLoading(true);
        setError("");

        if (status === "ALL") {
            const response = await api.get(
                `/api/job-applications/student/${studentId}`
            );

            setApplications(response.data);
        } else {
            const response = await api.get(
                `/api/job-applications/student/${studentId}/status/${status}`
            );

            setApplications(response.data);
        }
    } catch (err) {
        console.error(
            "Applications filter error:",
            err
        );

        setError(
            err.response?.data?.message ||
            "Failed to load applications."
        );
    } finally {
        setLoading(false);
    }
};

    useEffect(() => {
        const fetchApplications = async () => {
            try {
                const studentResponse =
                    await api.get("/api/student/me");

                const studentId =
                    studentResponse.data.id;

                const summaryResponse =
                    await api.get(
                `/api/job-applications/student/${studentId}/summary`
                );

                setSummary(summaryResponse.data);    

                await fetchApplicationsByStatus(
    studentId,
    selectedStatus
);
            } catch (err) {
                console.error(
                    "Applications fetch error:",
                    err
                );

                setError(
                    err.response?.data?.message ||
                    "Failed to load applications."
                );
            } finally {
                setLoading(false);
            }
        };

        fetchApplications();
    }, [selectedStatus]);

    if (loading) {
        return <h2>Loading applications...</h2>;
    }

    return (
    <div>
        <h1>My Applications</h1>

        <Navbar />

        <h2>Filter Applications</h2>

<div className="application-filter-card">
    <h2>Filter Applications</h2>

    <select
        value={selectedStatus}
        onChange={(e) =>
            setSelectedStatus(e.target.value)
        }
    >
        <option value="ALL">All</option>
        <option value="APPLIED">Applied</option>
        <option value="SHORTLISTED">Shortlisted</option>
        <option value="INTERVIEW">Interview</option>
        <option value="SELECTED">Selected</option>
        <option value="REJECTED">Rejected</option>
    </select>
</div>

        {summary && (
    <div className="application-summary-section">
        <h2>Application Summary</h2>

        <div className="application-summary-grid">

            <div className="application-summary-card">
                <span>Total Applications</span>
                <strong>
                    {summary.totalApplications}
                </strong>
            </div>

            <div className="application-summary-card">
                <span>Applied</span>
                <strong>
                    {summary.applied}
                </strong>
            </div>

            <div className="application-summary-card">
                <span>Shortlisted</span>
                <strong>
                    {summary.shortlisted}
                </strong>
            </div>

            <div className="application-summary-card">
                <span>Interview</span>
                <strong>
                    {summary.interview}
                </strong>
            </div>

            <div className="application-summary-card">
                <span>Selected</span>
                <strong>
                    {summary.selected}
                </strong>
            </div>

            <div className="application-summary-card">
                <span>Rejected</span>
                <strong>
                    {summary.rejected}
                </strong>
            </div>

        </div>
    </div>
)}

        {error && (
            <p>{error}</p>
        )}


            {!error && applications.length === 0 && (
                <p>
                    You have not applied for any jobs yet.
                </p>
            )}

            <div className="applications-grid">
    {applications.map((application) => (
        <div
            className="application-card"
            key={application.id}
        >
            <h2>
                {application.job?.title ||
                    "Job"}
            </h2>

            <div className="application-info">

                <p>
                    <span>Company</span>
                    <strong>
                        {application.job?.company ||
                            "N/A"}
                    </strong>
                </p>

                <p>
                    <span>Location</span>
                    <strong>
                        {application.job?.location ||
                            "N/A"}
                    </strong>
                </p>

                <p>
                    <span>Status</span>
                    <strong
                        className={`application-status status-${application.status?.toLowerCase()}`}
                    >
                        {application.status}
                    </strong>
                </p>

                <p>
                    <span>Applied At</span>
                    <strong>
                        {application.appliedAt
                            ? new Date(
                                  application.appliedAt
                              ).toLocaleString()
                            : "N/A"}
                    </strong>
                </p>

                <p>
                    <span>Match Percentage</span>
                    <strong>
                        {application.matchPercentageAtApplication}%
                    </strong>
                </p>

            </div>

            <Link
                className="view-application-link"
                to={`/applications/${application.id}`}
            >
                View Application Details →
            </Link>
        </div>
    ))}
</div>
        </div>
    );
}

export default Applications;