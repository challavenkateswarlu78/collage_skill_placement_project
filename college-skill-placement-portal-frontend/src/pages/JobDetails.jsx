import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../services/api";
import Navbar from "../components/Navbar";

function JobDetails() {
    const { id } = useParams();

    const [job, setJob] = useState(null);
    const [student, setStudent] = useState(null);

    const [loading, setLoading] = useState(true);
    const [applying, setApplying] = useState(false);

    const [error, setError] = useState("");
    const [message, setMessage] = useState("");
    const [alreadyApplied, setAlreadyApplied] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
    try {
        const [jobResponse, studentResponse] =
            await Promise.all([
                api.get(`/api/jobs/${id}`),
                api.get("/api/student/me"),
            ]);

        const jobData = jobResponse.data;
        const studentData = studentResponse.data;

        setJob(jobData);
        setStudent(studentData);

        const applicationsResponse =
            await api.get(
                `/api/job-applications/student/${studentData.id}`
            );

        const applications =
            applicationsResponse.data;

        const hasApplied = applications.some(
            (application) =>
                application.job?.id === jobData.id
        );

        setAlreadyApplied(hasApplied);
    } catch (err) {
        console.error(
            "Job details error:",
            err
        );

        setError(
            err.response?.data?.message ||
            "Failed to load job details."
        );
    } finally {
        setLoading(false);
    }
};

        fetchData();
    }, [id]);

    const handleApply = async () => {
        if (!student) {
            return;
        }

        setApplying(true);
        setError("");
        setMessage("");

        try {
            const response = await api.post(
                `/api/job-applications/student/${student.id}/job/${id}`
            );

            console.log(
                "Application response:",
                response.data
            );

            setMessage(
                "Application submitted successfully."
            );
            setAlreadyApplied(true);
        } catch (err) {
            console.error(
                "Application error:",
                err
            );

            setError(
                err.response?.data?.message ||
                "Failed to apply for this job."
            );
        } finally {
            setApplying(false);
        }
    };

    if (loading) {
        return <h2>Loading job details...</h2>;
    }

    if (error && !job) {
        return <p>{error}</p>;
    }

    if (!job) {
        return <p>Job not found.</p>;
    }

    return (
        <div>
            <h1>{job.title}</h1>

            <Navbar />

           <div className="job-details-card">

    <div className="job-info-grid">

        <div className="job-info-item">
            <span>Company</span>
            <strong>{job.company}</strong>
        </div>

        <div className="job-info-item">
            <span>Location</span>
            <strong>{job.location}</strong>
        </div>

        <div className="job-info-item">
            <span>Job Type</span>
            <strong>{job.jobType}</strong>
        </div>

        <div className="job-info-item">
            <span>Minimum CGPA</span>
            <strong>{job.minCgpa}</strong>
        </div>

        <div className="job-info-item">
            <span>Status</span>
            <strong>
                {job.active ? "Active" : "Inactive"}
            </strong>
        </div>

    </div>

    <div className="job-description">
        <h2>Description</h2>

        <p>{job.description}</p>
    </div>

    <div className="job-skills">
        <h2>Required Skills</h2>

        <p>{job.requiredSkills}</p>
    </div>

</div>

{message && (
    <p className="success-message">
        <strong>{message}</strong>
    </p>
)}


{error && (
     <p className="error-message">
        <strong>{error}</strong>
    </p>
)}

{job.active && student && (
    <div className="application-status-card">
        <h2>Application Status</h2>

        {alreadyApplied ? (
            <p className="application-applied">
                You have already applied for this job.
            </p>
        ) : (
            <p className="application-not-applied">
                You have not applied for this job yet.
            </p>
        )}
    </div>
)}

        {job.active && student && (
    alreadyApplied ? (
        <button
            className="already-applied-button"
            disabled
        >
            Already Applied
        </button>
    ) : (
        <button
            className="apply-job-button"
            onClick={handleApply}
            disabled={applying}
        >
            {applying
                ? "Applying..."
                : "Apply for Job"}
        </button>
    )
)}

            <Link
    className="back-to-jobs"
    to="/jobs"
>
    ← Back to Jobs
</Link>
        </div>
    );
}

export default JobDetails;