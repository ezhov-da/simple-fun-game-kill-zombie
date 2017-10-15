/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ezhov.killzombie;

import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Проигрываем музыку при старте игры
 *
 * @author rrndeonisiusezh
 */
public class PlayMusic {

    private static Player player;

    private PlayMusic() {

    }

    public static void play() {
        try {
            player = new Player(KillZombie.class.getResourceAsStream("src/play_music.mp3"));
            player.play();
        } catch (JavaLayerException ex) {
            Logger.getLogger(PlayMusic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
