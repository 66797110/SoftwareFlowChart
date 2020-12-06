package pers.sfc.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class SelectState implements State{
	@Override
	public void draw(Shape s, Graphics2D g) {
		g.setColor(Color.BLACK);
		s.drawEntity(g);
		g.setColor(Color.BLACK);
		s.drawCirRect(g);
		g.setColor(Color.BLACK);
		s.connectPoint(g);
	}

	@Override
	public int on(Shape s, Point2D pIn) {
		return s.onCorner(pIn);
	}

}
