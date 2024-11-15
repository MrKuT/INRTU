/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

/**
 *
 * @author Игорь
 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import static puzzle.BDrecord.result;
import static puzzle.BDrecord.stab;

public class Record implements ActionListener {

    JFrame records = new JFrame("Таблица рекордов");
    JDialog jd = new JDialog(records);
    JTable table = new JTable();
    public static String[] columnNames
            = {"№",
                "Имя",
                "Количество кликов",
                "Время"
            };

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            BDrecord.BDrecord();
            BDrecord.newTable();            
            BDrecord.ReadDB();

            records.setLocationRelativeTo(null); //Окно в центре экрана
            records.setResizable(false); //Запрет на изменения размеров
            records.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            records.setVisible(true);
            records.setPreferredSize(new Dimension(400, 250)); //Размеры таблицы
            records.remove(table);
            records.repaint();
            table = CreateTable();
            records.add(table);
            records.pack();
            records.validate();

            jd.setModal(true);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static JTable CreateTable() throws ClassNotFoundException, SQLException {

        int j = 1;
        String[][] data = new String[11][4];
        result = stab.executeQuery("SELECT * FROM record");
        String[] column = new String[4];
        for (int i = 1; i <= 4; i++) {
            column[i - 1] = columnNames[i - 1];
            data[0][i - 1] = column[i - 1];
        }

        while (result.next()) {
            String[] row = new String[4];
            for (int i = 1; i <= 4; i++) {
                row[i - 1] = result.getString(i);
                data[j][i - 1] = row[i - 1];
            }
            j++;
        }

        JTable table = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        ;
        }; 
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);

        BDrecord.CloseDB();
        return table;
    }

}
