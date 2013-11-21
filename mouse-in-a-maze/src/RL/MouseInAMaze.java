/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RL;

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
	public MouseInAMaze()
	{
		super("Mouse in a Maze - Reinforcement Learning in a Dynamic Environment");
        
		DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		setSize(new Dimension(new Dimension(displayMode.getWidth(), displayMode.getHeight())));
                
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);

		add(new Maze());
		setVisible(true);
		
		Thread repainter = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						repaint();
						Thread.sleep(33, 33); //30 fps
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		repainter.start();
	}
	
	
    
    public static void main(String[] args)
    {
        new MouseInAMaze();
    }
}
