/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL.Maze;

import java.awt.Point;
import java.util.Comparator;

/**
 *
 * @author jesse
 */
public class CellComparator implements Comparator<Point>
{
	private final Cell[][] maze;


	public CellComparator(Cell[][] maze)
	{
		this.maze = maze;
	}



	@Override
	public int compare(Point a, Point b)
	{
		float memA = ((OpenCell)maze[a.x][a.y]).getMemoryOf();
		float memB = ((OpenCell)maze[b.x][b.y]).getMemoryOf();
		return Float.compare(memA, memB);
	}
}
