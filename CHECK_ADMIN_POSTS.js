// Run this in browser console to debug admin posts issue

// Step 1: Check if logged in as admin
console.log('=== Admin Login Check ===');
const user = JSON.parse(localStorage.getItem('user'));
const token = localStorage.getItem('token');
console.log('User:', user);
console.log('Role:', user?.role);
console.log('Token exists:', !!token);

// Step 2: Test admin endpoint directly
console.log('\n=== Testing Admin Endpoint ===');
fetch('https://baby-adoption-backend.onrender.com/api/babies/admin/posts', {
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(response => {
  console.log('Response status:', response.status);
  console.log('Response headers:', [...response.headers.entries()]);
  return response.json();
})
.then(data => {
  console.log('Posts returned:', data);
  console.log('Number of posts:', Array.isArray(data) ? data.length : 'Not an array');
  if (Array.isArray(data) && data.length > 0) {
    console.log('First post:', data[0]);
  }
})
.catch(error => {
  console.error('Error:', error);
});

// Step 3: Compare with baby-list endpoint
console.log('\n=== Testing Baby List Endpoint ===');
fetch('https://baby-adoption-backend.onrender.com/api/babies/babies')
.then(r => r.json())
.then(data => {
  console.log('Baby-list posts:', data);
  console.log('Baby-list count:', Array.isArray(data) ? data.length : 'Not an array');
})
.catch(error => {
  console.error('Error:', error);
});

