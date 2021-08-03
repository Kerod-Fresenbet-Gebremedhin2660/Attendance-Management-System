package Game;

import Game.GamePanel;
import javax.swing.JFrame;

public class GameFrame extends JFrame
{
    private GamePanel game;
    
    public GameFrame()
    {
        // Add text to title bar 
        super("Space Intruders");
        
        // Make sure the program exits when the close button is clicked
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Create an instance of the Game class and turn on double buffering
        //  to ensure smooth animation
        game = new GamePanel();
        game.setDoubleBuffered(true);
        
        // Add the Breakout instance to this frame's content pane to display it
        this.getContentPane().add(game); 
        this.pack();
        this.setResizable(false);
        this.setSize(900, 900);
        //this.setLocationRelativeTo(null);
        
        // Start the game
        game.start();  
    }
}
