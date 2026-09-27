import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

function Jobs() {
    const [jobs, setJobs] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const pageSize = 10;

    useEffect(() => {
        const fetchJobs = async () => {
            setLoading(true);
            setError("");

            try {
                const response = await api.get(
                    `/api/jobs?page=${page}&size=${pageSize}`
                );

                setJobs(response.data.content || []);
                setTotalPages(
                    response.data.totalPages || 0
                );
            } catch (err) {
                console.error("Jobs fetch error:", err);

                setError(
                    err.response?.data?.message ||
                    "Failed to load jobs."
                );
            } finally {
                setLoading(false);
            }
        };

        fetchJobs();
    }, [page]);

    if (loading) {
        return <h2>Loading jobs...</h2>;
    }

    return (
        <div>
            <h1>Available Jobs</h1>

            {error && (
                <p>{error}</p>
            )}

            {jobs.length === 0 && !error && (
                <p>No jobs found.</p>
            )}

            {jobs.map((job) => (
                <div key={job.id}>
                    <h2>{job.title}</h2>

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

                    <Link to={`/jobs/${job.id}`}>
                        View Job
                    </Link>

                    <hr />
                </div>
            ))}

            {totalPages > 0 && (
                <div>
                    <button
                        disabled={page === 0}
                        onClick={() =>
                            setPage((current) => current - 1)
                        }
                    >
                        Previous
                    </button>

                    <span>
                        {" "}
                        Page {page + 1} of {totalPages}{" "}
                    </span>

                    <button
                        disabled={
                            page >= totalPages - 1
                        }
                        onClick={() =>
                            setPage((current) => current + 1)
                        }
                    >
                        Next
                    </button>
                </div>
            )}
        </div>
    );
}

export default Jobs;