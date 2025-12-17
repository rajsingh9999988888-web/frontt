import React, { useState } from 'react';

const FALLBACK_IMAGE = 'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1200&q=80';

interface SafeImageProps extends React.ImgHTMLAttributes<HTMLImageElement> {
  src: string | undefined | null;
  fallback?: string;
  alt: string;
}

/**
 * SafeImage component with automatic fallback handling
 * Handles broken images, missing URLs, and loading states
 */
export default function SafeImage({ src, fallback = FALLBACK_IMAGE, alt, className: imgClassName, ...props }: SafeImageProps) {
  const [imgSrc, setImgSrc] = useState<string>(() => {
    // If no src provided or empty, use fallback immediately
    if (!src || src.trim() === '') {
      return fallback;
    }
    
    const trimmedSrc = src.trim();
    
    // If src is already a full URL (http/https), use it directly
    if (trimmedSrc.startsWith('http://') || trimmedSrc.startsWith('https://')) {
      return trimmedSrc;
    }
    
    // If src is a relative path starting with /uploads/ or contains uploads/, construct full backend URL
    if (trimmedSrc.startsWith('/uploads/') || trimmedSrc.includes('uploads/')) {
      const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 
        (import.meta.env.PROD ? 'https://baby-adoption-backend.onrender.com' : 'http://localhost:8082');
      return `${API_BASE_URL}${trimmedSrc.startsWith('/') ? trimmedSrc : '/' + trimmedSrc}`;
    }
    
    // If src starts with /api/babies/images/, it's already a backend path - construct full URL
    if (trimmedSrc.startsWith('/api/babies/images/')) {
      const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 
        (import.meta.env.PROD ? 'https://baby-adoption-backend.onrender.com' : 'http://localhost:8082');
      return `${API_BASE_URL}${trimmedSrc}`;
    }
    
    // For other relative paths starting with /, return as is (browser will handle)
    if (trimmedSrc.startsWith('/')) {
      return trimmedSrc;
    }
    
    // For any other case, try to construct URL or return as is
    return trimmedSrc;
  });
  const [hasError, setHasError] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  const handleError = () => {
    if (!hasError && imgSrc !== fallback) {
      setHasError(true);
      setImgSrc(fallback);
      setIsLoading(false);
    }
  };

  const handleLoad = () => {
    setIsLoading(false);
  };

  return (
    <>
      {isLoading && (
        <div className="absolute inset-0 z-10 flex items-center justify-center bg-slate-200 dark:bg-slate-800">
          <div className="h-8 w-8 animate-spin rounded-full border-4 border-slate-300 border-t-slate-600 dark:border-slate-600 dark:border-t-slate-300"></div>
        </div>
      )}
      <img
        src={imgSrc}
        alt={alt}
        onError={handleError}
        onLoad={handleLoad}
        className={`${imgClassName || ''} ${isLoading ? 'opacity-0' : 'opacity-100'} transition-opacity duration-300`}
        {...props}
      />
    </>
  );
}

