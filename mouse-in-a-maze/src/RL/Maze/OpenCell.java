/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author jesse
 */
public class OpenCell extends Cell
{
	private float memoryOf;
	
	
	public OpenCell()
	{
		this(0f);
	}
	
	
	
	public OpenCell(float newMemory)
	{
		memoryOf = newMemory;
	}
	
	

	@Override
	public void draw(Graphics g, Rectangle rect)
	{
		g.setColor(Color.WHITE);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		
		g.setColor(Color.GREEN);
		
		int scaledX = (int)(memoryOf * rect.width);
		int scaledY = (int)(memoryOf * rect.height);
		g.fillOval((rect.width - scaledX) / 2 + rect.x, (rect.height - scaledY) / 2 + rect.y, scaledX, scaledY);

		//g.setColor(Color.RED);
		//g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}



	@Override
	public void update()
	{
		memoryOf *= 0.997f;
	}
	
	
	
	public float getMemoryOf()
	{
		return memoryOf;
	}
}
