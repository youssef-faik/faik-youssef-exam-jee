@echo off
cd C:\Users\youss\IdeaProjects\examen-jee\backend
call mvnw.cmd clean package
if %ERRORLEVEL% == 0 call mvnw.cmd spring-boot:run
pause
