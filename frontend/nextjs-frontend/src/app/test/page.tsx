// src/app/page.tsx
'use client';

import { useEffect } from 'react';
import { useSession } from 'next-auth/react';
import AnimatedButton from "@/components/animatedButton";
import NavLink from "@/components/navbarLink";

export default function Home() {
    const { data: session, status } = useSession();

    useEffect(() => {
        if (status === 'authenticated' && session.accessToken) {
            fetch('https://discord.com/api/v10/users/@me', {
                headers: {
                    'Authorization': `Bearer ${session.accessToken}`
                }
            })
                .then(res => {
                    if (!res.ok) throw new Error(`Discord API returned ${res.status}`);
                    return res.json();
                })
                .then(profile => {
                    console.log('🔵 Discord Profile:', profile);
                })
                .catch(err => {
                    console.error('⚠️ Discord fetch error:', err);
                });
        }
    }, [status, session]);

    return (
        <>
            <nav className="flex gap-4 p-4 bg-gray-100">
                <NavLink href="/home" textSize="text-sm">Home</NavLink>
                <NavLink href="/about" textSize="text-base">About</NavLink>
                <NavLink href="/contact" textSize="text-lg">Contact</NavLink>
            </nav>

            <main className="min-h-screen grid place-items-center p-24 bg-white dark:bg-black transition-colors">
                <div className="text-center space-y-8">
                    <h1 className="text-4xl font-bold text-black dark:text-white">
                        Cursor Visibility Test
                    </h1>

                    <div className="flex gap-6 justify-center">
                        <AnimatedButton size={1} onClick={() => alert('Default clicked!')}>
                            Default Button
                        </AnimatedButton>
                        <AnimatedButton size={0.8} onClick={() => alert('Small clicked!')}>
                            Small Button
                        </AnimatedButton>
                        <AnimatedButton size={1.5} onClick={() => alert('Large clicked!')}>
                            Large Button
                        </AnimatedButton>
                    </div>

                    <p className="text-neutral-600 dark:text-neutral-400">
                        You should see your Discord profile logged to the console once authenticated.
                    </p>
                </div>
            </main>
        </>
    );
}
