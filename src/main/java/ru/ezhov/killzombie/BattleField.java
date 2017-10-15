package ru.ezhov.killzombie;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Это основное игровое поле
 *
 * @author rrndeonisiusezh
 */
public class BattleField extends JFrame {

    private static final Logger LOG = Logger.getLogger(BattleField.class.getName());

    /** размеры игрового поля строк */
    private final static int COUNT_ROW = 5;
    /** размеры игрового поля столбцов */
    private final static int COUNT_COLUMN = 5;
    /** кол-во зомби на игровом поле */
    private final static int COUNT_ZOMBIE = COUNT_ROW * COUNT_COLUMN;
    /** панель с полем зомби */
    private final BattlePanel battlePanel = new BattlePanel();
    /** панель статистики */
    private final StatisticPanel statisticPanel = new StatisticPanel();

    public BattleField() {
        init();
    }

    /**
     * инициализируем игровое поле
     */
    private void init() {
        battlePanel.createZombie();
        add(battlePanel, BorderLayout.CENTER);
        add(statisticPanel, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** панель с зомби */
    private class BattlePanel extends JPanel {

        /** курсор в виде ножа для зомби */
        private final Image IMAGE_CURSOR_KNIFE
                = new ImageIcon(BattleField.class.getResource("src/knife.png")).getImage();
        private final List<Zombie> zombies = new ArrayList<Zombie>(COUNT_ZOMBIE);

        public BattlePanel() {
            init();
            launchZombie();
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(IMAGE_CURSOR_KNIFE, new Point(0, 0), "knife_kutsor"));
        }

        /** инициализируем поле */
        private void init() {
            setLayout(new GridLayout(COUNT_ROW, COUNT_COLUMN));
        }

        private void launchZombie() {
            Thread threadZombie = new Thread(new LaunchZombies());
            threadZombie.setDaemon(true);
            threadZombie.start();
        }

        /** создаем поле */
        public void createZombie() {
            for (int i = 0; i < COUNT_ZOMBIE; i++) {
                Zombie zombie = new Zombie();
                zombies.add(zombie);
                add(zombie);
            }
        }

        /** получаем список зомби */
        public List<Zombie> getZombies() {
            return zombies;
        }

    }

    /** панель статистики */
    private class StatisticPanel extends JPanel {

        private final Font FONT = new Font("consolas", Font.PLAIN, 40);
        /** фон панели статистики */
        private final Image IMAGE_BACKGROUND = new ImageIcon(BattleField.class.getResource("src/blood_backround.png")).getImage();
        /** надпись времени */
        private final JLabel labelStringTime = new JLabel("Осталось время:");
        /** надпись жизни */
        private final JLabel labelStringLife = new JLabel("Осталось жизни");
        /** надпись сколько убито зомби */
        private final JLabel labelStringZombie = new JLabel("Убито зомби:");
        /** надпись сколько взорвано бомб */
        private final JLabel labelStringExplosion = new JLabel("Взорвано бомб:");
        /** надпись времени результат */
        private final JLabel labelTime = new JLabel("time");
        /** надпись жизни результат */
        private final JLabel labelLife = new JLabel("life");
        /** надпись сколько убито зомби результат */
        private final JLabel labelZombie = new JLabel("zombie");
        /** надпись сколько взорвано бомб результат */
        private final JLabel labelExplosion = new JLabel("exploison");
        /** отступы от краев */
        private final Insets insets = new Insets(5, 5, 5, 5);

        public StatisticPanel() {
            init();
        }

        private void init() {
            setLayout(new GridBagLayout());
            //------------------------------------------------------------------
            add(labelStringTime, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            add(labelTime, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            //------------------------------------------------------------------
            add(labelStringLife, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            add(labelLife, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            //------------------------------------------------------------------
            add(labelStringZombie, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            add(labelZombie, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            //------------------------------------------------------------------
            add(labelStringExplosion, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            add(labelExplosion, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            //------------------------------------------------------------------
            labelStringTime.setFont(FONT);
            labelTime.setFont(FONT);
            labelStringLife.setFont(FONT);
            labelLife.setFont(FONT);
            labelStringZombie.setFont(FONT);
            labelZombie.setFont(FONT);
            labelStringExplosion.setFont(FONT);
            labelExplosion.setFont(FONT);
            //------------------------------------------------------------------
        }

        /**
         * устанавливаем оставшиеся время
         */
        public void setTime() {

        }

        /**
         * устанавливаем оставшиеся жизни
         *
         * @param life - жизни
         */
        public void setLife(int life) {
            labelLife.setText(String.valueOf(life));
        }

        /**
         * устанавливаем кол-во убитых зомби
         *
         * @param killZombie - кол-во убитых зомби
         */
        public void setKillZombie(int killZombie) {
            labelZombie.setText(String.valueOf(killZombie));
        }

        /**
         * устанавливаем кол-во взорваных бомб
         *
         * @param explosion - кол-во взорванных бомб
         */
        public void setExplosion(int explosion) {
            labelExplosion.setText(String.valueOf(explosion));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(IMAGE_BACKGROUND, 0, 0, null);
        }

    }

    private class LaunchZombies implements Runnable {

        /** начальное кол-во жизней */
        private int life = 100;
        /** кол-во убитых зомби */
        private int zombieCount = 0;
        /** кол-во взорванных бомб */
        private int exploisonCount = 0;

        /** список зомби */
        private List<Zombie> zombies;
        /** рандом используемый для отображения зомби */
        private final Random random = new Random();
        /** размер списка зомби */
        private int COUNT_ZOMBIE;
        /** текцщий зомби */
        private Zombie zombie;
        /**
         * текущий номер зомби его мы храним,
         * чтоб не повторять одного и тогоже зомби
         */
        private int nowZombie;
        /** скорость перемещения */
        private final static int SPEED = 800;

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "Ошибка при ожидании потоком смены зомби", ex);
            }
            /** получаем список зомби */
            zombies = battlePanel.getZombies();
            COUNT_ZOMBIE = zombies.size() - 1;
            PlayMusic.play();
            launch();

        }

        /** устанавливаем иконку зомби */
        private void setZombie() {
            zombie.setZombie();
            zombie.setActive(true);
        }

        /** устанавливаем иконку значка радиации */
        private void setLabel() {
            zombie.setLabel();
            zombie.setActive(false);
        }

        /** устанавливаем зомби */
        private void setNextZombie() {
            /*если текущий зомби не пустой, тогда ставим значок*/
            if (zombie != null) {
                setLabel();
            }
            int r = random.nextInt(COUNT_ZOMBIE);
            while (nowZombie == r) {
                r = random.nextInt(COUNT_ZOMBIE);
            }
            nowZombie = r;
            zombie = zombies.get(nowZombie);
            setZombie();

        }

        /** запускаем беготню */
        private void launch() {
            while (true) {
                /*запускаем зомби*/
                setNextZombie();
                /*ждем*/
                try {
                    Thread.sleep(SPEED);
                } catch (InterruptedException ex) {
                    LOG.log(Level.SEVERE, "Ошибка при ожидании потоком смены зомби", ex);
                }
                setResult();
            }
        }

        /** обновляем результаты убийства зомби */
        private void setResult() {
            for (Zombie z : zombies) {
                life = z.getCountLife(life);
                exploisonCount += z.getCountExploison();
            }
            /* 
             * а это мы смотрим, если не успели убить 
             * активного зобми, тогда отнимает жизнь за зомби
             */
            int zomCount = zombie.getCountZombie();
            if (zomCount == 0) {
                life -= Zombie.LIFE_ZOMBIE_GET;
            }
            zombieCount += zomCount;
            statisticPanel.setLife(life);
            statisticPanel.setKillZombie(zombieCount);
            statisticPanel.setExplosion(exploisonCount);

        }

    }

}
