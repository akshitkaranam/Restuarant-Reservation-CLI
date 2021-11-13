package com.company.menuItem;

/**
 * This is the basic MenuItem entity that is used in MenuList and PromotionPackage
 */


public class MenuItem {
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private MenuItemType itemType;

    enum MenuItemType {
        APPETIZER, MAINCOURSE, DRINKS, DESSERT

    }

    /**
     * This constructor is used when reading from the csv file.
     *
     * @param itemName           menu item name
     * @param itemDescription    description of the menu item
     * @param itemPrice          price of the menu item
     * @param menuItemTypeString String of the MenuItemType (i.e Appetizer)
     */

    public MenuItem(String itemName, String itemDescription, double itemPrice, String menuItemTypeString) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        menuItemTypeString = menuItemTypeString.replaceAll("\\s+", "");

        if (menuItemTypeString.equalsIgnoreCase("appetizer")) {
            itemType = MenuItemType.APPETIZER;
        } else if (menuItemTypeString.equalsIgnoreCase("maincourse")) {
            itemType = MenuItemType.MAINCOURSE;
        } else if (menuItemTypeString.equalsIgnoreCase("drinks")) {
            itemType = MenuItemType.DRINKS;
        } else if (menuItemTypeString.equalsIgnoreCase("dessert")) {
            itemType = MenuItemType.DESSERT;
        }


    }

    /**
     * <li>This constructor is used to initialise MenuItem with empty values in the attributes.
     * <li>This is specially useful when initialising for the first time
     */

    public MenuItem() {
    }

    /**
     * Sets the name of the MenuItem object.
     *
     * @param itemName name of the Menu Item
     */

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the name of the MenuItem object.
     *
     * @return name of MenuItem
     */

    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the item Description
     *
     * @param itemDescription description of the MenuItem
     */

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * Returns the description of the MenuItem.
     *
     * @return description of MenuItem
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the price of the MenuItem object.
     *
     * @param itemPrice
     */

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * @return price of MenuItem
     */

    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * Sets the ItemType of the MenuItem object.
     *
     * @param itemType itemType of the MenuItem
     */
    public void setItemType(MenuItemType itemType) {
        this.itemType = itemType;
    }

    /**
     * @return MenuItemType
     */

    public MenuItemType getItemType() {
        return itemType;
    }


    /**
     * Overriding toString() Method.
     *
     * @return itemName + description + itemPrice + itemType
     */

    @Override
    public String toString() {
        return "{" +
                "itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemType=" + itemType +
                "}";
    }
}
