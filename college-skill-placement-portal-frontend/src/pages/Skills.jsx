import { useEffect, useState } from "react";
import api from "../services/api";
import Navbar from "../components/Navbar";

function Skills() {
    const [student, setStudent] = useState(null);
    const [skills, setSkills] = useState([]);
    const [skillGaps, setSkillGaps] = useState([]);
    const [weakSkills, setWeakSkills] = useState([]);

    const [loading, setLoading] = useState(true);
const [error, setError] = useState("");
const [skillId, setSkillId] = useState("");
const [skillLevel, setSkillLevel] = useState("");
const [saving, setSaving] = useState(false);
const [message, setMessage] = useState("");

    useEffect(() => {
        const fetchSkillsData = async () => {
            try {
                const studentResponse =
                    await api.get("/api/student/me");

                const studentData = studentResponse.data;

                setStudent(studentData);

                const studentId = studentData.id;

                const [
                    skillsResponse,
                    skillGapResponse,
                    weakSkillsResponse,
                ] = await Promise.all([
                    api.get(
                        `/api/students/${studentId}/skills`
                    ),
                    api.get(
                        `/api/skill-gap/student/${studentId}`
                    ),
                    api.get(
                        `/api/skill-gap/student/${studentId}/weak`
                    ),
                ]);

                setSkills(skillsResponse.data);
                setSkillGaps(skillGapResponse.data);
                setWeakSkills(weakSkillsResponse.data);
            } catch (err) {
                console.error(
                    "Skills fetch error:",
                    err
                );

                setError(
                    err.response?.data?.message ||
                    "Failed to load skills information."
                );
            } finally {
                setLoading(false);
            }
        };

        fetchSkillsData();
    }, []);

    const handleSaveSkill = async (e) => {
    e.preventDefault();

    if (!student) {
        return;
    }

    setSaving(true);
    setError("");
    setMessage("");

    try {
        const response = await api.post(
            `/api/students/${student.id}/skills`,
            null,
            {
                params: {
                    skillId: Number(skillId),
                    skillLevel: Number(skillLevel),
                },
            }
        );

        setMessage("Skill saved successfully.");

        setSkillId("");
        setSkillLevel("");

        setSkills((current) => {
            const existingIndex = current.findIndex(
                (item) =>
                    item.skill?.id ===
                    response.data.skill?.id
            );

            if (existingIndex !== -1) {
                const updated = [...current];
                updated[existingIndex] = response.data;
                return updated;
            }

            return [...current, response.data];
        });
    } catch (err) {
        console.error(
            "Save skill error:",
            err
        );

        setError(
            err.response?.data?.message ||
            "Failed to save skill."
        );
    } finally {
        setSaving(false);
    }
};

    if (loading) {
        return <h2>Loading skills...</h2>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h1>My Skills</h1>
            <Navbar />

            {message && (
    <p>{message}</p>
)}

<div className="skill-form-card">
    <h2>Add / Update Skill</h2>

    <form onSubmit={handleSaveSkill}>
        <div className="form-group">
            <label>Skill ID</label>

            <input
                type="number"
                value={skillId}
                onChange={(e) =>
                    setSkillId(e.target.value)
                }
                required
            />
        </div>

        <div className="form-group">
            <label>Skill Level</label>

            <input
                type="number"
                min="0"
                max="100"
                value={skillLevel}
                onChange={(e) =>
                    setSkillLevel(e.target.value)
                }
                required
            />
        </div>

        <button
            type="submit"
            disabled={saving}
        >
            {saving
                ? "Saving..."
                : "Save Skill"}
        </button>
    </form>
</div>

            {student && (
                <p>
                    Student: {student.name}
                </p>
            )}

            <h2>Current Skills</h2>

{skills.length === 0 ? (
    <p>No skills found.</p>
) : (
    <div className="skills-grid">
        {skills.map((studentSkill) => (
            <div
                className="skill-card"
                key={studentSkill.id}
            >
                <h3>
                    {studentSkill.skill?.name ||
                        "Unknown"}
                </h3>

                <p>
                    Level:{" "}
                    <strong>
                        {studentSkill.skillLevel}%
                    </strong>
                </p>

                <div className="skill-progress">
                    <div
                        className="skill-progress-bar"
                        style={{
                            width: `${studentSkill.skillLevel}%`,
                        }}
                    />
                </div>
            </div>
        ))}
    </div>
)}

            <h2>Weak Skills</h2>

{weakSkills.length === 0 ? (
    <p>No weak skills found.</p>
) : (
    <div className="weak-skills-grid">
        {weakSkills.map((skill) => (
            <div
                className="weak-skill-card"
                key={skill.skillId}
            >
                <h3>{skill.skillName}</h3>

                <p>
                    <strong>Current Level:</strong>{" "}
                    {skill.currentLevel}%
                </p>

                <p>
                    <strong>Status:</strong>{" "}
                    <span className="weak-status">
                        {skill.status}
                    </span>
                </p>

                <p>
                    <strong>Recommendation:</strong>
                </p>

                <p>
                    {skill.recommendation}
                </p>
            </div>
        ))}
    </div>
)}

<h2>Skill Gap</h2>

{skillGaps.length === 0 ? (
    <p>No skill gaps found.</p>
) : (
    <div className="skill-gap-grid">
        {skillGaps.map((gap) => (
            <div
                className="skill-gap-card"
                key={gap.skillId}
            >
                <h3>{gap.skillName}</h3>

                <p>
                    <strong>Current Level:</strong>{" "}
                    {gap.currentLevel}%
                </p>

                <p>
                    <strong>Status:</strong>{" "}
                    <span className="skill-status">
                        {gap.status}
                    </span>
                </p>

                <p>
                    <strong>Recommendation:</strong>
                </p>

                <p>
                    {gap.recommendation}
                </p>
            </div>
        ))}
    </div>
)}

        </div>
    );
}

export default Skills;