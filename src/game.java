import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -4294731232513037123L;
	
	public static int WIDTH = 1080, HEIGHT = 720;

	
	Random random = new Random();
	private Thread thread;
	private boolean running = false;
	double FPS;
	double UPS;
	LoadinWindoe window;
	Menu menu;
	GameObjectStorage Objects;
	KeyAdapter input;
	Sandbox sandbox;
	boolean updating = false;
	boolean rendering = false;
	static boolean initiallising = false;
	
	public static enum Mode{
		Sandbox, Infection, Menu, Tag;
	}
	public static Mode gamemode;
	
	public game() {
		gamemode =  Mode.Menu;
		Objects = new GameObjectStorage();
		input = new PlayerInput(Objects, this);
		menu = new Menu(Objects, this);
		addMouseListener(menu);
		addKeyListener(input);
		window = new LoadinWindoe(WIDTH,HEIGHT ,"gg no re",this);
	}
	
	public void initialise_infection(){
		initiallising = true;
		while (updating == true) {
			System.out.println("waiting");
		}
		gamemode = Mode.Infection;
		for(int i = 0; i <= Objects.gameobjects.size()-1; i++){
			GameObject object = Objects.gameobjects.get(i);
			Objects.removeGameObject(object);
		}
		Objects = new GameObjectStorage();
		removeKeyListener(input);
		removeMouseListener(menu);
		input = new PlayerInput(Objects, this);
		addKeyListener(input);
		initiallising = false;
		
	}
	public void initialise_Sandbox(){
		initiallising = true;
		while (updating == true) {
			System.out.println("waiting");
		}
		gamemode = Mode.Sandbox;
		for(int i = 0; i <= Objects.gameobjects.size()-1; i++){
			GameObject object = Objects.gameobjects.get(i);
			Objects.removeGameObject(object);
		}
		Objects = new GameObjectStorage();
		removeKeyListener(input);
		input = new PlayerInput(Objects, this);
		sandbox = new Sandbox(Objects, this);
		addKeyListener(input);
		removeMouseListener(menu);
		addMouseListener(sandbox);
		initiallising = false;
	}
	public void initialise_Tag(){
		initiallising = true;
		while (updating == true) {
			System.out.println("waiting");
		}
		gamemode = Mode.Tag;
		for(int i = 0; i <= Objects.gameobjects.size()-1; i++){
			GameObject object =Objects.gameobjects.get(i);
			Objects.removeGameObject(object);
		}
		Objects =new GameObjectStorage();
		removeKeyListener(input);
		removeMouseListener(menu);
		input = new PlayerInput(Objects, this);
		sandbox = new Sandbox(Objects, this);
		addKeyListener(input);
		addMouseListener(sandbox);
		initiallising = false;
	}
	public void initialise_Menu(){
		initiallising = true;
		while (updating == true) {
			System.out.println("waiting");
		}
		gamemode = Mode.Menu;
		for(int i = 0; i <= Objects.gameobjects.size()-1; i++){
			GameObject object = Objects.gameobjects.get(i);
			Objects.removeGameObject(object);
		}
		Objects =new GameObjectStorage();
		menu = new Menu(Objects, this);
		addMouseListener(menu);
		removeKeyListener(input);
		initiallising = false;
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running= false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
	    double amountOfTicks = 600;
	    double amountOfFrames = 60;
	    double ns = 1000000000/ amountOfTicks;
	    double delta = 0;
	    long timer = System.currentTimeMillis();
	    int frames = 0;
	    int updates = 0;
	        
	    while(running){
	    	long now = System.nanoTime();
	    	delta += (now - lastTime) / ns;
	    	lastTime = now;
	    	while(amountOfFrames*delta/amountOfTicks >= 1) {
	    		while(delta >= 1){
	    			updating = true;
	    			update(1);
	    			updating = false;
	    			updates ++;
	    			delta--;
	    		}
	    		render();
	    		frames++;
	    		
	    	}
	    	try {
    			thread.sleep(1);
    		}catch(Exception e) {
    			System.out.print("thread not sleeping!!!");
    		}
	        if(System.currentTimeMillis() - timer > 1000){
	            timer += 1000;
	            FPS = frames;
	            UPS = updates;
	            frames = 0;
	            updates = 0; 
	        }
	    }

		/*while (running=true) {
			render();
			update();
		}*/
	}
		
	public void render() {
			BufferStrategy bufS = getBufferStrategy();
			if(bufS == null){
				createBufferStrategy(3);
				return;
			}
			Graphics g= bufS.getDrawGraphics();
			///////////////////////////
			LoadinWindoe.setBackground(g);
			window.drawHUD(g, Objects);
			///////////////////////////
			if(gamemode == Mode.Menu && Objects != null){
				menu.RenderMenu(g);
			}
			if(Objects != null)
				Objects.renderObjects(g);
			///////////////////////////
			g.dispose();
			bufS.show();
	}
	
	public void update(double delta){
		WIDTH = window.Main.getWidth();
		HEIGHT = window.Main.getHeight();
		if(WIDTH<0) {
			WIDTH = 1;
		}
		menu.UpdateMenu();
		if(Objects != null)
			Objects.updateObjects(delta);
	}


	public static void main(String[] args) {
		new game();

	}

}
