package pers.sfc.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class NormalState implements State{

	@Override
	public void draw(Shape s, Graphics2D g) {
		g.setColor(Color.BLACK);
		s.drawEntity(g);
	}

	@Override
	public int on(Shape s, Point2D pIn) {
		// TODO Auto-generated method stub
		return 0;
	}

}
