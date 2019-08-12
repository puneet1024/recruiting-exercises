package Deliverr;

import java.util.HashMap;

class Warehouse {
    String name;

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    HashMap<String, Integer> inventory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}