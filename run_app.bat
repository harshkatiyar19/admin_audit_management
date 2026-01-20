@echo off
cd /d "%~dp0"
echo Starting Audit Monitoring Application...
call mvnw.cmd spring-boot:run
pause
