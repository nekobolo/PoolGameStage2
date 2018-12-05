package application;

public class FactoryProducer {
	
	
	public static AbstractFactoryConfiguration getFactory(String choice){
		   
	      if(choice.equalsIgnoreCase("TABLE")){
	         return new ReadTableFactory();
	         
	      }else if(choice.equalsIgnoreCase("BALL")){
	         return new BallFactory();
	      }
	      
	      return null;
	   }

}
