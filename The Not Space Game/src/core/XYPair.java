package core;
import java.io.Serializable;

public class XYPair implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8665472748215094762L;
	public double x;
	public double y;

	public XYPair(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public XYPair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void translate(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public XYPair sum(double x, double y) {
		return new XYPair(this.x + x, this.y + y);
	}

	public XYPair sum(XYPair p) {
		return new XYPair(p.x + x, p.y + y);
	}

	public XYPair vectorTo(XYPair p) {
		return new XYPair(p.x - x, p.y - y);
	}

	public double magnitude() {
		return Math.sqrt((x) * (x) + (y) * (y));
	}

	public XYPair clone() {
		return new XYPair(x, y);
	}

	public XYPair unitVector() {
		double magnitude = magnitude();
		return new XYPair(x / magnitude, y / magnitude);
	}

	public XYPair rescale(double times) {
		x *= times;
		y *= times;
		return this;
	}
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public void add(XYPair p) {
		x += p.x;
		y += p.y;
	}

	

}
