import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static StockList stockList = new StockList();

    public static void main(String[] args) throws IOException {
        StockItem stockItem;

        try(BufferedReader stockFile = new BufferedReader(new FileReader("src/Stock.txt"))) {
            String stockData = stockFile.readLine();

            while (stockData != null) {
                String[] data = stockData.split(",");
                stockItem = new StockItem(data[0], Double.parseDouble(data[1]),Integer.parseInt(data[2]));
                stockList.addStock(stockItem);
                stockData = stockFile.readLine();
            }
        }



        Basket myBasket = new Basket("Joe");
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



        writeStockValues();



    }
    public static void writeStockValues() throws IOException{
        try(FileWriter stockFile = new FileWriter("src/Stock.txt")) {
            for(int i = 0; i < stockList.priceList().keySet().size(); i++) {
                stockFile.write(String.format("%s,%s,%s\n",stockList.priceList().keySet().toArray()[i], stockList.priceList().values().toArray()[i], stockList.inStock().values().toArray()[i]));
            }

        }
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
