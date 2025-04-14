// components/ClientCustomCursor.tsx
'use client';
import dynamic from 'next/dynamic';
import type { CustomCursorProps } from './customCursor';

const CustomCursor = dynamic(() => import('./customCursor'), { ssr: false });

export default function ClientCustomCursor(props: CustomCursorProps) {
    return <CustomCursor {...props} />;
}
