package puzzle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;
import static puzzle.Logic.stop;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Игорь
 */
public final class PuzzleGame extends JFrame {

    public static JPanel panel, panelka;
    JLabel img;
    JButton openBut, raBut, recBut, saveBut;
    Container container, butContainer;
    public ArrayList<PuzzleButton> buttons;//поле в котором содержатся кнопки(части изображения)
    private BufferedImage sourceImage;//входное изображение
    private BufferedImage resizedImage;//изображение с изменёнными под окно размерами
    private int width, height;//ширина и высота
    private final int DESIRED_WIDTH = 540;//ширина для изображения
    private final int DESIRED_HEIGHT = 960;//ширина для изображения
    private Image image;
    private PuzzleButton last;
    GameTimer timer;
    private BufferedImage loadImg;

    public PuzzleGame() {
        initUI();
        setDefaultCloseOperation(PuzzleGame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(2 * DESIRED_WIDTH, DESIRED_HEIGHT));
        setTitle("Пазл");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initUI() {//инициализация интерфейса            
        panel = createdGamePanel(loadImage(), 4, 3);
        panelka = new JPanel();//часть для меню+исходное изображение+таймер
        panelka.setBackground(Color.white);

        openBut = new JButton("Выбрать изображение");
        openBut.setFont(new Font("Serif", Font.BOLD, 18));

        openBut.addActionListener((ActionEvent e) -> {
            File file = new File("Image");
            JFileChooser jfc = new JFileChooser(file);
            jfc.setAcceptAllFileFilterUsed(false);//фильтр для выбора изображений
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.png, *.gif, *.jpg, *.jpeg", "png", "jpg", "jpeg");
            jfc.addChoosableFileFilter(filter);
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                try {
                    panel.removeAll();
                    panel = createdGamePanel(loadImage(selectedFile.getAbsolutePath()), 4, 3);
                    add(panel, BorderLayout.WEST);//добавляем игровое поле
                    panelka.remove(container);
                    panelka.add(crearedHintPanel());
                    Logic.stop = false;
                    GameTimer.start = false;
                    validate();

                } catch (IOException ex) {
                    Logger.getLogger(PuzzleGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(selectedFile.getAbsoluteFile());
            }
        });
        openBut.setAlignmentX(CENTER_ALIGNMENT);

        raBut = new JButton("Случайное изображение");
        raBut.setFont(new Font("Serif", Font.BOLD, 18));
        raBut.addActionListener((ActionEvent e) -> {
            panel.removeAll();
            panel = createdGamePanel(loadImage(), 4, 3);
            add(panel, BorderLayout.WEST);//добавляем игровое поле
            panelka.remove(container);
            panelka.repaint();
            panelka.add(crearedHintPanel());
            Logic.stop = false;
            GameTimer.start = false;
            validate();
        });
        raBut.setAlignmentX(CENTER_ALIGNMENT);

        recBut = new JButton("Рекорды");
        recBut.setFont(new Font("Serif", Font.BOLD, 18));
        recBut.addActionListener(new Record());

        panelka.add(crearedHintPanel());//добавляем контейнер с изображением и лейблами  
        add(panelka);
        //---------------------------------------------------------------------------------        

        panel.setPreferredSize(new Dimension(DESIRED_WIDTH, DESIRED_HEIGHT));
        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black);
        panel.setBorder(border);
        add(panel, BorderLayout.WEST);//добавляем игровое поле 
    }

    private BufferedImage rImage(BufferedImage img, int w, int h, int type) {//метод для изменения размера исходного изображения
        BufferedImage rImage = new BufferedImage(w, h, type);
        Graphics2D g = rImage.createGraphics();//возвращаем изображение как 2D объект
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();//выкидываем объект в память
        return rImage;
    }

    public void craftPuzzle(int a, int b) {//тут разбиваем на кнопки с изображениями и даем координаты

        buttons.clear();
        for (int i = 0; i < a; i++) {//делим изображение на таблицу 
            for (int j = 0; j < b; j++) {
                image = createImage(new FilteredImageSource(resizedImage.getSource(), new CropImageFilter(j * width / b, i * height / a, width / b, height / a)));
                PuzzleButton button = new PuzzleButton(image);
                if (i == a - 1 && j == b - 1) {
                    last = new PuzzleButton();
                    last.setBorderPainted(false);
                    last.setContentAreaFilled(false);
                    last.setClearButton(true);
                    button.putClientProperty("position", null);//сохраняем для данной кнопки первоначальную позицию
                } else {
                    button.putClientProperty("position", new Point(i, j));//сохраняем для данной кнопки первоначальную позицию                   
                    this.buttons.add(button);
                }
                System.out.println(button.getClientProperty("position"));
            }
        }
        buttons.add(last);
        //Collections.shuffle(buttons);//перемешиваем кнопки
        for (int i = 0; i < a * b; i++) {
            PuzzleButton btn = buttons.get(i);
            panel.add(btn);
            btn.addActionListener(new Logic.ClickAction());
        }
        Logic.buttons = this.buttons;

    }

    public Container crearedHintPanel() {// тут создаем контейнер с содержимым для второй панели
        GridBagLayout gbl = new GridBagLayout();//создать объект класса GridBagLayout  
        GridBagConstraints i = new GridBagConstraints();//создать объект класса GridBagConstraints, поля которого будут определять параметры размещения отдельных компонентов           

        container = new Container();
        container.setLayout(gbl);
        //--------------------------------------------------------------------------------------
        int imgw = this.width / 2;
        int imgh = this.height / 2;
        Border bimg = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(bimg, "Исходное изображение");
        img = new JLabel(new ImageIcon(rImage(resizedImage, imgw, imgh, BufferedImage.TYPE_INT_ARGB)));//тут заресайзить нужно для исходника 
        img.setBorder(titled);
        img.setAlignmentX(CENTER_ALIGNMENT);//выравниваем по вертикали по центру панели    
        //положение для изображения
        i.fill = GridBagConstraints.NONE;
        i.gridheight = 1;
        i.gridwidth = GridBagConstraints.REMAINDER;
        i.gridx = 0;
        i.gridy = 5;
        gbl.setConstraints(img, i);
        container.add(img);
        //----------------------------------------------------------------------------------------------
        timer = new GameTimer();
        timer.setAlignmentX(CENTER_ALIGNMENT);
        timer.setFont(new Font("Serif", Font.BOLD, 24));
        i.fill = GridBagConstraints.CENTER;
        i.gridx = 0;
        i.gridy = 4;
        i.insets.set(10, 15, 10, 15);
        gbl.setConstraints(timer, i);
        container.add(timer);
        //-+------------------------------------------------------------------------------------------
        i.fill = GridBagConstraints.NONE;
        i.gridwidth = 1;
        i.insets.set(25, 15, 10, 15);
        i.gridx = 0;
        i.gridy = 1;
        i.fill = GridBagConstraints.HORIZONTAL;

        gbl.setConstraints(raBut, i);
        container.add(raBut);
        i.insets.set(10, 15, 10, 15);
        i.gridx = 0;
        i.gridy = 2;
        gbl.setConstraints(openBut, i);
        container.add(openBut);
        i.gridx = 0;
        i.gridy = 3;
        gbl.setConstraints(recBut, i);
        container.add(recBut);
        //---------------------------------------------------------------------------------------------
        return container;
    }

    public JPanel createdGamePanel(BufferedImage img, int a, int b) {//тут создаем игровую панель        
        Logic.row = a;
        Logic.column = b;
        buttons = new ArrayList<>();
        panel = new JPanel();//инициализация поле с игровыми кнопками        
        panel.setLayout(new GridLayout(a, b));//алгоритм расположения кнопок(4x3-таблица)        
        this.sourceImage = img;
        this.resizedImage = rImage(sourceImage, DESIRED_WIDTH, DESIRED_HEIGHT, BufferedImage.TYPE_INT_ARGB);//BufferedImage.TYPE_INT_ARGB кодирование цвета интом
        this.width = resizedImage.getWidth();//найденая высота + ширина
        this.height = resizedImage.getHeight();
        Logic.panel = PuzzleGame.panel;
        craftPuzzle(a, b);
        panel.setPreferredSize(new Dimension(540, 960));
        Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black);
        panel.setBorder(border);
        return panel;
    }

    private BufferedImage loadImage() {//метод загрузки изображения        
        try {
            loadImg = chooseRndFile();
        } catch (IOException e) {
            System.out.println("Проблемы с исходным изображением");
        }
        return loadImg;
    }

    private BufferedImage loadImage(String name) throws IOException {//метод загрузки изображения 
        try {
            loadImg = ImageIO.read(new File(name));
        } catch (IOException e) {
            System.out.println("Проблемы с исходным изображением");
        }
        return loadImg;
    }

    public BufferedImage chooseRndFile() throws IOException {//выбираем случайное изображение
        Random rnd = new Random();
        String imgFile;
        int rndDir = 0;
        File file = new File("src\\Image");
        file.canExecute();
        if (file.exists() && !file.isFile()) {
            String[] dir = file.list();
            try {
                rndDir = rnd.nextInt(dir.length);
                imgFile = file.getAbsolutePath() + "\\" + dir[rndDir];
                System.out.println(imgFile);
                loadImg = ImageIO.read(new File(imgFile));
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(panel, "Проблемы с исходным изображением! Отсутствие изображений в папке Image",
                        "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                loadImg = new BufferedImage(DESIRED_WIDTH, DESIRED_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            };
        } else {
            JOptionPane.showMessageDialog(panel, "Проблемы с исходным изображением! Отсутствие папки Image в директории",
                    "Ошибка", JOptionPane.INFORMATION_MESSAGE);
            loadImg = new BufferedImage(DESIRED_WIDTH, DESIRED_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        }
        return loadImg;
    }

    public static class GameTimer extends JLabel {

        static long gameTimer = 0;
        private long startTime = -1;
        public static boolean start = false;

        public GameTimer() {

            Timer timer = new Timer(500, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!start) {
                        setText("Таймер: 0 секунд");
                    } else {
                        if (startTime == -1) {
                            startTime = System.nanoTime();
                        } else {
                            long time = System.nanoTime();
                            if (!stop) {
                                long timeLeft = (time - startTime);
                                gameTimer = timeLeft;
                                setText("Таймер: " + Long.toString(TimeUnit.NANOSECONDS.toSeconds(timeLeft)) + " секунд");
                            } else {
                                setText("Картинка собрана за " + Long.toString(getTimer()) + " секунд");
                                ((Timer) e.getSource()).stop();
                            }
                            revalidate();
                            repaint();
                        }
                    }
                }
            });
            timer.setInitialDelay(0);
            timer.start();
        }
        public static long getTimer(){
            gameTimer = TimeUnit.NANOSECONDS.toSeconds(gameTimer);
        return gameTimer;
        }
    }

    public class PuzzleButton extends JButton {//класс игровых кнопок

        private boolean isClearButton;//тригер пустой части изображения

        public PuzzleButton() {
            super();
            initUI();
        }

        public PuzzleButton(Image img) {
            super(new ImageIcon(img));//закрышиваем кнопку рисунком
            initUI();
        }

        private void initUI() {//визуализация дествий с картинкой

            isClearButton = false;

            addMouseListener(new MouseAdapter() {//событие для мышки

                @Override
                public void mouseEntered(MouseEvent e) {//при мышке наведеной на кнопку подсвечиваем границы "кнопки" белым
                    setBorder(BorderFactory.createLineBorder(Color.white));
                }

                @Override
                public void mouseExited(MouseEvent e) {//при выходе мышке кнопку подсвечиваем границы "кнопки" чёрным
                    setBorder(BorderFactory.createLineBorder(Color.gray));
                }

            }
            );
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        JFrame f = new JFrame();
                        f.setDefaultCloseOperation(PuzzleGame.EXIT_ON_CLOSE);
                        f.setTitle("Пауза");
                        f.setResizable(false);
                        f.pack();
                        f.setLocationRelativeTo(null);
                        f.setVisible(true);
                    }

                }
            });
        }

        //гетер и сетер для пустой части изображения
        public boolean isClearButton() {
            return isClearButton;
        }

        public void setClearButton(boolean isClearButton) {
            this.isClearButton = isClearButton;
        }
    }

}
