Param()

if (-not $env:VERCEL_TOKEN) {
    Write-Error "Please set the environment variable VERCEL_TOKEN with your Vercel personal token."
    exit 1
}

if (-not $env:VERCEL_PROJECT_ID) {
    Write-Error "Please set the environment variable VERCEL_PROJECT_ID with your project id."
    exit 1
}

$body = @{ 
    key = 'VITE_API_BASE_URL'
    value = 'https://baby-adoption-backend.onrender.com'
    target = 'production'
    type = 'encrypted'
} | ConvertTo-Json

$headers = @{ 'Authorization' = "Bearer $($env:VERCEL_TOKEN)"; 'Content-Type' = 'application/json' }

$url = "https://api.vercel.com/v9/projects/$($env:VERCEL_PROJECT_ID)/env"

Write-Host "Creating Vercel env var VITE_API_BASE_URL for project $($env:VERCEL_PROJECT_ID) ..."

$resp = Invoke-RestMethod -Method Post -Uri $url -Headers $headers -Body $body -ErrorAction Stop

Write-Host "Created env var:" ($resp | ConvertTo-Json -Depth 3)
