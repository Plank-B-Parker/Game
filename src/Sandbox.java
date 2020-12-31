import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Sandbox extends MouseAdapter implements MouseMotionListener{
	GameObjectStorage objects;
	GameObject tempObject;
	game game;
	boolean settingVel = false;
	boolean drawingCirc = false;
	boolean pickedUP = false;
	int CentreX;
	int CentreY;
	Random random;
	JSlider gravity, ballGravity, wallEfficeincy;
	static int mx = 0;
	static int my = 0;
	
	public Sandbox(GameObjectStorage objects, game Game) {
		this.objects = objects;
		game = Game;
		}
	
	public void mousePressed(MouseEvent e){
		System.out.println("click");
		settingVel = false;
		drawingCirc = false;
		pickedUP = false;
		int mX = e.getX();
		int mY = e.getY();
		if(settingVel == false && drawingCirc == false && pickedUP == false){
			
			for(int i = 0; i <= objects.gameobjects.size() - 1; i++){
				GameObject tempObject = objects.gameobjects.get(i);
				if(Math.abs(((mX - (tempObject.X + tempObject.radius))*(mX - (tempObject.X + tempObject.radius))) + ((mY - (tempObject.Y + tempObject.radius))*(mY - (tempObject.Y + tempObject.radius)))) <= tempObject.radius*tempObject.radius){
					settingVel = true;
					this.tempObject = tempObject;
					mx =mX;
					my=mY;	
				}
				else if(Math.abs(((mX - (tempObject.X + tempObject.radius))*(mX - (tempObject.X + tempObject.radius))) + ((mY - (tempObject.Y + tempObject.radius))*(mY - (tempObject.Y + tempObject.radius)))) > tempObject.radius*tempObject.radius) {
					drawingCirc = true;
					}
				}
			}
	}
	
	public void mouseReleased(MouseEvent e){
		System.out.println("Unclick");
		random = new Random();
		int mX = e.getX();
		int mY = e.getY();
		if(settingVel == true){
			System.out.println("gg");
			int j=0;
			for(int i = 0; i<= objects.gameobjects.size() - 1; i++){
				GameObject tempObject = objects.gameobjects.get(i);
				double distX = tempObject.X + tempObject.radius - mX;
				double distY = tempObject.Y + tempObject.radius - mY;
				if(distX*distX + distY*distY<= tempObject.radius*tempObject.radius){
					tempObject.motion.linkTo(this.tempObject);
					this.tempObject.motion.linkTo(tempObject);
					j++;
				}
			}
			if(j==0){
				this.tempObject.motion.linkTo(mX, mY);
			}
		}else if(drawingCirc == true){
			System.out.println("lol");
			if(game.gamemode == game.gamemode.Sandbox)
			objects.addGameObject(new GameObject(mX, mY, 0, 0, 0, 0, Color.GREEN, "Circ", 20 - 0*random.nextInt(5), 0.002, ID.Neutral, 0.5));
		}
		
	}
	
	public void mouseDrag(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		System.out.println("kinda works");
		if(settingVel == true){
			System.out.println("works");
			this.mx = mx;
			this.my = my;
			tempObject.looks.DrawLineFromCentre();
			
	}

}
}
