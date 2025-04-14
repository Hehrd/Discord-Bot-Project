// src/app/page.tsx
'use client';
import AnimatedButton from "@/components/animatedButton";
import NavLink from "@/components/navbarLink";

export default function Home() {
    return (<>
        <nav className="flex gap-4 p-4 bg-gray-100">
            <NavLink href="/home" textSize="text-sm">
                Home
            </NavLink>
            <NavLink href="/about" textSize="text-base">
                About
            </NavLink>
            <NavLink href="/contact" textSize="text-lg">
                Contact
            </NavLink>
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
                    <br /><br />
                    <AnimatedButton size={0.8} onClick={() => alert('Small button clicked!')}>
                        Small Button
                    </AnimatedButton>
                    <br /><br />
                    <AnimatedButton size={1.5} onClick={() => alert('Large button clicked!')}>
                        Large Button
                    </AnimatedButton>

                </div>

                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>
                <p className="text-neutral-600 dark:text-neutral-400">
                    You should see a cursor that changes between black (light mode) and white (dark mode)
                </p>

            </div>
        </main>
    </>
    );
}