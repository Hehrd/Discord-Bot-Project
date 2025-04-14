'use client';
import React, { FC } from 'react';

interface NavLinkProps {
    children: React.ReactNode;
    href: string;
    textSize?: string;
}

const NavLink: FC<NavLinkProps> = ({ children, href, textSize = 'text-base' }) => {
    return (
        <a href={href} className={`navlink ${textSize}`}>
            {children}
            <style jsx>{`
                .navlink {
                    display: inline-block;
                    padding: 0.4rem 1rem; /* reduced vertical padding */
                    border: 2px solid transparent;
                    border-radius: 9999px; /* pill shape */
                    color: #4b5563; /* gray-700 */
                    text-decoration: none;
                    position: relative;
                    transition: border-color 0.3s ease, color 0.3s ease;
                }
                .navlink:hover {
                    border-color: #3b82f6; /* blue-500 */
                    color: #3b82f6;
                    animation: pulseGlow 1.5s infinite;
                }
                @keyframes pulseGlow {
                    0% {
                        box-shadow: 0 0 0 0 rgba(59, 130, 246, 0.4);
                    }
                    70% {
                        box-shadow: 0 0 0 8px rgba(59, 130, 246, 0);
                    }
                    100% {
                        box-shadow: 0 0 0 0 rgba(59, 130, 246, 0);
                    }
                }
            `}</style>
        </a>
    );
};

export default NavLink;
