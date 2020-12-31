import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JLabel;



public class GameObject{
	
	Random random = new Random();
	double mass = 0;
	double density = 1/Math.PI;
	double X;
	double Y;
	double MaxRadChange;
	int radius;
	double OGRad;
	Physics motion;
	Appearance looks;
	static int no_objects = 0;
	ID id;
	ID idSub;
	Color idSubColour;
	JLabel tag;
	static int c = 0;
	double lastXv = 0;
	double lastYv = 0;
	boolean wrap = false;
	double verticies[][];
	
	
	public GameObject(double x, double y, double xv, double yv, double xa, double ya, Color colour ,String shape, int rad, double  gravity, ID id, double e) {
		X= x; 
		Y= y;
		radius = rad;
		OGRad = rad;
		MaxRadChange = rad*Math.random();
		 if(shape == "Circ"){mass = density*Math.PI*rad*rad;}
		 else if(shape == "emptyCirc"){mass = density*2*Math.PI*rad;}
		 if(e == 0){e = 0.00001;}
		motion = new Physics(X,Y,mass, xa, ya, xv, yv, gravity, e, this);
		looks = new Appearance(shape, colour);
		this.id = id;
		if(id == ID.Boss || id == ID.Enemy){idSub = ID.Enemy; idSubColour = Color.RED;}else{}
		if(id == ID.PLayer || id == ID.Allie){idSub = ID.Allie; idSubColour = Color.BLUE; }else{}
		if(id == ID.Defence || id == ID.Neutral){idSub = ID.Neutral; idSubColour = Color.MAGENTA;}else{}
		no_objects++;
	}
	
	public GameObject(double verticies[][], double xv, double yv, double xa, double ya, Color colour, double gravity, ID id, double e){
		this.verticies = new double[verticies.length][2];
		for(int i = 0; i<= verticies.length - 1; i++){
			this.verticies[i][0] = verticies[i][0];
			this.verticies[i][1] = verticies[i][1];
		}
		mass = 10;
		motion = new Physics(verticies ,mass, xa, ya, xv, yv, gravity, e, this);
		looks = new Appearance("Polygon", colour);
		this.id = id;
	}
	
	public GameObject(double x, double y, Color colour){
		X= x; 
		Y= y;
		radius = 10 + random.nextInt(10);
		OGRad = radius;
		MaxRadChange = radius*Math.random();
		mass = density*Math.PI*radius*radius;
		double e = Math.random();
		 if(e == 0){e = 0.00001;}
		motion = new Physics(X,Y, mass, 0, 0, 1 - 2*Math.random(), 0, 0.005, e, this);
		looks = new Appearance("Circ", colour);
		this.id = ID.Neutral;
		idSub = ID.Neutral;
		idSubColour = Color.MAGENTA;
		no_objects++;
	}
	
	public void renderObject(Graphics g){
		if(c==1){
			double KE = 0.5*mass*(motion.Yv*motion.Yv + motion.Xv*motion.Xv);
			double n = 1;
			double e = Math.pow(Math.E, KE*n);
			if(e>1000000000){e = 1000000000;}
			int grad = (int) ((2*255*e/(1+e)) - 255);
			g.setColor(new Color(grad, 0, 255 - grad));
		}if(c==2){
			double currentXv = motion.Xv;
			double currentYv = motion.Yv;
			double force = mass*Math.sqrt((currentYv - lastYv)*(currentYv - lastYv) + (currentXv - lastXv)*(currentXv - lastXv));
			double n = 1;
			double e = Math.pow(Math.E, force*n);
			if(e>1000000000){e = 1000000000;}
			int grad = (int) ((2*255*e/(1+e)) - 255);
			g.setColor(new Color(255 - grad + grad, 255 - grad, 255 - grad));
			lastYv = currentYv;
			lastXv = currentXv;
		}
		if(c==0) {
			g.setColor(looks.getColour());
		}
		switch(looks.getShape()){
		case "Rect":
			g.fillRect((int)(X), (int)(Y), 2*radius, 2*radius);
			
		case "Circ":
			g.fillOval((int)(X), (int)(Y), 2*radius, 2*radius);
		case "emptyCirc":
			g.drawOval((int)(X), (int)(Y), 2*radius, 2*radius);
		case "Polygon":
			if( verticies != null) {
				int x[] = new int[verticies.length];
				int y[] = new int[verticies.length];
			for(int i = 0; i <= verticies.length -1; i++) {
				x[i] = (int)verticies[i][0];
				y[i] = (int)verticies[i][1];
			}
			g.fillPolygon(x, y, verticies.length);
		}
		}
		if(motion.ConnectedObjects != null){
			for(int i = 0; i <= motion.ConnectedObjects.size() - 1; i++){
				g.setColor(Color.WHITE);
				g.drawLine((int)(X + radius),(int)(Y + radius), (int)(motion.ConnectedObjects.get(i).motion.X + motion.ConnectedObjects.get(i).radius), (int)(motion.ConnectedObjects.get(i).motion.Y + motion.ConnectedObjects.get(i).radius));
			}
		}
		if(motion.ConnectedPoints != null){
			for(int i = 0; i <= motion.ConnectedPoints.size() - 1; i++){
				g.setColor(Color.WHITE);
				g.drawLine((int)(X + radius),(int)(Y + radius), (int)(motion.ConnectedPoints.get(i)[0]), (int)(motion.ConnectedPoints.get(i)[1]));
			}
		}
		}
	
	public void updatePosition(){
		X = motion.getx();
		Y = motion.gety();	
		if(verticies != null) {
			for(int i = 0; i<= verticies.length - 1; i++){
				this.verticies[i][0] = motion.verticies[i][0];
				this.verticies[i][1] = motion.verticies[i][1];
			}
		}
		//if(wrap == true){
			//X = X%game.WIDTH;
			//Y = Y%game.HEIGHT;
		//}
	}
	public void setRadius(){
		radius = (int) (OGRad + MaxRadChange*Math.sin(motion.R));
		if(radius<=0){radius = 1;}
	}
	
	public void InfectionCollisionColour(GameObject object){
		if(game.gamemode == game.Mode.Infection){
			
			if(object.id == ID.Neutral){object.looks.ChangeColour(idSubColour); object.ChangeID(idSub);}else{}
			if(id == ID.Neutral){looks.ChangeColour(object.idSubColour); ChangeID(object.idSub);}else{}
			if((id == ID.Enemy || id == ID.Allie) && object.id == ID.Defence){looks.ChangeColour(object.idSubColour);ChangeID(object.idSub);}
			if((object.id == ID.Enemy || object.id == ID.Allie) && id == ID.Defence){object.looks.ChangeColour(idSubColour);object.ChangeID(idSub);}else{}
			
				
		}
	}
	public void ChangeID(ID id ){
		this.id = id;
		if(id == ID.Boss || id == ID.Enemy){idSub = ID.Enemy; idSubColour = Color.RED;}else{}
		if(id == ID.PLayer || id == ID.Allie){idSub = ID.Allie; idSubColour = Color.BLUE; }else{}
		if(id == ID.Defence || id == ID.Neutral){idSub = ID.Neutral; idSubColour = Color.MAGENTA;}else{}
	}
	public static void setKEBackground(){
		c++;
		if(c>2)c=0;
		
	}

}
