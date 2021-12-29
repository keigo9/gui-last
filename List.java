package last.free;

public class List {
    private String name;
    private int price;
    private int priceAmount;

    public Address(String name, int price, int priceAmount) {
        this.name = name;
        this.price = price;
        this.priceAmount = priceAmount;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceAmount() {
        return priceAmount;
    }
    public void setPriceAmount(int priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String toString() {
        String tmp = name + "," + price + "," + priceAmount;
        return tmp;
    }

    public static void main(String[] args) {
        List myList = new List("卵", 200, 200);
        System.out.println(myList);
        myList.setPrice(250);
        System.out.println(myList);
    }
}