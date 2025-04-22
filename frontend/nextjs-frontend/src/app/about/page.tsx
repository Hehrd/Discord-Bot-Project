import Image from 'next/image';
import { Instagram } from 'lucide-react';

export default function AboutPage() {
    return (
        <div className="min-h-[calc(100vh-6rem)] p-6 max-w-3xl mx-auto text-center space-y-6">
            <h1 className="text-4xl font-bold">About Us</h1>
            <p className="text-lg text-gray-700">The people behind the app</p>

            <div className="flex flex-col md:flex-row justify-center gap-10 mt-6">
                <div className="w-48 h-64 relative rounded-2xl overflow-hidden shadow-md transform transition duration-300 hover:scale-105 hover:ring-2 hover:ring-gray-300">
                    <Image src="/ali.png" alt="Ali" fill className="object-cover" />
                </div>
                <div className="w-48 h-64 relative rounded-2xl overflow-hidden shadow-md transform transition duration-300 hover:scale-105 hover:ring-2 hover:ring-gray-300">
                    <Image src="/gogo.png" alt="Gogo" fill className="object-cover" />
                </div>
            </div>

            <div className="flex flex-col md:flex-row justify-center gap-10 items-center mt-4">
                <div className="flex items-center gap-2">
                    <p className="font-medium">Aleksandar</p>
                    <a
                        href="https://www.instagram.com/ali_dqnkov/"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-pink-500 hover:text-pink-600 transition"
                    >
                        <Instagram size={20} />
                    </a>
                </div>

                <div className="flex items-center gap-2">
                    <p className="font-medium">Georgi</p>
                    <a
                        href="https://www.instagram.com/frogi_gogi/"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-pink-500 hover:text-pink-600 transition"
                    >
                        <Instagram size={20} />
                    </a>
                </div>
            </div>
        </div>
    );
}
