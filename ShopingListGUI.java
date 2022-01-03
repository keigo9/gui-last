package last.free;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;

public class ShopingListGUI extends JFrame {
    JTextField nameField, priceField, priceAmountField;
    DefaultListModel model, model2;
    JList list, list2;
    JButton addButton, removeButton, updateButton, doneButton, redoButton;
    JPanel pane;
    ShopingList memo, memo2;

    public static void main(String[] args) {
        JFrame w = new ShopingListGUI("ShopingListGUI");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(800, 400);
        w.setVisible(true);
    }

    public ShopingListGUI(String title) {
        super(title);
        memo = new ShopingList();
        memo2 = new ShopingList();
        pane = (JPanel) getContentPane();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("ファイル");
        menuBar.add(fileMenu);
        JMenuItem item;
        item = new JMenuItem(new OpenAction());
        fileMenu.add(item);
        item = new JMenuItem(new SaveAction());
        fileMenu.add(item);
        fileMenu.addSeparator();
        item = new JMenuItem(new ExitAction());
        fileMenu.add(item);

        model = new DefaultListModel();
        list = new JList(model);
        list.addListSelectionListener(new NameSelect());
        JScrollPane sc = new JScrollPane(list);
        sc.setBorder(new TitledBorder("買い物メモ"));
        pane.add(sc, BorderLayout.WEST);

        model2 = new DefaultListModel();
        list2 = new JList(model2);
        list2.addListSelectionListener(new NameSelect2());
        JScrollPane sc2 = new JScrollPane(list2);
        sc2.setBorder(new TitledBorder("購入済み"));
        pane.add(sc2, BorderLayout.CENTER);

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        pane.add(fields, BorderLayout.EAST);
        
        nameField = new JTextField(20);
        nameField.setBorder(new TitledBorder("名前"));
        fields.add(nameField);
        priceField = new JTextField(20);
        priceField.setBorder(new TitledBorder("値段"));
        fields.add(priceField);
        priceAmountField = new JTextField(20);
        priceAmountField.setBorder(new TitledBorder("合計金額"));
        priceAmountField.setEditable(false);
        fields.add(priceAmountField);

        addButton = new JButton(new AddAction());
        fields.add(addButton);
        updateButton = new JButton(new UpdateAction());
        fields.add(updateButton);
        removeButton = new JButton(new RemoveAction());
        fields.add(removeButton);
        doneButton = new JButton(new DoneAction());
        fields.add(doneButton);
        redoButton = new JButton(new RedoAction());
        fields.add(redoButton);
    }

    class NameSelect implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (list.getSelectedValue() != null) {
                nameField.setText(list.getSelectedValue().toString());
                List selectedList = memo.findName(list.getSelectedValue().toString());
                priceField.setText(selectedList.getPrice());
            }
        }
    }

    class NameSelect2 implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (list2.getSelectedValue() != null) {
                nameField.setText(list2.getSelectedValue().toString());
                List selectedList = memo.findName2(list2.getSelectedValue().toString());
                priceField.setText(selectedList.getPrice());
            }
        }
    }

    class OpenAction extends AbstractAction {
        OpenAction() {
            putValue(Action.NAME, "開く");
            putValue(Action.SHORT_DESCRIPTION, "開く");
        }
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser( "." ); // カレントディレクトリを指定してファイルチューザを生成
            fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY ); // モードを設定
            fileChooser.setDialogTitle( "テキストファイル選択" ); // タイトルを指定
            int ret = fileChooser.showOpenDialog( pane ); // ダイアログを開く
            if( ret != JFileChooser.APPROVE_OPTION ) return; // 選ばれていなければ
            String fileName = fileChooser.getSelectedFile().getAbsolutePath(); // 選ばれていればそのファイルのパスを得る
            model.clear();
            model2.clear();
            memo.open(fileName);
            // memo2.open(fileName);
            nameField.setText("");
            priceField.setText("");
            priceAmountField.setText(memo.getPriceAmount());
            for (String name: memo.getNames()) {
                model.addElement(name);
            }
            for (String name: memo.getNames2()) {
                model2.addElement(name);
            }
        }
    }

    class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(Action.NAME, "保存");
            putValue(Action.SHORT_DESCRIPTION, "保存");
        }
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser( "." ); // カレントディレクトリを指定してファイルチューザを生成
            int ret = fileChooser.showSaveDialog( pane ); // ダイアログを開く
            if( ret != JFileChooser.APPROVE_OPTION ) return; // 選ばれていなければ
            String fileName = fileChooser.getSelectedFile().getAbsolutePath(); // 選ばれていればそのファイルのパスを得る
            memo.save(fileName);
            System.out.println("saved");
        }
    }

    class ExitAction extends AbstractAction {
        ExitAction() {
            putValue(Action.NAME, "終了");
            putValue(Action.SHORT_DESCRIPTION, "終了");
        }
        public void actionPerformed(ActionEvent e) {
            Object[] msg = { "本当に終了しますか" };
            int ans = JOptionPane.showConfirmDialog( pane, msg, "はい・いいえ・取消し",
                                JOptionPane.YES_NO_CANCEL_OPTION );
            if ( ans == 0 ) {
                System.exit(0);
            }
        }
    }

    class AddAction extends AbstractAction {
        AddAction() {
            putValue(Action.NAME, "追加");
            putValue(Action.SHORT_DESCRIPTION, "追加");
        }
        public void actionPerformed(ActionEvent e) {
            if ("".equals(nameField.getText()) || "".equals(priceField.getText())) {
                return;
            } else {
                for(int i=0; i<model.getSize(); i++) {
                    if (nameField.getText().equals(model.get(i))) {
                        return;
                    }
                }
                List addList = new List(nameField.getText(), priceField.getText());
                memo.add(addList);
                memo.showList();
                model.addElement(nameField.getText());
                int sum = Integer.parseInt(priceAmountField.getText());
                sum = sum + Integer.parseInt(priceField.getText());
                nameField.setText("");
                priceField.setText("");
                priceAmountField.setText(Integer.toString(sum));
            }
        }
    }

    class UpdateAction extends AbstractAction {
        UpdateAction() {
            putValue(Action.NAME, "更新");
            putValue(Action.SHORT_DESCRIPTION, "更新");
        }
        public void actionPerformed(ActionEvent e) {
            if ("".equals(nameField.getText()) || "".equals(priceField.getText()) || "".equals(priceAmountField.getText())) {
                return;
            } else {
                if (memo.findName(nameField.getText()) != null) {
                    List updateList = memo.findName(nameField.getText());
                    int sum = Integer.parseInt(priceAmountField.getText());
                    sum = sum - Integer.parseInt(updateList.getPrice());
                    sum = sum + Integer.parseInt(priceField.getText());
                    memo.remove(updateList);
                    List updatedList = new List(nameField.getText(), priceField.getText());
                    memo.add(updatedList);
                    memo.showList();
                    priceAmountField.setText(Integer.toString(sum));
                }
            }
        }
    }

    class RemoveAction extends AbstractAction {
        RemoveAction() {
            putValue(Action.NAME, "削除");
            putValue(Action.SHORT_DESCRIPTION, "削除");
        }
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if ( index < 0 ) { return; }; 
            String select = (String) model.get( index );
            Object[] msg = { select, "を削除します" };
            int ans = JOptionPane.showConfirmDialog( pane, msg, "はい・いいえ・取消し",
                                JOptionPane.YES_NO_CANCEL_OPTION );
            if ( ans == 0 ) {
                nameField.setText("");
                priceField.setText("");
                int sum = Integer.parseInt(priceAmountField.getText());
                sum = sum - Integer.parseInt(priceField.getText());
                List removeList = memo.findName(list.getSelectedValue().toString());
                memo.remove(removeList);
                model.remove( index );
            }
        }
    }

    class DoneAction extends AbstractAction {
        DoneAction() {
            putValue(Action.NAME, "完了");
            putValue(Action.SHORT_DESCRIPTION, "完了");
        }
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if ( index < 0 ) { return; }; 
            String select = (String) model.get( index );
            List doneList = memo.findName(list.getSelectedValue().toString());
            model2.addElement(nameField.getText());
            memo.add2(doneList);
            
            memo.remove(doneList);
            model.remove( index );

            priceAmountField.setText(memo.getPriceAmount());
        }
    }

    class RedoAction extends AbstractAction {
        RedoAction() {
            putValue(Action.NAME, "戻す");
            putValue(Action.SHORT_DESCRIPTION, "戻す");
        }
        public void actionPerformed(ActionEvent e) {
            int index = list2.getSelectedIndex();
            if ( index < 0 ) { return; }; 
            String select = (String) model2.get( index );
            List redoList = memo.findName2(list2.getSelectedValue().toString());
            model.addElement(nameField.getText());
            memo.add(redoList);
            
            memo.remove2(redoList);
            model2.remove( index );

            priceAmountField.setText(memo.getPriceAmount2());
        }
    }
}