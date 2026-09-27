import { useEffect, useState } from "react";
import api from "../services/api";

function Skills() {
    const [student, setStudent] = useState(null);
    const [skills, setSkills] = useState([]);
    const [skillGaps, setSkillGaps] = useState([]);
    const [weakSkills, setWeakSkills] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

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

    if (loading) {
        return <h2>Loading skills...</h2>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h1>My Skills</h1>

            {student && (
                <p>
                    Student: {student.name}
                </p>
            )}

            <h2>Current Skills</h2>

            {skills.length === 0 ? (
                <p>No skills found.</p>
            ) : (
                <ul>
                    {skills.map((studentSkill) => (
                        <li key={studentSkill.id}>
                            Skill:{" "}
                            {studentSkill.skill?.name ||
                                "Unknown"}
                            {" — "}
                            Level:{" "}
                            {studentSkill.skillLevel}
                        </li>
                    ))}
                </ul>
            )}

            <h2>Skill Gap</h2>

            {skillGaps.length === 0 ? (
                <p>No skill gaps found.</p>
            ) : (
                <ul>
                    {skillGaps.map((gap, index) => (
                        <li key={gap.id || index}>
                            {JSON.stringify(gap)}
                        </li>
                    ))}
                </ul>
            )}

            <h2>Weak Skills</h2>

            {weakSkills.length === 0 ? (
                <p>No weak skills found.</p>
            ) : (
                <ul>
                    {weakSkills.map((skill, index) => (
                        <li key={skill.id || index}>
                            {JSON.stringify(skill)}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default Skills;