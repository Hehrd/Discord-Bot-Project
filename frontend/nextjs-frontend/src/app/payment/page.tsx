'use client';

import { useState } from "react";
import AnimatedButton from "@/components/animatedButton";

export default function PaymentPage() {
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);

    const handlePurchase = async () => {
        setLoading(true);
        try {
            const res = await fetch("/api/payment/start", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ plan: "basic" }),
            });

            if (!res.ok) throw new Error("Payment failed");

            const data = await res.json();

            if (data.success) {
                setSuccess(true);
            } else {
                throw new Error("Payment unsuccessful");
            }
        } catch (err) {
            alert("Something went wrong. Try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex min-h-[calc(100vh-6rem)] justify-center p-4">
            <div className="w-full max-w-md rounded-2xl p-8 bg-white flex flex-col gap-6 mt-20">
                <h2 className="text-2xl font-bold text-center">Get Your API Key</h2>
                <p className="text-center">Purchase access to generate your key.</p>
                <AnimatedButton onClick={handlePurchase} width="100%" height="56px">
                    {loading ? "Processing..." : "Purchase API Access"}
                </AnimatedButton>
                {success && (
                    <p className="text-center text-green-600 font-semibold">
                        Thank you for your support! Your API key has been sent to your email.
                    </p>
                )}
            </div>
        </div>
    );
}
