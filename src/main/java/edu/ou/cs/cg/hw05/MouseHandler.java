//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Mon Feb 29 23:46:15 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160225 [weaver]:	Original file.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.cg.hw05;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.media.nativewindow.util.Point;

//******************************************************************************

/**
 * The <CODE>MouseHandler</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class MouseHandler extends MouseAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View	view;
	private Point2D.Double startingOrigin;
	private Point2D.Double startingCursor;
	private Boolean draggingNode;
	private Point2D.Double startingCenter;
	private Point2D.Double previousDrag;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public MouseHandler(View view)
	{
		this.view = view;

		Component	component = view.getComponent();

		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}

	//**********************************************************************
	// Override Methods (MouseListener)
	//**********************************************************************

	public void		mouseClicked(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());
		view.selectNode(v);
	}

	public void		mouseEntered(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());

		view.setCursor(v);
	}

	public void		mouseExited(MouseEvent e)
	{
		view.setCursor(null);
	}

	public void		mousePressed(MouseEvent e)
	{
		this.startingOrigin = view.getOrigin();
		this.startingCursor = calcCoordinatesInView(e.getX(), e.getY());
		this.previousDrag = calcCoordinatesInView(e.getX(), e.getY());

		if(view.insideNode(this.startingCursor)) {
			this.draggingNode = true;
			Node activeNode = this.view.getActiveNode();
			this.startingCenter = activeNode.getCenter();
		} else {
			this.draggingNode = false;
		}
	}

	public void		mouseReleased(MouseEvent e)
	{
	}

	//**********************************************************************
	// Override Methods (MouseMotionListener)
	//**********************************************************************

	public void		mouseDragged(MouseEvent e)
	{
		Point2D.Double	cursor = calcCoordinatesInView(e.getX(), e.getY());
		if (this.draggingNode) {
			if (startingCursor != null && startingCenter != null) {
				if(Utilities.isShiftDown(e)) {
					System.out.println("shift down");
					Vector a = new Vector(startingCenter, this.previousDrag);
					Vector b = new Vector(startingCenter, cursor);
					Double rotate = Vector.getAngle(a, b);
					this.previousDrag = cursor;
					view.rotateActiveNode(rotate);
				} else {
					double dx = (cursor.getX() - startingCursor.getX());
					double dy = (cursor.getY() - startingCursor.getY());
					Point2D.Double nextCenter = new Point2D.Double(startingCenter.getX() + dx,
							startingCenter.getY() + dy);

					view.setCursor(cursor);
					view.moveActiveNode(nextCenter);
				}

			}
		} else {
			Double slowDown = 0.5;
			if (startingCursor != null && startingOrigin != null) {
				double dx = (cursor.getX() - startingCursor.getX()) * slowDown;
				double dy = (cursor.getY() - startingCursor.getY()) * slowDown;
				Point2D.Double nextOrigin = new Point2D.Double(startingOrigin.getX() - dx, startingOrigin.getY() - dy);

				view.setOrigin(nextOrigin);
				view.setCursor(cursor);
			}
		}

	}

	public void		mouseMoved(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());

		view.setCursor(v);
	}

	//**********************************************************************
	// Override Methods (MouseWheelListener)
	//**********************************************************************

	public void		mouseWheelMoved(MouseWheelEvent e)
	{
	}

	//**********************************************************************
	// Private Methods
	//**********************************************************************

	private Point2D.Double	calcCoordinatesInView(int sx, int sy)
	{
		int				w = view.getWidth();
		int				h = view.getHeight();
		Point2D.Double	p = view.getOrigin();
		double			vx = p.x + (sx * 2.0) / w - 1.0;
		double			vy = p.y - (sy * 2.0) / h + 1.0;

		return new Point2D.Double(vx, vy);
	}
}

//******************************************************************************
