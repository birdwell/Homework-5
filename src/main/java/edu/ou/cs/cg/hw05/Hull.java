package edu.ou.cs.cg.hw05;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.media.nativewindow.util.Point;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

class Hull {

	public static double orientation(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
		double result = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
		if (result == 0)
			return 0;
		return (result > 0) ? 1 : 2;
	}

	public static void convexHull(ArrayList<Point2D.Double> points, GL2 gl) {
		int n = points.size();
        Vector<Point2D.Double> hull = new Vector<Point2D.Double>();

		if (points.size() < 3) {
			if (points.size() == 2) {
				hull.add(points.get(0));
				hull.add(points.get(1));
		
				gl.glBegin(GL2.GL_LINES);
				gl.glLineWidth(3);
				gl.glColor3f(0.99f, 0.98f, 0.85f);
				for (Point2D.Double point : hull) {
					gl.glVertex2d(point.x, point.y);
				}
				gl.glEnd();
			}
			return;
		}		

		int l = 0;
		for(int i = 1; i < n; i++) {
			if (points.get(i).x < points.get(l).x) {
				l = i;
			}
		}
		int p = l, q;

		do {
			hull.add(points.get(p));

			q = (p + 1) % n;
			for (int i = 0; i < n; i++) {
				if (orientation(points.get(p), points.get(i), points.get(q)) == 2) {
					q = i;
				}
			}
			p = q;
		} while ( p != l );

		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(0.52f, 0.09f, 0.09f);
		for (Point2D.Double point : hull) {
			gl.glVertex2d(point.x, point.y);
		}
		gl.glEnd();

		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glColor3f(0.99f, 0.98f, 0.85f);
		for (Point2D.Double point : hull) {
			gl.glVertex2d(point.x, point.y);
		}
		gl.glEnd();
	}
}