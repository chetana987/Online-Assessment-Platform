# 🚀 Online Assessment Platform

A full-stack web application designed to conduct and manage coding assessments efficiently. This platform allows admins to create tests, evaluate student submissions automatically, and monitor performance in real time.

---

## 📌 Features

* 🔐 **Access Code-Based Test Entry**
* 💻 **Real-Time Coding Interface**
* ⚡ **Auto Code Compilation & Execution**
* 🧪 **Auto-Grading with Test Cases (Hidden + Visible)**
* ⏱️ **Timer-Based Exams**
* 📊 **Instant Result Generation**
* 👨‍💼 **Admin Dashboard for Test Management**

---

## 🛠️ Tech Stack

**Backend:**

* Java
* Spring Boot
* Spring Security (JWT Authentication)

**Frontend:**

* HTML
* CSS
* JavaScript

**Database:**

* MySQL

---

## 🏗️ Architecture

* Layered Architecture (Controller → Service → Repository)
* RESTful APIs for communication between frontend and backend
* Secure authentication and role-based access control

---

## 📂 Project Structure

```
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── dto/
 ├── config/
 └── resources/
```

---

## ⚙️ How to Run the Project

1. Clone the repository:

```bash
git clone https://github.com/chetana987/Online-Assessment-Platform.git
```

2. Navigate to project folder:

```bash
cd Online-Assessment-Platform
```

3. Configure MySQL in `application.yml`

4. Run the application:

```bash
mvn spring-boot:run
```

5. Open browser:

```
http://localhost:8080
```

---

## 🔑 Key Highlights

* Implemented **auto-evaluation engine** for coding problems
* Designed **secure exam environment with timer & access control**
* Built scalable backend using **Spring Boot REST APIs**
* Structured project using **clean architecture principles**


