/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RL;

import RL.Maze.Maze;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

/**
 *
 * @author jesse
 */
public class MouseInAMaze extends JFrame
{
	private final Maze maze = new Maze();
	
	
	public MouseInAMaze()
	{
		super("Mouse in a Maze - Reinforcement Learning in a Dynamic Environment");
        
		DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		setSize(displayMode.getWidth(), displayMode.getHeight());
		setLocation(0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		add(maze);
		setVisible(true);
		
		Thread runLoop = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						maze.update();
						maze.repaint();
						Thread.sleep(100, 100);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		runLoop.start();
	}
	
	
    
    public static void main(String[] args)
    {
        new MouseInAMaze();
    }
}
