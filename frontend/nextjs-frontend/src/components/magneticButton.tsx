'use client';

import { useRef, useState, ReactNode } from 'react';

interface MagneticButtonProps {
    children: ReactNode;
    className?: string;
    pullStrength?: number;
}

export default function MagneticButton({
    children,
    className = '',
    pullStrength = 0.3,
}: MagneticButtonProps) {
    const ref = useRef<HTMLButtonElement>(null);
    const [position, setPosition] = useState({ x: 0, y: 0 });

    const handleMouseMove = (e: React.MouseEvent<HTMLButtonElement>) => {
        if (!ref.current) return;

        const { clientX, clientY } = e;
        const { width, height, left, top } = ref.current.getBoundingClientRect();

        // Calculate pull effect
        const x = (clientX - (left + width / 2)) * pullStrength;
        const y = (clientY - (top + height / 2)) * pullStrength;

        setPosition({ x, y });
    };

    const resetPosition = () => {
        setPosition({ x: 0, y: 0 });
    };

    return (
        <button
            ref={ref}
            className={`relative overflow-hidden transition-transform duration-300 ${className}`}
            onMouseMove={handleMouseMove}
            onMouseLeave={resetPosition}
            style={{
                transform: `translate(${position.x}px, ${position.y}px)`,
            }}
            data-cursor-hover
        >
            {children}
        </button>
    );
}