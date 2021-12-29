package last.free;
import java.io.*;
import java.util.*;

public class ShopingList {
    private ArrayList < List > memo;

    public ShopingList() {
        memo = new ArrayList < List > ();
    }

    public void open(String filename) {
        try {
            memo.clear();
            File file = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] field = line.split(",");
                add(new List(field[0], field[1], field[2]));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(String filename) {
        try {
            File file = new File(filename);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (List list: memo) {
                writer.println(list);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(List list) {
        memo.add(list);
    }

    public void remove(List list) {
        memo.remove(list);
    }

    public void showList() {
        for (List list: memo) {
            System.out.println(list);
        }
    }

    public List findName(String name) {
        for (List list: memo) {
            if (name.equals(list.getName())) {
                return list;
            }
        }
        return null;
    }

    public List findPrice(String pri) {
        for (List list: memo) {
            if (pri.equals(list.getPrice())) {
                return list;
            }
        }
        return null;
    }

    public ArrayList < String > getNames() {
        ArrayList < String > nameList = new ArrayList < String > ();
        for (List list: memo) {
            nameList.add(list.getName());
        }
        return nameList;
    }

    public static void main(String[] args) {
        ShopingList list = new ShopingList();
        list.open("shopinglist.txt");
        list.showAddresses();
        List eggList = new List("卵", 200, 200);
        list.add(eggList);
        list.showAddresses();
    }

}