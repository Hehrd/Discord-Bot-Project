'use client';
import React, { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { useSession } from 'next-auth/react';
import AnimatedButton from '@/components/animatedButton';

type Database = { name: string };

type PagedResponse<T> = {
    content: T[];
    pageNumber: number;
    pageSize: number;
};

export default function DatabasesPage() {
    const { data: session } = useSession();
    const router = useRouter();
    const searchParams = useSearchParams();

    const containerName = searchParams.get('container');
    const page = Number(searchParams.get('page') || 0);
    const initialSize = Number(searchParams.get('size') || 5);
    const [size, setSize] = useState(initialSize);

    const [databases, setDatabases] = useState<Database[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!session || !containerName) return;

        const fetchDatabases = async () => {
            try {
                const res = await fetch(
                    `${process.env.NEXT_PUBLIC_API_BASE}/databases?container=${containerName}&page=${page}&size=${size}`,
                    {
                        headers: {
                            Authorization: `Bearer ${session.accessToken}`,
                        },
                    }
                );

                if (!res.ok) throw new Error('Failed to fetch databases');
                const data: PagedResponse<Database> = await res.json();
                setDatabases(data.content);
            } catch (err: any) {
                setError(err.message || 'Error loading databases');
            } finally {
                setLoading(false);
            }
        };

        fetchDatabases();
    }, [session, containerName, page, size]);

    const handleNext = () => {
        router.push(`/databases?container=${containerName}&page=${page + 1}&size=${size}`);
    };

    const handlePrev = () => {
        if (page > 0) {
            router.push(`/databases?container=${containerName}&page=${page - 1}&size=${size}`);
        }
    };

    const handleSizeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newSize = Math.max(1, Number(e.target.value));
        setSize(newSize);
        router.push(`/databases?container=${containerName}&page=0&size=${newSize}`);
    };

    return (
        <main className="min-h-[calc(100vh-6rem)] bg-white px-6 py-10 flex flex-col items-center">
            <h1 className="text-3xl font-bold mb-6 text-center">
                Databases in &#34;{containerName}&#34;
            </h1>

            <div className="mb-6 text-center">
                <label htmlFor="pageSize" className="block font-medium mb-2">
                    Databases per page:
                </label>
                <input
                    id="pageSize"
                    type="number"
                    min={1}
                    value={size}
                    onChange={handleSizeChange}
                    className="border border-gray-300 rounded px-3 py-1 text-center w-24"
                />
            </div>

            {loading ? (
                <p className="text-gray-600">Loading databases...</p>
            ) : error ? (
                <p className="text-red-600">{error}</p>
            ) : databases.length === 0 ? (
                <p className="text-gray-600">No databases found.</p>
            ) : (
                <>
                    <div className="flex flex-col items-center gap-4 mb-8 w-full max-w-md">
                        {databases.map((db) => (
                            <AnimatedButton
                                key={db.name}
                                onClick={() =>
                                    router.push(
                                        `/tables?container=${encodeURIComponent(containerName!)}&database=${encodeURIComponent(db.name)}&page=0&size=40`
                                    )
                                }
                            >
                                {db.name}
                            </AnimatedButton>
                        ))}
                    </div>

                    <div className="flex items-center gap-4">
                        <AnimatedButton onClick={handlePrev} >
                            Previous
                        </AnimatedButton>

                        <span className="font-medium text-gray-800">Page {page + 1}</span>

                        <AnimatedButton onClick={handleNext}>Next</AnimatedButton>
                    </div>
                </>
            )}
        </main>
    );
}
