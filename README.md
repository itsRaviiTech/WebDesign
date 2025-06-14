# 📘 Online Quiz System

## 📝 Project Overview

The Online Quiz System is a web-based application designed for the education industry, specifically targeting schools, universities, and online learning platforms. The goal of this system is to streamline the process of conducting quizzes, tracking student performance, and managing question banks in a centralized, accessible platform.

In traditional classroom or e-learning environments, teachers often face challenges in preparing, distributing, and grading quizzes manually or through non-integrated tools like printed forms or spreadsheets. Similarly, students may struggle to access quizzes on time or receive timely feedback on their performance.

This application addresses these problems by providing a dynamic quiz management solution with role-based access for teachers and students. Teachers can create and manage quizzes, assign them to students, and monitor individual performance, while students can access available quizzes, submit their answers, and instantly view their results.

The system enhances educational efficiency, reduces administrative workload, and supports data-driven decision-making in learning environments.

---

## 👥 Group Members & Responsibilities

| Name                   | Matric ID | Responsibility                             |
|------------------------|-----------|---------------------------------------------|
| Ravishanker Bahalu     | S70126    | User Authentication, Results & Reporting    |
| Jaya Selan A/L Balan   | S71298    | Teacher-Side: Quiz Management               |
| Kirtie A/P Jayabalan   | S72411    | Student-Side: Quiz Participation            |

---

## 🧪 How to Run the Project Locally

### 🖥️ Prerequisites

- Java JDK 8 (or compatible)
- Apache Tomcat 10.x
- XAMPP with MySQL running
- IDE that supports JSP/Servlets (e.g., NetBeans or IntelliJ)

---

### 📦 Setup Instructions

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/itsRaviiTech/WebDesign.git
   ```

2. **Start MySQL using XAMPP**
   - Launch XAMPP Control Panel.
   - Start **Apache** and **MySQL** modules.

3. **Create Database & Import SQL File**
   - Open [http://localhost/phpmyadmin](http://localhost/phpmyadmin)
   - Create a new database, e.g., `quizsystem`
   - Import the `quiz_db.sql` file located in the `/database/` folder of the project.

4. **Configure Database Connection in Code**
   - Open your `DBConnection.java` and check for the following connection string at line 18 - 21:
     ```java
     String jdbcURL = "jdbc:mysql://localhost:3306/quizsystem";
     String dbUser = "root";
     String dbPassword = ""; // default password if none set
     ```

5. **Deploy Project to Tomcat**
   - Open your IDE and import the project.
   - Set up Apache Tomcat as the server.
   - [IMPORTANT] Make sure in the project property that it is using **JDK 8**
   - Clean and build the project.
   - Run the project. Access it at:  
     [http://localhost:8080/quizSystem](http://localhost:8080/quizSystem)

---

### 🔐 Sample Credentials

| Role    | Username | Password |
|---------|----------|----------|
| Teacher | teacher1@example.com | 1234     |
| Student | student1@example.com | 1234     |

---

## 📎 Notes

- This system is designed to run **locally only** using XAMPP and Tomcat.
- No online deployment is provided in this version.
