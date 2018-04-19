//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Thu Feb 25 18:14:05 2016 by Chris Weaver
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
import java.awt.event.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.media.nativewindow.util.Point;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;

//******************************************************************************

/**
 * The <CODE>KeyHandler</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Utilities
{
	//**********************************************************************
	// Public Class Methods (Event Handling)
	//**********************************************************************

	public static boolean	isAltDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0);
	}

	public static boolean	isAltGraphDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.ALT_GRAPH_DOWN_MASK) != 0);
	}

	public static boolean	isControlDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0);
	}

	public static boolean	isMetaDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.META_DOWN_MASK) != 0);
	}

	public static boolean	isShiftDown(InputEvent e)
	{
		return ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0);
	}

	public static boolean	isButton1Down(InputEvent e)
	{
		return (((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) ||
				((e instanceof MouseEvent) &&
				 (((MouseEvent)e).getButton() == 1)));
	}

	public static boolean	isButton2Down(InputEvent e)
	{
		return (((e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0) ||
				((e instanceof MouseEvent) &&
				 (((MouseEvent)e).getButton() == 2)));
	}

	public static boolean	isButton3Down(InputEvent e)
	{
		return (((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) ||
				((e instanceof MouseEvent) &&
				 (((MouseEvent)e).getButton() == 3)));
	}

	public static void setColor(GL2 gl, int r, int g, int b, int a) {
		gl.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 0.5f);
	}

	public static void setColor(GL2 gl, int r, int g, int b) {
		setColor(gl, r, g, b, 255);
	}

	public static double distanceBetweenPoints(Point2D.Double a, Point2D.Double b) {
		return Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2);
	}
}

//******************************************************************************
