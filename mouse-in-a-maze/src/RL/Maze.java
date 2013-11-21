/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author jesse
 */
public class Maze extends JPanel
{
	private static int SIZE = 32;
	Cell[][] grid = new Cell[SIZE][SIZE];
	
	
	public Maze()
	{
		for (int x = 0; x < SIZE; x++)
			for (int y = 0; y < SIZE; y++)
				grid[x][y] = new OpenCell();
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
		{
			for (int y = 0; y < SIZE; y++)
			{
				g.setColor(grid[x][y].getColor());
				g.fillRect((int)(x * ratioX), (int)(y * ratioY), (int)ratioX, (int)ratioY);
			}
		}
	}
}
