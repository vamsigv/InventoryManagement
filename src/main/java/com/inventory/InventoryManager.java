package com.inventory;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by vamsi on 8/28/18.
 */
@Getter
@Setter
class InventoryManager {

    private static Map<Item, Integer> inventoryData = new LinkedHashMap<>();

    protected static void initialize() {
        inventoryData = new LinkedHashMap<>();
    }

    /**
     *
     * @param item
     * @param count
     * @return true if inventory is updated
     */
    protected static boolean updateInventory(Item item, Integer count) {
        if (Objects.isNull(inventoryData)) {
            return false;
        }
        if (inventoryData.containsKey(item)) {
            inventoryData.put(item, inventoryData.get(item) + count);
        } else {
            inventoryData.put(item, count);
        }
        return true;
    }

    /**
     * @param item
     * @param count
     * @return true if item is available else false
     */
    protected static boolean isStockAvailable(Item item, Integer count) {
        if (Objects.isNull(inventoryData)) {
            return false;
        }
        if (inventoryData.containsKey(item)) {
            Integer availableCount = inventoryData.get(item);
            if (count > availableCount) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     *
     * @param item
     * @param count
     * @return true if checkout is successful
     */
    protected static boolean checkoutItems(Item item, Integer count) {
        boolean isStockAvailable = isStockAvailable(item, count);
        if (!isStockAvailable || !inventoryData.containsKey(item)) {
            return false;
        }
        Integer countAvailable = inventoryData.get(item);
        if (count > countAvailable) {
            return false;
        }
        countAvailable -= count;
        inventoryData.put(item, countAvailable);
        return true;
    }

    /**
     *
     * @param name
     * @return the Item object if already present else NULL
     */
    public static Item getItemByName(String name) {
        if (Objects.nonNull(inventoryData) && inventoryData.size() > 0) {
            boolean isKeyPresent = inventoryData.containsKey(new Item(name));
            if (isKeyPresent) {
                return new Item(name);
            }
        }
        return null;
    }

    /**
     *
     * @return new unique ID to the item
     */
    public static int generateNewID() {
        return inventoryData.size() + 1;
    }

    /**
     * Display the inventory report
     * Id | Name | Price | Quantity
     */
    public static void generateInventoryReport() {
        System.out.println("**Inventory Report**");
        System.out.println("Id | Name | Cost | Quantity");
        StringBuffer displayString = new StringBuffer();
        for (Map.Entry<Item, Integer> eachItem : inventoryData.entrySet()) {
            displayString.append(eachItem.getKey().getId()).append(" | ").append(eachItem.getKey().getName())
                    .append(" | ").append(eachItem.getKey().getCost()).append(" | ").append(eachItem.getValue());
            displayString.append("\n");
        }
        System.out.println(displayString.toString());
    }
}
