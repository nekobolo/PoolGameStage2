package application;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 *
 * Use for Memento DP
 *
 * @author Daihui Yu
 */
public class Originator {

    private ArrayList<positionOfBall> balls;


    public void setState(ArrayList<positionOfBall> balls){
        this.balls = balls;
    }


    public Memento saveToMemento(){
        return new Memento(balls);
    }


    public ArrayList<positionOfBall> restoreBallsFromMemento(Memento memento){
        balls = memento.getSavedBalls();
        return balls;
    }



    public ArrayList<positionOfBall> getState(){
        return balls;
    }


}
