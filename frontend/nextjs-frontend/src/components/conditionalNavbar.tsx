'use client';
import { usePathname } from 'next/navigation';
import NavBar from '@/components/navbar';

const ConditionalNavBar: React.FC = () => {
    const pathname = usePathname();

    if (pathname === '/login') {

    }
    else {
        return <NavBar />;
    }

};

export default ConditionalNavBar;
