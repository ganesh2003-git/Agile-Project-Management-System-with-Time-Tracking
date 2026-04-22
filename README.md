<div align="center">

# 🚀 APMST
## Agile Project Management System with Time Tracking

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com)
[![GitHub API](https://img.shields.io/badge/GitHub-API-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com)
[![Chart.js](https://img.shields.io/badge/Chart.js-Latest-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white)](https://www.chartjs.org)

**A comprehensive full-stack web application for managing agile development teams efficiently.**

*Built with Spring Boot 3 | Java 21 | MySQL 8 | Bootstrap 5 | GitHub API*

[🚀 Features](#-features) • [🛠️ Tech Stack](#-tech-stack) • [⚙️ Installation](#-installation) • [📸 Modules](#-modules) • [💼 Interview Q&A](#-interview-qa) • [👨‍💻 Developer](#-developer)

---

</div>

## 📖 About The Project

**APMST** (Agile Project Management System with Time Tracking) is a **production-ready full-stack web application** that helps companies manage their software development teams using Agile methodology.

> Think of it as **Jira + GitHub + Time Tracker + Leaderboard** — all in one system, built from scratch!

### 🎯 Problem It Solves

| Problem | Solution |
|---|---|
| Manager sends tasks on WhatsApp — gets lost | Centralized task management with notifications |
| Nobody knows who is doing what | Kanban board shows real-time status |
| No idea how many hours were spent | Start/Stop timer tracks exact hours per task |
| Code not properly saved or versioned | Auto-push to GitHub when manager approves |
| Employees not motivated | Points & Leaderboard gamification system |
| No performance data for management | Analytics dashboard with live Chart.js charts |
| Opening IDE takes extra time | Start button opens VS Code automatically! |

---

## ✨ Features

<table>
<tr>
<td>

### 🔐 Authentication & Security
- BCrypt password encryption
- Role-based access control
- Auto-creates Admin on startup
- Spring Security 6 integration
- Custom login/logout pages

</td>
<td>

### 📁 Project Management
- Create projects with timelines
- Add/remove team members
- Track project status
- Project-wise analytics
- Set local project path

</td>
</tr>
<tr>
<td>

### 🏃 Sprint Management
- Plan sprints with goals & dates
- Track sprint status
- Sprint-wise task organization
- Sprint completion tracking
- Update status in real-time

</td>
<td>

### ✅ Task Management
- Create with priority & story points
- Attach PDF/DOC documents
- 5 status workflow levels
- Due date & estimated hours
- Manager assigns to developer

</td>
</tr>
<tr>
<td>

### 📋 Kanban Board
- Visual drag & drop board
- 5 columns: Backlog → Done
- Real-time REST API updates
- Per-project & sprint views
- Color-coded priority badges

</td>
<td>

### ⏱️ Time Tracking
- Start/Stop timer per task
- Manual time log entry
- Actual vs estimated comparison
- Daily/weekly time entries
- Project & user wise reports

</td>
</tr>
<tr>
<td>

### 🐙 GitHub Integration
- Manager approves → auto GitHub push
- Task summary as .md file
- GitHub commit link on task
- VS Code deep linking on Start
- Secure token configuration

</td>
<td>

### 🏆 Gamification
- +10 points: task done on time
- -5 points: missed deadline
- Monthly scorecard per employee
- Gold/Silver/Bronze leaderboard
- All-time points tracking

</td>
</tr>
<tr>
<td>

### 🔔 Notifications
- Task assigned notification
- Manager review notification
- Task approved/rejected alert
- Unread count in sidebar bell
- Mark all as read feature

</td>
<td>

### 📈 Analytics Dashboard
- Task status doughnut chart
- Hours per employee bar chart
- Hours per project chart
- Completion % progress bars
- Per-project analytics

</td>
</tr>
</table>

---

## 🛠️ Tech Stack

### Backend
| Technology | Version | Purpose |
|---|---|---|
| Spring Boot | 3.0+ | Web application framework |
| Java | 21 | Programming language |
| Spring Security | 6.0+ | Authentication & authorization |
| Spring Data JPA | 6.0+ | Object-relational mapping |
| Hibernate | 6.0+ | ORM implementation |
| Maven | 3.6+ | Dependency management |

### Database
| Technology | Version | Purpose |
|---|---|---|
| MySQL | 8.0+ | Relational database |
| HikariCP | 5.0+ | Connection pooling |
| MySQL Connector/J | 8.0+ | JDBC driver |

### Frontend
| Technology | Version | Purpose |
|---|---|---|
| Thymeleaf | Latest | Server-side HTML templates |
| Bootstrap | 5.3 | Responsive UI framework |
| Chart.js | Latest | Data visualization charts |
| JavaScript | ES6+ | Client-side interactions |
| HTML5 Drag & Drop API | - | Kanban board functionality |

### External Integrations
| Technology | Purpose |
|---|---|
| GitHub REST API v3 | Auto-push code on task approval |
| VS Code Deep Link | Open IDE from browser |
| BCrypt | Password encryption |

---

## 📋 System Requirements

### Hardware
| Component | Minimum | Recommended |
|---|---|---|
| Processor | Intel Core i3 | Intel Core i5 / Ryzen 5+ |
| RAM | 4 GB | 8 GB |
| Storage | 500 MB | 2 GB |
| Internet | 1 Mbps | 5 Mbps |

### Software
| Requirement | Version |
|---|---|
| Operating System | Windows 10/11 |
| JDK | Java 21 or higher |
| MySQL Server | 8.0 or higher |
| IDE | Spring Tool Suite 4.x |
| Web Browser | Chrome 90+, Firefox 88+, Edge 90+ |
| Git | Latest |

---

## ⚙️ Installation

### Step 1 — Clone the Repository
```bash
git clone https://github.com/ganesh2003-git/Agile-Project-Management-System-with-Time-Tracking.git
cd Agile-Project-Management-System-with-Time-Tracking
```

### Step 2 — Create MySQL Database
```sql
CREATE DATABASE apmst;
```

### Step 3 — Configure Application Properties
Open `src/main/resources/application.properties` and replace placeholder values:

```properties
# ── Database Configuration ──────────────────────────────
spring.datasource.url=jdbc:mysql://localhost:3306/apmst
spring.datasource.username=root
spring.datasource.password=ENTER_YOUR_MYSQL_PASSWORD

# ── JPA Configuration ────────────────────────────────────
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# ── Application Name ─────────────────────────────────────
spring.application.name=apmst

# ── File Upload ──────────────────────────────────────────
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# ── GitHub Integration ───────────────────────────────────
github.token=ENTER_YOUR_GITHUB_TOKEN
github.username=ENTER_YOUR_GITHUB_USERNAME
github.repo=ENTER_YOUR_GITHUB_REPO_NAME
```

### Step 4 — Protect Your Credentials
```bash
git update-index --assume-unchanged src/main/resources/application.properties
```

### Step 5 — Run the Project

**Option A — Using Spring Tool Suite (STS):**
```
Right click on project → Run As → Spring Boot App
```

**Option B — Using Maven:**
```bash
mvn spring-boot:run
```

**Option C — Using JAR:**
```bash
mvn clean install
java -jar target/apmst.jar
```

### Step 6 — Open in Browser
```
http://localhost:8080/login
```

---

## 🔑 Default Login Credentials

| Role | Email | Password |
|---|---|---|
| 👑 Admin | admin@apmst.com | admin123 |

> ⚠️ **Security Note:** Change the default password immediately after first login!

---

## 👥 User Roles & Access

| Feature | Admin | Manager | Developer |
|---|---|---|---|
| User Management | ✅ Full | ❌ | ❌ |
| Create Project | ✅ | ✅ | ❌ |
| Create Sprint | ✅ | ✅ | ❌ |
| Assign Tasks | ✅ | ✅ | ❌ |
| View Tasks | ✅ All | ✅ All | ✅ Own Only |
| Start Timer | ✅ | ✅ | ✅ |
| Log Time | ✅ | ✅ | ✅ |
| Approve Tasks | ✅ | ✅ | ❌ |
| Push to GitHub | ✅ | ✅ | ❌ |
| View Analytics | ✅ Full | ✅ Full | ✅ Limited |
| View Leaderboard | ✅ | ✅ | ✅ |

---

## 🔄 Complete Task Workflow

```
STEP 1:  Admin creates user accounts (Manager, Developer)
             ↓
STEP 2:  Manager creates a Project
             ↓
STEP 3:  Manager creates Sprint inside Project
             ↓
STEP 4:  Manager creates Task, attaches document, assigns to Developer
             ↓
STEP 5:  Developer gets notification on dashboard 🔔
             ↓
STEP 6:  Developer reads task + attached document
             ↓
STEP 7:  Developer clicks START → VS Code opens automatically! 💻
             ↓
STEP 8:  Developer does coding work
             ↓
STEP 9:  Developer marks task as DONE
             ↓
STEP 10: Status automatically goes to IN_REVIEW
             ↓
STEP 11: Manager gets notification to review 🔔
             ↓
STEP 12: Manager reviews → clicks APPROVE ✅
             ↓
STEP 13: Task summary automatically pushed to GitHub! 🐙
             ↓
STEP 14: Developer gets +10 points on Leaderboard! 🏆
             ↓
STEP 15: Analytics dashboard updates in real-time 📈
```

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/apmst/
│   │   ├── config/                    ← Security & configuration
│   │   │   ├── SecurityConfig.java
│   │   │   ├── CustomUserDetailsService.java
│   │   │   └── DataInitializer.java
│   │   │
│   │   ├── controller/                ← HTTP request handlers
│   │   │   ├── AuthController.java
│   │   │   ├── AdminController.java
│   │   │   ├── ProjectController.java
│   │   │   ├── SprintController.java
│   │   │   ├── TaskController.java
│   │   │   ├── TimeLogController.java
│   │   │   ├── NotificationController.java
│   │   │   ├── ScoreController.java
│   │   │   └── AnalyticsController.java
│   │   │
│   │   ├── entity/                    ← Database table entities
│   │   │   ├── User.java
│   │   │   ├── Project.java
│   │   │   ├── Sprint.java
│   │   │   ├── Task.java
│   │   │   ├── TimeLog.java
│   │   │   ├── EmployeeScore.java
│   │   │   └── Notification.java
│   │   │
│   │   ├── repository/                ← Data access layer
│   │   ├── service/                   ← Business logic interfaces
│   │   ├── serviceIMPL/               ← Business logic implementations
│   │   ├── dto/                       ← Data transfer objects
│   │   └── enums/                     ← Fixed value types
│   │
│   └── resources/
│       ├── templates/                 ← Thymeleaf HTML pages
│       │   ├── admin/
│       │   ├── analytics/
│       │   ├── kanban/
│       │   ├── notifications/
│       │   ├── projects/
│       │   ├── score/
│       │   ├── sprints/
│       │   ├── tasks/
│       │   ├── timelog/
│       │   ├── dashboard.html
│       │   └── login.html
│       ├── static/                    ← CSS, JS, file uploads
│       └── application.properties     ← App configuration
│
└── test/                              ← Unit tests
```

---

## 📊 Database Design

### Entity Relationships
```
USER ──────────────────── M:N ──── PROJECT
 │                                    │
 │ 1:N                                │ 1:N
 │                                    │
TASK ◄──────────────────────────── SPRINT
 │
 ├── 1:N ──── TIMELOG
 │
 ├── 1:N ──── NOTIFICATION
 │
 └── 1:N ──── EMPLOYEE_SCORE
```

### Database Tables
| Table | Description |
|---|---|
| users | System users with roles & points |
| projects | Project information & timelines |
| project_members | M:N team member assignments |
| sprints | Sprints within projects |
| tasks | Task details, timer, GitHub URL |
| timelogs | Time entries logged per task |
| employee_scores | Monthly gamification points |
| notifications | System notifications & alerts |

---

## 🚀 GitHub Integration Setup

### Get Personal Access Token:
1. Go to GitHub → **Settings**
2. Click **Developer Settings**
3. Click **Personal Access Tokens → Tokens (classic)**
4. Click **Generate new token (classic)**
5. Select permissions: `repo` ✅ and `workflow` ✅
6. Copy token → add to `application.properties`

### How Auto-Push Works:
```
Manager clicks Approve
        ↓
GitHubServiceImpl creates task summary (.md file)
        ↓
Content encoded to Base64
        ↓
PUT request to GitHub REST API
        ↓
File pushed to: repo/tasks/task-{id}-completed.md
        ↓
GitHub URL saved to task
        ↓
Employee notified of approval 🔔
```

---

## 📅 Development Milestones

| # | Milestone | Status |
|---|---|---|
| 1 | Project Setup & Spring Security Login | ✅ Complete |
| 2 | User Management with Role Assignment | ✅ Complete |
| 3 | Project Management with Team Members | ✅ Complete |
| 4 | Sprint Planning and Management | ✅ Complete |
| 5 | Task Management with File Upload | ✅ Complete |
| 6 | Kanban Board with Drag & Drop | ✅ Complete |
| 7 | Time Tracking with Start/Stop Timer | ✅ Complete |
| 8 | Points System and Leaderboard | ✅ Complete |
| 9 | Real-time Notification System | ✅ Complete |
| 10 | GitHub Auto-Push Integration | ✅ Complete |
| 11 | Analytics Dashboard with Chart.js | ✅ Complete |
| 12 | Advanced UI Polish & Role-based Sidebar | ✅ Complete |

---

## 🌟 Why APMST Stands Out

| Feature | Most Student Projects | APMST |
|---|---|---|
| Authentication | Basic login | BCrypt + Role-based Spring Security |
| Task Management | Simple CRUD | Full agile workflow + file upload |
| Frontend | Static pages | Drag-drop Kanban + real-time updates |
| Analytics | None | Live Chart.js charts + progress bars |
| External API | None | GitHub REST API integration |
| Time Tracking | None | Start/Stop timer + manual logging |
| Motivation System | None | Points + monthly leaderboard |
| IDE Integration | None | VS Code opens automatically on Start |
| Notifications | None | Real-time bell with unread count |
| Code Management | None | Auto GitHub push on approval |

---

## 💼 Interview Q&A

<details>
<summary><b>Q1. Why did you choose Spring Boot?</b></summary>

Spring Boot provides enterprise-grade security with Spring Security, 
powerful ORM with JPA/Hibernate, and auto-configuration that reduces 
setup time. For a system requiring role-based access, database 
relationships, and REST APIs, Spring Boot is the industry standard 
used by companies like LinkedIn and Netflix.
</details>

<details>
<summary><b>Q2. How does Spring Security work in your project?</b></summary>

Spring Security intercepts every HTTP request. I configured 
SecurityFilterChain to allow public access to /login only and 
restrict /admin/** to ADMIN role. Passwords are encrypted with 
BCrypt before saving. A custom UserDetailsService loads users by 
email instead of username.
</details>

<details>
<summary><b>Q3. How does the GitHub integration work?</b></summary>

I used GitHub REST API with Spring's RestTemplate. When manager 
approves a task, GitHubServiceImpl makes a PUT request to 
api.github.com. The task summary is Base64 encoded and sent as 
file content. The response contains the GitHub URL saved to the task.
</details>

<details>
<summary><b>Q4. What design patterns did you use?</b></summary>

MVC Pattern, Repository Pattern, Service Layer Pattern, DTO Pattern, 
Singleton Pattern (Spring beans), and Strategy Pattern (different 
dashboard data based on user role).
</details>

<details>
<summary><b>Q5. What challenges did you face?</b></summary>

Three main challenges: (1) Thymeleaf inline JavaScript caused errors 
— fixed with hidden HTML spans. (2) Spring Security CSRF blocked 
Kanban REST API — fixed by ignoring CSRF for /tasks/api/**. 
(3) Lombok @Data conflicted — replaced with explicit annotations.
</details>

---

## 🔮 Future Enhancements

- [ ] Real-time chat using WebSockets
- [ ] Mobile application (Android/iOS) with React Native
- [ ] AI-based task assignment recommendations
- [ ] Slack & Microsoft Teams integration
- [ ] Proper burndown charts per sprint
- [ ] Multi-tenant architecture for multiple organizations
- [ ] Export reports as PDF/Excel
- [ ] Docker containerization
- [ ] CI/CD pipeline with GitHub Actions

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👨‍💻 Developer

<div align="center">

**Ganesh Bawaskar**

🎓 Master of Computer Applications (MCA)

[![GitHub](https://img.shields.io/badge/GitHub-ganesh2003--git-181717?style=for-the-badge&logo=github)](https://github.com/ganesh2003-git)

---

### ⭐ If you found this project helpful, please give it a star! ⭐

*Built with ❤️ using Spring Boot & Java*

</div>
