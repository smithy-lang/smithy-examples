import { getMenuItems } from "@/app";
import MenuItem from "@/components/MenuItem";
import { CoffeeItem } from "@com.example/coffee-service-client";


const Menu = async () => {
    let menuItems: CoffeeItem[] = await getMenuItems();

    return (
        (menuItems?.length != 0) ? (
            <section>
                <div className="home_menu">
                    {menuItems?.map((item: CoffeeItem) => <MenuItem key={item.type} {...{coffeeType: item.type!, coffeeDescription: item.description!}} />)}
                </div>
            </section>
        ): (
            <div className="home_menu-error">
                <h2 className="text-xl font-bold">Could not get menu.</h2>
            </div>
        )
    )
}

export default Menu