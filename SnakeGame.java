package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private Apple apple;
    private Mushroom mushroom;
    private Watch watch;
    private int turnDelay;
    private boolean isGameStopped;
    private static final int GOAL = 30;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame(){
        score = 0;
        snake =  new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        createNewMushroom();
        createNewWatch();
        isGameStopped = false;
        timer1.start();
        timer2.start();
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        setScore(score);
    }
    private void drawScene(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
        if (snake.getLength() >= 10) {
            mushroom.draw(this);
        }
        if (snake.getLength() >= 15){
            watch.draw(this);
        }
    }
    @java.lang.Override
    public void onTurn(int step) {
        snake.move(apple);
        snake.eatMushroom(mushroom);
        snake.eatWatch(watch);
        if (apple.isAlive == false){
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if (mushroom.isAlive == false){
            createNewMushroom();
            score += 10;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if (watch.isAlive == false){
            createNewWatch();
            turnDelay += 20;
            setTurnTimer(turnDelay);
        }
        if (snake.isAlive == false){gameOver();}
        if (snake.getLength() > GOAL){win();}
        drawScene();
    }
    @Override
    public void onKeyPress(Key key){
        if (key == Key.LEFT){snake.setDirection(Direction.LEFT);}
        if (key == Key.RIGHT){snake.setDirection(Direction.RIGHT);}
        if (key == Key.UP){snake.setDirection(Direction.UP);}
        if (key == Key.DOWN){snake.setDirection(Direction.DOWN);}
        if (key == Key.SPACE && isGameStopped == true){createGame();}
    }
     private void createNewApple(){
        do {apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));}
        while (snake.checkCollision(apple) == true);
    }
    private void createNewMushroom() {
        do {mushroom = new Mushroom(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));}
        while (snake.checkCollision(mushroom) == true);
    }
    private void createNewWatch(){
        do {watch = new Watch(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));}
        while (snake.checkCollision(watch) == true);
    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE,"GAME OVER", Color.MAGENTA, 100);
    }
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE,"YOU WIN", Color.ROSYBROWN, 100);
    }
    Timer timer1 = new Timer(1500, new ActionListener() {
        @java.lang.Override
        public void actionPerformed(ActionEvent e) {
            createNewMushroom();
            if (isGameStopped == true)timer1.stop();
        }
    });
    Timer timer2 = new Timer(3000, new ActionListener() {
        @java.lang.Override
        public void actionPerformed(ActionEvent e) {
            if (snake.getLength() >= 20) {createNewApple();}
            if (isGameStopped == true)timer2.stop();
        }
    });
}