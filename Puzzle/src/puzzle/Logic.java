package puzzle;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static puzzle.BDrecord.result;
import static puzzle.BDrecord.stab;
import puzzle.PuzzleGame.PuzzleButton;
import puzzle.PuzzleGame.GameTimer;

/**
 *
 * @author Игорь
 */
class Logic {

    private static ArrayList<Point> solution;//поле для проверки на правильность собранной картинки

    public static ArrayList<PuzzleButton> buttons;
    public static JPanel panel;
    public static boolean stop = false;//флаг окончания игры
    static int counter = 0;
    public static int row;
    public static int column;

    static int getCounter() {
        return counter;
    }

    public Logic() {
        this.solution = getSolution();
    }

    private ArrayList<Point> getSolution() {
        solution = new ArrayList<>();
        //в поле solution записываем начальное значение положение точек изображения с последующей сверкой
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i == row - 1 && j == column - 1) {
                    solution.add(null);
                    break;
                }
                solution.add(new Point(i, j));
            }
        }
        return solution;
    }

    static void checkSolution(ArrayList<PuzzleButton> buttons, JPanel panel) {//проверяем правильность изображения 

        ArrayList current = new ArrayList();

        for (JComponent btn : buttons) {
            current.add((Point) btn.getClientProperty("position"));//записываем 
            System.out.println(btn.getClientProperty("position"));

        }
        System.out.println(solution.toString());
        System.out.println(current.toString());

        if (compareList(solution, current)) {
            try {
                //тут вин
                stop();
                String data[][] = dataRewrite();
                String clone[] = compareRecords(GameTimer.getTimer(), data);
                if (clone != null) {
                    BDrecord.BDrecord();
                    BDrecord.newTable();
                    BDrecord.ResetDB();
                    BDrecord.ReWriteDB(rebildBD(clone, data)); 
                }
                BDrecord.CloseDB();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean compareList(ArrayList solution, ArrayList current) {
        return solution.toString().contentEquals(current.toString());
    }

    public static boolean stop() {
        stop = true;
        return stop;
    }
//-------------------------операции по записи в бд---------------------------------

    public static String[][] dataRewrite() {
        String[][] data = new String[10][4];//в массив выносим то что в бд
        try {
            int j = 0;
            BDrecord.BDrecord();
            BDrecord.newTable();
            BDrecord.ReadDB();
            result = stab.executeQuery("SELECT * FROM record");
            while (result.next()) {
                String[] row = new String[4];
                for (int i = 1; i <= 4; i++) {
                    row[i - 1] = result.getString(i);
                    data[j][i - 1] = row[i - 1];
                }
                j++;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            BDrecord.CloseDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;

    }

    public static String[] compareRecords(long time, String[][] data) {//сравниваем и создаем строку для нового рекорда
        int flag = -1;
        String[] clone = new String[4];
        int i;
        for (i = 0; i <= 10; i++) {
            if (i == 10) {
                clone = null;
                break;
            }
            if (Long.valueOf(data[i][3]) > time && flag == -1) {
                flag = i;
                String name = JOptionPane.showInputDialog(panel, "Пазл собран! Вы установили новый рекорд!\nВведити свое имя для сохранения рекорда",
                        "Успех!", JOptionPane.INFORMATION_MESSAGE);
                clone[0] = String.valueOf(flag);
                clone[1] = name;
                clone[2] = String.valueOf(counter);
                clone[3] = String.valueOf(time);
                break;
            }
        }
        System.out.println(clone);
        if (clone == null) {
            JOptionPane.showMessageDialog(panel, "Пазл собран! Но у вас не получилось установить новый рекорд.\nНе расстраивайтесь, в слуеующий раз вам обязательно повезет!",
                    "Успех!", JOptionPane.INFORMATION_MESSAGE);
        }

        return clone;
    }

    public static String[][] rebildBD(String[] clone, String[][] data) {
        boolean flag = false;
        String[][] reData = new String[10][4];
        for (int i = 0; i < 10; i++) {
            if (i == Integer.parseInt(clone[0])) {//если номер строки с лучшим результатом совпадает
                for (int j = 0; j < 4; j++) {
                    reData[i][j] = clone[j];
                }
                flag = true;
                i++;
            } else {
                for (int j = 0; j < 4; j++) {
                    reData[i][j] = data[i][j];
                }
            }
            if (flag) {
                for (int j = 0; j < 4; j++) {
                    reData[i][j] = data[i - 1][j];
                }
            }
        }
        return reData;
    }

    //---------------------тут перестановка----------------------------------------
    public static class ClickAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {//реакция на клик
            checkButton(e);
            checkSolution(buttons, panel);
        }

        private void checkButton(ActionEvent e) {//проверяем кнопку, можно ли её сдвигать

            int indx = 0;//индекс позиции пустого кусочка изображения
            for (PuzzleButton button : buttons) {
                if (button.isClearButton()) {//если кнопка помечена пустым флагом записываем её индекс
                    indx = buttons.indexOf(button);
                    System.out.println("Пустой был в " + (indx + 1) + " ячейке");
                }
            }
            //обрабатываем ту кнопку на которую был произведен клик, запоминаем её индекс
            JButton button = (JButton) e.getSource();
            int bidx = buttons.indexOf(button);
            System.out.println("Перемещаемый был в " + (bidx + 1) + " ячейке");
            if ((((indx != 8) && (indx != 5) && (indx != 2)) && (bidx - 1 == indx))
                    || (((indx != 9) && (indx != 6) && (indx != 3)) && (bidx + 1 == indx))//если кнопка распологалась около пустого элемента перемещаем её
                    || (bidx - column == indx) || (bidx + column == indx)) {//                [bidx - 3]
                Collections.swap(buttons, bidx, indx);            //    [bidx - 1]   BUTTON    [bidx + 1]
                updateButtons();                                  //                [bidx + 3]
                if (!stop) {
                    GameTimer.start = true;
                } else {
                    GameTimer.start = false;
                }
                counter++;//считаем клики
                System.out.println("Кликов " + counter);
            }
        }

        private void updateButtons() {//обновляем все компоненты в панели

            panel.removeAll();

            for (JComponent btn : buttons) {

                panel.add(btn);

            }

            panel.validate();
        }
    }
}
