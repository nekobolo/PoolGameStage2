package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BallFactory implements AbstractFactoryConfiguration{

	@Override
	public Pool_table getPoolTable(String filepath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BallCollection getPoolBalls(String filepath) {
		
		JSONParser parser = new JSONParser();
		try {

		      Object obj = parser.parse(new FileReader(filepath));

		      
		      JSONObject jsonObject = (JSONObject) obj;

		      JSONObject Balls = (JSONObject) jsonObject.get("Balls");
		      
		      JSONArray ball_array = (JSONArray) Balls.get("ball");

	          
	          Builder ballCollectionBuilder = new BallCollectionBuilder(ball_array);
	          BallCollection ballCollection = ballCollectionBuilder.ReadBalls();

	          return ballCollection; 


		  } catch (FileNotFoundException e1) {
		      e1.printStackTrace();
		  } catch (IOException e2) {
		      e2.printStackTrace();
		  } catch (ParseException e3) {
		      e3.printStackTrace();
		  }
		
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	

}
