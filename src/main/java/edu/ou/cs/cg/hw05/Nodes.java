package edu.ou.cs.cg.hw05;

//import java.lang.*;
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
import com.jogamp.opengl.util.awt.TextRenderer;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import edu.ou.cs.cg.hw05.Network;

/**
 * Nodes
 */
public class Nodes {
	private ArrayList<Node> activeNodes;
	private ArrayList<Node> inactiveNodes;
	private Integer currentInactiveIndex;
	private Integer currentActiveIndex;

	public Nodes() {
		this.inactiveNodes = new ArrayList<Node>();
		this.activeNodes = new ArrayList<Node>();
		this.currentInactiveIndex = 0;
		this.currentActiveIndex = -1;

		for (String name : Network.getAllNames()) {
			this.inactiveNodes.add(new Node(name));
		}
	}

	public void getNextName() {
		if (currentInactiveIndex + 1 >= this.inactiveNodes.size()) {
			this.currentInactiveIndex = 0;
		} else {
			this.currentInactiveIndex += 1;
		}
	}

	public void getPreviousName() {
		if (currentInactiveIndex - 1 < 0) {
			this.currentInactiveIndex = this.inactiveNodes.size() - 1;
		} else {
			this.currentInactiveIndex -= 1;
		}
	}

	public void getNextActive() {
		if (this.currentActiveIndex == -1) {
			this.currentActiveIndex = 0;
			return;
		}

		if (currentActiveIndex + 1 >= this.activeNodes.size()) {
			this.currentActiveIndex = -1;
		} else {
			this.currentActiveIndex += 1;
		}
	}

	public void getPreviousActive() {
		if (this.currentActiveIndex == -1) {
			this.currentActiveIndex = this.activeNodes.size() - 1;
			return;
		}

		if (currentActiveIndex - 1 < 0) {
			this.currentActiveIndex = -1;
		} else {
			this.currentActiveIndex -= 1;
		}
	}


	public void drawNameLabel(TextRenderer textRenderer, GLAutoDrawable drawable) {
		textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		textRenderer.setColor(Color.YELLOW);
		textRenderer.setSmoothing(true);

		if (this.inactiveNodes.size() > 0) {
			textRenderer.draw(this.inactiveNodes.get(this.currentInactiveIndex).getName(), 2, 2);
		} else {
			textRenderer.draw("No names left", 2, 2);
		}
		
		textRenderer.endRendering();
	}

	public void insertNode() {
		if (this.inactiveNodes.size() == 0) return;

		Node activeNode = this.inactiveNodes.get(this.currentInactiveIndex);
		activeNode.activate();
		this.activeNodes.add(activeNode);
		this.currentActiveIndex = this.activeNodes.size() - 1;
		this.inactiveNodes.remove(activeNode);
		this.getNextName();
	}

	public void drawNodes(GL2 gl) {
		for (int i = 0; i < this.activeNodes.size(); i++) {
			this.activeNodes.get(i).draw(gl, i == this.currentActiveIndex);
		}
	}

	public void deleteActiveNode() {
		if (this.activeNodes.size() == 0) return;
		System.out.println("here");
		Node activeNode = this.activeNodes.get(this.currentActiveIndex);
		activeNode.disable();
		this.inactiveNodes.add(activeNode);
		this.activeNodes.remove(activeNode);
		this.getNextActive();
	}

	public Boolean insideNode(Point2D.Double click) {
		if (this.activeNodes.size() == 0) return false;

		for (int i = 0; i < this.activeNodes.size(); i++) {
			Vector clickVector = new Vector(this.activeNodes.get(i).getCenter(), click);
			if (this.activeNodes.get(i).contains(clickVector.getMagnitude())) {
				this.currentActiveIndex = i;
				return true;
			}
		}

		return false;
	}

	public void selectNode(Point2D.Double click) {
		if (this.activeNodes.size() == 0) return;
		
		for (int i = 0; i < this.activeNodes.size(); i++) {
			Vector clickVector = new Vector(this.activeNodes.get(i).getCenter(), click);
			if (this.activeNodes.get(i).contains(clickVector.getMagnitude())) {
				this.currentActiveIndex = i;
				break;
			}
		}
	}

	public void moveActiveNode(Character type, Double i) {
		if (currentActiveIndex != null && currentActiveIndex >= 0) {
			Node activeNode = this.activeNodes.get(currentActiveIndex);
			if (type == 'y') {
				activeNode.moveY(i);
			} else if (type == 'x') {
				activeNode.moveX(i);
			}
		}
	}

	public void moveActiveNode(Point2D.Double newCenter) {
		this.activeNodes.get(currentActiveIndex).move(newCenter);
	} 

	public void scaledActiveNode(Double i) {
		if (currentActiveIndex != null && currentActiveIndex >= 0) {
			Node activeNode = this.activeNodes.get(currentActiveIndex);
			activeNode.scale(i);
		}
	}

	public Node getActiveNode() {
		return this.activeNodes.get(currentActiveIndex);
	}

	public void rotateActiveNode(Double i) {
		this.activeNodes.get(currentActiveIndex).setRotate(i);
	}

	public ArrayList<Point2D.Double> getCenters() {
		ArrayList<Point2D.Double> centers = new ArrayList<Point2D.Double>();
		for (Node node : this.activeNodes) {
			centers.add(node.getCenter());
		}
		return centers;
	}
}