import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;


public class Basket {
    private final String name;
    private final Map<StockItem, Integer> list;

    public Basket(String name) {
        this.name = name;
        this.list = new TreeMap<>();
    }

    public int addToBasket(StockItem item, int quantity){
        if((item != null) && (quantity > 0) ){
            int inBasket = list.getOrDefault(item,0);
            list.put(item, inBasket + quantity);
            return inBasket;
        }
        return 0;
    }

    public int removeFromBasket(StockItem item, int quantity){
        if(item != null && quantity > 0  && (list.getOrDefault(item, 0) - quantity) > 0){
            list.put(item,quantity);
            return quantity;
        } else if(item != null && quantity > 0  && (list.getOrDefault(item, 0) - quantity) == 0){
            list.remove(item);
            return quantity;
        } else{
            return 0;
        }

    }

    public void clearBasket(){
        this.list.clear();
    }

    public Map<StockItem, Integer> Items(){
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        System.out.printf("\nShopping basket %s contains %s %s\n",name,list.size(),list.size() == 1 ? "product" : "products");

        double totalCost = 0.0;
        for(Map.Entry<StockItem, Integer> item: list.entrySet()){
            System.out.printf("%s\n\t%d %s have been purchased.\n", item.getKey(),item.getValue(),item.getKey().getName());
            totalCost += item.getKey().getPrice() * item.getValue();
        }
        return String.format("\tTotal cost of %s's basket is: %.2f",name,totalCost);
    }

    public String getName() {
        return name;
    }
}
