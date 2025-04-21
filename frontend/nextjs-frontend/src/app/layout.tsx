// app/layout.tsx
import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import PageWrapper from '@/components/pageWrapper';
import ConditionalNavBar from '@/components/conditionalNavbar';
import ClientCustomCursor from '@/components/clientCustomCursor';
import AuthProvider from '@/components/authProvider';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'Cursor Test',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className={`${inter.className} antialiased`}>
        <div className="sticky top-0 z-50">
          <ConditionalNavBar />
        </div>
        <ClientCustomCursor
          baseSize={28}
          stretchMultiplier={0.009}
          maxStretch={1.3}
          clickScale={0.9}
        />

        <AuthProvider>
          <PageWrapper>{children}</PageWrapper>
        </AuthProvider>
      </body>
    </html>
  );
}
