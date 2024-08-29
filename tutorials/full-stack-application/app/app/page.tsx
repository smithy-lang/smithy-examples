import Menu from "@/components/Menu";
import Image from "next/image";

export const dynamic = 'force-dynamic'

export default async function Page() {

    return (
        <main className="home">
            <div className="home_header">
                <div className="home_header_content">
                    <h2 className="mb-3 text-2xl font font-sans">
                        MyCafe{" "}
                    </h2>
                    <Image
                        src="/coffee-cup.png"
                        alt="Logo"
                        className=""
                        width={100}
                        height={24}
                        priority
                    />
                </div>
            </div>
            <div className="title_container">
                <h1 className="title text-4xl font-sans font-medium">
                    Enjoy a nice cup of joe!
                </h1>
            </div>
            <Menu></Menu>
        </main>
    );
}
