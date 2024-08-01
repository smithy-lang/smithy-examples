"use client"

import Image from "next/image";
import { useSearchParams } from "next/navigation";
import { Suspense } from "react";
import { getImage } from "../index";


export default function Page() {
    return (
      <Suspense>
            <main className="home">
                <Coffee></Coffee>
            </main>
      </Suspense>

    );
}

function Coffee() {
    const params = useSearchParams();
    const coffeeType = params.get("coffeeType") ?? "";
    const imageSrc = getImage(coffeeType)

    return (
        <div>
            <h2 className="text-center text-6xl">Enjoy!</h2>
            <Image className="" src={imageSrc} width={1024} height={1024} alt="coffee-display" priority/>
        </div>
    );
}
