'use client';
import React, { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { useSession } from 'next-auth/react';
import AnimatedButton from '@/components/animatedButton';
import NavLink from '@/components/navbarLink';

type Table = { name: string };

type PagedResponse<T> = {
    content: T[];
    pageNumber: number;
    pageSize: number;
};

export default function TablesPage() {
    const { data: session } = useSession();
    const router = useRouter();
    const searchParams = useSearchParams();

    const containerName = searchParams.get('container');
    const databaseName = searchParams.get('database');
    const page = Number(searchParams.get('page') || 0);
    const initialSize = Number(searchParams.get('size') || 5);
    const [size, setSize] = useState(initialSize);

    const [tables, setTables] = useState<Table[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!session || !containerName || !databaseName) return;

        const fetchTables = async () => {
            try {
                const res = await fetch(
                    `${process.env.NEXT_PUBLIC_API_BASE}/tables?container=${containerName}&database=${databaseName}&page=${page}&size=${size}`,
                    {
                        headers: {
                            Authorization: `Bearer ${session.accessToken}`,
                        },
                    }
                );

                if (!res.ok) throw new Error('Failed to fetch tables');
                const data: PagedResponse<Table> = await res.json();
                setTables(data.content);
            } catch (err: any) {
                setError(err.message || 'Error loading tables');
            } finally {
                setLoading(false);
            }
        };

        fetchTables();
    }, [session, containerName, databaseName, page, size]);

    const handleNext = () => {
        router.push(
            `/tables?container=${containerName}&database=${databaseName}&page=${page + 1}&size=${size}`
        );
    };

    const handlePrev = () => {
        if (page > 0) {
            router.push(
                `/tables?container=${containerName}&database=${databaseName}&page=${page - 1}&size=${size}`
            );
        }
    };

    const handleSizeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newSize = Math.max(1, Number(e.target.value));
        setSize(newSize);
        router.push(
            `/tables?container=${containerName}&database=${databaseName}&page=0&size=${newSize}`
        );
    };

    return (
        <main className="min-h-screen bg-white px-6 py-10 flex flex-col items-center">
            <h1 className="text-3xl font-bold mb-6 text-center">
                Tables in &#34;{databaseName}&#34;
            </h1>

            <div className="mb-6 text-center">
                <label htmlFor="pageSize" className="block font-medium mb-2">
                    Tables per page:
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
                <p className="text-gray-600">Loading tables...</p>
            ) : error ? (
                <p className="text-red-600">{error}</p>
            ) : tables.length === 0 ? (
                <p className="text-gray-600">No tables found.</p>
            ) : (
                <>
                    <div className="flex flex-col items-center gap-4 mb-8 w-full max-w-md">
                        {tables.map((table) => (
                            <NavLink
                                key={table.name}
                                href={`/tables/${encodeURIComponent(table.name)}?container=${encodeURIComponent(
                                    containerName!
                                )}&database=${encodeURIComponent(databaseName!)}&page=${page}&size=${size}`}
                                textSize="text-base"
                            >
                                {table.name}
                            </NavLink>
                        ))}
                    </div>

                    <div className="flex items-center gap-4">
                        <AnimatedButton onClick={handlePrev}>
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
