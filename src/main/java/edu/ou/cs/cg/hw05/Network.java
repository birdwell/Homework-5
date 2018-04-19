//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Mon Apr 11 21:27:45 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160411 [weaver]:	Original file.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.cg.hw05;

//import java.lang.*;
import java.awt.Color;
import java.util.ArrayList;

//******************************************************************************

/**
 * The <CODE>Network</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Network
{
	//**********************************************************************
	// Private Class Members
	//**********************************************************************

	// Mostly from https://en.wikipedia.org/wiki/List_of_most_popular_given_names
	private static final String[]	NAMES = new String[]
	{
		"Brenden Frederick",
		"Deandre Hayden",
		"Gunner Andersen",
		"Dixie Middleton",
		"Kiersten Gillespie",
		"Peter Rangel",
		"Kaylah Huerta",
		"Magdalena Herrera",
		"Ainsley Buckley",
		"Jose Banks",
		"Willie Adkins",
		"Van Downs",
		"Cason Luna",
		"David King",
		"Darren Smith",
		"Reece Miranda",
		"Ahmad Odonnell",
		"Shelby Mason",
		"Jadiel Hunter",
		"Tony Flynn",
		"Brice Bailey",
		"Jimena Maldonado",
		"Aditya Collier",
		"Kaley Mcfarland",
		"Braylon Leblanc",
		"Erica Harris",
		"Iris Holmes",
		"Jamie Faulkner",
		"Tyson Payne",
		"Eliana Kirby",
		"June Bryant",
		"Damien Rodgers",
		"Jaylen Robertson",
		"Emerson Barton",
		"Jonas Holloway",
		"Mason Evans",
		"Emery Johnson",
		"Gretchen Black",
		"Kennedi Cameron",
		"Adan Little",
		"Annalise Schultz",
		"Anastasia David",
		"Alina Cortez",
		"Dennis Porter",
		"Salma Estrada",
		"Jovan Henderson",
		"Nathanial Abbott",
		"Kyler Mccullough",
		"Ruth Zhang",
		"Estrella Kent",
		"Ari Estes",
		"Kamron Dominguez",
		"Waylon Hurley",
		"Bronson Mcgrath",
		"Nola Golden",
		"Reuben Nash",
		"Fernando Escobar",
		"Alberto Collins",
		"Finley Gay",
		"Theodore Chase",
		"Dakota Stanley",
		"Dorian Mcknight",
		"Tamia Brandt",
		"Terry Dunn",
		"Marlee Logan",
		"Anderson West",
		"Jesse Potts",
		"Kailyn Beasley",
		"Marissa Barron",
		"Santino Powers",
		"Lucille Osborne",
		"Diya Madden",
		"Cristian Ali",
		"Holden Ponce",
		"Isaiah Mcmahon",
		"Zachary Hayes",
		"Ivan Hays",
		"Raymond Mayer",
		"Kierra Sexton",
		"Brynn Yang",
		"Emily Bishop",
		"Kameron Hartman",
		"Kyle Pena",
		"Johnathan Gonzalez",
		"Uriah Atkins",
		"Jimmy Cox",
		"Ashtyn Fry",
		"Maddox Phillips",
		"Annabella Gordon",
		"Dustin Valdez",
		"Meredith Hart",
		"Perla Gibson",
		"Jenna Boyd",
		"Kadin Huang",
		"Kylie Molina",
		"Kaila Fields",
		"Mireya Carr",
		"Kenny Walker",
		"Amiya Ibarra",
		"Jagger Pennington",
	};

	// Just made up for sake of variety. Notice the stress test hidden in #7.
	private static final int[]		SIDES = new int[]
	{
		3, 11, 5, 4, 9, 6, 173, 9, 3,
		4, 8, 6, 32, 10, 8, 3, 5, 4,
		6, 16, 13, 6, 7, 4, 3, 5, 7,
	};

	// Modified for variety from the qualitative palettes at ColorBrewer.org
	private static final Color[]	COLORS = new Color[]
	{
		new Color(0x1b9e77),
		new Color(0xd95f02),
		new Color(0x7570b3),
		new Color(0xe7298a),
		new Color(0x66a61e),
		new Color(0xe6ab02),
		new Color(0xa6761d),
		new Color(0x66a666),
		new Color(0xd9d9d9),

		new Color(0xe41a1c),
		new Color(0x377eb8),
		new Color(0x4daf4a),
		new Color(0x984ea3),
		new Color(0xff7f00),
		new Color(0xffff33),
		new Color(0xa65628),
		new Color(0xf781bf),
		new Color(0x399999),

		new Color(0xfbb4ae),
		new Color(0xb3cde3),
		new Color(0xccebc5),
		new Color(0xdecbe4),
		new Color(0xfed9a6),
		new Color(0xffffcc),
		new Color(0xe5d8bd),
		new Color(0xfddaec),
		new Color(0xa2c2f2),
	};

	//**********************************************************************
	// Public Class Methods (Event Handling)
	//**********************************************************************

	public static ArrayList<Person> createNetwork(int n) {
		ArrayList<Person> people = new ArrayList<Person>();
		for(int i = 0; i <= n; i++) {
			people.add(new Person(NAMES[i]));
		}
		
		return people;
	}

	public static String[]	getAllNames()
	{
		// DON'T ABUSE THE MUTABLE ARRAY, PEOPLE
		return (String[])NAMES.clone();
	}

	public static int		getSides(String name)
	{
		// Ugh this is so inefficient!
		for (int i=0; i<NAMES.length; i++)
			if (NAMES[i].equals(name))
				return SIDES[i];

		return -1;
	}

	public static Color		getColor(String name)
	{
		// Why bother precomputing an O(log n) hash when O(n) is so nicely lazy?
		for (int i=0; i<NAMES.length; i++)
			if (NAMES[i].equals(name))
				return COLORS[i];

		return null;
	}

	public static void	main(String[] argv)
	{
		for (int i=0; i<NAMES.length; i++)
			System.out.println("" + i + ":" + getSides(NAMES[i]) + " -> " +
							   getColor(NAMES[i]) + " (" + NAMES[i] + ")");
	}
}

//******************************************************************************
