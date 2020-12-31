import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class LoadinWindoe extends Canvas {
	
	private static final long serialVersionUID = 7762626151667377595L;
	game Game;
	JFrame frame;
	JPanel Main;
	public LoadinWindoe(){
		
	}
	
	public LoadinWindoe(int width, int height, String title, game Game){
		frame = new JFrame(title);
		this.Game = Game;
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(0,0));
		
		Main = new JPanel(new GridLayout());
		frame.add(Main);
		Main.add(Game);
		Game.start();
	}
	static public void setBackground(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.setColor(Color.CYAN);
		//g.fillRect(0, game.HEIGHT/2 + 20, game.WIDTH, game.HEIGHT/2);
	}
	public void drawHUD(Graphics g, GameObjectStorage objects){
		HUD hud = new HUD(g, objects);
		hud.displayFPS(Game.FPS);
		hud.displayUPS(Game.UPS);
		hud.displayPlayerInfo();
		hud.displayPhysics();
	}
	}






