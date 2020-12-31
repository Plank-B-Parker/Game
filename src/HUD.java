import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class HUD {
Graphics g;
GameObjectStorage gameobjects;
Font font;
	public HUD(Graphics g, GameObjectStorage objects) {
		this.g= g;
		gameobjects = objects;
	}
	
	public void displayFPS(double fps){
		String FPS = "FPS : "+Double.toString(fps);
		g.setColor(Color.ORANGE);
		g.drawString(FPS, 0, 25);
	}
	public void displayUPS(double ups){
		String UPS = "UPS : "+Double.toString(ups);
		g.setColor(Color.ORANGE);
		g.drawString(UPS, 0,10);
	}
	public void displayPlayerInfo(){
		if(game.initiallising == false) {
			for(int i = 0; i <= gameobjects.gameobjects.size() - 1; i++){
				GameObject tempObject = gameobjects.gameobjects.get(i);
				if( tempObject.id == ID.PLayer){
					double totalAcc =  Math.sqrt(tempObject.motion.Xa*tempObject.motion.Xa + tempObject.motion.Ya*tempObject.motion.Ya);
					double totalVel = Math.sqrt(tempObject.motion.Xv*tempObject.motion.Xv + tempObject.motion.Yv*tempObject.motion.Yv);
					String totVel = Double.toString(totalVel);
					String X = Double.toString(tempObject.motion.X);
					String Y = Double.toString(tempObject.motion.Y);
					String xv = Double.toString(tempObject.motion.Xv);
					String yv = Double.toString(tempObject.motion.Yv);
					font = new Font("arial", 1, 10);
					g.setFont(font);
					g.setColor(tempObject.looks.getColour());
					g.drawString("Player", 900, 100);
					g.drawString("X Coordinate: " + X, 900, 150);
					g.drawString("Y Coordinate: " + Y, 900, 170);
					g.drawString("X VEL: " + xv, 900, 190);
					g.drawString("Y VEL: " + yv, 900, 210);
					g.drawString("TOTAL VEL: " + totVel, 900, 230);
					g.drawString("Acceleration VEL: " + totalAcc, 900, 250);
				
				
				}
			}
		}
	}
	public void displayPhysics(){
		double totalP = 0;
		double totalKE = 0;
		double totalPX = 0;
		double totalKEX = 0;
		double totalPY = 0;
		double totalKEY = 0;
		double min = 1/100;
		if(game.initiallising == false) {
			for(int i = 0; i <= gameobjects.gameobjects.size() - 1; i++){
				GameObject tempObject = gameobjects.gameobjects.get(i);
				totalPX += tempObject.mass*tempObject.motion.Xv;
				totalKEX += 0.5*tempObject.mass*tempObject.motion.Xv*tempObject.motion.Xv;
				totalPY += tempObject.mass*tempObject.motion.Yv;
				totalKEY += 0.5*tempObject.mass*tempObject.motion.Yv*tempObject.motion.Yv;
			}
			if(Math.abs(totalPX) < min){
				totalPX = 0;
			}
			if(Math.abs(totalPY) < min){
				totalPY = 0;
			}
			if(Math.abs(totalKEX) < min){
				totalKEX = 0;
			}
			if(Math.abs(totalKEY) < min){
				totalKEY = 0;
			}
			
			totalP = Math.sqrt(totalPY*totalPY + totalPX*totalPX);
			totalP = Math.round(totalP);
			totalKE =  totalKEY + totalKEX;
			totalKE = Math.round(totalKE);
			String TotalPx = Double.toString(totalPX);
			String TotalKEx = Double.toString(totalKEX);
			String TotalPy = Double.toString(totalPY);
			String TotalKEy = Double.toString(totalKEY);
			String TotalP = Double.toString(totalP);
			String TotalKE = Double.toString(totalKE);
			g.setColor(Color.GRAY);
			g.drawString("TOTAL KENETIC ENERGY: " + TotalKE, 540, 50);
			g.drawString("TOTAL KENETIC ENERGY IN X: "+TotalKEx, 540, 70);
			g.drawString("TOTAL KENETIC ENERGY IN Y: "+TotalKEy, 540, 90);
			g.drawString("TOTAL MOMENTUM: " + TotalP, 270, 50);
			g.drawString("TOTAL MOMENTUM IN X: " + TotalPx, 270, 70);
			g.drawString("TOTAL MOMENTUM IN Y: " + TotalPy, 270, 90);
		}
	}
}
