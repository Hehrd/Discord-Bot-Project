'use client';
import React from 'react';
import { Users, Settings } from 'lucide-react';

export default function FeaturesPage() {
    return (
        <main className="min-h-[calc(100vh-6rem)] max-w-3xl mx-auto px-6 py-10 space-y-12">
            <h1 className="text-4xl font-bold text-center mb-6">Features</h1>

            <section className="space-y-3">
                <div className="flex items-center gap-3 text-xl font-semibold">
                    <Users size={20} className="text-blue-500" />
                    <span>Feature One</span>
                </div>
                <p className="text-gray-600">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet bibendum justo.
                </p>
            </section>

            <section className="space-y-3">
                <div className="flex items-center gap-3 text-xl font-semibold">
                    <Settings size={20} className="text-green-500" />
                    <span>Feature Two</span>
                </div>
                <p className="text-gray-600">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet bibendum justo.
                </p>
            </section>

            <p className="text-center text-gray-500 italic pt-8">
                More features and detailed usage guides coming soon.
            </p>
        </main>
    );
}
