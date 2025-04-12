'use client';
import React, { useState, FC, MouseEventHandler, useMemo, useCallback } from 'react';

interface AnimatedButtonProps {
    children: React.ReactNode;
    onClick?: MouseEventHandler<HTMLButtonElement>;
    /**
     * Base size factor for the button.
     * 1 is normal size, values below 1 shrink the button, above 1 enlarge it.
     */
    size?: number;
}

const AnimatedButton: FC<AnimatedButtonProps> = ({ children, onClick, size = 1 }) => {
    const [isHovered, setIsHovered] = useState(false);
    const [isActive, setIsActive] = useState(false);

    // Compute the interactive scale factor based on hover/active state.
    const interactiveScale = isHovered ? (isActive ? 0.95 : 1.05) : 1;
    const totalScale = size * interactiveScale;

    // Dynamic styles for the outer container.
    const outerStyle = useMemo(() => ({
        display: 'inline-block',
        border: '2px solid #000',
        borderRadius: '8px',
        padding: '4px',
        transition: 'transform 0.2s ease',
        transform: `scale(${totalScale})`,
        willChange: 'transform'
    }), [totalScale]);

    // Dynamic styles for the inner button.
    const buttonStyle = useMemo(() => ({
        background: isHovered ? '#fafafa' : '#ffffff',
        border: 'none', // inner button has no border; the outer container provides it
        borderRadius: '8px',
        padding: '12px 24px',
        fontSize: '16px',
        cursor: 'pointer',
        overflow: 'hidden',
        transition: 'box-shadow 0.2s ease, background 0.2s ease',
        boxShadow: isHovered ? '0 0 12px rgba(0, 0, 0, 0.15)' : 'none',
    }), [isHovered]);

    // Dynamic text styling.
    const textStyle = useMemo(() => ({
        position: 'relative',
        color: 'white',
        mixBlendMode: 'difference',
    }), []);

    // Stable event handlers.
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
            {/* Inline the pseudo-element styles using styled JSX */}
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
