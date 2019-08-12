package Deliverr;

import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Used Java Jackson library for JSON parsing
 * @link jackson https://github.com/FasterXML/jackson
 * @link junit https://search.maven.org/search?q=g:junit%20AND%20a:junit&core=gav
 * test.InventoryAllocator class to produce the cheapest shipment
 *
 */
public class InventoryAllocator {
    /**
     *
     * @param orderString This is the string of orders
     * @param inventJsonArr This is the string of the list of test.Warehouse objects
     * @return List of test.Warehouse objects matching the orders in the warehouse, based on the order of appearance in the warehouse
     */
    protected List<Warehouse> customerOrder(String orderString, String inventJsonArr) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Warehouse> answer = new ArrayList<>();
        if (orderString == null || orderString.length() == 0 || inventJsonArr == null || inventJsonArr.length() == 0)
            return answer;
        try {
            Warehouse[] warehouses = objectMapper.readValue(inventJsonArr, Warehouse[].class);
            // Convert JSON String to Map
            HashMap<String,Integer> orderMap = objectMapper.readValue(orderString,new TypeReference<Map<String, Integer>>() {});
            // Maintaining a copy of the order HashMap
            HashMap<String, Integer> orderCopy = new HashMap<>(orderMap);
            for (Warehouse house : warehouses) {
                // Creating the resultant warehouse object
                Warehouse resItem = new Warehouse();
                resItem.setName(house.getName());
                HashMap<String, Integer> resInvent = new HashMap<>();
                resItem.inventory = resInvent;
                HashMap<String, Integer> houseInventory = house.getInventory();
                boolean present = false;
                // Iterate through each item in all the warehouses
                for (String orderItem : orderMap.keySet()) {
                    // Check if the warehouse contains the order item
                    if (houseInventory.containsKey(orderItem) && houseInventory.get(orderItem) > 0 && orderCopy.containsKey(orderItem)) {
                        present = true;
                        // Compare the quantity of product in the order and in the warehouse
                        if (orderCopy.get(orderItem) > houseInventory.get(orderItem)) {
                            resInvent.put(orderItem, houseInventory.get(orderItem));
                            orderCopy.put(orderItem, orderCopy.get(orderItem) - houseInventory.get(orderItem));
                        } else {
                            resInvent.put(orderItem, orderCopy.get(orderItem));
                            orderCopy.remove(orderItem);
                        }
                    }
                }
                if(present) {
                    answer.add(resItem);
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException f) {
            f.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
    public static void main(String[] args) {
        InventoryAllocator dv = new InventoryAllocator();
        String orderString = "{\"apple\": 5,\"banana\": 5}";
        String invent = "[ { \"name\": \"owd\", \"inventory\": { \"apple\": 5, \"orange\": 10 } }, { \"name\": \"dm\", \"inventory\": { \"banana\": 5, \"orange\": 10 } } ]";

        List<Warehouse> res = dv.customerOrder(orderString,invent);
        System.out.println(res.toString());
        for(Warehouse w:res)
            System.out.println(w.getName() + " --> " + w.getInventory());
    }
}






