package com.javarush.games.snake;
import com.javarush.engine.cell.*;

public class Mushroom extends GameObject{
    public boolean isAlive = true;
    private static final String MUSHROOM_SIGN = "\uD83C\uDF44";
    public Mushroom(int x, int y) {super(x, y);}
    public void draw(Game game){game.setCellValueEx(x, y, Color.NONE, MUSHROOM_SIGN, Color.RED, 75);
    }
}
