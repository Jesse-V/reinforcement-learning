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
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import javax.swing.JPanel;

/**
 *
 * @author jesse
 */
public class Maze extends JPanel
{
	private static int SIZE = 32;
	private Cell[][] grid = new Cell[SIZE][SIZE];
	private Random prng = new Random();
	
	
	public Maze()
	{
		for (int x = 0; x < SIZE; x++)
			for (int y = 0; y < SIZE; y++)
				grid[x][y] = new OpenCell();
		generate();
	}
	
	/*
		width = passed in int arg0
		height = passed in int arg1
		seed = random
	
		array = new int[height][width];
		N = 1, S = 2, E = 4, W = 8
		DX, DY, and Opposite are hashtables
		
		
	*/
	
	public void carve(int cx, int cy, int[][] grid)
	{
		System.out.println(cx + ", " + cy);
		char[] directions = { 'N', 'S', 'E', 'W' };
		for (int i = 0; i < directions.length; i++)
		{
			int pos = prng.nextInt(directions.length);
			char temp = directions[i];
			directions[i] = directions[pos];
			directions[pos] = temp;			
		}
		
		for (char direction : directions)
		{
			int nx = cx + getX(direction);
			int ny = cy + getY(direction);
			
			if (ny >= 0 && ny < SIZE && nx >= 0 && nx < SIZE && grid[ny][nx] == 0)
			{
				grid[cy][cx] |= (int)direction;
				grid[ny][nx] |= (int)getOpposite(direction);
				carve(nx, ny, grid);
			}
		}
	}
	
	
	
	private int getX(char c)
	{
		switch (c)
		{
			case 'E':
				return 1;
			case 'W':
				return -1;
			case 'N':
				return 0;
			case 'S':
				return 0;
		}
		
		throw new IllegalStateException();
	}
	
	
	
	private int getY(char c)
	{
		switch (c)
		{
			case 'E':
				return 0;
			case 'W':
				return 0;
			case 'N':
				return -1;
			case 'S':
				return 1;
		}
		
		throw new IllegalStateException();
	}
	
	
	
	private char getOpposite(char c)
	{
		switch (c)
		{
			case 'E':
				return 'W';
			case 'W':
				return 'E';
			case 'N':
				return 'S';
			case 'S':
				return 'N';
		}
		
		throw new IllegalStateException();
	}
	
	
	
	public void generate()
	{
		/*CellState[][] maze = new CellState[SIZE][SIZE];
		for (CellState[] row : maze)
			for (CellState cell : row)
				cell = CellState.EMPTY;
		
		int x = SIZE - 2, y = SIZE - 1;
		maze[x][y] = CellState.VISITED;
		
		Stack<Point> stack = new Stack<Point>();
		while (anyUnvisitedCells(maze))
		{
			ArrayList<Point> neighbors = new ArrayList<Point>();
			if (x + 2 < SIZE && maze[x + 2][y] == CellState.VISITED)
				neighbors.add(new Point(x + 2, y));
			if (x - 2 >= 0 && maze[x - 2][y] == CellState.VISITED)
				neighbors.add(new Point(x - 2, y));
			if (y + 2 < SIZE && maze[x][y + 2] == CellState.VISITED)
				neighbors.add(new Point(x, y + 2));
			if (y - 2 >= 0 && maze[x][y - 2] == CellState.VISITED)
				neighbors.add(new Point(x, y - 2));
			
			if (!neighbors.isEmpty())
			{
				Point randomNeighbor = neighbors.get(prng.nextInt(neighbors.size()));
				stack.push(new Point(x, y));
				maze[(randomNeighbor.x + x) / 2][randomNeighbor.y + y / 2] = CellState.EMPTY;
				maze[randomNeighbor.x][randomNeighbor.y] = CellState.VISITED;
				x = randomNeighbor.x;
				y = randomNeighbor.y;
			}
			else if (!stack.empty())
			{
				Point cell = stack.pop();
				x = cell.x;
				y = cell.y;
			}
			else
			{
				x = prng.nextInt(SIZE);
				y = prng.nextInt(SIZE);
				maze[x][y] = CellState.VISITED;
			}
		}*/
		
		//int[][] maze = new int[SIZE][SIZE];
		//carve(0, 0, maze);
		
		
		
		char[][] maze = DepthFirstMazeGen();
		for (int j = 0; j < SIZE; j++)
			for (int k = 0; k < SIZE; k++)
				grid[j][k] = maze[j][k] == 'X' ? new WallCell() : new OpenCell();
	}
	
	
	
	private boolean anyUnvisitedCells(CellState[][] maze)
	{
		for (CellState[] row : maze)
			for (CellState cell : row)
				if (cell == CellState.EMPTY)
					return true;
		return false;
	}
	
	
	
    public char[][] DepthFirstMazeGen()
	{
		char[][] maze = new char[SIZE][SIZE];
		for (int j = 0; j < SIZE; j++)
			for (int k = 0; k < SIZE; k++)
				maze[j][k] = 'X';
		
        Random myRand = new Random();
        Stack<Integer> stack = new Stack<Integer>();
		
        int x = myRand.nextInt(SIZE);
        while (x % 2 == 0)
            x = myRand.nextInt(SIZE);
        int y = myRand.nextInt(SIZE);
        while (y % 2 == 0)
            y = myRand.nextInt(SIZE);

        maze[x][y] = ' ';
        int total = (SIZE * SIZE) / 4;
        int visited = 1;
        int random[] = new int[4];
        int totalrand;

        while (visited < total)
		{
            totalrand = 0;
            if (x > 1 && maze[x - 2][y] == 'X')
                random[totalrand++] = 1;
            if (x < SIZE - 2 && maze[x + 2][y] == 'X')
                random[totalrand++] = 2;
            if (y > 1 && maze[x][y - 2] == 'X')
                random[totalrand++] = 3;
            if (y < SIZE - 2 && maze[x][y + 2] == 'X')
                random[totalrand++] = 4;

            if (totalrand > 0)
			{
                switch(random[myRand.nextInt(totalrand)])
				{
                    case 1: maze[x-2][y] = maze[x-1][y] = ' ';
                            x -= 2;
                            stack.push(x * SIZE + y);
                            visited++;
                            break;
                    case 2: maze[x+2][y] = maze[x+1][y] = ' ';
                            x += 2;
                            stack.push(x * SIZE + y);
                            visited++;
                            break;
                    case 3: maze[x][y-2] = maze[x][y-1] = ' ';
                            y -= 2;
                            stack.push(x * SIZE + y);
                            visited++;
                            break;
                    case 4: maze[x][y+2] = maze[x][y+1] = ' ';
                            y += 2;
                            stack.push(x * SIZE + y);
                            visited++;
                            break;
                }
            }
            else
			{
                int vert = stack.pop();
                x = vert / SIZE;
                y = vert % SIZE;
            }
        }
		
        return maze;
    }
	
	
	
	public void recurse(int x, int y)
	{
		grid[x][y] = new OpenCell();
		
		int neighbor = prng.nextInt(4);
		if (x - 2 >= 0 && neighbor == 0)
			recurse(x, y);
			
		//prng.nextInt(SIZE), prng.nextInt(SIZE)
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
				grid[x][y].draw(g, new Rectangle((int)(x * ratioX), (int)(y * ratioY), (int)ratioX, (int)ratioY));
	}
	
	
	
	private enum CellState
	{
		EMPTY, WALL, VISITED; 
	}
}
