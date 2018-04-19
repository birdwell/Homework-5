//******************************************************************************
// Copyright (C) 2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Sat Apr 07 2018 22:16:00 GMT-0500 (CDT) by Josh Birdwell
//******************************************************************************
// Major Modification History:
// 20180407 [birdwell]: Homework 4	
// 
//
//******************************************************************************
// Notes:
/*
	Same class from my last homework
*/
//******************************************************************************

package edu.ou.cs.cg.hw05;

import java.awt.geom.Point2D;

public class Vector {
	private final double x;
	private final double y;
	private final double magnitude;
	private Point2D.Double startPoint;
	private Point2D.Double endPoint;

	public Vector() {
		x = y = (double) 0.0;
		this.magnitude = this.calcMagnitude();
	}

	public Vector(double x, double y, double magnitude) {
		this.x = x;
		this.y = y;

		this.startPoint = null;
		this.endPoint = null;

		this.magnitude = magnitude;
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;

		this.startPoint = null;
		this.endPoint = null;

		this.magnitude = this.calcMagnitude();
	}

	public Vector(Point2D.Double startPoint, Point2D.Double endPoint) {
		this.x = endPoint.x - startPoint.x;
		this.y = endPoint.y - startPoint.y;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.magnitude = this.calcMagnitude();
	}

	// Regular vector math
	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}

	public Vector sub(Vector other) {
		return new Vector(x - other.x, y - other.y);
	}

	public Vector mul(Vector other) {
		return new Vector(x * other.x, y * other.y);
	}

	public double calcMagnitude() {
		return (double) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public double dot(Vector other) {
		return (x * other.x) + (y * other.y);
	}

	// This was important for the reflection
	public Vector normalize() {
		return new Vector(-this.y / this.magnitude, this.x / this.magnitude);
	}

	public Vector scale(double s) {
		return new Vector(x * s, y * s);
	}

	public String toString() {
		if (this.startPoint != null && this.endPoint != null) {
			return "Start(" + this.startPoint.x + ", " + this.startPoint.y + ")" 
					+ " End(" + this.endPoint.x + ", " + this.endPoint.y + ")";
		}
		return "Vector(" + x + ", " + y + ")";
	}

	/**
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public Point2D.Double getStart() {
		return this.startPoint;
	}

	public Point2D.Double getEnd() {
		return this.endPoint;
	}

	public double addX(double x) {
		return (double) x + this.x;
	}

	public double addY(double y) {
		return (double) y + this.y;
	}

	// Very important funciton to return a reflected vector off a normal vector
	public Vector reflect(Vector n) {
		// r = d − 2 ( d ⋅ n ) n
		return this.sub(n.scale(this.dot(n) * 2));
	}

	public static double getAngle(Vector a, Vector b) {
		return Math.atan2(b.y,b.x) - Math.atan2(a.y,a.x);
	}
}