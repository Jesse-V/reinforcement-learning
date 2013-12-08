/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL.Maze;

import RL.MersenneTwister;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;
import javax.swing.JPanel;

/**
 *
 * @author jesse
 */
public class Maze extends JPanel implements MouseListener
{
	private static final int SIZE = 32;
	private Cell[][] maze = new Cell[SIZE][SIZE];
	private final MersenneTwister prng = new MersenneTwister(System.currentTimeMillis());
	private static final int START_Y = 1, GENERATED_SIZE = SIZE - 2;
	private int openCellCount, exploredCellCount, mouseTrailLength;
	private Mouse mouse;
	private int solvedCounter;
	

	public Maze()
	{
		reset();
	}
	
	
	
	public final void reset()
	{
		maze = new Cell[SIZE][SIZE];
		solvedCounter = 0;
		
		boolean[][] grid = DepthFirstMazeGen(1, START_Y, GENERATED_SIZE);
		
		//count the number of open cells
		openCellCount = 0;
		for (int j = 1; j < GENERATED_SIZE; j++)
			for (int k = 1; k < GENERATED_SIZE; k++)
				if (grid[j][k])
					openCellCount++;

		//make everything a wall
		for (int j = 0; j < SIZE; j++)
			for (int k = 0; k < SIZE; k++)
				maze[j][k] = new WallCell();

		//copy over generated maze
		for (int j = 0; j < GENERATED_SIZE; j++)
			for (int k = 0; k < GENERATED_SIZE; k++)
				maze[j][k] = grid[j][k] ? new OpenCell() : new WallCell();

		//finalize maze preperation
		maze[0][START_Y] = mouse = new Mouse(new Point(0, START_Y));
		for (int j = 0; j < SIZE; j++)
			maze[SIZE - 1][j] = new OpenCell();
		
		int yEnding = 0;
		while (maze[maze.length - 3][yEnding] instanceof WallCell)
			yEnding = prng.nextInt(maze.length);
		maze[maze.length - 2][yEnding] = new OpenCell();

		addMouseListener(this);
	}



	public void update()
	{
		Point newLoc = mouse.update(maze);
		maze[newLoc.x][newLoc.y] = mouse;
		
		if (newLoc.x == SIZE - 1)
			handleMazeSolved(newLoc);
		
		for (int j = 0; j < SIZE; j++)
			for (int k = 0; k < SIZE; k++)
				maze[j][k].update();
	}
	
	
	
	public void handleMazeSolved(Point newLoc)
	{
		//System.out.println("Mouse solved the maze!");
		solvedCounter++;
		repaint();
		
		exploredCellCount = 0;
		for (int j = 1; j < GENERATED_SIZE; j++)
			for (int k = 1; k < GENERATED_SIZE; k++)
				if (maze[j][k] instanceof OpenCell && ((OpenCell)maze[j][k]).getMemoryOf() > 0)
					exploredCellCount++;
		mouseTrailLength = mouse.getTrailLength();
		//System.out.println(openCellCount + "		" + exploredCellCount + "	" + mouse.getTrailLength());
		
		maze[newLoc.x][newLoc.y] = new OpenCell();
		mouse.setLocation(new Point(0, START_Y));
		maze[0][START_Y] = mouse;
		mouse.setExploringMode(false);
	}
	
	
	
	public final boolean[][] DepthFirstMazeGen(int x, int y, int size)
	{
		//https://en.wikipedia.org/wiki/Maze_generation_algorithm#Depth-first_search
		//http://stackoverflow.com/questions/13182790/dfs-maze-generator

		boolean[][] grid = new boolean[size][size];
		for (int j = 0; j < size; j++)
			for (int k = 0; k < size; k++)
				grid[j][k] = false;

		grid[x][y] = true;
		int total = (size * size) / 4;
		int visitedCount = 1;
		int random[] = new int[4];

		Stack<Integer> stack = new Stack<Integer>();
		while (visitedCount < total)
		{
			int neighborCount = 0;
			if (x > 1        && grid[x - 2][y] == false)
				random[neighborCount++] = 1;
			if (x < size - 2 && grid[x + 2][y] == false)
				random[neighborCount++] = 2;
			if (y > 1        && grid[x][y - 2] == false)
				random[neighborCount++] = 3;
			if (y < size - 2 && grid[x][y + 2] == false)
				random[neighborCount++] = 4;

			if (neighborCount > 0)
			{
				switch(random[prng.nextInt(neighborCount)])
				{
					case 1:
						grid[x - 2][y] = grid[x - 1][y] = true;
						x -= 2;
						stack.push(x * size + y);
						visitedCount++;
						break;

					case 2:
						grid[x + 2][y] = grid[x + 1][y] = true;
						x += 2;
						stack.push(x * size + y);
						visitedCount++;
						break;

					case 3:
						grid[x][y - 2] = grid[x][y - 1] = true;
						y -= 2;
						stack.push(x * size + y);
						visitedCount++;
						break;

					case 4:
						grid[x][y + 2] = grid[x][y + 1] = true;
						y += 2;
						stack.push(x * size + y);
						visitedCount++;
						break;
				}
			}
			else
			{
				int vert = stack.pop();
				x = vert / size;
				y = vert % size;
			}
		}

		return grid;
	}

	

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		float ratioX = getWidth()  / SIZE;
		float ratioY = getHeight() / SIZE;

		for (int x = 0; x < SIZE; x++)
			for (int y = 0; y < SIZE; y++)
				maze[x][y].draw(g, new Rectangle((int)(x * ratioX), (int)(y * ratioY), (int)ratioX, (int)ratioY));
	}
	
	
	
	public int getOpenCount()
	{
		return openCellCount;
	}

	

	public int getExploredCount()
	{
		return exploredCellCount;
	}
	
	
	
	public int getMouseTrailLength()
	{
		return mouseTrailLength;
	}

	

	public int getSolvedCounter()
	{
		return solvedCounter;
	}

	

	@Override
	public void mouseClicked(MouseEvent e)
	{
		float x = SIZE * (e.getX() / (float)getWidth());
		float y = SIZE * (e.getY() / (float)getHeight());

		if (e.getButton() == MouseEvent.BUTTON1)
			maze[(int)x][(int)y] = new OpenCell();
		else
			maze[(int)x][(int)y] = new WallCell();
	}



	@Override
	public void mousePressed(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}
}
