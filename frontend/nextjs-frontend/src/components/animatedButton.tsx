'use client';
import { wrap } from 'module';
import React, { useState, FC, MouseEventHandler, useMemo, useCallback } from 'react';

interface AnimatedButtonProps {
    children: React.ReactNode;
    onClick?: MouseEventHandler<HTMLButtonElement>;

    size?: number;

    width?: number | string;

    height?: number | string;
}

const AnimatedButton: FC<AnimatedButtonProps> = ({
    children,
    onClick,
    size = 1,
    width,
    height,
}) => {
    const [isHovered, setIsHovered] = useState(false);
    const [isActive, setIsActive] = useState(false);

    const interactiveScale = isHovered ? (isActive ? 0.95 : 1.05) : 1;
    const totalScale = size * interactiveScale;

    const resolvedWidth = typeof width === 'number' ? `${width}px` : width || 'auto';
    const resolvedHeight = typeof height === 'number' ? `${height}px` : height || 'auto';

    const outerStyle = useMemo(() => ({
        display: 'inline-block',
        border: '2px solid #000',
        borderRadius: '8px',
        padding: '4px',
        transition: 'transform 0.2s ease',
        transform: `scale(${totalScale})`,
        willChange: 'transform',
        width: resolvedWidth,
        height: resolvedHeight,
    }), [totalScale, resolvedWidth, resolvedHeight]);

    const buttonStyle = useMemo(() => ({
        background: isHovered ? '#fafafa' : '#ffffff',
        border: 'none',
        borderRadius: '8px',
        width: '100%',
        height: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize: '16px',
        cursor: 'pointer',
        overflow: 'hidden',
        transition: 'box-shadow 0.2s ease, background 0.2s ease',
        boxShadow: isHovered ? '0 0 12px rgba(0, 0, 0, 0.15)' : 'none',
        whiteSpace: 'nowrap',
        padding: '0 6px'
    }), [isHovered]);

    const textStyle = useMemo(() => ({
        position: 'relative',
        color: 'white',
        mixBlendMode: 'difference',
    }), []);

    const handleMouseEnter = useCallback(() => setIsHovered(true), []);
    const handleMouseLeave = useCallback(() => {
        setIsHovered(false);
        setIsActive(false);
    }, []);
    const handleMouseDown = useCallback(() => setIsActive(true), []);
    const handleMouseUp = useCallback(() => setIsActive(false), []);

    return (
        <>
            <div style={outerStyle}>
                <button
                    data-cursor-hover
                    onClick={onClick}
                    onMouseEnter={handleMouseEnter}
                    onMouseLeave={handleMouseLeave}
                    onMouseDown={handleMouseDown}
                    onMouseUp={handleMouseUp}
                    style={buttonStyle}
                >
                    <span style={textStyle}>{children}</span>
                </button>
            </div>

            <style jsx>{`
                button {
                position: relative;
                overflow: hidden;
                z-index: 1;
                }
                button::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgb(0, 0, 0);
                z-index: -1;
                transform: scaleX(0);
                transform-origin: left;
                transition: transform 0.25s ease;
                }
                button:hover::before {
                transform: scaleX(1);
                }
                button:active {
                transform: scale(0.98);
                }
            `}</style>
        </>
    );
};

export default React.memo(AnimatedButton);
