import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;


public class GameObjectStorage{
	Random random = new Random();
	LinkedList <GameObject> gameobjects = new LinkedList<GameObject>();
	GameObject GameObject1;
	GameObject GameObject2;
	GameObject GameObject3;
	
	public GameObjectStorage() {
		double grav = 0.002;
		if(game.gamemode == game.Mode.Sandbox){
			GameObject1 = new GameObject(710 + 1 - random.nextInt(2) ,240,0,0,0,0, Color.BLUE, "Circ", 20, grav, ID.PLayer, 1);
			GameObject2 = new GameObject(700 + 1 - random.nextInt(2),270,0,0,0,0, Color.RED, "Circ", 25, grav, ID.Boss, 0.9);
			GameObject3 = new GameObject(715 + 1 - random.nextInt(2), 220, 0, 0, 0, 0, Color.GREEN, "Circ", 10, grav, ID.Defence, 0.8);
			for(int j = 1; j <= 100; j++){
				addGameObject(new GameObject(random.nextInt(game.WIDTH), random.nextInt(game.HEIGHT), 0, 0, 0, 0, new Color(100,100,100),"Circ", 5, grav, ID.Neutral, 1-0*Math.random() ));
				
			}
			//addGameObject(GameObject1);
			//addGameObject(GameObject2);
			//addGameObject(GameObject3);
			
			
			//double verticies[][] = {{0,0},{0,100},{100,100},{100,0}};
			//addGameObject(new GameObject(verticies, 0.1, 0.2, 0, 0, Color.white, 0, ID.Neutral, 1));
			
			
		}
		if(game.gamemode == game.Mode.Infection){
			addGameObject(new GameObject(random.nextInt(game.WIDTH), random.nextInt(game.HEIGHT), 1-random.nextInt(2), 0, 0, 0, Color.CYAN,"Circ", 10 - random.nextInt(2), 0, ID.PLayer, 1 ));
			addGameObject(new GameObject(random.nextInt(game.WIDTH), random.nextInt(game.HEIGHT), 5 - random.nextInt(10), 5 - random.nextInt(10), 0, 0, Color.RED,"Circ", 10 - random.nextInt(2), 0, ID.Boss, 1));
			for (int i = 1; i <= 11; i++){
				addGameObject(new GameObject(random.nextInt(game.WIDTH), random.nextInt(game.HEIGHT), 0, 0, 0, 0, Color.green,"Circ", 25 - random.nextInt(5), 0, ID.Defence, 0.01));
				for(int j = 1; j <= 20; j++){
					addGameObject(new GameObject(random.nextInt(game.WIDTH), random.nextInt(game.HEIGHT), 2.5 - random.nextInt(5), 2.5 - random.nextInt(5), 0, 0, Color.MAGENTA,"Circ", 10 - random.nextInt(2), 0, ID.Neutral, 1 ));
				}
			}
		}
		if(game.gamemode == game.Mode.Tag){
			GameObject1 = new GameObject(400,300,0,0,0,0, Color.BLUE, "Circ", 20/4, 0.00, ID.PLayer, 0.7);
			GameObject2 = new GameObject(440 ,300,0,0,0,0, Color.RED, "Circ", 20/4, 0.00, ID.Boss, 0.7);
			GameObject3 = new GameObject(480 , 300, 0, 0, 0, 0, Color.GREEN, "Circ", 20/4, 0.00, ID.Defence, 0.7);
			for(int j = 1; j <= 200; j++){
				addGameObject(new GameObject(game.WIDTH*Math.random(), game.HEIGHT*Math.random(),2.5 - random.nextInt(5), 2.5 - random.nextInt(5), 0, 0, Color.WHITE,"Circ", 5, 0, ID.Neutral, 0.7 ));
			}
			addGameObject(GameObject1);
			addGameObject(GameObject2);
			addGameObject(GameObject3);
			//GameObject1.motion.linkTo(400, 100);
			//GameObject2.motion.linkTo(440, 100);
			//GameObject3.motion.linkTo(480, 100);
		}
	}
	
	public void addGameObject(GameObject object){
		gameobjects.add(object);
	}
	
	public void removeGameObject(GameObject object){
		gameobjects.remove(object);
	}
	
	public void renderObjects(Graphics g){
		for(int i = 0; i <= gameobjects.size()-1;i++){
			gameobjects.get(i).renderObject(g);
		}
	}
	
	public void updateObjects(double delta){
		Physics.setDelta(delta);
		if(game.initiallising == false) {
			for(int i = 0; i <=  gameobjects.size()-1;i++){
				gameobjects.get(i).motion.updateAttraction();
				}
			InsertionSort();
			for(int i = 0; i <=  gameobjects.size()-1;i++){
				gameobjects.get(i).motion.checkCollision(this, i);
			}
			for(int i = 0; i <=  gameobjects.size()-1;i++){
				gameobjects.get(i).motion.updateAcceleration();
			}
			for(int i = 0; i <=  gameobjects.size()-1;i++){
				gameobjects.get(i).motion.updateVelocity();
			}
			for(int i = 0; i <=  gameobjects.size()-1;i++){
				gameobjects.get(i).motion.updatePosition();
			}
			for(int i = 0; i <=  gameobjects.size()-1;i++){
				gameobjects.get(i).updatePosition();
			}
		}
	}
	public void InsertionSort(){
		if(game.initiallising == false) {
			for(int i = 1; i<= gameobjects.size() - 1; i++) {
				GameObject current = gameobjects.get(i);
				while(current.motion.X < gameobjects.get(i-1).motion.X) {
					gameobjects.set(i, gameobjects.get(i-1));
					gameobjects.set(i-1, current);
					i--;
					if(i < 1)
						break;
				}
			}
			/*for(int i = 1; i<= gameobjects.size() - 1; i++) {
				if(i < gameobjects.size() - 1) {
					System.out.print(Math.round(gameobjects.get(i).motion.X) + " ");		
				}else {
					System.out.println(Math.round(gameobjects.get(i).motion.X) + " ");
				}
			}*/
		}
	}
}
