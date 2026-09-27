import { useEffect, useState } from "react";
import api from "../services/api";
import { Link } from "react-router-dom";

function Applications() {
    const [applications, setApplications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [summary, setSummary] = useState(null);

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

                const response = await api.get(
                    `/api/job-applications/student/${studentId}`
                );

                setApplications(response.data);
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
    }, []);

    if (loading) {
        return <h2>Loading applications...</h2>;
    }

    return (
    <div>
        <h1>My Applications</h1>

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

                <hr />
            </div>
        )}

        {error && (
            <p>{error}</p>
        )}

            {error && (
                <p>{error}</p>
            )}

            {!error && applications.length === 0 && (
                <p>
                    You have not applied for any jobs yet.
                </p>
            )}

            {applications.map((application) => (
                <div key={application.id}>
                    <h2>
                        {application.job?.title ||
                            "Job"}
                    </h2>

                    <p>
                        <strong>Company:</strong>{" "}
                        {application.job?.company ||
                            "N/A"}
                    </p>

                    <p>
                        <strong>Location:</strong>{" "}
                        {application.job?.location ||
                            "N/A"}
                    </p>

                    <p>
                        <strong>Status:</strong>{" "}
                        {application.status}
                    </p>

                    <p>
                        <strong>Applied At:</strong>{" "}
                        {application.appliedAt
                            ? new Date(
                                  application.appliedAt
                              ).toLocaleString()
                            : "N/A"}
                    </p>

                    <p>
                        <strong>Match Percentage:</strong>{" "}
                        {application.matchPercentageAtApplication}%
                    </p>

                    <p>
                <Link
                    to={`/applications/${application.id}`}
                     >
                     View Application Details
                </Link>
                    </p>

                    <hr />
                </div>
            ))}
        </div>
    );
}

export default Applications;