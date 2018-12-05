package application;
import java.util.ArrayList;

/**
 * @author Daihui Yu
 */
public class CompositeBall extends ComponentBall{

    private ArrayList<ComponentBall> children = new ArrayList<>();



    public CompositeBall(double x, double y, double r){
        super(x,y,r);
    }

    public CompositeBall(){

    }




    @Override
    public ArrayList<ComponentBall> getChildren(){
        return children;
    }

    @Override
    public void addChild(ComponentBall ball){
        children.add(ball);
    }

    @Override
    public void removeChild(ComponentBall ball){
        children.remove(ball);
    }




    ///////////////////////////////////////////////////////////////
    @Override
    protected void setOpos(double oposX, double oposY) {

    }
    @Override
    protected void setColor(String color){ this.color = color; }
    @Override
    protected String getColor(){ return color; }
    @Override
    protected void setMass(double mass) {
        this.mass = mass;
    }
    @Override
    protected double getMass() {
        return mass;
    }
    @Override
    protected void setVelocity(double velx, double vely) {
        this.velx = velx;
        this.vely= vely;

    }
    @Override
    protected void setVelocityX(double velx) {
        this.velx = velx;

    }
    @Override
    protected double getVelocityX() {
        return velx;

    }
    @Override
    protected void setVelocityY(double vely) {
        this.vely= vely;

    }
    @Override
    protected double getVelocityY() {
        return vely;

    }


}
