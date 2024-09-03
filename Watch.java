package com.javarush.games.snake;
import com.javarush.engine.cell.*;
public class Watch extends GameObject{
    public boolean isAlive = true;
    private static final String WATCH_SIGN = "\u231A";
    public Watch(int x, int y) {
        super(x, y);
    }
    public void draw(Game game) {
        game.setCellValueEx(x, y, Color.NONE, WATCH_SIGN, Color.WHITE, 75);
    }
}
