// src/pages/Dashboard.jsx
import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import SearchBar from '../components/SearchBar';
import SideBar from '../components/SideBar';
import Paginator from '../components/Paginator';
import LoadSpinner from '../components/LoadSpinner';
import NoProductsAvailable from '../components/NoProductsAvailable';

const Dashboard = () => {
  const user = useSelector(state => state.auth.user);
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');

  useEffect(() => {
    // Simulate loading products
    setTimeout(() => {
      setProducts([
        {
          id: 1,
          name: 'Vintage Camera',
          price: 299.99,
          description: 'Beautiful vintage camera in excellent condition',
          image: 'https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=400',
          category: 'Electronics'
        },
        {
          id: 2,
          name: 'Handcrafted Wooden Bowl',
          price: 45.00,
          description: 'Unique handcrafted wooden bowl made from sustainable materials',
          image: 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400',
          category: 'Home & Garden'
        },
        {
          id: 3,
          name: 'Designer Watch',
          price: 599.99,
          description: 'Elegant designer watch with premium materials',
          image: 'https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=400',
          category: 'Fashion'
        }
      ]);
      setLoading(false);
    }, 1500);
  }, []);

  const handleSearch = (term) => {
    setSearchTerm(term);
    setCurrentPage(1);
  };

  const handleCategoryChange = (category) => {
    setSelectedCategory(category);
    setCurrentPage(1);
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-byui-light-gray flex items-center justify-center">
        <LoadSpinner />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-byui-light-gray">
      {/* Header Section */}
      <div className="bg-white shadow-sm border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center">
              <h1 className="text-2xl font-bold text-byui-dark">Marketplace</h1>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-sm text-byui-medium-gray">
                Welcome, {user?.name || 'User'}
              </span>
              <button className="bg-byui-blue text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-blue-800 transition duration-150 ease-in-out">
                My Account
              </button>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Sidebar */}
          <div className="lg:w-64">
            <SideBar 
              selectedCategory={selectedCategory}
              onCategoryChange={handleCategoryChange}
            />
          </div>

          {/* Main Content */}
          <div className="flex-1">
            {/* Search Bar */}
            <div className="mb-6">
              <SearchBar onSearch={handleSearch} />
            </div>

            {/* Products Grid */}
            {products.length > 0 ? (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                {products.map((product) => (
                  <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition duration-300 ease-in-out">
                    <div className="aspect-w-1 aspect-h-1 w-full">
                      <img
                        src={product.image}
                        alt={product.name}
                        className="w-full h-48 object-cover"
                      />
                    </div>
                    <div className="p-4">
                      <h3 className="text-lg font-semibold text-byui-dark mb-2">
                        {product.name}
                      </h3>
                      <p className="text-byui-medium-gray text-sm mb-3 line-clamp-2">
                        {product.description}
                      </p>
                      <div className="flex items-center justify-between">
                        <span className="text-2xl font-bold text-byui-blue">
                          ${product.price}
                        </span>
                        <button className="bg-byui-blue text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-blue-800 transition duration-150 ease-in-out">
                          View Details
                        </button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <NoProductsAvailable />
            )}

            {/* Pagination */}
            {products.length > 0 && (
              <div className="mt-8">
                <Paginator
                  currentPage={currentPage}
                  totalPages={totalPages}
                  onPageChange={handlePageChange}
                />
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard; 