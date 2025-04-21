'use client';
import React from 'react';
import { usePathname } from 'next/navigation';
import NavLink from './navbarLink';

interface NavItem {
    label: string;
    href: string;
    isLogout?: boolean;
}

const NavBar: React.FC = () => {
    const pathname = usePathname();

    const navItems: NavItem[] = [
        { label: 'Containers', href: '/' },
        { label: 'Getting Started', href: '/getting-started' },
        { label: 'Features', href: '/features' },
        { label: 'About Us', href: '/about' },
        { label: 'test', href: '/test' },
        { label: 'Logout', href: '/logout', isLogout: true },


    ];

    return (
        <nav className="flex justify-between items-center bg-gray-100 text-gray-700 mb-4 p-4 rounded">
            <div className="flex gap-4">
                {navItems
                    .filter((item) => !item.isLogout)
                    .map((item) => (
                        <NavLink key={item.href} href={item.href}>
                            <span className={pathname === item.href ? 'font-bold text-blue-600' : ''}>
                                {item.label}
                            </span>
                        </NavLink>
                    ))}
            </div>

            <div>
                {navItems
                    .filter((item) => item.isLogout)
                    .map((item) => (
                        <NavLink key={item.href} href={item.href}>
                            <span className={pathname === item.href ? 'font-bold text-blue-600' : ''}>
                                {item.label}
                            </span>
                        </NavLink>
                    ))}
            </div>
        </nav>

    );
};

export default NavBar;
