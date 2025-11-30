import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';

interface User {
  email: string;
  role: string;
  // Add other user properties as needed
}

interface AuthContextType {
  isLoggedIn: boolean;
  user: User | null;
  token: string | null;
  login: (userData: User, authToken: string) => void;
  logout: () => void;
  redirectPath: string | null;
  setRedirectPath: (path: string | null) => void;
  clearRedirectPath: () => void;
  isAdmin: () => boolean;
  isEmployee: () => boolean;
  canAddPost: () => boolean;
  isAuthReady: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [redirectPath, setRedirectPath] = useState<string | null>(null);
  const [isAuthReady, setIsAuthReady] = useState(false);

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    if (storedToken && storedUser) {
      setToken(storedToken);
      setUser(JSON.parse(storedUser));
      setIsLoggedIn(true);
    }
    setIsAuthReady(true);
  }, []);

  const login = (userData: User, authToken: string) => {
    setIsLoggedIn(true);
    setUser(userData);
    setToken(authToken);
    localStorage.setItem('token', authToken);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
    setToken(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setRedirectPath(null);
  };

  const clearRedirectPath = () => {
    setRedirectPath(null);
  };

  const isAdmin = (): boolean => !!(user && user.role === 'ADMIN');

  const isEmployee = (): boolean => !!(user && user.role === 'EMPLOYEE');

  // Backend now allows any authenticated user to add posts.
  const canAddPost = (): boolean => isLoggedIn;

  return (
    <AuthContext.Provider value={{
      isLoggedIn,
      user,
      token,
      login,
      logout,
      redirectPath,
      setRedirectPath,
      clearRedirectPath,
      isAdmin,
      isEmployee,
      canAddPost,
      isAuthReady,
    }}>
      {children}
    </AuthContext.Provider>
  );
};
