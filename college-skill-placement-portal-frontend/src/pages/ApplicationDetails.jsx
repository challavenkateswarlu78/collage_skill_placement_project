import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../services/api";

function ApplicationDetails() {
    
    const { id } = useParams();

    const [application, setApplication] = useState(null);
    const [history, setHistory] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    

    useEffect(() => {
        const fetchApplicationDetails = async () => {
            try {
                const applicationResponse =
                    await api.get(
                        `/api/job-applications/${id}`
                    );

                const historyResponse =
                    await api.get(
                        `/api/job-applications/${id}/history`
                    );

                setApplication(
                    applicationResponse.data
                );

                setHistory(
                    historyResponse.data
                );
            } catch (err) {
                console.error(
                    "Application details error:",
                    err
                );

                setError(
                    err.response?.data?.message ||
                    "Failed to load application details."
                );
            } finally {
                setLoading(false);
            }
        };

        fetchApplicationDetails();
    }, [id]);

    if (loading) {
        return (
            <h2>
                Loading application details...
            </h2>
        );
    }

    if (error) {
        return <p>{error}</p>;
    }

    if (!application) {
        return (
            <p>
                Application not found.
            </p>
        );
    }

    return (
        <div>
            <h1>Application Details</h1>

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

            <hr />

            <h2>
                Application Status History
            </h2>

            {history.length === 0 ? (
                <p>
                    No status history available.
                </p>
            ) : (
                <ul>
                    {history.map((item) => (
                        <li key={item.id}>
                            <strong>
                                {item.status}
                            </strong>

                            {" — "}

                            {item.changedAt
                                ? new Date(
                                      item.changedAt
                                  ).toLocaleString()
                                : "N/A"}
                        </li>
                    ))}
                </ul>
            )}
            

            <br />

            <Link to="/applications">
                Back to My Applications
            </Link>
            
        </div>
    );
}

export default ApplicationDetails;