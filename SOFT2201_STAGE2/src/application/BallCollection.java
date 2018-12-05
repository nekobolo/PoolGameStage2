package application;

import java.util.ArrayList;

public class BallCollection {


	 protected ArrayList<ComponentBall> nodes = new  ArrayList<>();
	 protected int size;

	 public int getSize(){ return nodes.size(); }

	 public ComponentBall getElement(int index){ return nodes.get(index);}

	 public void addBall(ComponentBall ball) {
		 nodes.add(ball);
	 }

	 public ArrayList<ComponentBall> getBalls(){
		 return nodes;
	 }

	 public void setBalls(ArrayList<ComponentBall> list){
	 	nodes = list;
	 }
}
