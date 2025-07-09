// src/components/NoProductsAvailable.jsx
import React from 'react';

const NoProductsAvailable = ({ message = 'No products found', showSearch = true }) => {
  return (
    <div className="text-center py-12">
      <div className="max-w-md mx-auto">
        {/* Icon */}
        <div className="mx-auto h-24 w-24 text-byui-medium-gray mb-4">
          <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1} d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
          </svg>
        </div>
        
        {/* Message */}
        <h3 className="text-lg font-medium text-byui-dark mb-2">
          {message}
        </h3>
        
        <p className="text-byui-medium-gray mb-6">
          {showSearch 
            ? "Try adjusting your search criteria or browse our categories to find what you're looking for."
            : "Check back later for new products or browse our categories."
          }
        </p>
        
        {/* Action Buttons */}
        <div className="flex flex-col sm:flex-row gap-3 justify-center">
          {showSearch && (
            <button className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-lg text-white bg-byui-blue hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-byui-blue transition duration-150 ease-in-out">
              <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
              Clear Search
            </button>
          )}
          
          <button className="inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-lg text-byui-dark bg-white hover:bg-byui-light-gray focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-byui-blue transition duration-150 ease-in-out">
            <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
            </svg>
            Browse Categories
          </button>
        </div>
      </div>
    </div>
  );
};

export default NoProductsAvailable; 