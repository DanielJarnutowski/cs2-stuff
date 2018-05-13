/*
Daniel Jarnutowski

email: naruto016@live.com
*/
import javax.swing.JFrame;

public class Graph
{
   public static void main (String[] args)
   {
      JFrame frame = new JFrame ("Directed Graph");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add (new GraphPanel());

      frame.pack();
      frame.setVisible(true);
   }
}
