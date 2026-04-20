package gameView;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameView {
	private int height, width;
	private String title;

	private JFrame jf; // Fönstret
	private Canvas canvas; // ritytan där renderingen sker

	private Graphics2D g;
	private BufferStrategy backBuffer;
	
	private Camera camera; 

	public GameView(int width, int height, String title) {
		this.height = height;
		this.width = width;
		this.title = title;
		
		camera = new Camera(0, 0, width, height);
		createWindow();
	}

	private void createWindow() {
		// Skapar vår rityta canvas med rätt bredd och höjd
		canvas = new Canvas();
		canvas.setSize(new Dimension(width, height));

		// Skapar fönstret.
		jf = new JFrame(title);
		// Lägger in ritytan i fönstret.
		jf.add(canvas);

		// Lite inställningar
		jf.setResizable(false); // Går ej att ändra storlek på fönster
		jf.pack(); // Packar så att inget tomrum visas
		jf.setLocationRelativeTo(null); // Placeras i mitten på skärmen
		
		// Går att stänga av med x-rutan
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		jf.setIgnoreRepaint(true); // Ritas inte om av JVM.       
		jf.setVisible(true); // Gör allt synligt!
		canvas.requestFocus(); //Ger vår canvas fokus
	
		canvas.createBufferStrategy(2); 
		backBuffer = canvas.getBufferStrategy();
		}

	/**
	 * startar render
	 * */
	public void beginRender() {
		g = (Graphics2D)backBuffer.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		
		//Transformering av var saker ska renderas baserat på kamerans position
		g.translate(-camera.getXPos(), -camera.getYPos());
	}

	/**
	 * Ritar upp bilderna
	 * */
	public void openRender(Drawable drawObj) {
		drawObj.draw(g);
	}

	public void openRender(Drawable[] drawArray) {
		for(Drawable drawoObj : drawArray) {
			drawoObj.draw(g);
		}
	}

	public void openRender(Collection<? extends Drawable> drawList) {
		for(Drawable drawoObj : drawList) {
			drawoObj.draw(g);
		}
	}
	
	/**
	 * Visar bilderna som ritades
	 * */
	public void show() {
		g.dispose();
		backBuffer.show();
		Toolkit.getDefaultToolkit().sync(); //Checka exakt vad den gör!!!
	}
	
	//setters
	public void setKeyListener(KeyListener keyListener) {
		canvas.addKeyListener(keyListener);
	}

	//getters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public JFrame getJF() {
		return jf;
	}
	
	//
}
