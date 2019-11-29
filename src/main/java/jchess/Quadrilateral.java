package jchess;

public class Quadrilateral implements IShape{
	private int x1, y1;
	private int x2, y2;
	private int x3, y3;
	private int x4, y4;
	
	public Quadrilateral() {
		
	}

	public Quadrilateral(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.x4 = x4;
		this.y4 = y4;
		
	}
	
	public int getTopLeftX() {
		return this.x4;
	}
	
	public int getTopLeftY() {
		return this.y4;
	}

}
