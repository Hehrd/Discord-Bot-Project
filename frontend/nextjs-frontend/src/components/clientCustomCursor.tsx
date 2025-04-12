// components/ClientCustomCursor.tsx
'use client';
import dynamic from 'next/dynamic';
import type { CustomCursorProps } from './customCursor';

// Dynamically import the CustomCursor component with SSR disabled.
const CustomCursor = dynamic(() => import('./customCursor'), { ssr: false });

export default function ClientCustomCursor(props: CustomCursorProps) {
    return <CustomCursor {...props} />;
}
