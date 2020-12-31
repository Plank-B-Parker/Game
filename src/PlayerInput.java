import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class PlayerInput extends KeyAdapter{
	GameObjectStorage Objects;
	game Game;
	public PlayerInput(GameObjectStorage Objects, game game) {;
		 this.Objects = Objects;
		Game = game;
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_F2) {
			new VariableChanger(Game);
		}
		if(key == KeyEvent.VK_ESCAPE){
			Game.initialise_Menu();
			Physics.ballAttactionG = false;
		}
		if(key == KeyEvent.VK_B){
			GameObject.setKEBackground();
		}
		if(key == KeyEvent.VK_J){
			Physics.ToggleBonding();
		}
		if(key == KeyEvent.VK_K){
			Physics.ToggleGrav();
		}
		if(key == KeyEvent.VK_G){
			if(game.gamemode == game.Mode.Sandbox) {
				Physics.toggleBallAttractionG();
			}
		}
		if(key == KeyEvent.VK_D){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccX(0.001);
			}
		}
		if(key == KeyEvent.VK_A){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccX(-0.001);
				}
			}
		if(key == KeyEvent.VK_W){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccY(-0.001);
			}
		}
		if(key == KeyEvent.VK_S){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccY(0.001);
				}
			}
		if(key == KeyEvent.VK_DOWN){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccY(0.001);
				}
		}
		}
		if(key == KeyEvent.VK_UP){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccY(-0.001);
				}
		}
		}
		
		if(key == KeyEvent.VK_LEFT){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccX(-0.001);
			}
		}
		}
		
		if(key == KeyEvent.VK_RIGHT){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccX(0.001);
				}
			}
		}
		if(key == KeyEvent.VK_ENTER){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.setxvelocity(0);
				Objects.gameobjects.get(i).motion.setyvelocity(0);
				}
		}
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccX(0);
				Objects.gameobjects.get(i).motion.setAccX(0);
			}
		}
		if(key == KeyEvent.VK_A){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccX(0);
				Objects.gameobjects.get(i).motion.setAccX(0);
				}
			}
		if(key == KeyEvent.VK_W){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccY(0);
				Objects.gameobjects.get(i).motion.setAccY(0);
			}
		}
		if(key == KeyEvent.VK_S){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				Objects.gameobjects.get(i).motion.addAccY(0);
				Objects.gameobjects.get(i).motion.setAccY(0);
				}
			}
		if(key == KeyEvent.VK_DOWN){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccY(0);
					Objects.gameobjects.get(i).motion.setAccY(0);
				}
		}
		}
		
		if(key == KeyEvent.VK_UP){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccY(0);
					Objects.gameobjects.get(i).motion.setAccY(0);
				}
		}
		}
		
		if(key == KeyEvent.VK_LEFT){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccX(0);
					Objects.gameobjects.get(i).motion.setAccX(0);
				}
		}
		}
		
		if(key == KeyEvent.VK_RIGHT){
			for(int i = 0; i <= Objects.gameobjects.size()-1; i ++){
				if(Objects.gameobjects.get(i).id == ID.PLayer){
					Objects.gameobjects.get(i).motion.addAccX(0);
					Objects.gameobjects.get(i).motion.setAccX(0);
				}
		}
		}
	
}
}

