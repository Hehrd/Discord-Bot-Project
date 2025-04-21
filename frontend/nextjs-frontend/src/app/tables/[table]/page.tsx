'use client';

import { useParams, useSearchParams, useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import { useSession } from 'next-auth/react';


const parsePlainTextTable = (text: string) => {
    const lines = text.trim().split('\n');
    if (lines.length < 2) return [];
    const headers = lines[0].split('|').map((h) => h.trim());
    return lines.slice(2).map((line) => {
        const cols = line.split('|').map((c) => c.trim());
        return Object.fromEntries(headers.map((h, i) => [h, cols[i]]));
    });
};

const fetchTable = (
    apiBase: string,
    accessToken: string,
    container: string,
    database: string,
    table: string,
    page: number,
    size: number
) => {
    const url = new URL('/tables/rows', apiBase);
    url.searchParams.set('container', container);
    url.searchParams.set('database', database);
    url.searchParams.set('table', table);
    url.searchParams.set('page', page.toString());
    url.searchParams.set('size', size.toString());

    return fetch(url.toString(), {
        headers: {
            Authorization: `Bearer ${accessToken}`,
            'Content-Type': 'text/plain',
        },
    }).then((res) => {
        if (!res.ok) {
            const err: any = new Error(`HTTP ${res.status}`);
            err.status = res.status;
            throw err;
        }
        return res.text();
    });
};

export default function TablePage() {
    const { table } = useParams<{ table: string }>();
    const searchParams = useSearchParams();
    const router = useRouter();
    const { data: session, status } = useSession();

    const container = searchParams.get('container') ?? '';
    const database = searchParams.get('database') ?? '';
    const page = parseInt(searchParams.get('page') || '0');
    const [size, setSize] = useState(10);

    const [rows, setRows] = useState<any[]>([]);
    const [totalPages, setTotalPages] = useState(1);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (status !== 'authenticated' || !table || !container || !database) return;
        setLoading(true);
        setError(null);

        fetchTable(
            process.env.NEXT_PUBLIC_API_BASE!,
            session.accessToken as string,
            container,
            database,
            table,
            page,
            size
        )
            .then((txt) => {
                const parsed = parsePlainTextTable(txt);
                setRows(parsed);
                setTotalPages(Math.max(1, Math.ceil(parsed.length / size)));
            })
            .catch((err: any) =>
                setError(err.status === 404 ? 'No such table.' : "Couldn't fetch table.")
            )
            .finally(() => setLoading(false));
    }, [status, session, container, database, table, page, size]);

    const updateUrl = (newPage: number, newSize = size) => {
        const p = new URLSearchParams();
        p.set('container', container);
        p.set('database', database);
        p.set('page', newPage.toString());
        p.set('size', newSize.toString());
        router.push(`/tables/${table}?${p.toString()}`);
    };

    if (status === 'loading') return <p className="p-10">Loading session…</p>;
    if (status === 'unauthenticated') return <p className="p-10">Please log in.</p>;

    return (
        <>
            <div className="min-h-[calc(100vh-6rem)] px-8 py-16 bg-white dark:bg-black text-gray-800 dark:text-white">
                <div className="max-w-4xl mx-auto">

                    <h1 className="text-3xl font-bold capitalize text-center mb-6">
                        {container} / {database} / {table}
                    </h1>

                    {loading && <p className="text-center py-10">Loading rows…</p>}
                    {error && <p className="text-center text-red-500 py-10">{error}</p>}

                    {!loading && !error && (
                        <>
                            <div className="overflow-x-auto border border-gray-200 dark:border-gray-700 rounded-xl shadow-sm mx-auto">
                                <table className="w-max table-auto text-center">
                                    <thead className="bg-gray-100 dark:bg-zinc-900">
                                        <tr>
                                            {rows.length > 0 &&
                                                Object.keys(rows[0]).map((k) => (
                                                    <th key={k} className="px-6 py-3 text-sm font-semibold uppercase tracking-wider text-gray-600 dark:text-gray-300">
                                                        {k}
                                                    </th>
                                                ))}
                                        </tr>
                                    </thead>
                                    <tbody className="divide-y divide-gray-200 dark:divide-gray-800">
                                        {rows.map((row, i) => (
                                            <tr key={i} className="hover:bg-gray-50 dark:hover:bg-zinc-800 transition">
                                                {Object.values(row).map((val, j) => (
                                                    <td key={j} className="px-6 py-4 whitespace-nowrap text-sm">{String(val)}</td>
                                                ))}
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>

                            <div className="mt-6 flex justify-between items-center gap-4">
                                <div className="flex items-center gap-2 text-sm">
                                    <label htmlFor="sizeInput">Rows /page:</label>
                                    <input
                                        id="sizeInput"
                                        type="number"
                                        min={1}
                                        step={1}
                                        className="w-20 border border-gray-300 rounded px-2 py-1"
                                        value={size}
                                        onChange={(e) => {
                                            const n = Math.max(1, parseInt(e.target.value) || 1);
                                            setSize(n);
                                            updateUrl(0, n);
                                        }}
                                    />
                                </div>

                                <div className="flex items-center gap-4">
                                    <button
                                        disabled={page <= 0}
                                        onClick={() => updateUrl(page - 1)}
                                        className="px-4 py-2 rounded-md bg-gray-200 dark:bg-zinc-700 hover:bg-gray-300 dark:hover:bg-zinc-600 disabled:opacity-40"
                                    >Prev</button>
                                    <span className="text-sm">
                                        Page <strong>{page + 1}</strong> of <strong>{totalPages}</strong>
                                    </span>
                                    <button
                                        disabled={page >= totalPages - 1}
                                        onClick={() => updateUrl(page + 1)}
                                        className="px-4 py-2 rounded-md bg-gray-200 dark:bg-zinc-700 hover:bg-gray-300 dark:hover:bg-zinc-600 disabled:opacity-40"
                                    >Next</button>
                                </div>
                            </div>
                        </>
                    )}
                </div>
            </div>

            <style jsx global>{`
        input[type=number]::-webkit-outer-spin-button,
        input[type=number]::-webkit-inner-spin-button { -webkit-appearance:none; margin:0 }
        input[type=number] { -moz-appearance:textfield }
      `}</style>
        </>
    );
}
