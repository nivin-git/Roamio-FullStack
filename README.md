# Roamio-style Full Stack Starter

A runnable starter for a travel-planning platform inspired by the publicly observable workflow of Roamio.

## Stack
- Backend: Java 25, Spring Boot 3.5, Spring Web, Spring Data JPA, Spring Security, JWT, PostgreSQL
- Frontend: React + Vite + TypeScript
- Database: PostgreSQL
- Dev: Docker Compose

## Requirements
- JDK 25+
- Maven 3.9+ (or use the Maven wrapper after adding one)
- Node.js 20+
- Docker Desktop (optional, recommended for PostgreSQL)

## 1. Start PostgreSQL
```bash
docker compose up -d postgres
```

## 2. Start backend
```bash
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080

## 3. Start frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend: http://localhost:5173

## Demo
The app includes registration/login, trip creation, destination browsing, a simple AI-style trip assistant, provider matching, and a traveler dashboard.

Set `OPENAI_API_KEY` if you want to replace the local demo assistant with a real AI provider. The starter deliberately works without an API key.

## Database
Default local settings:
- database: roamio
- user: roamio
- password: roamio

Change them in `backend/src/main/resources/application.properties`.

## VS Code
Open the extracted `roamio-fullstack` folder in VS Code. Install:
- Extension Pack for Java
- Spring Boot Extension Pack
- ESLint
- Prettier
