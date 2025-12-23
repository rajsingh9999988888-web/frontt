@echo off
REM Automatic File Copy Script
REM Ye file run karo - automatically files XAMPP htdocs mein copy ho jayengi

echo ========================================
echo PHP Backend Files Copy Script
echo ========================================
echo.

REM Source folder
set "SOURCE=%~dp0"
set "TARGET=C:\xampp\htdocs\baby-adoption-backend-php"

echo Source: %SOURCE%
echo Target: %TARGET%
echo.

REM Check if XAMPP exists
if not exist "C:\xampp\htdocs" (
    echo ERROR: XAMPP htdocs folder nahi mila!
    echo Please install XAMPP first.
    pause
    exit /b 1
)

echo Copying files...
echo.

REM Create target directory
if not exist "%TARGET%" mkdir "%TARGET%"

REM Copy all files
xcopy /E /I /Y "%SOURCE%*" "%TARGET%\"

REM Create uploads folder
if not exist "%TARGET%\uploads" mkdir "%TARGET%\uploads"

echo.
echo ========================================
echo Files copied successfully!
echo ========================================
echo.
echo Next steps:
echo 1. Browser mein jao: http://localhost/baby-adoption-backend-php/auto-setup-tables.php
echo 2. Phir test karo: http://localhost/baby-adoption-backend-php/test-setup.php
echo.
pause

