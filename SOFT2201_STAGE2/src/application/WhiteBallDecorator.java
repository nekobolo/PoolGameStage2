package application;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Modified:
 * Change the naming of variables to follow lowerCamelCase style
 */
public class WhiteBallDecorator extends BallDecorator {

	@Override
	public ArrayList<ComponentBall> getChildren(){ return null;}
	
	
	 public WhiteBallDecorator(PoolBalls decoratedBall) {
		 
		
	      super(decoratedBall);	
	  
	 }
	 @Override
	   public void setMass(double mass) {
		 decoratedBall_l.setMass(mass+2);	       
		 paintWhite(decoratedBall_l); 
	   }
	 //decorating function
	public void paintWhite(PoolBalls decoratedBall) {
		decoratedBall.setFill(Color.WHITE);
	}
	@Override
	protected double getMass() {
		// TODO Auto-generated method stub
		return mass; 
	}
	@Override
	protected String getColor(){
	 	return color;
	}
	@Override
	protected void setColor(String color){
		this.color = color;
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
//	@Override
//	protected void setChange(double changeX, double changeY) {
//		this.changeX = changeX;
//		this.changeY = changeY;
//	}
//	@Override
//	protected double getChangeX() {
//		return changeX;
//	}
//	@Override
//	protected double getChangeY() {
//		return changeY;
//	}
	@Override
	protected void setOpos(double oposX, double oposY) {
		this.oposX = oposX;
		this.oposY = oposY;
	}
//	@Override
//	protected double getOposX() {
//		return oposX;
//	}
//	@Override
//	protected double getOposY() {
//		return oposY;
//	}
	 
	 
}
