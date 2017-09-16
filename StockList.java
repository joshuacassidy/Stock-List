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
        return (inStock != null) && (quantity > 0)  ? inStock.finaliseStock(quantity) :  0;


    }

    public int reserveStock(String item, int quantity){
        StockItem inStock = list.get(item);
        return inStock != null && quantity > 0  ? inStock.reserveStock(quantity) : 0;
    }

    public int unReserveStock(String item, int quantity){
        StockItem inStock = list.get(item);
        return inStock != null && quantity > 0 ?  inStock.unReserveStock(quantity) : 0;
    }

    public StockItem get(String key){
        return list.get(key);
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