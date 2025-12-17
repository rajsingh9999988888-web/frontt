/**
 * Domain Configuration
 * Update this file with your actual domain name
 * This will be used throughout the application for SEO and canonical URLs
 * 
 * Priority:
 * 1. Environment variable VITE_SITE_DOMAIN (recommended for deployment)
 * 2. This constant (for local development)
 */

// Update this with your actual production domain
// For production, use environment variable VITE_SITE_DOMAIN instead
export const SITE_DOMAIN = import.meta.env.VITE_SITE_DOMAIN || 'https://yourdomain.com'; // ← CHANGE THIS

// Get site domain (uses env var if available, otherwise uses constant)
export const getSiteDomain = (): string => {
  // Check for environment variable first (for production)
  if (import.meta.env.VITE_SITE_DOMAIN) {
    return import.meta.env.VITE_SITE_DOMAIN;
  }
  // Fallback to constant (for local development)
  return SITE_DOMAIN;
};

// Helper function to build canonical URLs
export const buildCanonicalUrl = (path: string): string => {
  const domain = getSiteDomain();
  const cleanPath = path.startsWith('/') ? path : `/${path}`;
  return `${domain}${cleanPath}`;
};

