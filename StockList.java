import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StockList {
    private final Map<String,StockItem> list;

    public StockList() {
        this.list = new LinkedHashMap<>();
    }

    public int addStock(StockItem item){
        if(item != null){
            StockItem inStock = list.getOrDefault(item.getName(),item);
            if(inStock != item){
                item.adjustStock(inStock.availableQuantity());
            }
            list.put(item.getName(), item);
            return item.availableQuantity();
        }
        return 0;
    }
    public int sellStock(String item,int quantity){
        StockItem inStock = list.get(item);
        if ((inStock != null) && (quantity > 0)){
            return inStock.finaliseStock(quantity);
        }
        return 0;
    }

    public int reserveStock(String item, int quantity){
        StockItem inStock = list.get(item);

        if(inStock != null && quantity > 0){
            return inStock.reserveStock(quantity);
        }
        return 0;
    }

    public int unreserveStock(String item, int quantity){
        StockItem inStock = list.get(item);

        if(inStock != null && quantity > 0){
            return inStock.unReserveStock(quantity);
        }
        return 0;
    }

    public StockItem get(String key){
        return list.get(key);
    }

    public Map<String, StockItem> Items(){
        return Collections.unmodifiableMap(list);
    }

    public Map<String,Double> PriceList(){
        Map<String,Double> prices = new LinkedHashMap<>();
        for (Map.Entry<String,StockItem> item: list.entrySet()){
            prices.put(item.getKey(),item.getValue().getPrice());
        }
        return Collections.unmodifiableMap(prices);
    }

    @Override
    public String toString(){
        System.out.println("\nStock List:");
        double totalCost = 0.0;
        for(Map.Entry<String,StockItem> item : list.entrySet()){
            StockItem stockItem = item.getValue();
            double itemValue = stockItem.availableQuantity() * stockItem.getPrice();
            System.out.printf("%s. There are %d %s in stock. Total value of the %s in stock is %.2f.\n", stockItem, stockItem.availableQuantity(),stockItem.getName(),stockItem.getName(), itemValue);
            totalCost +=itemValue;
        }
        return String.format("\tTotal stock value %.2f",totalCost);
    }

}