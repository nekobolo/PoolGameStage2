package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import javafx.scene.shape.*;

import javafx.scene.image.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends Application {

    /*
    add cue stick variable
    */
	private Line stick;
	private double clickX;
	private double clickY;

	int whiteBallIndex = 0;

	// record if balls are moving
	// true : moving   false: not moving
	// save to mmemento only if all the balls are not moving && last frame is moving
	boolean lastFrameIsMoving = false;


	// number state in memento list
	int numOfSavedStates = 0;
	int currentState = -1;

	ArrayList<ComponentBall> brokenBalls = new ArrayList<>();


  public static void main(String[] args) { launch(args); }

  public Pair<Point2D, Point2D> calculateCollision(Point2D positionA, Point2D velocityA, double massA, Point2D positionB, Point2D velocityB, double massB) {


      Point2D collisionVector = positionA.subtract(positionB);
      collisionVector = collisionVector.normalize();


      double vA = collisionVector.dotProduct(velocityA);
      double vB = collisionVector.dotProduct(velocityB);


      if (vB <= 0 && vA >= 0) {
          return new Pair<>(velocityA, velocityB);
      }


      double optimizedP = (2.0 * (vA - vB)) / (massA + massB);


      Point2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
      Point2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));

      return new Pair<>(velAPrime, velBPrime);
  }

	public void moveComposite(ComponentBall compositeBall){
		for(int i=0;i<compositeBall.getChildren().size();i++){
			ComponentBall child = compositeBall.getChildren().get(i);
			child.setVelocityX(compositeBall.getVelocityX());
			child.setVelocityY(compositeBall.getVelocityY());
			child.setCenterX(compositeBall.getCenterX() + child.getOffsetXWithParent());
			child.setCenterY(compositeBall.getCenterY() + child.getOffsetYWithParent());

			if("composite".equals(child.getType())){
				moveComposite(child);
			}

		}
	}


  public void setAfterCollision(BallCollection balls, Bounds table_bounds) {
	  for(ComponentBall traverse_block : balls.getBalls()){
	    	 for (ComponentBall static_bloc : balls.getBalls()) {
		      if (traverse_block != static_bloc) {
		    	  checkBallCollisions( traverse_block,  static_bloc, table_bounds);

		    	  if("composite".equals(traverse_block.getType()) && !traverse_block.getBreakStatus()){
		    	  	moveComposite(traverse_block);
				  }
				  if("composite".equals(static_bloc.getType()) && !static_bloc.getBreakStatus()){
		    	  	moveComposite(static_bloc);
				  }

		        }
		      }
	    }
  }
  public void checkBallCollisions(ComponentBall traverse_block, ComponentBall static_bloc, Bounds table_bounds) {

		   double deltaX = traverse_block.getCenterX() - static_bloc.getCenterX();
		   double deltaY = traverse_block.getCenterY() - static_bloc.getCenterY();
		   double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		   if(distance <= traverse_block.getRadius() + static_bloc.getRadius()){



			   Point2D posA = new Point2D(traverse_block.getCenterX(), traverse_block.getCenterY());
		 	   Point2D velA = new Point2D(traverse_block.getVelocityX(), traverse_block.getVelocityY());
		 	   double massA = traverse_block.getMass();

		 	   Point2D posB = new Point2D(static_bloc.getCenterX(),static_bloc.getCenterY());
		 	   Point2D velB = new Point2D(static_bloc.getVelocityX(), static_bloc.getVelocityY());
		 	   double massB = static_bloc.getMass();;

		 	   Pair<Point2D, Point2D> results = calculateCollision(posA, velA, massA, posB, velB, massB);

			   if(traverse_block.getType().equals("composite")){

				   Point2D deltaV_A = new Point2D(velA.getX() - results.getKey().getX(),velA.getY() - results.getKey().getY());

				   breakingBallPhysics(traverse_block,deltaV_A);

			   }

			   if(static_bloc.getType().equals("composite")){

				   Point2D deltaV_B = new Point2D(velB.getX() - results.getValue().getX(),velB.getY() - results.getValue().getY());
				   breakingBallPhysics(static_bloc,deltaV_B);

			   }


		 	   traverse_block.setVelocityX(results.getKey().getX());
		 	   traverse_block.setVelocityY(results.getKey().getY());

		 	   static_bloc.setVelocityX(results.getValue().getX());
		 	   static_bloc.setVelocityY(results.getValue().getY());


		 	   traverse_block.setCenterX(traverse_block.getCenterX() + traverse_block.getVelocityX() );
		 	   traverse_block.setCenterY(traverse_block.getCenterY() + traverse_block.getVelocityY() );
		 	   checkWallCollsions(traverse_block, table_bounds); // make sure that balls dont go outside
		 	   checkWallCollsions(static_bloc, table_bounds);
		 	   static_bloc.setCenterX(static_bloc.getCenterX() + static_bloc.getVelocityX() );
		 	   static_bloc.setCenterY(static_bloc.getCenterY() + static_bloc.getVelocityY() );




		   }
  }


  public void checkWallCollsions(ComponentBall traverse_block, Bounds table_bounds) {
	  if(( traverse_block.getCenterY() >= (table_bounds.getMaxY()  - traverse_block.getRadius()))){

			 traverse_block.setVelocityY(-traverse_block.getVelocityY());


     	  	 traverse_block.setCenterY(traverse_block.getCenterY() + traverse_block.getVelocityY() );


  	  }
  	  if( traverse_block.getCenterY() <= (table_bounds.getMinY()  +  traverse_block.getRadius())){


      	   	traverse_block.setVelocityY(-traverse_block.getVelocityY());


         	 traverse_block.setCenterY(traverse_block.getCenterY() + traverse_block.getVelocityY() );

  	     	//break;

  	  }
  	  if(( traverse_block.getCenterX() <= (table_bounds.getMinX()  + traverse_block.getRadius()))){
      	    traverse_block.setVelocityX(-traverse_block.getVelocityX());

      		traverse_block.setCenterX(traverse_block.getCenterX() + traverse_block.getVelocityX() );




  	  }
  	  if(( traverse_block.getCenterX() >= (table_bounds.getMaxX()  - traverse_block.getRadius()))){
       	   	traverse_block.setVelocityX(-traverse_block.getVelocityX());

      	   	traverse_block.setCenterX(traverse_block.getCenterX() + traverse_block.getVelocityX() );


  	  }
  }
  public void moveBall(ComponentBall traverse_block, Bounds table_bounds) {
	  	 traverse_block.setCenterX(traverse_block.getCenterX() + traverse_block.getVelocityX() );
	  	 checkWallCollsions(traverse_block, table_bounds); // make sure ball does not go beyond walls
	  	 traverse_block.setCenterY(traverse_block.getCenterY() + traverse_block.getVelocityY() );
	  	 checkWallCollsions(traverse_block, table_bounds);
  }
  public void calculateFriction(ComponentBall traverse_block, double table_friction) {
	  if(traverse_block.getVelocityX() > 0) {
			 traverse_block.setVelocityX(traverse_block.getVelocityX() - table_friction);

			 if(traverse_block.getVelocityX() < 0) {
				 traverse_block.setVelocityX(0);
			 }
		 }
		 if(traverse_block.getVelocityX() < 0) {
			 traverse_block.setVelocityX(traverse_block.getVelocityX() + table_friction);
			 if(traverse_block.getVelocityX() > 0) {
				 traverse_block.setVelocityX(0);
			 }
		 }

		 if(traverse_block.getVelocityY() > 0) {
			 traverse_block.setVelocityY(traverse_block.getVelocityY() - table_friction);

			 if(traverse_block.getVelocityY() < 0) {
				 traverse_block.setVelocityY(0);
			 }
		 }
		 if(traverse_block.getVelocityY() < 0) {
			 traverse_block.setVelocityY(traverse_block.getVelocityY() + table_friction);
			 if(traverse_block.getVelocityY() > 0) {
				 traverse_block.setVelocityY(0);
			 }
		 }
  }


  public void breakingBallPhysics(ComponentBall ball, Point2D deltaV){

	  double ballMass = ball.getMass();
	  double ballStrength = ball.getStrength();
	  double ballRadius = ball.getRadius();
	  int numComponentBalls = ball.getChildren().size();


	  // Initial velocity of parent
	  Point2D preCollisionVelocity = new Point2D(ball.getVelocityX(), ball.getVelocityY());

	  //Point2D deltaV;
	  double a = deltaV.getX();
	  double b = deltaV.getY();
	  double c = Math.sqrt(Math.pow(a,2) + Math.pow(b,2));

	  double energyOfCollision = ballMass * Math.pow(c, 2);

	  if (ballStrength < energyOfCollision) {


		  double energyPerBall = energyOfCollision / numComponentBalls;

		  Point2D pointOfCollision = Point2D.ZERO.subtract(deltaV.normalize().multiply(ballRadius));

		  ball.setBreakStatus(true);


		  for(int i=0;i<numComponentBalls;i++){

		  	ComponentBall child = ball.getChildren().get(i);


		  	double componentBallMass = child.getMass();

		  	Point2D componentBallPosition = new Point2D(child.getCenterX(),child.getCenterY());

			//for each component ball
			double velX = preCollisionVelocity.getX() + (Math.sqrt(energyPerBall / componentBallMass) * componentBallPosition.subtract(pointOfCollision).normalize().getX());
			double velY = preCollisionVelocity.getY() + (Math.sqrt(energyPerBall / componentBallMass) * componentBallPosition.subtract(pointOfCollision).normalize().getY());

			child.setVelocity(velX,velY);

		  }


	  }
  }





  // cue stick physics function
  public void setDragListeners(final ComponentBall block, Pane root,Pool_table table) {
	    final Delta dragDelta = new Delta();

	    block.setOnMousePressed(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	        // record a delta distance for the drag and drop operation.
	        dragDelta.x = block.getCenterX() - mouseEvent.getSceneX();
	        dragDelta.y = block.getCenterY() - mouseEvent.getSceneY();

	        block.setOpos(block.getCenterX(), block.getCenterY());

	        block.setCursor(Cursor.NONE);

	        /* add in stage2 */
	        clickX = block.getCenterX();
	        clickY = block.getCenterY();

	        if(table.getImageOrColor()){
	        	clickX += table.getImageOffsetX();
	        	clickY += table.getImageOffsetY();
			}

	      }
	    });
	    block.setOnMouseReleased(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {

			  /* add in stage 2 */
			  if(stick != null){

				//calculate the release direction and set velocity
				  double velx = stick.getStartX() - stick.getEndX();
				  double vely = stick.getStartY() - stick.getEndY();
				  if(block.getVelocityX() == 0 && block.getVelocityY() == 0) {
					 block.setVelocity(velx/7 , vely/7 ); // Sensitivity
				  }
				  block.setCursor(Cursor.HAND);
			  }


			  root.getChildren().remove(stick);

			  stick = null;
	      }
	    });
	    block.setOnMouseDragged(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {

	      	/* add in stage 2 */
			  if(stick == null){
				  stick = new Line(clickX,clickY,mouseEvent.getX(),mouseEvent.getY());
				  root.getChildren().add(stick);
			  }
			  else{

				  stick.setEndX(mouseEvent.getX());
				  stick.setEndY(mouseEvent.getY());
			  }
	      }
	    });
	  }
	  class Delta { double x, y;}

	public void traverse_composite(ComponentBall ball, Pane root, Pool_table table, String action, BallCollection ballList, ArrayList<positionOfBall> positionList){


		for(int i=0; i < ball.getChildren().size();i++){

			if("composite".equals(ball.getChildren().get(i).getType())){

				if("add".equals(action)){
					if(table.getImageOrColor()){
						ball.getChildren().get(i).setLayoutX(table.getImageOffsetX());
						ball.getChildren().get(i).setLayoutY(table.getImageOffsetY());
					}
					root.getChildren().add(ball.getChildren().get(i));
					traverse_composite(ball.getChildren().get(i),root,table,"add",null,null);
				}

				if("break".equals(action)){

					ball.getChildren().get(i).setType("leaf");
					ballList.getBalls().add(ball.getChildren().get(i));
					traverse_composite(ball.getChildren().get(i),root,table,"break",ballList,null);

				}

				if("storePosition".equals(action)){

					ComponentBall curr = ball.getChildren().get(i);

					double positionX = curr.getCenterX();
					double positionY = curr.getCenterY();

					positionOfBall recordedPosition = new positionOfBall(curr,positionX,positionY);

					positionList.add(recordedPosition);

					traverse_composite(curr,null,null,"storePosition",null,positionList);

				}



			}
			else{

				if("add".equals(action)){
					if(table.getImageOrColor()){
						ball.getChildren().get(i).setLayoutX(table.getImageOffsetX());
						ball.getChildren().get(i).setLayoutY(table.getImageOffsetY());
					}
					root.getChildren().add(ball.getChildren().get(i));
				}

				if("break".equals(action)){
					ballList.getBalls().add(ball.getChildren().get(i));
				}

				if("storePosition".equals(action)){

					ComponentBall curr = ball.getChildren().get(i);

					double positionX = curr.getCenterX();
					double positionY = curr.getCenterY();

					positionOfBall recordedPosition = new positionOfBall(curr,positionX,positionY);

					positionList.add(recordedPosition);

				}
			}
		}

	}


	public void dropIntoPocket(Pool_table table, BallCollection balls, Pane root){
		Circle[] pockets = table.getPockets();

		for(int i=0;i<balls.getSize();i++){
			ComponentBall current = balls.getElement(i);
			for(int j=0;j<6;j++){
				double pocketX;
				double pocketY;

				if(table.getImageOrColor()){
					pocketX = pockets[j].getLayoutX();
					pocketY = pockets[j].getLayoutY();
				}

				pocketX = pockets[j].getCenterX();
				pocketY = pockets[j].getCenterY();

				double ballX = current.getCenterX();
				double ballY = current.getCenterY();

				double deltaX = pocketX - ballX;
				double deltaY = pocketY - ballY;

				double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

				if(distance <= pockets[j].getRadius() - current.getRadius()){
					//root.getChildren().remove(current);
					//balls.getBalls().remove(current);
					current.setCenterX(-100.0);
					current.setCenterY(-100.0);
					//current.setDropped(true);
				}
			}
		}

	}

	/**
	 *
	 * @return true if balls are moving, false otherwise
	 */
	public boolean checkBallsCondition(BallCollection balls, Pool_table table, Pane root){
		boolean result = false;
		for(int i=0;i<balls.getSize();i++){
			if(balls.getElement(i).getVelocityX() != 0.0 || balls.getElement(i).getVelocityY()!=0.0){
				result = true;
				return result;
			}
		}
		return result;
	}






  @Override public void start(Stage primaryStage) {




	  String filepath = "config.json";

	  AbstractFactoryConfiguration TableFactory = FactoryProducer.getFactory("table");
	  AbstractFactoryConfiguration BallFactory = FactoryProducer.getFactory("ball");

	  Pool_table new_table = TableFactory.getPoolTable(filepath);
	  BallCollection balls =  BallFactory.getPoolBalls(filepath);

	  // Get the index for the white ball
	  for(int i=0;i<balls.getSize();i++){
		  if("white".equals(balls.getElement(i).getColor())){
		  	whiteBallIndex = i;
		  }
	  }



	  double table_friction = new_table.getFriction();

	  primaryStage.setTitle("Press white circles and release to execute virtual cue stick");



	  Circle[] pockets = new_table.getPockets();


	  final Pane root = new Pane();
	  Scene scene;

	  if(!new_table.getImageOrColor()){
		  scene = new Scene(root, new_table.getWidth(), new_table.getHeight());

		  root.getChildren().add(new_table);
		  // Add pockets
		  for(int i=0;i<6;i++){
			  root.getChildren().add(pockets[i]);
		  }
		  // the background is an image which contain offsets
		  // so balls also need to relocate
		  for(int i=0;i<balls.getSize();i++){

			  ComponentBall currentBall = balls.getElement(i);


			  if("composite".equals(currentBall.getType())){

				  if(new_table.getImageOrColor()){
					  currentBall.setLayoutX(new_table.getImageOffsetX());
					  currentBall.setLayoutY(new_table.getImageOffsetY());
				  }

				  root.getChildren().add(currentBall);

				  // add ball into table
				  traverse_composite(currentBall,root,new_table,"add",null,null);

			  }

			  else{
				  if(new_table.getImageOrColor()){
					  currentBall.setLayoutX(new_table.getImageOffsetX());
					  currentBall.setLayoutY(new_table.getImageOffsetY());
				  }
				  root.getChildren().add(currentBall);
			  }


		  }


	  }
	  else{
		  scene = new Scene(root, new_table.getImageSizeX(), new_table.getImageSizeY());

		  root.getChildren().add(new_table);

		  Image backgroundImage = new Image(new_table.getImagePath());
		  ImageView mv = new ImageView(backgroundImage);
		  mv.setFitWidth(new_table.getImageSizeX());
		  mv.setFitHeight(new_table.getImageSizeY());

		  root.getChildren().add(mv);

		  // Add pockets
		  for(int i=0;i<6;i++){
			  root.getChildren().add(pockets[i]);
		  }
		  //the background is an image which contain offsets
		  // so balls also need to relocate
		  for(int i=0;i<balls.getSize();i++){

			  ComponentBall currentBall = balls.getElement(i);


			  if("composite".equals(currentBall.getType())){

				  if(new_table.getImageOrColor()){
					  currentBall.setLayoutX(new_table.getImageOffsetX());
					  currentBall.setLayoutY(new_table.getImageOffsetY());
				  }

				  root.getChildren().add(currentBall);

				  // add ball into table
				  traverse_composite(currentBall,root,new_table,"add",null,null);

			  }

			  else{
				  if(new_table.getImageOrColor()){
					  currentBall.setLayoutX(new_table.getImageOffsetX());
					  currentBall.setLayoutY(new_table.getImageOffsetY());
				  }
				  root.getChildren().add(currentBall);
			  }


		  }


	  }

		/* add in stage 2
	  	*  improve the implementation: only let the white ball can be clicked
	  	* */
	  setDragListeners(balls.getElement(whiteBallIndex),root,new_table);


	  // Used for memento  <buttons>
	  Button undoButton = new Button("undo");
	  Button restartButton = new Button("restart");
	  undoButton.relocate(0,200);
	  restartButton.relocate(0,250);
	  root.getChildren().add(undoButton);
	  root.getChildren().add(restartButton);


	  Originator originator = new Originator();
	  Caretaker caretaker = new Caretaker();



	  undoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		  @Override
		  public void handle(MouseEvent event) {
			  if(numOfSavedStates>=2) {


				  currentState--;
				  ArrayList<positionOfBall> positionList = originator.restoreBallsFromMemento(caretaker.getMemento(currentState));

				  // reset positions of balls
				  for(int i=0;i<balls.getSize();i++){

				  	ComponentBall curr = balls.getElement(i);


				  	for(int j=0;j<positionList.size();j++){

				  		if(curr == positionList.get(j).getPositionBall()){
				  			double positionX = positionList.get(j).getPositionX();
				  			double positionY = positionList.get(j).getPositionY();
				  			curr.setCenterX(positionX);
				  			curr.setCenterY(positionY);
						}

						// if encounter a broken ball, restore it
						if(positionList.get(j).getPositionBall().getBreakStatus()){

				  			positionOfBall tmp = positionList.get(j);

				  			ComponentBall restoredBall = tmp.getPositionBall();


				  			// remove its children from balls
							for(int x = 0; x < restoredBall.getChildren().size();x++){

								for(int y = 0; y < balls.getSize();y++){

									ComponentBall child = restoredBall.getChildren().get(x);
									if(child == balls.getElement(y)){
										balls.getBalls().remove(child);
										root.getChildren().remove(child);
									}
								}
							}


							double positionX = tmp.getPositionX();
							double positionY = tmp.getPositionY();

							restoredBall.setVelocity(0.0,0.0);
							restoredBall.setCenterX(positionX);
							restoredBall.setCenterY(positionY);


							// add restored broken ball into ball list and root
							if(restoredBall.getIsRemoved()){
								balls.addBall(restoredBall);
								root.getChildren().add(restoredBall);
								traverse_composite(restoredBall,root,new_table,"add",null,null);
								restoredBall.setIsRemoved(false);
								restoredBall.setDropped(false);
								restoredBall.setBreakStatus(false);
							}


						}

					}

				  }




				  // remove current state from memento
				  int indexToRemove = currentState + 1;
				  caretaker.removeMemento(caretaker.getMemento(indexToRemove));
				  numOfSavedStates--;



			  }
		  }
	  });

	  restartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		  @Override
		  public void handle(MouseEvent event) {
			  if(numOfSavedStates>=1){

				  currentState = 0;
				  ArrayList<positionOfBall> positionList = originator.restoreBallsFromMemento(caretaker.getMemento(currentState));


				  // reset positions of balls
				  for(int i=0;i<balls.getSize();i++){

					  ComponentBall curr = balls.getElement(i);

					  for(int j=0;j<positionList.size();j++){

						  if(curr == positionList.get(j).getPositionBall()){
							  double positionX = positionList.get(j).getPositionX();
							  double positionY = positionList.get(j).getPositionY();
							  curr.setCenterX(positionX);
							  curr.setCenterY(positionY);
						  }

						  // if encounter a broken ball, restore it
						  if(positionList.get(j).getPositionBall().getBreakStatus()){

							  positionOfBall tmp = positionList.get(j);



							  ComponentBall restoredBall = tmp.getPositionBall();


							  // remove its children from balls
							  for(int x = 0; x < restoredBall.getChildren().size();x++){

								  for(int y = 0; y < balls.getSize();y++){

									  ComponentBall child = restoredBall.getChildren().get(x);
									  if(child == balls.getElement(y)){
										  balls.getBalls().remove(child);
										  root.getChildren().remove(child);
									  }
								  }
							  }


							  double positionX = tmp.getPositionX();
							  double positionY = tmp.getPositionY();

							  restoredBall.setVelocity(0.0,0.0);

							  restoredBall.setCenterX(positionX);
							  restoredBall.setCenterY(positionY);

							  // add restored broken ball into ball list and root
							  if(restoredBall.getIsRemoved()){
								  balls.addBall(restoredBall);
								  root.getChildren().add(restoredBall);
								  traverse_composite(restoredBall,root,new_table,"add",null,null);
								  restoredBall.setIsRemoved(false);
								  restoredBall.setDropped(false);
								  restoredBall.setBreakStatus(false);
							  }


						  }

					  }

				  }

				  // remove other states from memento
				  int indexToRemove = numOfSavedStates-1;
				  for(int i = indexToRemove;i>0;i--){
					  caretaker.removeMemento(caretaker.getMemento(i));

				  }
				  numOfSavedStates = 1;

			  }
		  }
	  });







	  //The Time line watches for the event when the ball hits a wall
	 Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),
             new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent t) {

			 boolean currentMovingState = checkBallsCondition(balls,new_table,root);
			 if(!currentMovingState && lastFrameIsMoving){


			 	// Store positions of all the balls into a list
				ArrayList<positionOfBall> list = new ArrayList<>();

				for(int i=0;i<balls.getSize();i++){
					ComponentBall curr = balls.getElement(i);

					double positionX = curr.getCenterX();
					double positionY = curr.getCenterY();


					positionOfBall recordedPosition = new positionOfBall(curr,positionX,positionY);

					list.add(recordedPosition);



					if(curr.getType().equals("composite")){
						traverse_composite(curr,null,null,"storePosition",null,list);
					}



				}

				 saveMemento command = new saveMemento(caretaker,originator,list);

				// Store the list contains positions of all the balls at this state into memento
				 command.execute();
				 numOfSavedStates++;
				 currentState++;


			 }
			 lastFrameIsMoving = currentMovingState;



         	dropIntoPocket(new_table,balls,root);

         	for(int i=0;i<balls.getSize();i++){
         		if(balls.getElement(i).getType().equals("composite") && balls.getElement(i).getBreakStatus()){
         			ComponentBall parentBall = balls.getElement(i);

         			traverse_composite(parentBall,root,new_table,"break",balls,null);


					parentBall.setIsRemoved(true);

					brokenBalls.add(parentBall);

         			root.getChildren().remove(parentBall);

         			balls.getBalls().remove(parentBall);

				}
			}

        	 Bounds table_bounds =  new_table.getBoundsInLocal();

        	 for(ComponentBall traverse_block : balls.getBalls()){

        		 moveBall(traverse_block, table_bounds);
        		 setAfterCollision(balls, table_bounds); // trying to make sure that collision is registered even at high speeds
        		 calculateFriction(traverse_block, table_friction);
        		 checkWallCollsions(traverse_block, table_bounds);

    	     }


        	 setAfterCollision(balls, table_bounds);






	}
     }));

	 timeline.setCycleCount(Timeline.INDEFINITE);
     timeline.play();




	 primaryStage.setScene(scene);
	 primaryStage.show();


  }
}
