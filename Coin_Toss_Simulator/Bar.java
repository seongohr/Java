// Name: Seongoh Ryoo

// we included the import statements for you
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Bar class
 * A labeled bar that can serve as a single bar in a bar graph.
 * The text for the label is centered under the bar.
 * 
 * NOTE: we have provided the public interface for this class. Do not change
 * the public interface. You can add private instance variables, constants,
 * and private methods to the class. You will also be completing the
 * implementation of the methods given.
 * 
 */
public class Bar {
   
   private int bottomNum;
   private int leftNum;
   private int widthNum;
   private int heightNum;
   private Color colorName;
   private String labelName;
   private double barScale;



   /**
      Creates a labeled bar.  You give the height of the bar in application
      units (e.g., population of a particular state), and then a scale for how
      tall to display it on the screen (parameter scale). 
  
      @param bottom  location of the bottom of the label
      @param left  location of the left side of the bar
      @param width  width of the bar (in pixels)
      @param barHeight  height of the bar in application units
      @param scale  how many pixels per application unit
      @param color  the color of the bar
      @param label  the label at the bottom of the bar
   */
   public Bar(int bottom, int left, int width, int barHeight,
              double scale, Color color, String label) {
      bottomNum = bottom;
      leftNum = left;
      widthNum = width;
      heightNum = barHeight;
      colorName = color;
      labelName = label;
      barScale = scale;
   }
      
   /**
      Draw the labeled bar. 
      @param g2  the graphics context
   */
   public void draw(Graphics2D g2) {
      final int VERT_BUFFER = 20;//vertical buffer between the wall and the bar graph - fixed value
      final int BAR_WIDTH = 50;//the width of the bar(in pixel) - fixed value
      
      //Draw a label
      Font font = g2.getFont();
      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D labelBounds = font.getStringBounds(labelName, context);
      int widthOfLabel = (int)Math.round(labelBounds.getWidth());
      int heightOfLabel = (int)Math.round(labelBounds.getHeight());
      g2.setColor(Color.BLACK);
      g2.drawString(labelName, leftNum + BAR_WIDTH/2 - widthOfLabel/2 , bottomNum ); 
      
      //compute the height of each bar graph = how many pixels per application unit * height of the bar in application units
      int realHeight = (int)Math.round(barScale * heightNum); 
      int startRect = bottomNum - heightOfLabel - realHeight; //starting point of the rectangle when drawing
                  
      //Draw a bar
      Rectangle bar = new Rectangle(leftNum ,startRect, widthNum, realHeight);
      g2.setColor(colorName);
      g2.fill(bar);
   }
}
