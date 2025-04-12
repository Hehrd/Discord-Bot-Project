// components/CustomCursor.tsx
'use client';
import { useEffect, useRef } from 'react';

export interface CustomCursorProps {
    baseSize?: number;
    stretchMultiplier?: number;
    maxStretch?: number;
    clickScale?: number;
}

export default function CustomCursor({
    baseSize = 34,
    stretchMultiplier = 0.007,
    maxStretch = 1.5,
    clickScale = 0.8,
}: CustomCursorProps) {
    const cursorRef = useRef<HTMLDivElement>(null);
    const innerFillRef = useRef<HTMLDivElement>(null);

    // Stores the latest mouse coordinates.
    const cursorPosRef = useRef({ x: 0, y: 0 });
    const isHoveringButtonRef = useRef(false);
    const isClickingRef = useRef(false);
    const angleRef = useRef(0);

    // On mount, try to read the last mouse position from localStorage.
    useEffect(() => {
        const storedPos = localStorage.getItem('lastMousePos');
        if (storedPos) {
            try {
                const pos = JSON.parse(storedPos);
                cursorPosRef.current = pos;
                if (cursorRef.current) {
                    (cursorRef.current as any).displayPos = { ...pos };
                    cursorRef.current.style.left = `${pos.x}px`;
                    cursorRef.current.style.top = `${pos.y}px`;
                }
            } catch (err) {
                console.error('Error parsing stored mouse position:', err);
            }
        }
    }, []);

    // Mouse event listeners update refs and store coordinates.
    useEffect(() => {
        const handleMouseMove = (e: MouseEvent) => {
            const pos = { x: e.clientX, y: e.clientY };
            cursorPosRef.current = pos;
            localStorage.setItem('lastMousePos', JSON.stringify(pos));

            const target = e.target as HTMLElement;
            const interactiveElement = target.closest(
                'button, [role="button"], a, input[type="submit"], input[type="button"], [data-cursor-hover]'
            );
            isHoveringButtonRef.current = !!interactiveElement;
        };

        const handleMouseDown = () => {
            isClickingRef.current = true;
        };

        const handleMouseUp = () => {
            isClickingRef.current = false;
        };

        window.addEventListener('mousemove', handleMouseMove);
        window.addEventListener('mousedown', handleMouseDown);
        window.addEventListener('mouseup', handleMouseUp);

        return () => {
            window.removeEventListener('mousemove', handleMouseMove);
            window.removeEventListener('mousedown', handleMouseDown);
            window.removeEventListener('mouseup', handleMouseUp);
        };
    }, []);

    // Animate the cursor movement.
    useEffect(() => {
        let animationFrame: number;
        const cursorElem = cursorRef.current;
        if (cursorElem && !(cursorElem as any).displayPos) {
            (cursorElem as any).displayPos = { ...cursorPosRef.current };
        }

        const animate = () => {
            if (cursorElem) {
                const displayPos = (cursorElem as any).displayPos as { x: number; y: number };
                const { x, y } = cursorPosRef.current;
                const dx = x - displayPos.x;
                const dy = y - displayPos.y;
                angleRef.current = Math.atan2(dy, dx);

                displayPos.x += dx * 0.3;
                displayPos.y += dy * 0.3;

                const distance = Math.hypot(dx, dy);
                const stretchFactor = Math.min(1 + distance * stretchMultiplier, maxStretch);
                const sizeMultiplier = isClickingRef.current ? clickScale : 1;
                const fillPercentage = isHoveringButtonRef.current && !isClickingRef.current ? '75%' : '0%';

                cursorElem.style.left = `${displayPos.x}px`;
                cursorElem.style.top = `${displayPos.y}px`;
                cursorElem.style.width = `${baseSize * sizeMultiplier}px`;
                cursorElem.style.height = `${baseSize * sizeMultiplier}px`;
                cursorElem.style.transform = `
          translate(-50%, -50%)
          rotate(${angleRef.current}rad)
          scale(${stretchFactor}, ${1 / stretchFactor})
        `;

                if (innerFillRef.current) {
                    innerFillRef.current.style.width = fillPercentage;
                    innerFillRef.current.style.height = fillPercentage;
                }
            }
            animationFrame = requestAnimationFrame(animate);
        };

        animationFrame = requestAnimationFrame(animate);
        return () => cancelAnimationFrame(animationFrame);
    }, [baseSize, stretchMultiplier, maxStretch, clickScale]);

    return (
        <div
            ref={cursorRef}
            style={{
                position: 'fixed',
                left: '0px',
                top: '0px',
                width: '38px',
                height: '38px',
                backgroundColor: 'transparent',
                borderRadius: '50%',
                border: '2px solid white',
                pointerEvents: 'none',
                zIndex: 9999,
                mixBlendMode: 'difference',
                filter: 'drop-shadow(0 0 2px rgba(0,0,0,0.5))',
                willChange: 'transform',
                boxSizing: 'border-box',
                transition: 'width 0.1s ease, height 0.1s ease'
            }}
            aria-hidden="true"
        >
            <div
                ref={innerFillRef}
                className="cursor-inner"
                style={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: '0%',
                    height: '0%',
                    backgroundColor: 'white',
                    borderRadius: '50%',
                    transition: 'width 0.2s ease, height 0.2s ease'
                }}
            />
        </div>
    );
}
