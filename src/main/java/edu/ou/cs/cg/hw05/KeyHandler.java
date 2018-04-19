//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Mon Feb 29 23:36:04 2016 by Chris Weaver
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
import java.awt.RenderingHints.Key;
import java.awt.event.*;
import java.awt.geom.*;

//******************************************************************************

/**
 * The <CODE>KeyHandler</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class KeyHandler extends KeyAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View	view;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public KeyHandler(View view)
	{
		this.view = view;

		Component	component = view.getComponent();

		component.addKeyListener(this);
	}

	//**********************************************************************
	// Override Methods (KeyListener)
	//**********************************************************************

	public void		keyPressed(KeyEvent e)
	{
		Point2D.Double	p = view.getOrigin();

		switch (e.getKeyCode())
		{
			case KeyEvent.VK_PERIOD:
				if (Utilities.isShiftDown(e)) {
					view.cycleNames(1);
				} else {
					view.cycleActiveNodes(1);
				}
				
				break;
			case KeyEvent.VK_COMMA:
				if (Utilities.isShiftDown(e)) {
					view.cycleNames(0);
				} else {
					view.cycleActiveNodes(0);
				}
				break;
			case KeyEvent.VK_UP: 
				if (Utilities.isShiftDown(e)) {
					view.scaleActiveNode(1.0);
				} else {
					view.moveActiveNode('y', 1.0);
				}
				break;
			case KeyEvent.VK_DOWN:
				if (Utilities.isShiftDown(e)) {
					view.scaleActiveNode(-1.0);
				} else {
					view.moveActiveNode('y', -1.0);
				}
				break;
			case KeyEvent.VK_LEFT:
				if (Utilities.isShiftDown(e)) {
					view.scaleActiveNode(-1.0);
				} else {
					view.moveActiveNode('x', -1.0);
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (Utilities.isShiftDown(e)) {
					view.scaleActiveNode(1.0);
				} else {
					view.moveActiveNode('x', 1.0);
				}
				break;
			case KeyEvent.VK_ENTER:
				view.activeNode();
				break;
			
			case KeyEvent.VK_BACK_SPACE:
				view.deleteActiveNode();
				break;
			case KeyEvent.VK_DELETE:
				view.deleteActiveNode();
				return;
		}

		view.setOrigin(p);
	}
}

//******************************************************************************
