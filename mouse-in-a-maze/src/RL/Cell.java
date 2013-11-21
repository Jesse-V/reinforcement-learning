/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL;

import java.awt.Color;

/**
 *
 * @author jesse
 */
public class Cell
{
	private float memoryOf = 1;
	
	
	Color getColor()
	{
		return new Color(memoryOf, memoryOf, memoryOf);
	}
	
	
	
	public void decayMemory()
	{
		memoryOf *= 0.5f;
	}
}
