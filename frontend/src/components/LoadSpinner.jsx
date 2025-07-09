// src/components/LoadSpinner.jsx
import React from 'react';

const LoadSpinner = ({ size = 'md', text = 'Loading...' }) => {
  const sizeClasses = {
    sm: 'h-4 w-4',
    md: 'h-8 w-8',
    lg: 'h-12 w-12',
    xl: 'h-16 w-16'
  };

  return (
    <div className="flex flex-col items-center justify-center">
      <div className={`${sizeClasses[size]} animate-spin rounded-full border-4 border-gray-200 border-t-byui-blue`}></div>
      {text && (
        <p className="mt-4 text-sm text-byui-medium-gray font-medium">{text}</p>
      )}
    </div>
  );
};

export default LoadSpinner; 