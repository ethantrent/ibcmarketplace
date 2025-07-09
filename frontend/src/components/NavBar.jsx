// src/components/NavBar.jsx
import React from 'react';
import { Link, useLocation } from 'react-router-dom';

const NavBar = () => {
  const location = useLocation();

  const navItems = [
    { path: '/dashboard', label: 'Home', icon: '🏠' },
    { path: '/products', label: 'Products', icon: '📦' },
    { path: '/categories', label: 'Categories', icon: '📂' },
    { path: '/orders', label: 'Orders', icon: '📋' },
    { path: '/profile', label: 'Profile', icon: '👤' }
  ];

  return (
    <nav className="bg-white shadow-sm border-b border-gray-200">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex space-x-8">
          {navItems.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center px-3 py-4 text-sm font-medium border-b-2 transition duration-150 ease-in-out ${
                location.pathname === item.path
                  ? 'border-byui-blue text-byui-blue'
                  : 'border-transparent text-byui-medium-gray hover:text-byui-dark hover:border-gray-300'
              }`}
            >
              <span className="mr-2">{item.icon}</span>
              {item.label}
            </Link>
          ))}
        </div>
      </div>
    </nav>
  );
};

export default NavBar; 