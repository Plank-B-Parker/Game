



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


public class Menu extends MouseAdapter {

	game Game;
	GameObjectStorage Objects;
	Random random = new Random();
	Color colour;
	Font font = new Font("arial", 1, 50);
	
	public void mousePressed(MouseEvent e){
		int mouseX = e.getX();
		int mouseY = e.getY();
		if(mouseX>0 || mouseY>0)System.out.println("click");
		if(mouseY >= 165 && mouseY <= 200 && (mouseX >= 90 && mouseX <=310)){
			Game.initialise_Sandbox();
		}
		if(mouseY >= 365 && mouseY <= 400 && (mouseX >= 90 && mouseX <=190)){
			Game.initialise_Tag();
		}
		if(mouseY >= 565 && mouseY <= 600 && (mouseX >= 90 && mouseX <=310)){
			Game.initialise_infection();
		}
	}
	public void mouseReleased(MouseEvent e){
		
	}
	
	public Menu(GameObjectStorage Objects, game Game) {
		this.Game = Game;
		this.Objects = Objects;
		for (int i = 1; i <= 15; i++){
			Objects.addGameObject(new GameObject(random.nextInt(game.WIDTH), 10, 5, 5, 0, 0, Color.MAGENTA,"Circ", 15 - random.nextInt(4), 0.1, ID.Neutral, Math.random() ));
		}
	}
	public void RenderMenu(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("SandBox", 100, 200);
		g.drawString("Infection", 100, 600);
		g.drawString("Tag", 100, 400);
		
	}
	public void UpdateMenu(){
		if(game.gamemode == game.Mode.Menu){
			colour = new Color(random.nextInt(155) + 100, random.nextInt(155) + 100, random.nextInt(155) + 100);
			if(Math.random() > 0.8)
				Objects.addGameObject(new GameObject(random.nextInt(game.WIDTH), -100, colour));
			for(int i = 0; i <= Objects.gameobjects.size() - 1; i++){
				GameObject tempObject = Objects.gameobjects.get(i);
				if(tempObject.Y >= game.HEIGHT || tempObject.Y <= -tempObject.radius - 300){
					Objects.removeGameObject(tempObject);
				}
			}
		}
	}
	

}
