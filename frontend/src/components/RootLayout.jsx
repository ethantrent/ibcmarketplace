// src/components/RootLayout.jsx
import React from 'react';
import Header from './Header';
import NavBar from './NavBar';
import Footer from './Footer';

const RootLayout = ({ children }) => {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      {/* Header */}
      <Header />
      
      {/* Navigation */}
      <NavBar />
      
      {/* Main Content */}
      <main className="flex-1">
        {children}
      </main>
      
      {/* Footer */}
      <Footer />
    </div>
  );
};

export default RootLayout; 