import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Particle extends KineticSimulator
{
   private static int part_Width = 30, part_Height = 30;
   private ParticleSimulator pS;
   private int x, y, xa =2, ya = 2;
   
   public Particle(ParticleSimulator pS)
   {
      this.pS = pS;
      x = (int)(Math.random() * ((pS.getWidth() - 45) - 22) + 1) + 22);
      y = (int)(Math.random() * (pS.getHeight() - 0));
   }  
   
   public void update()
   {
      x += xa;
      y += ya;
      if(x < 0)
      {
         
   }
}