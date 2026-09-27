# College Skill Placement Portal — Backend Documentation

## 1. Project Overview

| Item | Detail |
|---|---|
| Project Name | College Skill Placement Portal |
| Backend Language/Framework | Java + Spring Boot |
| Database | MySQL |
| Authentication | Spring Security + JWT (JSON Web Token) |
| API Documentation | Swagger / OpenAPI |
| Build Tool | Maven |
| Backend Status | ✅ Complete |

This document explains what the backend does, how its pieces fit together, and how to call its APIs. It's written so that any developer joining the project — even without prior context — can understand the system.

---

## 2. What the System Does

The portal connects **students**, **admins**, and **job opportunities** through a set of secure REST APIs. Students can manage their profiles and skills, browse and apply to jobs, take assessments, practice coding problems, and receive notifications. Admins get a separate, protected set of tools to manage the platform and view reports.

---

## 3. Main Modules

The backend is organized into these functional areas:

1. Authentication & JWT
2. Student Management
3. Skills Management
4. Skill-Gap Analysis
5. Jobs
6. Job Matching
7. Job Applications
8. Assessments
9. Questions (MCQ & Coding)
10. DSA Practice
11. Notifications
12. File Upload/Download
13. Search
14. Pagination
15. Admin Dashboard
16. Reports & Analytics
17. Exception Handling
18. Validation
19. API Documentation (Swagger)

---

## 4. System Architecture

**Request flow (how a request travels through the system):**

```
Frontend / Postman / Swagger
             ↓
        Controllers        (receive the request)
             ↓
          Services         (business logic)
             ↓
        Repositories       (data access layer)
             ↓
       JPA / Hibernate     (object-to-database mapping)
             ↓
           MySQL           (database)
```

**Security flow (how a request gets authorized):**

```
Login → JWT issued → Spring Security checks the token
       → Role / Ownership check → Access granted to Protected API
```

**High-level module map:**

```
                     COLLEGE SKILL PLACEMENT PORTAL
                                  │
          ┌───────────────────────┼───────────────────────┐
          │                       │                       │
       Student                 Admin              Authentication
          │                       │                       │
          ▼                       ▼                       ▼
   Skills, Jobs,            Dashboard,                  JWT
   Applications,            Reports,
   Assessments, DSA,        Management
   Notifications, Files
          │                       │
          └───────────┬───────────┘
                       ▼
                Spring Boot API
                       │
                       ▼
                Spring Data JPA
                       │
                       ▼
                     MySQL
```

---

## 5. API Endpoint Reference

### 🔐 Authentication
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/login` | Authenticate a user and receive a JWT token |

### 👨‍🎓 Students
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/students` | Create a student |
| GET | `/api/students` | List all students |
| GET | `/api/students/{id}` | Get one student by ID |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |
| GET | `/api/students/search?keyword=...` | Search students by keyword |

Pagination example: `GET /api/students?page=0&size=10`

### 💼 Jobs
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/jobs` | Create a job listing |
| GET | `/api/jobs` | List all jobs |
| GET | `/api/jobs/{id}` | Get one job by ID |
| PUT | `/api/jobs/{id}` | Update a job |
| DELETE | `/api/jobs/{id}` | Delete a job |
| GET | `/api/jobs/active` | List currently active jobs |
| GET | `/api/jobs/company/{company}` | List jobs by company |
| GET | `/api/jobs/location/{location}` | List jobs by location |
| GET | `/api/jobs/type/{jobType}` | List jobs by type |
| GET | `/api/jobs/search?keyword=...` | Search jobs by keyword |

Pagination example: `GET /api/jobs?page=0&size=10`

### 📄 Job Applications
The application module supports:
- Creating an application
- Viewing all applications
- Viewing a student's applications
- Viewing a single application
- Updating application status
- Withdrawing an application
- Searching applications
- Viewing application history
- Viewing an application summary

Pagination is available on the main application list.

### 🧠 Skills & Skill-Gap
Base paths: `/api/skills/...` and `/api/skill-gap/...`

Key endpoint:
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/skill-gap/student/{studentId}` | Get a student's skill-gap analysis |

### 📝 Assessments
Covers: Assessments, Questions, MCQ Questions, Coding Questions, Assessment Attempts, and Assessment Results.

Examples:
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/assessment-attempts/start` | Start an assessment attempt |
| GET | `/api/mcq-questions/question/{id}` | Get a specific MCQ question |

### 💻 DSA (Data Structures & Algorithms Practice)
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/dsa/problems/daily/today` | Get today's daily DSA problem |

*(Tested during regression testing.)*

### 🔔 Notifications
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/notifications/user/{userId}` | Get all notifications for a user |
| GET | `/api/notifications/user/{userId}/unread` | Get unread notifications |
| PUT | `/api/notifications/{notificationId}/read/{userId}` | Mark one notification as read |
| PUT | `/api/notifications/user/{userId}/read-all` | Mark all notifications as read |
| POST | `/api/notifications/admin` | Send an admin notification |

### 📁 Files
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/files/upload` | Upload a file |
| GET | `/api/files/user/{userId}` | List a user's files |
| GET | `/api/files/download/{fileId}` | Download a file |

### 👨‍💼 Admin
Protected APIs (require the ADMIN role) covering:
- Admin dashboard
- Admin search
- Management operations
- Reports
- Notifications

### 📚 Swagger / OpenAPI
- Documentation exposed at: `/v3/api-docs`
- Interactive testing available through the Swagger UI
- Swagger supports JWT authorization via the **Authorize** button

---

## 6. Database

**Database name:** `college_skill_portal`

**Key entities:**
User, Student, Skill, StudentSkill, Company, Job, JobApplication, Assessment, Question, MCQQuestion, CodingQuestion, AssessmentAttempt, Notification, FileMetadata

---

## 7. Security

Implemented security measures:
- JWT-based authentication
- Student / Admin role separation
- Protected APIs by role
- Student ownership checks (students can only access their own data)
- Swagger JWT authorization support
- Environment-based database password
- Environment-based JWT secret

Example configuration (values pulled from environment variables, not hardcoded):
```
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

---

## 8. Testing Completed

- ✅ Authentication testing
- ✅ Student integration testing
- ✅ Jobs testing
- ✅ Job application testing
- ✅ Assessment testing
- ✅ Admin testing
- ✅ Security testing
- ✅ Database review
- ✅ Performance review
- ✅ Logging review
- ✅ Configuration review
- ✅ Regression testing
- ✅ Swagger testing
- ✅ JWT authorization testing
- ✅ Project cleanup

---

## 9. Current Status

**Backend Development: COMPLETE ✅**

The backend includes: REST APIs, MySQL database, JPA/Hibernate, JWT security, role authorization, validation, exception handling, search, pagination, notifications, file management, assessments, DSA practice, job matching, Swagger documentation, and testing.

> **Note:** This documentation reflects only the features and endpoints actually implemented. Modules like Reports/Analytics and DSA should not be assumed to have more functionality than what is described above.

---

## 10. What's Next

With the backend complete, the next phase is **frontend development**, planned as follows:

1. React project setup
2. Frontend folder structure
3. Install required packages
4. Configure API connection to Spring Boot
5. Login page
6. JWT token handling
7. Protected routes
8. Student dashboard
9. Student profile
10. Skills & skill-gap page
11. Jobs page
12. Job details
13. Job application
14. My applications
15. Assessments
16. DSA practice
17. Notifications
18. File management
19. Admin dashboard
20. Admin management pages
21. Reports & analytics
22. Search & pagination UI
23. Loading/error handling
24. Responsive design
25. Frontend-backend integration testing
26. Final frontend cleanup
27. Complete project testing
