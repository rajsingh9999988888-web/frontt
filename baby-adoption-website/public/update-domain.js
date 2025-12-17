/**
 * Script to update domain in static files
 * Run: node public/update-domain.js yourdomain.com
 */

const fs = require('fs');
const path = require('path');

const newDomain = process.argv[2] || 'yourdomain.com';
const fullDomain = newDomain.startsWith('http') ? newDomain : `https://${newDomain}`;

console.log(`Updating domain to: ${fullDomain}`);

// Files to update
const filesToUpdate = [
  {
    path: path.join(__dirname, 'sitemap.xml'),
    replacements: [
      { from: /https:\/\/yourdomain\.com/g, to: fullDomain }
    ]
  },
  {
    path: path.join(__dirname, '../index.html'),
    replacements: [
      { from: /https:\/\/yourdomain\.com/g, to: fullDomain }
    ]
  },
  {
    path: path.join(__dirname, 'robots.txt'),
    replacements: [
      { from: /https:\/\/yourdomain\.com/g, to: fullDomain }
    ]
  }
];

filesToUpdate.forEach(({ path: filePath, replacements }) => {
  try {
    let content = fs.readFileSync(filePath, 'utf8');
    let updated = false;
    
    replacements.forEach(({ from, to }) => {
      if (content.match(from)) {
        content = content.replace(from, to);
        updated = true;
      }
    });
    
    if (updated) {
      fs.writeFileSync(filePath, content, 'utf8');
      console.log(`✅ Updated: ${filePath}`);
    } else {
      console.log(`⏭️  No changes needed: ${filePath}`);
    }
  } catch (error) {
    console.error(`❌ Error updating ${filePath}:`, error.message);
  }
});

console.log('\n✅ Domain update complete!');
console.log(`\nDon't forget to update: src/config/domain.ts`);

