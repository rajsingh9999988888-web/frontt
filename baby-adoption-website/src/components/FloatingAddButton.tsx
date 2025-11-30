import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

export default function FloatingAddButton(): React.JSX.Element {
  const navigate = useNavigate();
  const { isLoggedIn, setRedirectPath } = useAuth();

  const onClick = () => {
    if (isLoggedIn) {
      navigate('/add-post');
      return;
    }
    // Not logged in: remember redirect and go to login page
    setRedirectPath('/add-post');
    navigate('/login-signup');
  };

  return (
    <button
      onClick={onClick}
      aria-label="Add Post"
      className="fixed right-4 bottom-6 z-50 inline-flex h-14 w-14 items-center justify-center rounded-full bg-[#ff4f70] text-white shadow-xl transition hover:scale-105 focus:outline-none md:right-8 md:bottom-8"
    >
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" className="h-6 w-6">
        <path d="M12 5v14M5 12h14" strokeLinecap="round" strokeLinejoin="round" />
      </svg>
    </button>
  );
}
