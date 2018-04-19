package edu.ou.cs.cg.hw05;

import java.awt.Color;
import java.util.*;
import java.util.Random;

public class Person {	
	public String name;
	public Integer sides;
	public Color color;
	public ArrayList<Person> friends;

	public Person(String name) {
		this.color = new Color((int)(Math.random() * 0x1000000));
		this.name = name;
		this.sides = (int)(Math.random()*100);
	}

	public void addFriends(ArrayList<Person> friends) {
		this.friends = friends;
	}
}