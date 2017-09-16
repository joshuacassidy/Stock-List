import java.io.*;
import java.util.*;

public class Main {

    private static StockList stockList = new StockList();

    public static void main(String[] args) throws IOException,ClassNotFoundException {

        System.out.print("\033[H\033[2J");
        readStockValues();
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
        try(ObjectOutputStream stockFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/Stock.dat")))) {
            for(int i = 0; i < stockList.priceList().keySet().size(); i++) {
                stockFile.writeObject(String.format("%s,%s,%s",stockList.priceList().keySet().toArray()[i], stockList.priceList().values().toArray()[i], stockList.inStock().values().toArray()[i]));
            }
        }
    }

    public static void readStockValues() throws IOException, ClassNotFoundException{
        StockItem stockItem;
        try(ObjectInputStream stockFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream("src/Stock.dat")))) {
            boolean stockEof = false;
            while(!stockEof) {
                try {
                    Object planetData = stockFile.readObject();
                    String[] data = planetData.toString().split(",");
                    stockItem = new StockItem(data[0], Double.parseDouble(data[1]),Integer.parseInt(data[2]));
                    stockList.addStock(stockItem);
                } catch (EOFException e) {
                    stockEof = true;
                }
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
        System.out.printf("\n%s's basket has been processed.",basket.getName());
        basket.clearBasket();
    }

}
