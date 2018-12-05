package application;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;


public class Pool_table extends Rectangle {

	/**
	 *  Added in stage 2: new features for table: image
	 *
	 */
	private String imagePath;
	private Long imageOffsetX;
	private Long imageOffsetY;
	private Long imageSizeX;
	private Long imageSizeY;
	private boolean imageOrColor;  //image:true  color:false
	private Circle[] pockets = new Circle[6];

	
	private double friction; 
	private final double stroke_width = 35;

	protected void setImageOrColor(boolean bool){ imageOrColor = bool; }
	protected boolean getImageOrColor(){ return imageOrColor;}
	protected void setPockets(Circle[] array){ pockets = array; }
	protected Circle[] getPockets(){ return pockets; }

	protected void setImageSize(Long x, Long y) {
		imageSizeX = x;
		imageSizeY = y;
	}

	protected Long getImageSizeX(){ return imageSizeX; }
	protected Long getImageSizeY(){ return imageSizeY; }


	protected void setImagePath(String path) { imagePath = path; }
	protected String getImagePath() { return imagePath; }

	protected void setImageOffset(Long x, Long y) {
		imageOffsetX = x;
		imageOffsetY = y;
	}

	protected Long getImageOffsetX(){ return imageOffsetX; }
	protected Long getImageOffsetY(){ return imageOffsetY; }


	Pool_table(int x, int y, int w, int h){
		super(x, y , w, h);
	}
	protected void setFriction(double friction) {
		this.friction = friction; 
	}
	protected double getFriction() {
		return friction; 
	}
	protected double getPoolWidth() {
		return stroke_width; 
	}
}
