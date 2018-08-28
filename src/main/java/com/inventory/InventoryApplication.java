package com.inventory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created by vamsi on 8/28/18.
 */
public class InventoryApplication {

    /**
     *
     * @param itemName
     * @param quantity
     * @param cost
     * @return true if inventory is updated
     */
    public static boolean insertItem(String itemName, int quantity, double cost) {
        itemName = itemName.toUpperCase();
        Item item = InventoryManager.getItemByName(itemName);
        if (Objects.isNull(item)) {
            item = new Item(itemName);
            item.setCost(cost);
            item.setId(InventoryManager.generateNewID());
        }
        if (InventoryManager.updateInventory(item, quantity)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param itemName
     * @param quantity
     * @return true if checkout is successful
     */
    public static boolean checkoutItem(String itemName, int quantity){
        itemName = itemName.toUpperCase();
        if (InventoryManager.checkoutItems(new Item(itemName), quantity)) {
            return true;
        }
        return false;
    }

    /**
     * Generates the inventory report which contains the details of all the items
     */
    public static void displayInventoryReport(){
        InventoryManager.generateInventoryReport();
    }

    /**
     * Main class to test all scenarios
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int choice;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String itemName;
        int quantity;
        double cost;
        Item item;
        while (true) {
            System.out.println("Please enter the choice. 1. New Item, 2. Checkout, 3.Inventory Report");
            choice = Integer.parseInt(bufferedReader.readLine());
            switch (choice) {
            case 1:
                System.out.println("Enter the item name which has come to stock");
                itemName = bufferedReader.readLine();
                System.out.println("Enter the price of the item");
                cost = Double.parseDouble(bufferedReader.readLine());
                System.out.println("Enter the Quantity");
                quantity = Integer.parseInt(bufferedReader.readLine());
                boolean isItemInserted = insertItem(itemName,quantity,cost);
                if (isItemInserted) {
                    System.out.println("Inventory is updated successfully");
                } else {
                    System.out.println("Inventory updation is failed");
                }
                break;
            case 2:
                System.out.println("Enter the checkout item");
                itemName = bufferedReader.readLine();
                System.out.println("Enter the Quantity");
                quantity = Integer.parseInt(bufferedReader.readLine());
                boolean isCheckoutSuccessful = checkoutItem(itemName, quantity);
                if (isCheckoutSuccessful) {
                    System.out.println("Items are checked out successfully! Thank you!");
                } else {
                    System.out.println("Items are not available in stock. Sorry!");
                }
                break;
            case 3:
                displayInventoryReport();
                break;
            default:
                System.out.println("Entered invalid entry. Please enter valid inputs");
                break;
            }
        }
    }

}
