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
    // If src is a relative path, construct full URL
    if (src.startsWith('/') || !src.startsWith('http')) {
      // Check if it's a local upload path
      if (src.startsWith('/uploads/') || src.includes('uploads/')) {
        // Construct full backend URL
        const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 
          (import.meta.env.PROD ? 'https://baby-adoption-backend.onrender.com' : 'http://localhost:8082');
        return `${API_BASE_URL}${src.startsWith('/') ? src : '/' + src}`;
      }
      // For other relative paths, return as is (browser will handle)
      return src;
    }
    return src;
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

