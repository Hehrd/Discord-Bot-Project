// app/login/page.tsx
'use client';

import { useState } from 'react';
import AnimatedButton from '@/components/animatedButton';

export default function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log('Logging in with:', email, password);
    };

    return (
        <form
            onSubmit={handleSubmit}
            autoComplete="off"
            className="
                mx-auto
                bg-white rounded-3xl p-8
                shadow-lg
                h-[53vh]       
                w-[30vw]
                flex flex-col justify-between items-center
            "
        >
            <h2 className="text-3xl font-bold text-center mb-6 text-gray-800">
                Login
            </h2>

            <div className="mb-4 w-full">
                <label
                    htmlFor="email"
                    className="block text-sm font-medium text-gray-700 mb-1"
                >
                    Email
                </label>
                <input
                    type="email"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    autoComplete="off"
                    className="
                        mt-1 block w-full px-4 py-2
                        border border-gray-300 rounded-2xl shadow-sm
                        focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500
                    "
                    placeholder="Enter your email"
                />
            </div>

            <div className="mb-6 w-full">
                <label
                    htmlFor="password"
                    className="block text-sm font-medium text-gray-700 mb-1"
                >
                    Password
                </label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    autoComplete="off"
                    className="
                        mt-1 block w-full px-4 py-2
                        border border-gray-300 rounded-2xl shadow-sm
                        focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500
                    "
                    placeholder="Enter your password"
                />
            </div>

            <div className="flex justify-center w-full">
                <AnimatedButton width={200} height={40}>
                    Login
                </AnimatedButton>
            </div>
        </form>
    );
}
