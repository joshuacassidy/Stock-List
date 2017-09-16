import java.util.Map;

public class Main {

    private static StockList stockList = new StockList();

    public static void main(String[] args) {
        StockItem stockItem;


        stockItem= new StockItem("bread", 0.87,100);
        stockList.addStock(stockItem);

        stockItem = new StockItem("cake", 2.87,100);
        stockList.addStock(stockItem);

        stockItem = new StockItem("milk", 2.10,3);
        stockList.addStock(stockItem);
        stockItem = new StockItem("rice", 1.10,30);
        stockList.addStock(stockItem);
        stockItem = new StockItem("pasta", 2.27,1);
        stockList.addStock(stockItem);
        stockItem = new StockItem("roll", 1.00,4);
        stockList.addStock(stockItem);
        stockItem = new StockItem("chicken", 1.87,40);
        stockList.addStock(stockItem);
        stockItem = new StockItem("ham", 2.17,50);
        stockList.addStock(stockItem);

        stockItem = new StockItem("lettuce", 4.45,7);
        stockList.addStock(stockItem);


        Basket myBasket = new Basket("Joe");
        sellItem(myBasket,"roll", 4);
        sellItem(myBasket,"rice", 2);
        sellItem(myBasket,"pasta", 2);
        sellItem(myBasket,"bread", 2);
        sellItem(myBasket,"cake", 2);


        Basket basket = new Basket("Josh");
        sellItem(basket,"lettuce",3);
        sellItem(basket,"ham",4);
        sellItem(basket,"chicken",3);
        removeItem(basket,"chicken",3);
        removeItem(basket,"ham",2);
        removeItem(basket,"lettuce",3);

        System.out.println(myBasket);
        System.out.println(basket);

        checkOut(myBasket);
        checkOut(basket);

        System.out.println(stockList);

    }

    public static int sellItem(Basket basket, String item, int quantity){
        StockItem stockItem = stockList.get(item);
        if(stockList.reserveStock(item,quantity) != 0){
            return basket.addToBasket(stockItem,quantity);
        } else{
            System.out.print(stockItem == null ? String.format("we don't sell %s\n",item) : "");
            return 0;
        }
    }

    public static int removeItem(Basket basket, String item, int quantity){
        StockItem stockItem = stockList.get(item);
        if(basket.removeFromBasket(stockItem,quantity) == quantity){
            return stockList.unReserveStock(item,quantity);
        }
        else{
            System.out.print(stockItem == null ? String.format("we don't sell %s\n",item) : "");
            return 0;
        }
    }

    public static void checkOut(Basket basket){
        for(Map.Entry<StockItem, Integer> item : basket.Items().entrySet()){
            stockList.sellStock(item.getKey().getName(),item.getValue());
        }
        basket.clearBasket();
    }

}
