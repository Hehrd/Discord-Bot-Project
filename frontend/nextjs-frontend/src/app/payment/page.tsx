'use client';

import { useState } from "react";
import AnimatedButton from "@/components/animatedButton";
import { useSession } from 'next-auth/react';

export default function PaymentPage() {
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const { data: session } = useSession();

    const handlePurchase = async () => {
        setLoading(true);
        try {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_BASE}/acc/apikeys/genkey`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
		    Authorization: `Bearer ${session.accessToken}`,
                },
                body: JSON.stringify({ plan: "basic" }),
            });

            if (!res.ok) throw new Error("Payment failed");

            const data = await res.text();

            if (data.includes("Successfully")) {
                setSuccess(true);
            } else {
                throw new Error("Payment unsuccessful");
            }
        } catch (err) {
            alert("Something went wrong. Try again.");
	    console.log(err);
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
