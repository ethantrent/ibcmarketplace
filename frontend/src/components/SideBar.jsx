// src/components/SideBar.jsx
import React from 'react';

const SideBar = ({ selectedCategory, onCategoryChange }) => {
  const categories = [
    { id: 'all', name: 'All Categories', icon: '🏠', count: 150 },
    { id: 'electronics', name: 'Electronics', icon: '📱', count: 45 },
    { id: 'fashion', name: 'Fashion', icon: '👕', count: 32 },
    { id: 'home', name: 'Home & Garden', icon: '🏡', count: 28 },
    { id: 'books', name: 'Books', icon: '📚', count: 15 },
    { id: 'sports', name: 'Sports & Outdoors', icon: '⚽', count: 20 },
    { id: 'toys', name: 'Toys & Games', icon: '🎮', count: 12 },
    { id: 'automotive', name: 'Automotive', icon: '🚗', count: 8 }
  ];

  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <h3 className="text-lg font-semibold text-byui-dark mb-4">Categories</h3>
      <div className="space-y-2">
        {categories.map((category) => (
          <button
            key={category.id}
            onClick={() => onCategoryChange(category.id)}
            className={`w-full flex items-center justify-between px-3 py-2 rounded-lg text-left transition duration-150 ease-in-out ${
              selectedCategory === category.id
                ? 'bg-blue-50 text-byui-blue border border-blue-200'
                : 'text-byui-dark hover:bg-byui-light-gray'
            }`}
          >
            <div className="flex items-center">
              <span className="mr-3 text-lg">{category.icon}</span>
              <span className="font-medium">{category.name}</span>
            </div>
            <span className={`text-sm ${
              selectedCategory === category.id ? 'text-byui-blue' : 'text-byui-medium-gray'
            }`}>
              {category.count}
            </span>
          </button>
        ))}
      </div>

      {/* Filters Section */}
      <div className="mt-8">
        <h3 className="text-lg font-semibold text-byui-dark mb-4">Filters</h3>
        
        {/* Price Range */}
        <div className="mb-6">
          <h4 className="text-sm font-medium text-byui-dark mb-3">Price Range</h4>
          <div className="space-y-2">
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">Under $50</span>
            </label>
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">$50 - $100</span>
            </label>
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">$100 - $200</span>
            </label>
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">Over $200</span>
            </label>
          </div>
        </div>

        {/* Condition */}
        <div className="mb-6">
          <h4 className="text-sm font-medium text-byui-dark mb-3">Condition</h4>
          <div className="space-y-2">
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">New</span>
            </label>
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">Like New</span>
            </label>
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">Good</span>
            </label>
            <label className="flex items-center">
              <input type="checkbox" className="rounded border-gray-300 text-byui-blue focus:ring-byui-blue" />
              <span className="ml-2 text-sm text-byui-dark">Fair</span>
            </label>
          </div>
        </div>

        {/* Clear Filters */}
        <button className="w-full bg-byui-light-gray text-byui-dark px-4 py-2 rounded-lg text-sm font-medium hover:bg-gray-200 transition duration-150 ease-in-out">
          Clear All Filters
        </button>
      </div>
    </div>
  );
};

export default SideBar; 