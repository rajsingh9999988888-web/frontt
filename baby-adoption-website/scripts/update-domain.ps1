# PowerShell script to update domain in all files
# Usage: .\scripts\update-domain.ps1 "yourdomain.com"

param(
    [Parameter(Mandatory=$true)]
    [string]$Domain
)

$fullDomain = if ($Domain -notmatch "^https?://") { "https://$Domain" } else { $Domain }

Write-Host "Updating domain to: $fullDomain" -ForegroundColor Green

# Files to update
$files = @(
    @{
        Path = "public\sitemap.xml"
        Pattern = "https://yourdomain\.com"
    },
    @{
        Path = "index.html"
        Pattern = "https://yourdomain\.com"
    },
    @{
        Path = "public\robots.txt"
        Pattern = "https://yourdomain\.com"
    },
    @{
        Path = "src\config\domain.ts"
        Pattern = "https://yourdomain\.com"
    }
)

foreach ($file in $files) {
    $filePath = Join-Path $PSScriptRoot ".." $file.Path
    $filePath = Resolve-Path $filePath -ErrorAction SilentlyContinue
    
    if ($filePath -and (Test-Path $filePath)) {
        $content = Get-Content $filePath -Raw
        if ($content -match $file.Pattern) {
            $content = $content -replace $file.Pattern, $fullDomain
            Set-Content $filePath -Value $content -NoNewline
            Write-Host "✅ Updated: $($file.Path)" -ForegroundColor Green
        } else {
            Write-Host "⏭️  No changes needed: $($file.Path)" -ForegroundColor Yellow
        }
    } else {
        Write-Host "❌ File not found: $($file.Path)" -ForegroundColor Red
    }
}

Write-Host "`n✅ Domain update complete!" -ForegroundColor Green
Write-Host "`nNext steps:" -ForegroundColor Cyan
Write-Host "1. Review the updated files" -ForegroundColor White
Write-Host "2. Commit changes: git add . && git commit -m 'Update domain to $fullDomain'" -ForegroundColor White
Write-Host "3. Push: git push" -ForegroundColor White

