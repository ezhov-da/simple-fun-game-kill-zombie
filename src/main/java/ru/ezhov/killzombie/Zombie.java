package ru.ezhov.killzombie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import sun.misc.VM;

/**
 *
 * @author rrndeonisiusezh
 */
public final class Zombie extends JButton implements ActionListener {

    private static final Logger LOG = Logger.getLogger(Zombie.class.getName());

    /** картинка зомби */
    private final static Icon ICON_ZOMBIE = new ImageIcon(Zombie.class.getResource("src/zombie.png"));
    /** картинка значка */
    private final static Icon ICON_LABEL = new ImageIcon(Zombie.class.getResource("src/danger.png"));
    /** картинка крови */
    private final static Icon ICON_BLOOD = new ImageIcon(Zombie.class.getResource("src/dead_zombie.png"));
    /** картинка взрыва */
    private final static Icon ICON_EXPLOISON = new ImageIcon(Zombie.class.getResource("src/explosion.png"));
    /** сколько жизней забирает зомби */
    public static final int LIFE_ZOMBIE_GET = 5;
    /** сколько жизней забирает взрыв */
    public static final int LIFE_EXPLOISON_GET = 10;
    /** отображен ли в данный момент зомби */
    private boolean active;
    /** переменная, которая говорит о том, сколько жизей забрал этот зомби */
    private int lifeMinus;
    /** говорит о том, что выбран зомби */
    private int zombie;
    /** говорит о том, что взрыв */
    private int exploison;

    /** по умолчанию отображается значок */
    public Zombie() {
        setLabel();
        setOpaque(true);
        setFocusPainted(false);
        addActionListener(this);
    }

    /** устанавливаем значок зомби */
    public void setZombie() {
        setIcon(ICON_ZOMBIE);
    }

    /** устанавливаем значок опасности */
    public void setLabel() {
        setIcon(ICON_LABEL);
    }

    /** устанавливаем значок крови */
    public void setBlood() {
        setIcon(ICON_BLOOD);
    }

    /** устанавливаем значок взрыва */
    public void setExplosion() {
        setIcon(ICON_EXPLOISON);
    }

    /**
     * активирован ли зоби
     *
     * @return true - активирован
     * false - не активирован
     */
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (active) {
            setBlood();
            zombie = 1;
        } else {
            setExplosion();
            lifeMinus = LIFE_EXPLOISON_GET;
            exploison = 1;
            Thread thread = new Thread(new Explosion());
            thread.setDaemon(true);
            thread.start();
        }
    }

    /**
     * Отнимаем кол-во жизней
     *
     * @param life - то кол-во жизней игрока, которое на текущий момент
     * @return кол-во жизней после того, как зомби укусил или произошел взрыв
     */
    public int getCountLife(int life) {
        life = life - lifeMinus;
        lifeMinus = 0;
        return life;
    }

    /**
     * убили ли зомби
     *
     * @return возвращаем единицу, если успели убить зомби
     */
    public int getCountZombie() {
        int returnZombie = zombie;
        zombie = 0;
        return returnZombie;
    }

    /**
     * взорвались ли
     *
     * @return возвращаем единицу, если произошел взрыв
     */
    public int getCountExploison() {
        int returnExploison = exploison;
        exploison = 0;
        return returnExploison;
    }

    /** класс реализует смену иконки взрыв на значок */
    private class Explosion implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "Смена взрыва на значок", ex);
            }
            setLabel();
        }

    }

}
