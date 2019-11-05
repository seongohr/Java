// Name: Seongoh Ryoo

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


/**
 * CoinSimComponent class
 *
 * Call the method simulating coin toss trials by the input of the number of trials
 * override paintComponent to draw bar graph whenever the window is resized
 * 
 */
public class CoinSimComponent extends JComponent
{  
   private int twoHeads;
   private int headTails;
   private int twoTails;
   private int trialsIn;
   private double actualTrials;
   private String label1;
   private String label2;
   private String label3;

    /**
    Creates a CoinTossSimulator, run simulation and get the results 
    create a the contents of the label under the bar
  
    @param inputTrials  number of coin toss trials received from the keyboard input
   */   
   public CoinSimComponent(int inputTrials)
   {
      trialsIn = inputTrials;
      
      CoinTossSimulator simulator = new CoinTossSimulator();
      simulator.run(trialsIn);
      twoHeads = simulator.getTwoHeads();
      headTails = simulator.getHeadTails();
      twoTails = simulator.getTwoTails();
      actualTrials = simulator.getNumTrials();

      //label including the number of results from the simulation
      label1 = labelMaker("Two Heads : ", twoHeads);
      label2 = labelMaker("A Head and a Tail : ", headTails);
      label3 = labelMaker("Two Tails : ", twoTails);
         
   }
    

   /**
    percentage of each case from the total number of trials
    @param numerator  the number each case (Two Heads, A Head and a Tail, Two Tails) 
    
   */    
   private String calPercent(int numerator)
   {
      String percent = "(" + Integer.toString((int)Math.round( numerator / actualTrials * 100 )) + "%)";
      return percent;
   }
   
   /**
    A complete contents of the label.
    @param title  the name of the case (Two Heads, A Head and a Tail, Two Tails)
    @param result  the number of each case from coin toss trials
    ex) Two Heads : 32(30%)
  
   */    
   private String labelMaker(String title, int result)
   {
      String labelResult = title + Integer.toString(result) + calPercent(result);
      return labelResult;
   }
   
    /**
    Override paintComponent to draw a bar graph whenever the window is resized
    @param g  graphics context
    
   */ 
   public void paintComponent(Graphics g)
   {  
      final int BAR_WIDTH = 50; //the width of the bar(in pixel) - fixed value 
      final int VERT_BUFFER = 20; //vertical buffer between the wall and the bar graph - fixed value
      
      //the color of each bar graph  - fixed
      final Color COLOR1 = Color.RED;
      final Color COLOR2 = Color.GREEN;
      final Color COLOR3 = Color.BLUE;
   
      Graphics2D g2 = (Graphics2D) g;
      
      //get window size
      double winHeight = getHeight();
      double winWidth = getWidth();
      
      //width between the bars and walls
      double width = (winWidth - 3 * BAR_WIDTH) / 4;
      
      //location of the left side of the bar
      int left1 = (int)Math.round(width);
      int left2 = (int)Math.round((2 * width) + BAR_WIDTH);
      int left3 = (int)Math.round(3 * width + 2 * BAR_WIDTH);
      
      //location of the bottom of the label    
      int bottom = (int)Math.round(winHeight - VERT_BUFFER);
      
      //compute height of label
      Font font = g2.getFont();
      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D labelBounds = font.getStringBounds(label1, context);
      int heightOfLabel = (int)Math.round(labelBounds.getHeight());
      
      //compute scale - how many pixels per application unit
      double scale = (winHeight - 2 * VERT_BUFFER - heightOfLabel)/actualTrials;

      //create 3 bar
      Bar bar1 = new Bar(bottom, left1, BAR_WIDTH, twoHeads, scale , COLOR1, label1);
      Bar bar2 = new Bar(bottom, left2, BAR_WIDTH, headTails, scale , COLOR2, label2);
      Bar bar3 = new Bar(bottom, left3, BAR_WIDTH, twoTails, scale , COLOR3, label3);
      
      //call draw method to draw 3 bars
      bar1.draw(g2);
      bar2.draw(g2); 
      bar3.draw(g2);
   }
}

