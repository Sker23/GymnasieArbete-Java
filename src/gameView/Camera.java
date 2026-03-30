package gameView;

public class Camera {
	private int xPos, yPos; //x och y positionerna som transformerar gameView
	private int width, height; //Samma längd och höjd som gameView
	
    public Camera (int xPos, int yPos, int width, int height ){
    	this.xPos = xPos;
    	this.yPos = yPos;
    	this.width = width;
    	this.height = height;
    }
    
    /**
     * Metod som gör förflyttningen, dvs ändrar xPos och yPos
     * Gör att gameView med hjälp av kameran är centrerad på spelaren baserat på spelaren position
     * @param deltatim antal nanosekunder sedan förra anropet 
     */
    public void update(double x, double y) {
		xPos = (int)x - width / 2;  //centrerar x-axeln längst till vänstra 
		
    	if (x <= width/2) {
    		xPos = 0;
		} 

		yPos = (int)y - height / 2; //centrerar y-axeln till toppen

        //System.out.println("cameraX: " + xPos);
        //System.out.println("cameraY: " + yPos);
    }
    
    /**
     * Returner position x
     * */
    public int getXPos() {
    	return xPos;
    }
    
    /**
     * Returner position y
     * */
    public int getYPos() {
    	return yPos;
    }
    
    public void setXPos(int xPos) {
    	this.xPos = xPos;
    }
    
    public void setYPos(int yPos) {
    }

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
