package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;
import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject{
    public boolean isAlive = true;
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    private Direction direction = Direction.LEFT;
    public Snake(int x, int y) {
        super(x, y);
        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x+1, y);
        GameObject third = new GameObject(x+2, y);
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }
    public void draw(Game game){
        for (GameObject o: snakeParts) {
            if (isAlive == true) {
                if (snakeParts.indexOf(o) != 0) {
                    game.setCellValueEx(o.x, o.y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
                else {
                    game.setCellValueEx(o.x, o.y, Color.NONE, HEAD_SIGN, Color.BLACK,75);
                }
            }
            else {
                if (snakeParts.indexOf(o) != 0) {
                    game.setCellValueEx(o.x, o.y, Color.NONE, BODY_SIGN, Color.RED,75);
                }
                else {
                    game.setCellValueEx(o.x, o.y, Color.NONE, HEAD_SIGN, Color.RED,75);
                }
            }
        }
    }
    public void setDirection(Direction direction){
        GameObject a = snakeParts.get(0);
        GameObject b = snakeParts.get(1);
         if (this.direction == Direction.LEFT && a.x == b.x ||
        this.direction == Direction.RIGHT && a.x == b.x ||
        this.direction == Direction.UP && a.y == b.y ||
        this.direction == Direction.DOWN && a.y == b.y){
        }
        else{this.direction = direction;}
    }
    public void move(Apple apple){
        GameObject NewHead = createNewHead();
        if (NewHead.x >= SnakeGame.WIDTH || NewHead.x < 0 || NewHead.y >= SnakeGame.HEIGHT || NewHead.y < 0){
            isAlive = false;
        }
        if (NewHead.x == apple.x && NewHead.y == apple.y){
            apple.isAlive = false;
            snakeParts.add(0, NewHead);
        }
        else {
            if (checkCollision(NewHead) == true){isAlive = false;}
            else {
                snakeParts.add(0, NewHead);
                removeTail();
            }
        }
    }
    public void eatMushroom(Mushroom mushroom){
        GameObject head = snakeParts.get(0);
        if (head.x == mushroom.x && head.y == mushroom.y){
            mushroom.isAlive = false;
            if (snakeParts.size() >= 2){removeTail();}
        }
    }
    public void eatWatch(Watch watch){
        GameObject head = snakeParts.get(0);
        if (head.x == watch.x && head.y == watch.y){
            watch.isAlive = false;
        }
    }
    public GameObject createNewHead(){
        GameObject head = snakeParts.get(0);
        if (direction.equals(Direction.LEFT)){
            head =  new GameObject(head.x-1, head.y);
        }
        if (direction.equals(Direction.RIGHT)){
            head =  new GameObject(head.x+1, head.y);
        }
        if (direction.equals(Direction.UP)){
            head =  new GameObject(head.x, head.y-1);
        }
        if (direction.equals(Direction.DOWN)){
            head =  new GameObject(head.x, head.y+1);
        }
        return head;
    }
    public void removeTail(){
        int index = snakeParts.size();
        snakeParts.remove(index-1);
    }
    public boolean checkCollision(GameObject head){
        boolean ign = false;
        for (GameObject o: snakeParts) {
            if (head.x == o.x && head.y == o.y) {ign = true;break;}
            else ign = false;
        }
       return ign;
    }
    public int getLength(){
        return snakeParts.size();
    }
}