//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Mar  1 18:52:22 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file.
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
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.TextRenderer;
import edu.ou.cs.cg.hw05.Node;

//******************************************************************************

/**
 * The <CODE>Interaction</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class View
	implements GLEventListener
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	public static final int				DEFAULT_FRAMES_PER_SECOND = 60;
	private static final DecimalFormat	FORMAT = new DecimalFormat("0.000");

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final GLJPanel			canvas;
	private int						w;				// Canvas width
	private int						h;				// Canvas height

	private final KeyHandler		keyHandler;
	private final MouseHandler		mouseHandler;

	private final FPSAnimator		animator;

	private TextRenderer			renderer;

	private Point2D.Double				origin;		// Current origin coordinates
	private Point2D.Double				cursor;		// Current cursor coordinates
	private Nodes nodes;


	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public View(GLJPanel canvas)
	{
		this.canvas = canvas;
		this.nodes = new Nodes();

		// Initialize model
		origin = new Point2D.Double(0.0, 0.0);
		cursor = null;

		// Initialize rendering
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, DEFAULT_FRAMES_PER_SECOND);
		//animator.start();

		// Initialize interaction
		keyHandler = new KeyHandler(this);
		mouseHandler = new MouseHandler(this);
	}

	//**********************************************************************
	// Getters and Setters
	//**********************************************************************

	public int	getWidth()
	{
		return w;
	}

	public int	getHeight()
	{
		return h;
	}

	public Point2D.Double	getOrigin()
	{
		return new Point2D.Double(origin.x, origin.y);
	}

	public void		setOrigin(Point2D.Double origin)
	{
		this.origin.x = origin.x;
		this.origin.y = origin.y;
		canvas.repaint();
	}

	public Point2D.Double	getCursor()
	{
		return cursor;
	}

	public void		setCursor(Point2D.Double cursor)
	{
		this.cursor = cursor;
		canvas.repaint();
	}

	public void		clear()
	{
		canvas.repaint();
	}

	//**********************************************************************
	// Public Methods
	//**********************************************************************

	public Component	getComponent()
	{
		return (Component)canvas;
	}

	//**********************************************************************
	// Override Methods (GLEventListener)
	//**********************************************************************

	public void		init(GLAutoDrawable drawable)
	{
		w = drawable.getWidth();
		h = drawable.getHeight();

		renderer = new TextRenderer(new Font("Monospaced", Font.PLAIN, 12), true, true);
	}

	public void		dispose(GLAutoDrawable drawable)
	{
		renderer = null;
	}

	public void		display(GLAutoDrawable drawable)
	{
		updateProjection(drawable);

		update(drawable);
		render(drawable);
	}

	public void		reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		this.w = w;
		this.h = h;
	}

	public void cycleNames (Integer i) {
		if (i == 1) {
			this.nodes.getNextName();
		} else if (i == 0) {
			this.nodes.getPreviousName();
		}
	}

	public void cycleActiveNodes (Integer i) {
		if (i == 1) {
			this.nodes.getNextActive();
		} else if (i == 0) {
			this.nodes.getPreviousActive();
		}
	}

	public void activeNode () {
		this.nodes.insertNode();
	}

	public void deleteActiveNode () {
		this.nodes.deleteActiveNode();
	}

	public void selectNode(Point2D.Double click) {
		this.nodes.selectNode(click);
	}

	public void moveActiveNode(Character type, Double i) {
		this.nodes.moveActiveNode(type, i);
	}

	public void moveActiveNode(Point2D.Double newCenter) {
		this.nodes.moveActiveNode(newCenter);
		canvas.repaint();
	}

	public void scaleActiveNode(Double i) {
		this.nodes.scaledActiveNode(i);
	}

	public Boolean insideNode(Point2D.Double click) {
		return this.nodes.insideNode(click);
	}

	public Node getActiveNode() {
		return this.nodes.getActiveNode();
	}

	public void rotateActiveNode(Double i) {
		this.nodes.rotateActiveNode(i);
		canvas.repaint();
	}

	//**********************************************************************
	// Private Methods (Viewport)
	//**********************************************************************

	private void	updateProjection(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();
		GLU		glu = new GLU();

		float	xmin = (float)(origin.x - 1.0);
		float	xmax = (float)(origin.x + 1.0);
		float	ymin = (float)(origin.y - 1.0);
		float	ymax = (float)(origin.y + 1.0);

		gl.glMatrixMode(GL2.GL_PROJECTION);			// Prepare for matrix xform
		gl.glLoadIdentity();						// Set to identity matrix
		glu.gluOrtho2D(xmin, xmax, ymin, ymax);		// 2D translate and scale
	}

	//**********************************************************************
	// Private Methods (Rendering)
	//**********************************************************************

	private void	update(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		Hull.convexHull(this.nodes.getCenters(), gl);
		this.nodes.drawNameLabel(renderer, drawable);
		this.nodes.drawNodes(gl);
	}

	private void	render(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		// Clear the buffer
		drawAxes(gl);							// X and Y axes
		drawCursor(gl);							// Crosshairs at mouse location
		Hull.convexHull(this.nodes.getCenters(), gl);
		this.nodes.drawNameLabel(renderer, drawable);
		this.nodes.drawNodes(gl);
	}

	//**********************************************************************
	// Private Methods (Scene)
	//**********************************************************************


	private void	drawAxes(GL2 gl)
	{
		gl.glBegin(GL.GL_LINES);

		gl.glColor3f(0.25f, 0.25f, 0.25f);
		gl.glVertex2d(-10.0, 0.0);
		gl.glVertex2d(10.0, 0.0);

		gl.glVertex2d(0.0, -10.0);
		gl.glVertex2d(0.0, 10.0);

		gl.glEnd();
	}

	private void	drawCursor(GL2 gl)
	{
		if (cursor == null)
			return;

		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glColor3f(0.5f, 0.5f, 0.5f);

		for (int i=0; i<32; i++)
		{
			double	theta = (2.0 * Math.PI) * (i / 32.0);

			gl.glVertex2d(cursor.x + 0.05 * Math.cos(theta),
						  cursor.y + 0.05 * Math.sin(theta));
		}

		gl.glEnd();
	}

}

//******************************************************************************
