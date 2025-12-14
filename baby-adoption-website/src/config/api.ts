// API Configuration
// In production, this will use the Render backend URL
// In development, it uses localhost
// Set VITE_API_BASE_URL in Vercel environment variables

const getApiBaseUrl = (): string => {
  // Check for environment variable (Vercel will provide this)
  if (import.meta.env.VITE_API_BASE_URL) {
    const url = import.meta.env.VITE_API_BASE_URL.trim();
    if (url) {
      console.log('[API Config] Using VITE_API_BASE_URL:', url);
      return url;
    }
  }
  
  // Check if we're in production (Vercel deployment)
  if (import.meta.env.PROD) {
    // Default production URL - Render backend
    const prodUrl = 'https://baby-adoption-backend.onrender.com';
    console.log('[API Config] Production mode, using:', prodUrl);
    return prodUrl;
  }
  
  // Development: use localhost
  const devUrl = 'http://localhost:8082';
  console.log('[API Config] Development mode, using:', devUrl);
  return devUrl;
};

export const API_BASE_URL = getApiBaseUrl();

// Log the final API_BASE_URL for debugging
console.log('[API Config] Final API_BASE_URL:', API_BASE_URL);

// Helper function to build API URLs
export const buildApiUrl = (path: string): string => {
  // Remove leading slash if present to avoid double slashes
  const cleanPath = path.startsWith('/') ? path.slice(1) : path;
  return `${API_BASE_URL}/${cleanPath}`;
};

export default API_BASE_URL;

