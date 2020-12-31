import java.util.ArrayList;

public class Physics {
	double X;
	double Y;
	double verticies[][];
	double R = 0;
	double Yv;
	double Xv;
	double W;
	double Rv = Math.random()*0;
	double Xa;
	double Ya;
	static double delta;
	double gravity;
	static boolean grav = true;
	double G = 0.0001;
	double CollisionVel;
	double CollisionVel2;
	double GattractionX;
	double GattractionY;
	double Xj = 0;
	double Yj = 0;
	double J;
	double constraintX=0;
	double constraintY=0;
	static double springConstant = 0.1;
	static double springLength = 30;
	static double springWallLength = 30;
	boolean hasCollided = false;
	double e;
	double fluidDensity = 0;
	double bondEnergy = 1;
	double breakEnergy = 100;
	double constraintFric = 0;
	static boolean bonding =false;
	double BallEffiency;
	static boolean ballAttactionG = false;
	final static double wallEfficiency = 1;
	double ballFriction = 0;
    final double Mass;
    GameObject object;
    GameObjectStorage ObjectStorage;
    ArrayList<GameObject> ConnectedObjects = new ArrayList<GameObject>();
    ArrayList<double[]> ConnectedPoints = new ArrayList<double[]>();
	
	public Physics(double x, double y ,double m, double xa, double ya, double xv, double yv, double grav, double e, GameObject object) {
		this.object = object;
		gravity = grav;
		Mass = m;
		X = x;
		Y = y;
		setAccX(xa);
		setAccY(ya);
		setxvelocity(xv);
		setyvelocity(yv);
		this.e = e;
		BallEffiency = 1/e;
	}
	public Physics(double verticies[][] ,double m, double xa, double ya, double xv, double yv, double grav, double e, GameObject object) {
		this.object = object;
		gravity = grav;
		Mass = m;
		this.verticies = new double[verticies.length][2];
		for(int i = 0; i<= verticies.length - 1; i++){
			this.verticies[i][0] = verticies[i][0];
			this.verticies[i][1] = verticies[i][1];
			X += this.verticies[i][0]/verticies.length;
			Y += this.verticies[i][1]/verticies.length;
		}
		setAccX(xa);
		setAccY(ya);
		setxvelocity(xv);
		setyvelocity(yv);
		this.e = e;
		BallEffiency = 1/e;
	}
	public static void setDelta(double Delta) {
		delta = Delta;
	}
	public void updatePosition(){ 
		if(object.looks.shape == "Circ" || object.looks.shape == "emptyCirc") {updateR();}
		updateX();
		updateY();
		/*if(Y<game.HEIGHT/2){
			fluidDensity = 0;
		}else{
			fluidDensity = 10;
		}
		drag = 0.01*fluidDensity*Math.PI*object.radius/(2*object.mass);*/
	}
	public void updateVelocity() {
		/*if(Xv*Xv + Yv*Yv <= 0.07*0.07) {
			setxvelocity(0);
			setyvelocity(0);
		}*/
		updateXvel();
		updateYvel();
	}
	public void updateAcceleration() {
		KeepConnected();
		updateXacc();
		updateYacc();
		
	}
	public void checkCollision(GameObjectStorage ObjectStorage, int i) {
		this.ObjectStorage = ObjectStorage;
		impact();
		collision(i);
	}
	public void updateAttraction() {
		ballAttractionG();
	}
	
	
	public void setyvelocity(double v){
		Yv = v;
	}
	
	public void setxvelocity(double v){
		Xv = v;	
	}
	
	public void setAccX(double a){
		Xa = a;
	}
	
	
	public void setAccY(double a){
		Ya = a;
	}
	
	
	public void setx(double x){
		X= x;
	}
	
	public void sety(double y){
		Y= y;
	}
	
	public double getx(){
		return X;
	}
	
	public double gety(){
		return Y;
	}
	private void updateX(){
		X = X + Xv*delta;
		if(object.looks.shape == "Polygon") {
			for(int i = 0; i<= verticies.length - 1; i++){
				verticies[i][0] += Xv*delta;
			}
		}
		}
	
	private void updateY(){
		Y = Y + Yv*delta;
		if(object.looks.shape == "Polygon") {
			for(int i = 0; i<= verticies.length - 1; i++){
				verticies[i][1] += Yv*delta;
			}
		}
	}
	
	private void updateR(){
		double Ri = object.radius; 
		R += Rv*delta;
		object.setRadius();
		double Rf = object.radius;
		double deltaR = Rf - Ri;
		X -= (Math.sqrt(2)/2)*deltaR;
		Y -= (Math.sqrt(2)/2)*deltaR;
	}
	
	private void updateXvel(){
		if(Xv != 0){
		Xv += (Xa + GattractionX + constraintX - (0.01*fluidDensity*Math.PI*object.radius/(2*object.mass))*Xv*Xv*(Xv/Math.abs(Xv)))*delta;
		}else{
			Xv += (Xa + GattractionX + constraintY)*delta;
		}
	}
	
	private void updateYvel(){
		double grav = gravity;
		if(!Physics.grav)
			grav = 0;
		if(Yv!=0){
		Yv += (Ya + grav + GattractionY + constraintY - (0.01*fluidDensity*Math.PI*object.radius/(2*object.mass))*Yv*Yv*(Yv/Math.abs(Yv)))*delta;
		}else{
			Yv += (Ya + grav + GattractionY + constraintY)*delta;
		}
	}
	
	private void updateXacc(){
		Xa += Xj*delta;
	}
	private void updateYacc(){
		Ya += Yj*delta;
	}
	public void addAccX(double i) {
		Xj = i;
		updateXacc();
	}
	
	public void addAccY(double i) {
		Yj = i;
		updateYacc();
	}
	public void linkTo(GameObject link){
		if(link != object) {
			ConnectedObjects.add(link);
		}
	}
	public void linkTo(double x, double y){
		ConnectedPoints.add(new double [] {x,y}); 
	}
	public static void ToggleBonding(){
		if(bonding)
			bonding = false;
		else
			bonding = true;
	}
	public static void ToggleGrav(){
		if(grav)
			grav = false;
		else
			grav = true;
	}
	public void KeepConnected(){
		double cX = 0;
		double cY = 0;
		if(ConnectedObjects != null){
			for(int i = 0; i <= ConnectedObjects.size() - 1; i++){
				GameObject tempObject = ConnectedObjects.get(i);
				
				double distance = Math.sqrt(Distance(tempObject));
				double distX = X + object.radius - tempObject.motion.X - tempObject.radius;
				double distY = Y + object.radius - tempObject.motion.Y - tempObject.radius;
				double r = object.radius + tempObject.radius + springLength; 
				double X = distance - r;
				if(0.5*springConstant*X*X>breakEnergy && bonding){
					ConnectedObjects.remove(tempObject);
				}
				cX += - distX*(X*springConstant)/(distance*object.mass);
				cY += - distY*(X*springConstant)/(distance*object.mass);
				
				/*if(Math.abs(Xv - tempObject.motion.Xv) > 0 && constraintFric <= X*k ){
					cX -= - distX*X*k/(distance*object.mass);
					cX += distX*((constraintFric*X/Math.abs(X)) - X*k)/(distance*object.mass);
				}
				if(Math.abs(Yv - tempObject.motion.Yv) > 0 && constraintFric <= X*k ){
					cY -= - distY*X*k/(distance*object.mass);
					cY += distY*((constraintFric*X/Math.abs(X)) - X*k)/(distance*object.mass);
				}
				if(constraintFric > X*k){
					cX -= - distX*X*k/(distance*object.mass);
					cY -= - distY*X*k/(distance*object.mass);
				}*/
			}
		}
		if(ConnectedPoints != null){
			for(int i = 0; i<= ConnectedPoints.size() - 1; i++){
				double x = ConnectedPoints.get(i)[0];
				double y = ConnectedPoints.get(i)[1];
				double distX = X + object.radius - x;
				double distY = Y + object.radius - y;
				double distance = Math.sqrt(distX*distX + distY*distY);
				double r = object.radius + springWallLength;
				double X = distance - r;
				
				cX += - distX*(X*springConstant)/(distance*object.mass);
				cY += - distY*(X*springConstant)/(distance*object.mass);
				
				/*if(Math.abs(Xv) > 0 && constraintFric <= X*k ){
					cX -= - distX*X*k/(distance*object.mass);
					cX += distX*((constraintFric*X/Math.abs(X)) - X*k)/(distance*object.mass);
				}
				if(Math.abs(Yv) > 0 && constraintFric <= X*k ){
					cY -= - distY*X*k/(distance*object.mass);
					cY += distY*((constraintFric*X/Math.abs(X)) - X*k)/(distance*object.mass);
				}
				if(constraintFric >= X*k){
					cX -= - distX*X*k/(distance*object.mass);
					cY -= - distY*X*k/(distance*object.mass);
				}*/
			}
		}
		constraintX = cX;
		constraintY = cY;
		
		
	}
	
	private void impact(){
		if(game.gamemode == game.Mode.Menu){
			if(X >= game.WIDTH - 2*object.radius|| X <= 0){
				if(X > game.WIDTH - 2*object.radius){
					setx(game.WIDTH-2*object.radius);
				}if(X < 0){
					setx(0);
				}
				Xv = -Xv*Math.sqrt(wallEfficiency);
				}
			if(Y >= 165 - 2*object.radius && Y <= 200 && (X >= 90 && X <=310)){
				while(Y > 165-2*object.radius){
					sety(165-2*object.radius);
					Y= 165-2*object.radius;
				}
		
				Yv = -Yv*Math.sqrt(wallEfficiency);
			
			}
			if(Y >= 365 - 2*object.radius && Y <= 400 && (X >= 90 && X <=190)){
				while(Y > 365-2*object.radius){
					sety(365-2*object.radius);
					Y= 365-2*object.radius;
				}
		
				Yv = -Yv*Math.sqrt(wallEfficiency);
			}
			if(Y >= 565 - 2*object.radius && Y <= 600 && (X >= 90 && X <=310)){
				while(Y > 565-2*object.radius){
					sety(565-2*object.radius);
					Y= 565-2*object.radius;
				}
		
			Yv = -Yv*Math.sqrt(wallEfficiency);
			}
		}
		else{
			if(object.wrap == false){
			if(X >= game.WIDTH - 2*object.radius|| X <= 0){
				while(X > game.WIDTH - 2*object.radius){
					X = game.WIDTH - 2*object.radius;
				}while(X < 0){
					X = 0;
				}
				Xv = -Xv*Math.sqrt(wallEfficiency);
			}
		
			if(Y >= game.HEIGHT - 2*object.radius || Y <= 0 ){
				while(Y > game.HEIGHT -2*object.radius){
					sety(game.HEIGHT -2*object.radius);
					Y= game.HEIGHT -2*object.radius;
				}if(Y < 0){
					Y = 0;
				}
				Yv = -Yv*Math.sqrt(wallEfficiency);	
					
					/*if(Math.abs(Yv*wallEfficiency) <= wallEfficiency){
							Yv = 0;	
					}*/
				}	
			}
			else{
				double r = ObjectStorage.gameobjects.get(0).radius;
				for(int  i = 1; i <= ObjectStorage.gameobjects.size() - 1; i++){
					if(r < ObjectStorage.gameobjects.get(i).radius){r = ObjectStorage.gameobjects.get(i).radius;}
				}
				
				if(X >= game.WIDTH && Xv > 0){
					X = -2*r;
				}
				if(X + 2*object.radius <= 0 && Xv < 0){
					X = game.WIDTH;
				}
				if(Y >= game.HEIGHT && Yv > 0){
					Y = -2*r;
				}
				if(Y + 2*object.radius <= 0 && Yv< 0){
					Y = game.HEIGHT;
				}	
			}
		}
	}
	
	private void collision(int j){
		if(ObjectStorage != null){
			int i = j + 1;
			if(i <= ObjectStorage.gameobjects.size() - 1) {
				while(ObjectStorage.gameobjects.get(i).motion.X <= X + 2*object.radius){
					GameObject tempObject = ObjectStorage.gameobjects.get(i);
					if(object!=tempObject){
						double x = tempObject.motion.X;
						double y = tempObject.motion.Y;
						double X2v =tempObject.motion.Xv;
						double Y2v = tempObject.motion.Yv;
						double k = tempObject.mass/Mass;
				
				
						if(object.looks.getShape() == "Circ" && tempObject.looks.getShape() == "Circ"||object.looks.getShape() == "emptyCirc" && tempObject.looks.getShape() == "emptyCirc"||object.looks.getShape() == "emptyCirc" && tempObject.looks.getShape() == "Circ"||object.looks.getShape() == "Circ" && tempObject.looks.getShape() == "emptyCirc"){
					
							double distanceSquared = Distance(tempObject);
							double distance = Math.sqrt(distanceSquared);
							double R1 = object.radius;
							double R2 = tempObject.radius;
							double X1centre = X + object.radius;
							double Y1centre = Y + object.radius;
							double X2centre = x + tempObject.radius;
							double Y2centre = y + tempObject.radius;
							double distX = X2centre-X1centre;
							double distY = Y2centre-Y1centre;
					
							if (distanceSquared <= (R1 + R2)*(R1 + R2) && distanceSquared != 0){
								
								double middle = (R1*R1 - R2*R2 + distanceSquared)/(2*distance);
								double displace1 = R1 - middle;
								double displace2 = middle - (distance - R2);
								double distanceOverlap = R1 + R2 - distance;
							
								tempObject.motion.setx((x) + ((displace2)*(distX/distance)));
								tempObject.motion.sety((y) + ((displace2)*(distY/distance)));
								X -= ((displace1)*(distX/distance));
								Y -= ((displace1)*(distY/distance));
						
								double newdistX = distX + X2v - Xv;
								double newdistY = distY + Y2v - Yv;
								double newtotdistSq = newdistX*newdistX + newdistY*newdistY;
								if(distanceSquared > newtotdistSq) {
						
									double PerpendicularVelY = (Yv - distY*((Xv*distX + Yv*distY)/distanceSquared));
									double PerpendicularVelX = (Xv - distX*((Xv*distX + Yv*distY)/distanceSquared));
									double ParralelVelBefore = (Xv*distX + Yv*distY)/(distance);
						
									double PerpendicularVel2Y = (Y2v - distY*((X2v*distX + Y2v*distY)/distanceSquared));
									double PerpendicularVel2X = (X2v - distX*((X2v*distX + Y2v*distY)/distanceSquared));
									double ParralelVelBefore2 = (X2v*distX + Y2v*distY)/distance;
						
									double e = (this.e + tempObject.motion.e)/2;
									double totKE = 0.5*object.mass*(Xv*Xv + Yv*Yv) + 0.5*tempObject.mass*(X2v*X2v + Y2v*Y2v);
						
									if(totKE < bondEnergy && !ConnectedObjects.contains(tempObject) && bonding){
										tempObject.motion.linkTo(object);
										linkTo(tempObject);
										//e = (e)*(bondEnergy-totKE)/totKE;
									}
									e = 1/e;
									collisionVel(ParralelVelBefore, ParralelVelBefore2, k, e );
									Xv =getCollisionVel()*(distX/distance) + PerpendicularVelX;
									Yv =getCollisionVel()*(distY/distance) + PerpendicularVelY;
						
						
									collisionVel2(ParralelVelBefore, ParralelVelBefore2, k, e );
									double Xv2 = (getCollisionVel2()*(distX/distance)) + PerpendicularVel2X;
									tempObject.motion.setxvelocity(Xv2);
									double Yv2 = (getCollisionVel2()*(distY/distance)) + PerpendicularVel2Y;
									tempObject.motion.setyvelocity(Yv2);
									
									if(game.gamemode == game.Mode.Infection) object.InfectionCollisionColour(tempObject);
								}
							}else if(distanceSquared == 0){
								x = tempObject.X;
								y = tempObject.Y;
								double displacementX = R1*Math.random();
								double displacementY = Math.sqrt(R1*R1 - displacementX*displacementX);
								tempObject.motion.setx((x) + displacementX/2);
								tempObject.motion.sety((y) + displacementY/2);
								setx(X -  displacementX/2);
							sety(Y - displacementY/2);
							}
							
						}
				
					}
						i++;
					if(i > ObjectStorage.gameobjects.size() - 1)
						break;
				}
			}
		}	
	}
	private void collisionVel(double V, double V2, double k, double e){
			CollisionVel = (((e+1)*k*V2)+(e-k)*V)/(e*(k+1));
	}
	private void collisionVel2(double V, double V2, double k, double e){
		CollisionVel2 = (((e+1)*V)+(e*k-1)*V2)/(e*(k+1));
	}
	
	private double getCollisionVel(){
		return CollisionVel;
	}

	private double getCollisionVel2(){
		return CollisionVel2;
	}

	
	

	private double Distance(GameObject object2){
		double X1centre = X + object.radius;
		double X2centre = object2.motion.X + object2.radius;
		double Y1centre = Y + object.radius;
		double Y2centre = object2.motion.Y + object2.radius;
		double distanceSquared = 0;
		distanceSquared = Math.abs((X1centre - X2centre)*(X1centre - X2centre) + (Y1centre - Y2centre)*(Y1centre - Y2centre));
		return distanceSquared;
	}
	
	
	private void ballAttractionG(){
		if(ballAttactionG == true && ObjectStorage != null){
			ArrayList<Double> accX = new ArrayList<Double>();
			ArrayList<Double> accY = new ArrayList<Double>();
			
			double totalaccX = 0;
			double totalaccY = 0;
			for(int i = 0; i <= ObjectStorage.gameobjects.size() - 1; i ++){	
				GameObject tempObject = ObjectStorage.gameobjects.get(i);
				if(tempObject != object){	
					double m1 = object.mass;
					double m2 = tempObject.mass;
					double X2 = tempObject.X + tempObject.radius;
					double Y2 = tempObject.Y + tempObject.radius;
					double r = Math.sqrt(Distance(tempObject));
					double acc = G*m1*m2/(r*r*m1);
					accX.add(acc*(X2 - (X + object.radius)));
					accY.add(acc*(Y2 - (Y + object.radius)));
					if(object.wrap == true){
						double R = ObjectStorage.gameobjects.get(0).radius;
						for(int j = 1; j <= ObjectStorage.gameobjects.size() - 1; j++){
							if(R < ObjectStorage.gameobjects.get(i).radius){ R = ObjectStorage.gameobjects.get(i).radius;}
						}
						double rx = 0;
						double ry = 0;
						if(X2 - (X + object.radius)>0){
							rx = -(X + object.radius + (game.WIDTH - X2));
						}
						if(X2 - (X + object.radius)<0){
							rx  = X2 + (game.WIDTH - X - object.radius);
						}
						if(Y2 - (Y + object.radius)>0){
							ry = -(Y + object.radius + (game.HEIGHT - Y2));
						}
						if(Y2 - (Y + object.radius)<0){
							ry = Y2 + (game.HEIGHT - Y - object.radius);
						}
						r = Math.sqrt(ry*ry + rx*rx);
						acc = G*m1*m2/(r*r*m1);
						accX.add(acc*rx/r);
						accY.add(acc*ry/r);
					}
				}
			}
			for(int i = 0; i <=  accX.size() - 1; i++){
				totalaccX = totalaccX + accX.get(i);
				totalaccY = totalaccY + accY.get(i);
			}
			GattractionX = totalaccX;
			GattractionY = totalaccY;
			
		}
		if(ballAttactionG == false){
			GattractionX = 0;
			GattractionY = 0;
		}
		
	}
	public static void toggleBallAttractionG(){
		if(ballAttactionG == false){
			ballAttactionG =true;
		}else{
			ballAttactionG = false;
		}
	}
	
}