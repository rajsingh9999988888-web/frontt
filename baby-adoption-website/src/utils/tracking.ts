/**
 * Tracking Utility
 * Tracks user actions like WhatsApp clicks with city/district information
 */

import { API_BASE_URL } from '../config/api';

interface WhatsAppClickData {
  postId?: number;
  city?: string;
  district?: string;
  state?: string;
  whatsappNumber?: string;
  pageUrl?: string;
}

/**
 * Track WhatsApp click with city/district information
 * Sends data to backend before opening WhatsApp
 */
export async function trackWhatsAppClick(data: WhatsAppClickData): Promise<void> {
  try {
    // Get current page URL
    const pageUrl = window.location.href;
    
    // Prepare tracking data
    const trackingData = {
      postId: data.postId || null,
      city: data.city || null,
      district: data.district || null,
      state: data.state || null,
      whatsappNumber: data.whatsappNumber || null,
      pageUrl: pageUrl
    };

    // Send tracking data to backend (fire and forget - don't wait for response)
    fetch(`${API_BASE_URL}/track/whatsapp-click`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(trackingData),
    }).catch((error) => {
      // Silently fail - don't block user from opening WhatsApp
      console.error('Failed to track WhatsApp click:', error);
    });
  } catch (error) {
    // Silently fail - don't block user from opening WhatsApp
    console.error('Error tracking WhatsApp click:', error);
  }
}

/**
 * Handle WhatsApp click with tracking
 * Tracks the click and then opens WhatsApp with a pre-filled message
 */
export function handleWhatsAppClick(
  whatsappNumber: string,
  postData?: {
    id?: number;
    city?: string;
    district?: string;
    state?: string;
    name?: string;
  }
): void {
  // Track the click
  trackWhatsAppClick({
    postId: postData?.id,
    city: postData?.city,
    district: postData?.district,
    state: postData?.state,
    whatsappNumber: whatsappNumber,
  });

  // Generate welcome message based on post data
  let welcomeMessage = 'Hello! I am interested in your service.';
  
  if (postData?.name) {
    welcomeMessage = `Hello ${postData.name}! I am interested in your service.`;
  } else if (postData?.city) {
    welcomeMessage = `Hello! I am interested in your service in ${postData.city}.`;
  }

  // Clean WhatsApp number (remove +, spaces, dashes)
  const cleanNumber = whatsappNumber.replace(/[^0-9]/g, '');
  
  // Open WhatsApp with pre-filled message
  const whatsappUrl = `https://wa.me/${cleanNumber}?text=${encodeURIComponent(welcomeMessage)}`;
  window.open(whatsappUrl, '_blank', 'noopener,noreferrer');
}

