$projectDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$serverJar = "D:\Server-Test\paper.jar"

# Copying while Paper is running can leave the old plugin loaded (or lock the JAR).
# Stop it with /stop first so worlds are saved cleanly, then run this script.
$runningServer = Get-CimInstance Win32_Process -Filter "Name = 'java.exe'" |
    Where-Object { $_.CommandLine -like "*$serverJar*" }

if ($runningServer) {
    Write-Host "Paper is still running. In its console, run: stop" -ForegroundColor Yellow
    exit 1
}

& (Join-Path $projectDir "gradlew.bat") deployAndRunPaper
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}
