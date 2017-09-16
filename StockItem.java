
public class StockItem implements Comparable<StockItem> {
    private final String name;
    private double price;
    private int quantityInStock;
    private int reserved = 0;


    public StockItem(String name, double price, int quantityInStock) {
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int availableQuantity() {
        return quantityInStock - reserved;
    }

    public void setPrice(double price) {
            this.price = price > 0.0 ? price : this.price;
    }

    public void adjustStock(int quantity) {
        int newQuantity = this.quantityInStock + quantity;
        if(newQuantity >= 0){
            this.quantityInStock = newQuantity;
        }
    }

    public int reserveStock(int quantity){
        if(quantity <= availableQuantity()){
            reserved += quantity;
            return quantity;
        }
        return 0;
    }

    public int unReserveStock(int quantity){
        if(quantity <= availableQuantity()){
            reserved -= quantity;
            return quantity;
        }
        return 0;
    }

    public int finaliseStock(int quantity){
        if(quantity <= reserved){
            quantityInStock -= quantity;
            reserved -= quantity;
            return quantity;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("Entering Stock item");
        if(obj == this){
            return true;
        }
        else if (obj == null || (obj.getClass() != this.getClass())){
            return false;
        } else {
            String objName = ((StockItem) obj).getName();
            return this.name.equals(objName);
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + 31;
    }

    @Override
    public int compareTo(StockItem o) {
        if(this == o){
            return 0;
        } else if(o != null){
            return this.name.compareTo(o.getName());
        } else{
            throw new NullPointerException();
        }
    }

    @Override
    public String toString() {
        return String.format("\tItem name: %s. Item cost: %.2f",this.name,this.price);
    }
}
