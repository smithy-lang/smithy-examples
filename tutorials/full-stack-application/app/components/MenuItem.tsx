import Image from "next/image";
import { CoffeeType } from "@com.example/coffee-shop-client"  
import Order from "./Order";
import { displayName, getImage } from "@/app";

interface MenuItemProps {
    coffeeType: CoffeeType
    coffeeDescription: string
}

const MenuItem = (item: MenuItemProps) => {
    const imageSrc = getImage(item.coffeeType)
    return (
        <div className="menu-item_card">
            <div className="menu-item_title">
                <h2 className="menu-item_title_text">
                    {displayName(item.coffeeType)}
                </h2>
            </div>

            <div className="menu-item_content">
                <div className="menu-item_image_container">
                    <Image className="menu-item_image" src={imageSrc} width={256} height={256} alt="coffee-display" priority/>
                </div>

                <div className="menu-item_box">
                    <p className="menu-item_box_description">
                        <span>
                            Description
                        </span>
                    </p>
                    <ul className="menu-item_box_list">
                        {item.coffeeDescription.split("\n").map((text, i) => <li key={item.coffeeType+i}>{text}</li>)}
                    </ul>
                    <div className="font-bold">
                        <Order coffeeType={item.coffeeType}></Order>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default MenuItem
