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
import java.util.Random;

/**
 *
 * @author jesse
 */
public class Mouse extends Cell
{
	public static final Color COLOR = Color.GRAY;
	private Point location;
	private final Random prng = new Random(System.currentTimeMillis());
	
	
	public Mouse(Point startingLocation)
	{
		location = startingLocation;
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
		
		final Point[] neighbors = {
			new Point(location.x + 1, location.y),
			new Point(location.x - 1, location.y),
			new Point(location.x,     location.y - 1),
			new Point(location.x,     location.y + 1)
		};
		
		ArrayList<Point> emptyNeighbors = new ArrayList<Point>(4);
		for (Point neighbor : neighbors)
			if (neighbor.x >= 0 && maze[neighbor.x][neighbor.y] instanceof OpenCell)
				emptyNeighbors.add(neighbor);
		
		//if a wall is available into the goal bar, then take it
		if (location.x == maze.length - 1 && neighbors[0].equals(emptyNeighbors.get(0)))
		{
			location = neighbors[0];
			return location;
		}
		
		ArrayList<Point> olderNeighbors = new ArrayList<Point>(4);
		for (Point emptyNeighbor : emptyNeighbors)
			if (((OpenCell)maze[emptyNeighbor.x][emptyNeighbor.y]).getMemoryOf() <= 0.9f)
				olderNeighbors.add(emptyNeighbor);
		
		if (olderNeighbors.isEmpty())
			olderNeighbors = emptyNeighbors;

		int index = prng.nextInt(olderNeighbors.size());
		location = olderNeighbors.get(index);
		return location;
	}
	
	
	
	public Point getLocation()
	{
		return location;
	}
}
