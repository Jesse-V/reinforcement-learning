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
public class Mouse extends Cell
{
	public static final Color COLOR = Color.GRAY;


	@Override
	public void draw(Graphics g, Rectangle rect)
	{
		g.setColor(COLOR);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
}
