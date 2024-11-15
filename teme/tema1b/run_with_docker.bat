@echo off

:: Start Docker Compose and build containers
docker-compose up -d --build

:: Check if the previous command succeeded
if %ERRORLEVEL% NEQ 0 (
    echo Nu s-a putut crea/porni containerul Docker
    docker-compose down
    cd ..
    exit /b %ERRORLEVEL%
)

:: Execute the command inside the Docker container
docker exec -w /apd/checker -it apd_container /apd/checker/checker.sh

:: Stop and clean up Docker containers
docker-compose down
cd ..