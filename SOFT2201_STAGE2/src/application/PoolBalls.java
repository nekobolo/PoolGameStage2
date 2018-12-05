package application;

import javafx.scene.shape.Circle;

/**
 * Modified:
 * Adding new variable: color and corresponding methods
 * Change the naming of variables to follow lowerCamelCase style
 */
public abstract class PoolBalls extends ComponentBall  {

	PoolBalls(double x, double y, double r){
		super(x, y ,r); 
	}
	PoolBalls(){

		
	}

	@Override
	public void addChild(ComponentBall ball){

		System.out.println("Cannot add to a leaf ball\n");

	}

	@Override
	public void removeChild(ComponentBall ball){

		System.out.println("Cannot remove from a leaf ball\n");

	}


}
