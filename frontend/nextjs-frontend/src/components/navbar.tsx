'use client';
import React from 'react';
import { usePathname } from 'next/navigation';
import NavLink from './navbarLink';

const NavBar: React.FC = () => {
    const pathname = usePathname();

    const navItems = [
        { label: 'Databases', href: '/' },
        { label: 'About Us', href: '/about' },
        { label: 'Getting Started', href: '/getting-started' },
        { label: 'Features', href: '/features' },
        { label: 'Docs', href: '/docs' },
        { label: 'Login', href: '/login' },
    ];

    return (
        <nav className="flex gap-4 bg-gray-100 text-gray-700 mb-4 py-2 px-4 rounded">
            {navItems.map(item => (
                <NavLink key={item.href} href={item.href}>
                    <span className={pathname === item.href ? 'font-bold text-blue-600' : ''}>
                        {item.label}
                    </span>
                </NavLink>
            ))}
        </nav>
    );
};

export default NavBar;
