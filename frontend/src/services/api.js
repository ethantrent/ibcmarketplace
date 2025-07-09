import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Adjust if your backend base path differs
  withCredentials: true, // Send cookies (for JWT in httpOnly cookie)
});

// Example: login
export const login = async (email, password) => {
  const response = await api.post('/auth/login', { email, password });
  return response.data;
};

// Example: logout
export const logout = async () => {
  const response = await api.post('/auth/logout');
  return response.data;
};

// Example: register
export const register = async (email, password, name) => {
  const response = await api.post('/auth/register', { email, password, name });
  return response.data;
};

// Add more API methods as needed

export default api; 