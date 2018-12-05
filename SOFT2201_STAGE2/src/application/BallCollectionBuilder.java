package application;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javafx.scene.paint.Color;
public class BallCollectionBuilder extends Builder{



	BallCollectionBuilder(JSONArray arrayToRead){
		this.arrayToRead = arrayToRead;
	}


	// Create simple ball defined in stage 1
	public ComponentBall createSimpleBall(JSONObject ball, ComponentBall parent){

		JSONObject position  = (JSONObject) ball.get("position");

		String colour = (String) ball.get("colour");

		Double mass = (Double)  ball.get("mass");

		Double d_pX = (Double) position.get("x");
		//int n_pX = (int) Math.round(d_pX);

		Double d_pY = (Double) position.get("y");
		//int n_pY = (int) Math.round(d_pY);

		Double d_vX = 0.0;
		Double d_vY = 0.0;

		Double offsetX = d_pX;
		Double offsetY = d_pY;

		if(parent != null){
			d_vX = parent.getVelocityX();
			d_vY = parent.getVelocityY();

			d_pX += parent.getCenterX();
			d_pY += parent.getCenterY();

		}
		else{

			JSONObject velocity  = (JSONObject) ball.get("velocity");
			d_vX = (Double) velocity.get("x");
			d_vY = (Double) velocity.get("y");
		}


		Double radius = 0.0;

		if(parent == null){
			// This is not a child of composite ball
			radius = 15.0;
		}
		else{
			radius = parent.getRadius() * 0.66;
		}






		if(colour.equals("white")) {


			PoolBalls ball_element = new Cue_pool_ball(d_pX, d_pY, radius);
			ball_element.setOffsetWithParent(offsetX,offsetY);
			ball_element.setType("leaf");
			ball_element.setRadius(radius);
			ball_element.setColor("white");
			ball_element.setVelocity(d_vX, d_vY);
			ball_element.setMass(mass);
			Color c = Color.web(colour);
			ball_element.setFill(c);
			//ballCollection.addBall(ball_element);
			return ball_element;

		}
		else {
			PoolBalls ball_element = new Reg_pool_ball(d_pX, d_pY, radius);
			ball_element.setOffsetWithParent(offsetX,offsetY);
			ball_element.setType("leaf");
			ball_element.setRadius(radius);
			ball_element.setVelocity(d_vX, d_vY);
			ball_element.setMass(mass);
			Color c = Color.web(colour);
			ball_element.setFill(c);
			ball_element.setColor(colour);
			//ballCollection.addBall(ball_element);
			return ball_element;
		}

	}


	// Stage 2 ball creation
	public ComponentBall createComponentBall(JSONObject ball, ComponentBall parent){


		// ball with "mass" is a simple ball
		if(ball.containsKey("mass")){

			ComponentBall simpleBall = createSimpleBall(ball, parent);

			return simpleBall;

		}

		// Otherwise it is composite
		else{
			JSONObject position  = (JSONObject) ball.get("position");

			String colour = (String) ball.get("colour");

			Double strength = (Double) ball.get("strength");

			Double d_pX = (Double) position.get("x");
			//int n_pX = (int) Math.round(d_pX);

			Double d_pY = (Double) position.get("y");
			//int n_pY = (int) Math.round(d_pY);

			Double d_vX = 0.0;
			Double d_vY = 0.0;

			Double offsetX = d_pX;
			Double offsetY = d_pY;

			if(parent != null){
				d_vX = parent.getVelocityX();
				d_vY = parent.getVelocityY();


				d_pX += parent.getCenterX();
				d_pY += parent.getCenterY();

			}
			else{

				JSONObject velocity  = (JSONObject) ball.get("velocity");
				d_vX = (Double) velocity.get("x");
				d_vY = (Double) velocity.get("y");
			}

			Double mass = 0.0;   // mass = sum(mass of children)

			Double radius = 0.0;

			if(parent == null){
				radius = 15.0;
			}
			else{
				radius = parent.getRadius() * 0.66;
			}

			ComponentBall cb = new CompositeBall(d_pX, d_pY, radius);


			cb.setOffsetWithParent(offsetX,offsetY);
			cb.setBreakStatus(false);
			cb.setType("composite");
			cb.setRadius(radius);
			cb.setVelocity(d_vX, d_vY);

			Color c = Color.web(colour);
			cb.setFill(c);
			cb.setColor(colour);
			cb.setStrength(strength);


			JSONArray childrenBalls = (JSONArray)ball.get("balls");

			for(int i=0; i < childrenBalls.size();i++){
				ComponentBall child = createComponentBall((JSONObject) childrenBalls.get(i),cb);
				mass += child.getMass();
				cb.addChild(child);
			}

            cb.setMass(mass);

			return cb;
		}



	}




	@Override
	public BallCollection ReadBalls() {

		BallCollection ballCollection = new BallCollection();

		if(arrayToRead != null) {

			for(int i=0; i< arrayToRead.size(); i++){

				JSONObject ball = (JSONObject) arrayToRead.get(i);

				ComponentBall componentBall = createComponentBall(ball,null);

				ballCollection.addBall(componentBall);

			}

			return ballCollection;

		}
		else {
			System.out.println( "Array is null");
		}


		return null;
	}

}
