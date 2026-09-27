import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../services/api";

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

            <p>
                <strong>Company:</strong>{" "}
                {job.company}
            </p>

            <p>
                <strong>Location:</strong>{" "}
                {job.location}
            </p>

            <p>
                <strong>Job Type:</strong>{" "}
                {job.jobType}
            </p>

            <p>
                <strong>Description:</strong>{" "}
                {job.description}
            </p>

            <p>
                <strong>Required Skills:</strong>{" "}
                {job.requiredSkills}
            </p>

            <p>
                <strong>Minimum CGPA:</strong>{" "}
                {job.minCgpa}
            </p>

            <p>
                <strong>Active:</strong>{" "}
                {job.active ? "Yes" : "No"}
            </p>

            {message && (
                <p>{message}</p>
            )}

            {error && (
                <p>{error}</p>
            )}

            {job.active && student && (
    alreadyApplied ? (
        <button disabled>
            Already Applied
        </button>
    ) : (
        <button
            onClick={handleApply}
            disabled={applying}
        >
            {applying
                ? "Applying..."
                : "Apply for Job"}
        </button>
    )
)}

            <br />
            <br />

            <Link to="/jobs">
                Back to Jobs
            </Link>
        </div>
    );
}

export default JobDetails;