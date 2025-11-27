// API Configuration
// In production, this will use the Render backend URL
// In development, it uses localhost
// Set VITE_API_BASE_URL in Vercel environment variables

const getApiBaseUrl = (): string => {
  // Check for environment variable (Vercel will provide this)
  if (import.meta.env.VITE_API_BASE_URL) {
    return import.meta.env.VITE_API_BASE_URL;
  }
  
  // Check if we're in production (Vercel deployment)
  if (import.meta.env.PROD) {
    // Default production URL (update this with your Render backend URL)
    return 'https://your-backend.onrender.com';
  }
  
  // Development: use localhost
  return 'http://localhost:8082';
};

export const API_BASE_URL = getApiBaseUrl();

// Helper function to build API URLs
export const buildApiUrl = (path: string): string => {
  // Remove leading slash if present to avoid double slashes
  const cleanPath = path.startsWith('/') ? path.slice(1) : path;
  return `${API_BASE_URL}/${cleanPath}`;
};

export default API_BASE_URL;

