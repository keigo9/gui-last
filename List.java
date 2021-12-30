package last.free;

public class List {
    private String name;
    private String price;

    public List(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String toString() {
        String tmp = name + "," + price;
        return tmp;
    }

    public static void main(String[] args) {
        List myList = new List("卵", "200");
        System.out.println(myList);
        myList.setPrice("250");
        System.out.println(myList);
    }
}