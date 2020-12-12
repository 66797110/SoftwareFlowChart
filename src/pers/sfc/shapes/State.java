package pers.sfc.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public interface State {
	public void draw(Shape s,Graphics2D g);
	public int on(Shape s,Point2D pIn);
	public boolean contains(Shape s,Point2D pIn);
}