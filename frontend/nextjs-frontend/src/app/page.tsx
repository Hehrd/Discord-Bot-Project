'use client';
import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import AnimatedButton from '@/components/animatedButton';

type Table = { name: string };
type Database = { name: string; tables: Table[] };
type Container = { name: string; databases: Database[] };

const containers: Container[] = [
  {
    name: 'Container A',
    databases: [
      { name: 'AuthDB', tables: [{ name: 'Users' }, { name: 'Roles' }] },
      { name: 'LogsDB', tables: [{ name: 'AccessLogs' }, { name: 'ErrorLogs' }] },
    ],
  },
  {
    name: 'Container B',
    databases: [
      { name: 'ShopDB', tables: [{ name: 'Products' }, { name: 'Orders' }, { name: 'Customers' }] },
      { name: 'SupportDB', tables: [{ name: 'Tickets' }, { name: 'Agents' }] },
    ],
  },
];

export default function Home() {
  const [selectedContainer, setSelectedContainer] = useState<Container | null>(null);
  const [selectedDatabase, setSelectedDatabase] = useState<Database | null>(null);

  const handleContainerClick = (name: string) => {
    const isAlreadySelected = selectedContainer?.name === name;
    setSelectedContainer(isAlreadySelected ? null : containers.find((c) => c.name === name) || null);
    setSelectedDatabase(null);
  };

  const handleDatabaseClick = (name: string) => {
    if (!selectedContainer) return;
    const isAlreadySelected = selectedDatabase?.name === name;
    setSelectedDatabase(isAlreadySelected ? null : selectedContainer.databases.find((d) => d.name === name) || null);
  };

  return (
    <main className="min-h-screen bg-white px-6 py-10">
      <div className="max-w-4xl mx-auto pt-24 flex flex-col items-center">

        {containers.length === 0 ? (
          <div className="text-center text-gray-600 text-lg">
            No containers available.
          </div>
        ) : (
          <>
            <motion.h2 layout className="text-2xl font-bold mb-4 text-center">
              Select a Container
            </motion.h2>
            <motion.div layout className="flex flex-wrap justify-center gap-4 mb-8">
              {containers.map((container) => (
                <AnimatedButton
                  key={container.name}
                  onClick={() => handleContainerClick(container.name)}
                >
                  {container.name}
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
                    Databases in {selectedContainer.name}
                  </h2>
                  <div className="flex flex-wrap justify-center gap-4 mb-6">
                    {selectedContainer.databases.map((db) => (
                      <AnimatedButton
                        key={db.name}
                        onClick={() => handleDatabaseClick(db.name)}
                      >
                        {db.name}
                      </AnimatedButton>
                    ))}
                  </div>
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
                    Tables in {selectedDatabase.name}
                  </h2>
                  <motion.ul
                    className="list-disc list-inside max-w-md mx-auto space-y-1 text-center"
                    layout
                  >
                    {selectedDatabase.tables.map((table, i) => (
                      <motion.li
                        key={table.name}
                        initial={{ opacity: 0, x: -10 }}
                        animate={{ opacity: 1, x: 0 }}
                        transition={{ delay: i * 0.05 }}
                      >
                        {table.name}
                      </motion.li>
                    ))}
                  </motion.ul>
                </motion.div>
              )}
            </AnimatePresence>
          </>
        )}
      </div>
    </main>
  );
}
