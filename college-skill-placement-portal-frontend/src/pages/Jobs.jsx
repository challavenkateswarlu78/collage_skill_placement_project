import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";
import Navbar from "../components/Navbar";

function Jobs() {
    const [jobs, setJobs] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [searchKeyword, setSearchKeyword] = useState("");
const [isSearching, setIsSearching] = useState(false);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const pageSize = 10;

    useEffect(() => {
    const fetchJobs = async () => {
        setLoading(true);
        setError("");

        try {
            if (isSearching && searchKeyword.trim()) {
                const response = await api.get(
                    `/api/jobs/search?keyword=${encodeURIComponent(
                        searchKeyword.trim()
                    )}`
                );

                setJobs(response.data || []);
                setTotalPages(0);
            } else {
                const response = await api.get(
                    `/api/jobs?page=${page}&size=${pageSize}`
                );

                setJobs(response.data.content || []);

                setTotalPages(
                    response.data.totalPages || 0
                );
            }
        } catch (err) {
            console.error(
                "Jobs fetch error:",
                err
            );

            setError(
                err.response?.data?.message ||
                "Failed to load jobs."
            );
        } finally {
            setLoading(false);
        }
    };

    fetchJobs();
}, [page, isSearching, searchKeyword]);

const handleSearch = (e) => {
    e.preventDefault();

    if (!searchKeyword.trim()) {
        setIsSearching(false);
        setPage(0);
        return;
    }

    setPage(0);
    setIsSearching(true);
};

const handleClearSearch = () => {
    setSearchKeyword("");
    setIsSearching(false);
    setPage(0);
};

    if (loading) {
        return <h2>Loading jobs...</h2>;
    }

    return (
        <div>
            <h1>Available Jobs</h1>

            <Navbar />

            <form className="job-search-form" onSubmit={handleSearch}>
    <input
        type="text"
        placeholder="Search jobs..."
        value={searchKeyword}
        onChange={(e) =>
            setSearchKeyword(e.target.value)
        }
    />

    <button type="submit">
        Search
    </button>

    {isSearching && (
        <button
            type="button"
            onClick={handleClearSearch}
        >
            Clear
        </button>
    )}
</form>

            {error && (
                <p>{error}</p>
            )}

            {jobs.length === 0 && !error && (
                <p>No jobs found.</p>
            )}

            <div className="jobs-grid">
    {jobs.map((job) => (
        <div
            className="job-card"
            key={job.id}
        >
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

            <Link
                className="job-details-link"
                to={`/jobs/${job.id}`}
            >
                View Job →
            </Link>
        </div>
    ))}
</div>

            {!isSearching && totalPages > 0 && (
                <div className="pagination">
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