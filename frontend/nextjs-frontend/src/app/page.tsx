'use client';

import { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import AnimatedButton from '@/components/animatedButton';
import NavLink from '@/components/navbarLink';

type Container = { name: string };
type Database = { name: string };
type Table = { name: string };

export default function Home() {
  /* data */
  const [containers, setContainers] = useState<Container[]>([]);
  const [databases, setDatabases] = useState<Database[]>([]);
  const [tables, setTables] = useState<Table[]>([]);

  /* selections */
  const [selectedContainer, setSelectedContainer] = useState<string | null>(null);
  const [selectedDatabase, setSelectedDatabase] = useState<string | null>(null);

  /* loading / error flags */
  const [loadingContainers, setLoadingContainers] = useState(true);
  const [loadingDatabases, setLoadingDatabases] = useState(false);
  const [loadingTables, setLoadingTables] = useState(false);
  const [error, setError] = useState<string | null>(null);


  useEffect(() => {
    fetch(`${process.env.NEXT_PUBLIC_API_BASE}/api/containers`)
      .then((r) => {
        if (!r.ok) throw new Error('Failed to fetch containers');
        return r.json();
      })
      .then(setContainers)
      .catch((e: any) => setError(e.message || 'Error loading containers'))
      .finally(() => setLoadingContainers(false));
  }, []);

  useEffect(() => {
    if (!selectedContainer) return;
    setLoadingDatabases(true);
    setSelectedDatabase(null);
    setTables([]);

    fetch(
      `${process.env.NEXT_PUBLIC_API_BASE}/api/containers/${encodeURIComponent(
        selectedContainer
      )}/databases`
    )
      .then((r) => {
        if (!r.ok) throw new Error('Failed to fetch databases');
        return r.json();
      })
      .then(setDatabases)
      .catch((e: any) => setError(e.message || 'Error loading databases'))
      .finally(() => setLoadingDatabases(false));
  }, [selectedContainer]);

  useEffect(() => {
    if (!selectedContainer || !selectedDatabase) return;
    setLoadingTables(true);

    fetch(
      `${process.env.NEXT_PUBLIC_API_BASE}/api/containers/${encodeURIComponent(
        selectedContainer
      )}/databases/${encodeURIComponent(selectedDatabase)}/tables`
    )
      .then((r) => {
        if (!r.ok) throw new Error('Failed to fetch tables');
        return r.json();
      })
      .then(setTables)
      .catch((e: any) => setError(e.message || 'Error loading tables'))
      .finally(() => setLoadingTables(false));
  }, [selectedDatabase, selectedContainer]);

  return (
    <main className="min-h-[calc(100vh-6rem)] bg-white px-6 py-8">
      <div className="max-w-4xl mx-auto pt-24 flex flex-col items-center">
        {loadingContainers ? (
          <p className="text-gray-600 text-lg">Loading containers…</p>
        ) : error ? (
          <p className="text-red-600 text-lg">{error}</p>
        ) : (
          <>
            <motion.h2 layout className="text-2xl font-bold mb-4 text-center">
              Select a Container
            </motion.h2>
            <motion.div layout className="flex flex-wrap justify-center gap-4 mb-8">
              {containers.map((c) => (
                <AnimatedButton
                  key={c.name}
                  onClick={() =>
                    setSelectedContainer((prev) => (prev === c.name ? null : c.name))
                  }
                >
                  {c.name}
                </AnimatedButton>
              ))}
            </motion.div>

            <AnimatePresence mode="wait">
              {selectedContainer && (
                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  exit={{ opacity: 0, y: -10 }}
                  transition={{ duration: 0.3 }}
                >
                  <h2 className="text-xl font-semibold mb-3 text-center">
                    Databases in {selectedContainer}
                  </h2>
                  {loadingDatabases ? (
                    <p className="text-gray-600 text-center mb-4">Loading databases…</p>
                  ) : (
                    <div className="flex flex-wrap justify-center gap-4 mb-6">
                      {databases.map((db) => (
                        <AnimatedButton
                          key={db.name}
                          onClick={() =>
                            setSelectedDatabase((prev) => (prev === db.name ? null : db.name))
                          }
                        >
                          {db.name}
                        </AnimatedButton>
                      ))}
                    </div>
                  )}
                </motion.div>
              )}
            </AnimatePresence>

            <AnimatePresence mode="wait">
              {selectedDatabase && (
                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  exit={{ opacity: 0, y: -10 }}
                  transition={{ duration: 0.3 }}
                >
                  <h2 className="text-xl font-semibold mb-2 text-center">
                    Tables in {selectedDatabase}
                  </h2>
                  {loadingTables ? (
                    <p className="text-gray-600 text-center">Loading tables…</p>
                  ) : (
                    <motion.ul
                      className="list-disc list-inside max-w-md mx-auto space-y-1 text-center"
                      layout
                    >
                      {tables.map((t, i) => (
                        <motion.li
                          key={t.name}
                          initial={{ opacity: 0, x: -10 }}
                          animate={{ opacity: 1, x: 0 }}
                          transition={{ delay: i * 0.05 }}
                        >
                          <NavLink
                            href={`/tables/${encodeURIComponent(
                              t.name.toLowerCase()
                            )}?container=${encodeURIComponent(
                              selectedContainer!
                            )}&database=${encodeURIComponent(selectedDatabase!)}`}
                            textSize="text-base"
                          >
                            {t.name}
                          </NavLink>
                        </motion.li>
                      ))}
                    </motion.ul>
                  )}
                </motion.div>
              )}
            </AnimatePresence>
          </>
        )}
      </div>
    </main>
  );
}
