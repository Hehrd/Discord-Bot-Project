'use client';

import { useState } from 'react';
import { signIn } from 'next-auth/react';
import AnimatedButton from '@/components/animatedButton';

export default function LoginPage() {

    const handleDiscordLogin = () => {
        signIn('discord');
    };

    return (
        <form
            onSubmit={(e: React.FormEvent) => {
                e.preventDefault();
            }}
            autoComplete="off"
            className="
                mx-auto
                bg-white rounded-3xl p-8
                shadow-lg
                h-[30vh]       
                w-[30vw]
                flex flex-col justify-between items-center
            "
        >
            <h2 className="text-3xl font-bold text-center mb-6 text-gray-800">
                Login
            </h2>


            {/* Discord Login Button */}
            <div className="flex justify-center w-full mt-4">
                <AnimatedButton onClick={handleDiscordLogin} width={200} height={40}>
                    Login with Discord
                </AnimatedButton>
            </div>
        </form>
    );
}
