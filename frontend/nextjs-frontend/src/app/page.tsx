// src/app/page.tsx
'use client';
import React, { useState } from 'react';

type Column = {
  name: string;
  type: string;
};

type Row = {
  [key: string]: string | number;
};

type Database = {
  name: string;
  columns: Column[];
  rows: Row[];
};

export default function Home() {
  // Mocked backend response with two databases
  const databases: Database[] = [
    {
      name: 'User Database',
      columns: [
        { name: 'ID', type: 'number' },
        { name: 'Name', type: 'string' },
        { name: 'Email', type: 'string' },
        { name: 'Role', type: 'string' },
      ],
      rows: [
        { ID: 1, Name: 'Alice', Email: 'alice@example.com', Role: 'Admin' },
        { ID: 2, Name: 'Bob', Email: 'bob@example.com', Role: 'User' },
        { ID: 3, Name: 'Charlie', Email: 'charlie@example.com', Role: 'Moderator' },
      ],
    },
    {
      name: 'Orders Database',
      columns: [
        { name: 'OrderID', type: 'number' },
        { name: 'Customer', type: 'string' },
        { name: 'Total', type: 'number' },
        { name: 'Status', type: 'string' },
      ],
      rows: [
        { OrderID: 101, Customer: 'Alice', Total: 123.45, Status: 'Completed' },
        { OrderID: 102, Customer: 'Bob', Total: 67.89, Status: 'Processing' },
        { OrderID: 103, Customer: 'Charlie', Total: 200.0, Status: 'Pending' },
      ],
    },
  ];

  const [selectedDb, setSelectedDb] = useState<Database>(databases[0]);

  const handleDatabaseChange = (dbName: string) => {
    const db = databases.find((d) => d.name === dbName);
    if (db) setSelectedDb(db);
  };

  return (
    <main className="container mx-auto p-4">
      <nav className="flex gap-4 bg-gray-100 text-gray-700 mt-4 mb-4 py-2 px-4 rounded">
        {databases.map((db) => (
          <button
            key={db.name}
            onClick={() => handleDatabaseChange(db.name)}
            className={`py-1 px-3 rounded transition-colors ${selectedDb.name === db.name
              ? 'bg-blue-500 text-white'
              : 'hover:bg-blue-200'
              }`}
          >
            {db.name}
          </button>
        ))}
      </nav>

      <h1 className="text-3xl font-bold mb-4">{selectedDb.name}</h1>
      <table className="min-w-full border border-gray-300">
        <thead>
          <tr className="bg-gray-200">
            {selectedDb.columns.map((col) => (
              <th
                key={col.name}
                className="px-4 py-2 border border-gray-300 text-left"
              >
                {col.name}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {selectedDb.rows.map((row, index) => (
            <tr key={index} className="even:bg-gray-100">
              {selectedDb.columns.map((col) => (
                <td
                  key={col.name}
                  className="px-4 py-2 border border-gray-300"
                >
                  {row[col.name]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  );
}
