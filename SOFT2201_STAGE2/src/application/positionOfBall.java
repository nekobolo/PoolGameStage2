package application;

/**
 * Self-create data structure to store position of balls used for memento
 * positionX & positionY is corresponding position for the ball at a single state
 */
public class positionOfBall {

    private ComponentBall ball;
    private double positionX;
    private double positionY;

    positionOfBall(ComponentBall ball, double positionX, double positionY){
        this.ball = ball;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX(){
        return positionX;
    }

    public double getPositionY(){
        return positionY;
    }

    public ComponentBall getPositionBall(){
        return ball;
    }

}
