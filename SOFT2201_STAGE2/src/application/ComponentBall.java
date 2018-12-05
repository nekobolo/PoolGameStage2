package application;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * @author Daihui Yu
 */
public abstract class ComponentBall extends Circle {

    /**
     *   leaf or composite
     */
    private String type;

    private int radius;

    private double strength;

    private boolean isDropped = false; //true: dropped into pockets  false: not drop

    private boolean isRemoved = false; // true: removed  false: not removed

    private boolean isBreak = false;  // true: broken   false: not broken

    private double offsetXWithParent;
    private double offsetYWithParent;

    protected String color;
    protected  double velx;
    protected double vely;
    protected double changeX, changeY;
    protected double oposX, oposY;
    protected double mass;

    public void setIsRemoved(boolean i){
        isRemoved = i;
    }

    public boolean getIsRemoved(){
        return isRemoved;
    }

    public ComponentBall(double x, double y, double r){
        super(x,y,r);
    }

    public ComponentBall(){

    }

    public void setDropped(boolean b){
        isDropped = b;
    }

    public boolean getDroppedCondition(){
        return isDropped;
    }

    public void setOffsetWithParent(double x, double y){
        offsetXWithParent = x;
        offsetYWithParent = y;
    }

    public double getOffsetXWithParent(){ return offsetXWithParent; }
    public double getOffsetYWithParent(){ return offsetYWithParent; }


    /**
     *   Add a component ball into children of composite
     */
    public abstract void addChild(ComponentBall ball);

    /**
     *   Remove a component ball from children of composite
     */
    public abstract void removeChild(ComponentBall ball);

    public boolean getBreakStatus(){ return isBreak; }
    public void setBreakStatus(boolean bool){
        isBreak = bool;
    }

    public void setRadius(int radius){ this.radius = radius; }


    public String getType(){ return type; }
    public void setType(String type){ this.type = type; }

    public void setStrength(double strength){ this.strength = strength;}
    public double getStrength(){ return strength;}

    protected abstract ArrayList<ComponentBall> getChildren();

    protected abstract void setColor(String color);
    protected abstract String getColor();

    protected abstract void setMass(double mass);
    protected abstract double getMass();
    protected abstract void setVelocity(double velx, double vely);
    protected abstract void setVelocityX(double velx);
    protected abstract double getVelocityX();
    protected abstract void setVelocityY(double vely);
    protected abstract double getVelocityY();
   //protected abstract void setChange(double changeX, double changeY);

    //protected abstract double getChangeX();
    //protected abstract double getChangeY();
    protected abstract void setOpos(double oposX, double oposY);
    //protected abstract double getOposX();
    //protected abstract double getOposY();




}
