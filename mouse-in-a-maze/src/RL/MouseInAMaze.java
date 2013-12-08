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
		setVisible(false);
		
		Thread runLoop = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					final int SAMPLE_SIZE = 25000;
					int[] openCounts = new int[SAMPLE_SIZE];
					int[] exploredCounts = new int[SAMPLE_SIZE];
					int[] trailLengths = new int[SAMPLE_SIZE];
					
					for (int j = 0; j < SAMPLE_SIZE; j++)
					{
						maze.reset();
						
						while (true)
						{
							maze.update();
							
							if (maze.getSolvedCounter() == 1)
							{
								openCounts[j] = maze.getOpenCount();
								exploredCounts[j] = maze.getExploredCount();
							}

							if (maze.getSolvedCounter() == 2)
							{
								trailLengths[j] = maze.getMouseTrailLength();
								break;
							}
						}
					}
					
					for (int j = 0; j < SAMPLE_SIZE; j++)
						System.out.println(trailLengths[j] / (float)exploredCounts[j]);
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
