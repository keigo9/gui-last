package last.free;
import java.io.*;
import java.util.*;

public class ShopingList {
    private ArrayList < List > memo;
    private ArrayList < List > memo2;

    public ShopingList() {
        memo = new ArrayList < List > ();
        memo2 = new ArrayList < List > ();
    }

    public void open(String filename) {
        try {
            memo.clear();
            memo2.clear();
            File file = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("未購入")) {
                    break;
                }
            }
            while ((line = reader.readLine()) != null) {
                if (line.equals("購入済み")) {
                    break;
                }
                String[] field = line.split(",");
                add(new List(field[0], field[1]));
            }
            while ((line = reader.readLine()) != null) {
                String[] field = line.split(",");
                add2(new List(field[0], field[1]));
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
            writer.println("未購入");
            for (List list: memo) {
                writer.println(list);
            }
            writer.println("購入済み");
            for (List list: memo2) {
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
    public void add2(List list) {
        memo2.add(list);
    }

    public void remove(List list) {
        memo.remove(list);
    }
    public void remove2(List list) {
        memo2.remove(list);
    }

    public void showList() {
        for (List list: memo) {
            System.out.println(list);
        }
    }
    public void showList2() {
        for (List list: memo2) {
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
    public List findName2(String name) {
        for (List list: memo2) {
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
    public List findPrice2(String pri) {
        for (List list: memo2) {
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
    public ArrayList < String > getNames2() {
        ArrayList < String > nameList = new ArrayList < String > ();
        for (List list: memo2) {
            nameList.add(list.getName());
        }
        return nameList;
    }

    public String getPriceAmount() {
        int sum = 0;
        for (List list: memo) {
            sum = sum + Integer.parseInt(list.getPrice());
        }
        return Integer.toString(sum);
    }
    public String getPriceAmount2() {
        int sum = 0;
        for (List list: memo2) {
            sum = sum + Integer.parseInt(list.getPrice());
        }
        return Integer.toString(sum);
    }

    public static void main(String[] args) {
        ShopingList list = new ShopingList();
        list.open("shopinglist.txt");
        System.out.println(list.getPriceAmount());
    }

}