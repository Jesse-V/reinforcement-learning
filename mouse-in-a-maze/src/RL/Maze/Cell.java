/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL.Maze;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author jesse
 */
public abstract class Cell
{
	public abstract void draw(Graphics g, Rectangle rectangle);
	public void update() {};
}
