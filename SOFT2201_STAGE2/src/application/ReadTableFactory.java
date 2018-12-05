package application;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.shape.Circle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.paint.Color;



public class ReadTableFactory implements AbstractFactoryConfiguration {

	@Override
	public Pool_table getPoolTable(String filepath) {
		
		JSONParser parser = new JSONParser();
		  

	    try {
	    	
	      Object obj = parser.parse(new FileReader(filepath));
			
		  JSONObject jsonObject = (JSONObject) obj;
		    
	      JSONObject table = (JSONObject) jsonObject.get("Table");

	      if(table.containsKey("image")){

			  JSONObject image = (JSONObject) table.get("image");

			  String imagePath = (String) image.get("path");

			  JSONObject offset = (JSONObject) image.get("offset");
			  Long offsetX = (Long) offset.get("x");
			  Long offsetY = (Long) offset.get("y");

			  JSONObject imageSize = (JSONObject) image.get("size");
			  Long imageX = (Long) imageSize.get("x");
			  Long imageY = (Long) imageSize.get("y");

			  Circle[] pocketsCollection = new Circle[6];

			  // loop through pocket array
			  JSONArray pockets = (JSONArray) table.get("pockets");
			  int pocketIndex = 0;
			  for(Object p : pockets){

				  // Get position
				  JSONObject position = (JSONObject) ((JSONObject) p).get("position");
				  double positionX = (Double) position.get("x");
				  double positionY = (Double) position.get("y");

				  // Get radius
				  double radius = (Double) ((JSONObject) p).get("radius");


				  Circle pocket = new Circle(positionX,positionY,radius);

				  pocket.setRadius(radius);
				  pocket.setFill(Color.BLACK);
				  pocketsCollection[pocketIndex] = pocket;

				  pocketIndex++;

			  }


			  String friction = String.valueOf(table.get("friction")) ;
			  double friction_number = Double.parseDouble(friction);

			  JSONObject size = (JSONObject) table.get("size");

			  String table_x = String.valueOf(size.get("x")) ;
			  String table_y = String.valueOf(size.get("y")) ;

			  int n_table_x = Integer.parseInt(table_x);
			  int n_table_y = Integer.parseInt(table_y);

			  Pool_table pool = new Pool_table(0, 0, n_table_x, n_table_y);

			  pool.setTranslateX(offsetX);
			  pool.setTranslateY(offsetY);

			  pool.setPockets(pocketsCollection);
			  pool.setImageSize(imageX,imageY);
			  pool.setImagePath(imagePath);
			  pool.setImageOffset(offsetX,offsetY);
			  pool.setFriction(friction_number);
			  pool.setImageOrColor(true);
			  return pool;

		  }
		  else{
			  String table_colour = (String) table.get("colour");

			  Circle[] pocketsCollection = new Circle[6];

			  // loop through pocket array
			  JSONArray pockets = (JSONArray) table.get("pockets");
			  int pocketIndex = 0;
			  for(Object p : pockets){

				  // Get position
				  JSONObject position = (JSONObject) ((JSONObject) p).get("position");
				  double positionX = (Double) position.get("x");
				  double positionY = (Double) position.get("y");

				  // Get radius
				  double radius = (Double) ((JSONObject) p).get("radius");


				  Circle pocket = new Circle();

				  pocket.setRadius(radius);
				  pocket.setFill(Color.BLACK);
				  pocket.relocate(positionX,positionY);
				  pocketsCollection[pocketIndex] = pocket;

				  pocketIndex = pocketIndex + 1;

			  }
			  String friction = String.valueOf(table.get("friction")) ;
			  double friction_number = Double.parseDouble(friction);

			  JSONObject size = (JSONObject) table.get("size");

			  String table_x = String.valueOf(size.get("x")) ;
			  String table_y = String.valueOf(size.get("y")) ;

			  int n_table_x = Integer.parseInt(table_x);
			  int n_table_y = Integer.parseInt(table_y);

			  Pool_table pool = new Pool_table(0, 0, n_table_x, n_table_y);

			  Color c = Color.web(table_colour);
			  pool.setPockets(pocketsCollection);
			  pool.setFill(c);
			  pool.setFriction(friction_number);
			  pool.setImageOrColor(false);
			  return pool;

		  }

        

	  } catch (FileNotFoundException e1) {
	      e1.printStackTrace();
	  } catch (IOException e2) {
	      e2.printStackTrace();
	  } catch (ParseException e3) {
	      e3.printStackTrace();
	  }
	    
	    return null; 
	    
	}

	@Override
	public BallCollection getPoolBalls(String filepath) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
