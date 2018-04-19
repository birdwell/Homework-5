//******************************************************************************
// Copyright (C) 2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Sat Apr 07 2018 22:16:00 GMT-0500 (CDT) by Josh Birdwell
//******************************************************************************
// Major Modification History:
// 20180407 [birdwell]: Homework 5
// 
//
//******************************************************************************
// Notes:
/*
	This is the main part where the interaction with all the nodes in the network happens
*/
//******************************************************************************

package edu.ou.cs.cg.hw05;

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
import edu.ou.cs.cg.hw05.Network;
import edu.ou.cs.cg.hw05.Utilities;

/**
 * Node
 * 
 */
public class Node {
	// Constants
	private static double MIN = -0.75;
	private static double MAX = 0.75;
	private double radius = 0.05;
	private Random generator;

	// Properties of the node class
	private String name;
	private Integer sides;
	private Color color;
	private Point2D.Double center;
	private Double rotate;
	private Boolean beenActiveBefore;

	public Node(String name) {
		this.name = name;
		this.sides = Network.getSides(name);
		this.color = Network.getColor(name);
		this.beenActiveBefore = false;
		this.rotate = 0.0;
		generator = new Random();
	}

	/**
	 * Basic getters
	 */
	
	public String getName() {
		return this.name;
	}

	public Point2D.Double getCenter() {
		return this.center;
	}

	/**
	 * Contains is for the clicking!
	 */
	
	public Boolean contains(Double length) {
		return length <= this.radius;
	}

	public void activate() {
		if (!this.beenActiveBefore) {
			double x = this.generator.nextDouble() * (MAX - MIN) + MIN;
			double y = this.generator.nextDouble() * (MAX - MIN) + MIN;
			this.center = new Point2D.Double(x, y);
		}
		this.beenActiveBefore = true;
	}

	//**********************************************************************
	// Move Methods
	//**********************************************************************
	
	public void move(Point2D.Double newCenter) {
		this.center = newCenter;
	}

	public void moveX(double i) {
		double x = this.center.x + (this.radius * 2 * (i * 0.1));
		this.center = new Point2D.Double(x, this.center.y);
	}

	public void moveY(double i) {
		double y = this.center.y + (this.radius * 2 * (i * 0.1));
		this.center = new Point2D.Double(this.center.x, y);
	}

	//**********************************************************************
	// Scale - Rotate - Drawing Methods
	//**********************************************************************

	public void scale(double i) {
		if (i == 1) {
			this.radius = Math.abs(this.radius + (this.radius * (1.1 * i)));
		} else {
			this.radius = Math.abs(this.radius + (this.radius * (0.1 * i)));
		}
	}

	public void setRotate(double i) {
		this.rotate = i;
	}

	public void draw(GL2 gl, Boolean selected) {
		double offset = (360 / (double) this.sides) + this.rotate;
		double cx = this.center.x;
		double cy = this.center.y;

		// Fill Polygon
		gl.glBegin(GL2.GL_POLYGON);
		Utilities.setColor(gl, this.color.getRed(), this.color.getGreen(), this.color.getBlue());
		for (double theta = 0; theta < 360; theta += offset) {
			double x = cx + radius * Math.cos(Math.toRadians(theta));
			double y = cy + radius * Math.sin(Math.toRadians(theta));
			gl.glVertex2d(x, y);
		}
		gl.glEnd();

		// Draw Outline
		gl.glBegin(GL.GL_LINE_LOOP);
		if (selected) {
			Utilities.setColor(gl, 255, 255, 255);
		} else {
			Utilities.setColor(gl, 75, 75, 75);
		}
		
		for (double theta = 0; theta < 360; theta += offset) {
			double x = cx + radius * Math.cos(Math.toRadians(theta));
			double y = cy + radius * Math.sin(Math.toRadians(theta));
			gl.glVertex2d(x, y);
		}
		gl.glEnd();
	}

}