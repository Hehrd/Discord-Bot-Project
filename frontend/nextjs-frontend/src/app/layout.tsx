// src/app/layout.tsx
import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import ClientCustomCursor from '../components/clientCustomCursor';
import ConditionalNavBar from '@/components/conditionalNavbar';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'Cursor Test',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className={`${inter.className} antialiased`}>
        <ConditionalNavBar />
        <ClientCustomCursor
          baseSize={33}
          stretchMultiplier={0.009}
          maxStretch={1.5}
          clickScale={0.9}
        />
        {children}
      </body>
    </html>
  );
}
