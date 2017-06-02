import java.awt.*;
import javax.swing.*;  
import java.awt.event.*;

public class ParticleSimulator extends KineticSimulator implements ActionListener
{
   private KineticSimulator kS;
   
   private int window_Width = 425, window_Height = 450;

   public ParticleSimulator()
   {
      setSize(window_Width, window_Height);
      setTitle("Kinetics Simulator");
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
      //set up bufferedImage to update window
   }
      
   @Override
   public void paint(Graphics g)
   {
      g.setColor(Color.GRAY);
      g.fillRect(0, 0, window_Width - 6, window_Height - 5);
      
      g.setColor(Color.BLACK);
      g.fillRect(12, 35, 400, 400);
      
      g.setColor(Color.WHITE);
      g.fillRect(22, 45, 380, 380);
   }
   
   public void actionPerformed(ActionEvent e)
   {
   
   }
     
}