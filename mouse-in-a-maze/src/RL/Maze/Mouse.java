/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author jesse
 */
public class Mouse extends Cell
{
	public static final Color COLOR = Color.GRAY;
	private Point location, previousLocation;
	private final Random prng = new Random(System.currentTimeMillis());
	private boolean exploringMode = true;
	
	
	public Mouse(Point startingLocation)
	{
		previousLocation = location = startingLocation;
		System.out.println("Mouse created, exploring mode enabled");
	}

	

	@Override
	public void draw(Graphics g, Rectangle rect)
	{
		g.setColor(COLOR);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	
	
	public Point update(Cell[][] maze)
	{
		maze[location.x][location.y] = new OpenCell(1); //put down memory
		
		//all possible moves
		final Point[] neighbors = {
			new Point(location.x + 1, location.y),
			new Point(location.x - 1, location.y),
			new Point(location.x,     location.y - 1),
			new Point(location.x,     location.y + 1)
		};
		
		//assemble list of available legal moves
		ArrayList<Point> possibleMoves = new ArrayList<Point>(4);
		for (Point neighbor : neighbors)
			if (neighbor.x >= 0 && maze[neighbor.x][neighbor.y] instanceof OpenCell)
				possibleMoves.add(neighbor);
		
		//shuffle to ensure randomness for moves with equal memory
		for (int j = 0; j < possibleMoves.size(); j++)
		{
			int randIndex = prng.nextInt(possibleMoves.size());
			Point temp = possibleMoves.get(j);
			possibleMoves.set(j,         possibleMoves.get(randIndex));
			possibleMoves.set(randIndex, temp);
		}
		
		//sort according to memory
		Collections.sort(possibleMoves, new CellComparator(maze));
		
		//if the mouse is using previous memory to find optimal path,
		//but it's only possible next move is the previous location,
		//then the maze has been changed. Search for alternative solution
		if (!exploringMode && possibleMoves.size() == 1 && possibleMoves.get(0).equals(previousLocation))
		{
			System.out.println("Memory led to dead end, confusing! Reverting to exploring mode.");
			exploringMode = true;
		}
			
		previousLocation = location;
		if (exploringMode)
			return location = possibleMoves.get(0); //take move that's least remembered
		else  //take move that's most remembered, but not the previous location
			return location = possibleMoves.get(Math.max(possibleMoves.size() - 2, 0));
	}
	
	
	
	public Point getLocation()
	{
		return location;
	}
	
	
	
	public void setLocation(Point newLocation)
	{
		previousLocation = location = newLocation;
	}
	
	
	
	public void setExploringMode(boolean shouldBeExploring)
	{
		exploringMode = shouldBeExploring;
		System.out.println("Mouse's exploring mode is now set to " + exploringMode);
	}
}
