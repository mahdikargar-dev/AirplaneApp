import React from 'react';
import { useState } from 'react';

// Header Component
const Header = () => {
    return (
        <header className="bg-white shadow-md">
            <nav className="container mx-auto px-6 py-4">
                <div className="flex justify-between items-center">
                    <div className="text-xl font-bold text-gray-800">Airplane App</div>
                    <div className="space-x-4">
                        <a href="/login" className="text-blue-600 hover:text-blue-800">Login</a>
                        <a href="/register" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Register</a>
                    </div>
                </div>
            </nav>
        </header>
    );
};

// Login Component
const Login = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });
            if (response.ok) {
                // Handle successful login
                window.location.href = '/';
            } else {
                alert('Login failed. Please check your credentials.');
            }
        } catch (error) {
            console.error('Login error:', error);
            alert('An error occurred during login.');
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 flex flex-col">
            <Header />
            <div className="flex-1 flex items-center justify-center">
                <div className="bg-white p-8 rounded-lg shadow-md w-96">
                    <h2 className="text-2xl font-bold mb-6 text-center">Login</h2>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div>
                            <label className="block text-gray-700 mb-2">Username</label>
                            <input
                                type="text"
                                className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                                value={formData.username}
                                onChange={(e) => setFormData({...formData, username: e.target.value})}
                                required
                            />
                        </div>
                        <div>
                            <label className="block text-gray-700 mb-2">Password</label>
                            <input
                                type="password"
                                className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                                value={formData.password}
                                onChange={(e) => setFormData({...formData, password: e.target.value})}
                                required
                            />
                        </div>
                        <button
                            type="submit"
                            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
                        >
                            Login
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

// Register Component
const Register = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });
            if (response.ok) {
                alert('Registration successful! Please login.');
                window.location.href = '/login';
            } else {
                alert('Registration failed. Please try again.');
            }
        } catch (error) {
            console.error('Registration error:', error);
            alert('An error occurred during registration.');
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 flex flex-col">
            <Header />
            <div className="flex-1 flex items-center justify-center">
                <div className="bg-white p-8 rounded-lg shadow-md w-96">
                    <h2 className="text-2xl font-bold mb-6 text-center">Register</h2>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div>
                            <label className="block text-gray-700 mb-2">Username</label>
                            <input
                                type="text"
                                className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                                value={formData.username}
                                onChange={(e) => setFormData({...formData, username: e.target.value})}
                                required
                            />
                        </div>
                        <div>
                            <label className="block text-gray-700 mb-2">Password</label>
                            <input
                                type="password"
                                className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                                value={formData.password}
                                onChange={(e) => setFormData({...formData, password: e.target.value})}
                                required
                            />
                        </div>
                        <button
                            type="submit"
                            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
                        >
                            Register
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

// Main App Component
const App = () => {
    return (
        <div className="min-h-screen bg-gray-100">
            <Header />
            {/* Main content area */}
            <div className="container mx-auto px-6 py-8">
                <h1 className="text-3xl font-bold text-center text-gray-800">Welcome to Airplane App</h1>
                <p className="text-center text-gray-600 mt-4">Please login or register to continue</p>
            </div>
        </div>
    );
};

export default App;