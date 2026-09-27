import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../services/api";
import Navbar from "../components/Navbar";

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

            <Navbar />


           <div className="application-details-card">

    <h2>
        {application.job?.title ||
            "Job"}
    </h2>

    <div className="application-details-grid">

        <div className="application-detail-item">
            <span>Company</span>
            <strong>
                {application.job?.company ||
                    "N/A"}
            </strong>
        </div>

        <div className="application-detail-item">
            <span>Location</span>
            <strong>
                {application.job?.location ||
                    "N/A"}
            </strong>
        </div>

        <div className="application-detail-item">
            <span>Status</span>
            <strong className="application-current-status">
                {application.status}
            </strong>
        </div>

        <div className="application-detail-item">
            <span>Applied At</span>
            <strong>
                {application.appliedAt
                    ? new Date(
                          application.appliedAt
                      ).toLocaleString()
                    : "N/A"}
            </strong>
        </div>

        <div className="application-detail-item">
            <span>Match Percentage</span>
            <strong>
                {application.matchPercentageAtApplication}%
            </strong>
        </div>

    </div>

</div>

            <div className="status-history-section">

    <h2>
        Application Status History
    </h2>

    {history.length === 0 ? (
        <div className="no-history-card">
            <p>
                No status history available.
            </p>
        </div>
    ) : (
        <div className="status-timeline">

            {history.map((item, index) => (
                <div
                    className="timeline-item"
                    key={item.id}
                >

                    <div className="timeline-number">
                        {index + 1}
                    </div>

                    <div className="timeline-content">

                        <h3>
                            {item.status}
                        </h3>

                        <p>
                            <span>Date</span>

                            <strong>
                                {item.changedAt
                                    ? new Date(
                                          item.changedAt
                                      ).toLocaleString()
                                    : "N/A"}
                            </strong>
                        </p>

                    </div>

                </div>
            ))}

        </div>
    )}

</div>
            

           <Link
    className="back-to-applications"
    to="/applications"
>
    ← Back to My Applications
</Link>
            
        </div>
    );
}

export default ApplicationDetails;