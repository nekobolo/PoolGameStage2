package application;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 *
 * Use for Memento DP
 * @author Daihui Yu
 */
public class Memento {

    private ArrayList<positionOfBall> balls;

     //Constructor
    public Memento(ArrayList<positionOfBall> balls){

        this.balls = balls;
    }


    public ArrayList<positionOfBall> getSavedBalls(){ return balls; }



}
