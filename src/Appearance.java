import java.awt.Color;
import java.awt.Graphics;


public class Appearance {

	String shape;
	Color colour;
	int mX;
	int mY;
	boolean DrawingLine;
	
	
	public Appearance(String Shape, Color Colour) {
		shape = Shape;
		colour = Colour;
	}
	
	
	public void ChangeColour(Color Colour){
		colour = Colour;
	}
	
	public void ChangeShape(String Shape){
		shape = Shape;
	}

	public String getShape() {
		
		return shape;
	}

	public Color getColour() {
		
		return colour;
	}
	public void InfectionCollisionColour(GameObject object1, GameObject object2){
		
	}
	
	public void DrawLineFromCentre(){
		DrawingLine = true;
		
	}
	public void StopDrawingLine(){
		DrawingLine = false;
	}


}
