import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.List;

public class KineticSimulator extends JFrame implements ItemListener, ActionListener
{
   private static int WIN_WIDTH = 700, WIN_HEIGHT = 500; //definite size of the control panel
   private static ParticleSimulator pS; //for the particle simulator, or visual display
   private static KineticSimulator kS;
   private static GasLaws gasLawCalc;
   private static JTextField pres1, pres2, vol1, vol2, temp1, temp2, mol1, mol2, rate1, rate2, molMass1, molMass2; //for the input, able to be activated/deactivated
   private static JTextArea instruct; //instructions for using the textfields
   private static ButtonGroup group;
   private static JButton calcAndSim; //for the button to calculate the equation with the given variables & simulate the formula
   private static JRadioButtonMenuItem bLaw, cLaw, gLLaw, cGE, aLaw, iGE, dLPP, gLED, vDWE; //menu buttons for selecting the laws to be used
                                    /* Boyle's Law
                                       Charles' Law
                                       Gay-Lussac's Law
                                       Combined Gas Law Equation
                                       Avogadro's Law
                                       Ideal Gas Equation
                                       Dalton's Law of Partial Pressures
                                       Graham's Law of Effusion/Diffusion
                                       Van der Waal's Equation */ 
   
   public JMenuBar createMenuBar() //for creating the menu bar in which the laws are selected through radio buttons
   {
      JMenuBar menuBar;
      JMenu menu;  
                           
      menuBar = new JMenuBar();
      menu = new JMenu("Laws/Equations"); //name of the menu
      menu.getAccessibleContext().setAccessibleDescription("Full of laws/equations to calculate with.");
      menuBar.add(menu);
      
      group = new ButtonGroup(); //so only one of these buttons can be active at a time
      bLaw = new JRadioButtonMenuItem("Boyle's Law | P1V1 = P2V2");
      cLaw = new JRadioButtonMenuItem("Charles' Law | V1/T1 = V2/T2");
      gLLaw = new JRadioButtonMenuItem("Gay-Lussac's Law | P1/T1 = P2/T2");
      cGE = new JRadioButtonMenuItem("Combined Gas Law Equation | (P1V1)/T1 = (P2V2)/T2");
      aLaw = new JRadioButtonMenuItem("Avogadro's Law | V1/n1 = V2/n2"); 
      iGE = new JRadioButtonMenuItem("Ideal Gas Equation | PV = nRT");
      dLPP = new JRadioButtonMenuItem("Dalton's Law of Partial Pressures | Ptotal = P1 + P2 + P3 + ...");
      gLED = new JRadioButtonMenuItem("Graham's Law of Effusion/Diffusion | r1/r2 = sqrt(M2/M1)");
      vDWE = new JRadioButtonMenuItem("Van der Waal's Equation | (P + an^2/v^2)(V - nb) = nRT");
      
      group.add(bLaw);
      group.add(cLaw);
      group.add(gLLaw);
      group.add(cGE);
      group.add(aLaw);
      group.add(iGE);
      group.add(dLPP);
      group.add(gLED);
      group.add(vDWE);
         
      menu.add(bLaw); //adding the buttons to the menu
      menu.add(cLaw);
      menu.add(gLLaw);
      menu.add(cGE);
      menu.add(aLaw);
      menu.add(iGE);
      menu.add(dLPP);
      menu.add(gLED);
      menu.add(vDWE);
      
      bLaw.addItemListener(this); //linking the buttons to the ItemListener
      cLaw.addItemListener(this);
      gLLaw.addItemListener(this);
      cGE.addItemListener(this);
      aLaw.addItemListener(this);
      iGE.addItemListener(this);
      dLPP.addItemListener(this);
      gLED.addItemListener(this);
      vDWE.addItemListener(this);
          
      return menuBar; //return the finished menu that has been constructed
   }
   
   public Container createContentPane()
   {
      JPanel contentPane = new JPanel(new BorderLayout());
      contentPane.setOpaque(true);
      
      return contentPane;      
   }
   
   public void createAndShowGUI() //actually constructing the control panel
   { 
      JFrame frame = new JFrame("Kinetics Simulator"); //name of the program, identical to the one for ParticleSimulator
      frame.setSize(WIN_WIDTH, WIN_HEIGHT);
      frame.setBackground(Color.WHITE);
      frame.setResizable(false);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
       
      frame.setContentPane(this.createContentPane());
     
      JPanel inputValuePanel = new JPanel(new GridLayout(24, 1)); //setting up the panel for the textfields and labels
      JPanel instructPanel = new JPanel(); //setting up panel for instructions to the user
      JPanel calcSimPanel = new JPanel(); //setting up panel for calculating & simulating formula
      
      pres1 = new JTextField(); //process of making the textfields and their corresponding labels
      JLabel initPres = new JLabel("Initial Pressure");
      
      pres2 = new JTextField();
      JLabel finalPres = new JLabel("Final Pressure");
      
      vol1 = new JTextField();
      JLabel initVol = new JLabel("Initial Volume");
      
      vol2 = new JTextField();
      JLabel finalVol = new JLabel("Final Volume");
      
      temp1 = new JTextField();
      JLabel initTemp = new JLabel("Initial Temperature");
      
      temp2 = new JTextField();
      JLabel finalTemp = new JLabel("Final Temperature");
      
      mol1 = new JTextField();
      JLabel initMol = new JLabel("Initial/First Moles");
      
      mol2 = new JTextField();
      JLabel finalMol = new JLabel("Final/Second Moles");
      
      rate1 = new JTextField();
      JLabel firstRate = new JLabel("First Rate of Effusion/Diffusion");
      
      rate2 = new JTextField();
      JLabel secondRate = new JLabel("Second Rate of Effusion/Diffusion");
      
      molMass1 = new JTextField();
      JLabel firstMolMass = new JLabel("First Molar Mass");
      
      molMass2 = new JTextField();
      JLabel secondMolMass = new JLabel("Second Molar Mass");
      
      pres1.setEditable(false); //making all the textfields non-editable from the start so a law HAS to be picked.
      pres2.setEditable(false);
      vol1.setEditable(false);
      vol2.setEditable(false);
      temp1.setEditable(false);
      temp2.setEditable(false);
      mol1.setEditable(false);
      mol2.setEditable(false);
      rate1.setEditable(false);
      rate2.setEditable(false);
      molMass1.setEditable(false);
      molMass2.setEditable(false);
      
      String text = "Select an equation from the drop-down menu to begin. The convention for inputting values is:\n\n1.094atm | (Pressure)\n183mmHg | (Pressure)\n430.3K | (Temperature)\n100.67C | (Temperature)\n65mol | (Moles)\n16g/mol | (Molar Mass)\n567mL | (Volume)\n3.4L | (Volume)\n2.34r (Rate)\n\nMost units can be used, although the standard that will definitely work is: Atm, L, K, Mol, R, G/Mol";
      
      instruct = new JTextArea(2, 20); //setting up a text area to be used to convey instructions to the user
      instruct.setText(text);
      instruct.setWrapStyleWord(true);
      instruct.setLineWrap(true);
      instruct.setOpaque(false);
      instruct.setEditable(false);
      instruct.setFocusable(false);
      instruct.setBackground(UIManager.getColor("Label.background"));
      instruct.setFont(UIManager.getFont("Label.font"));
      instruct.setBorder(UIManager.getBorder("Label.border"));
      
      instructPanel.add(instruct);
      
      calcAndSim = new JButton("Calculate/Simulate"); //setting up a button for calculating/simulating the equation
      calcSimPanel.add(calcAndSim);
      calcAndSim.setEnabled(false);
      calcAndSim.addActionListener(this);
         
      inputValuePanel.add(initPres); //adding the labels & textfields to the appropriate panel
      inputValuePanel.add(pres1);
      inputValuePanel.add(finalPres);
      inputValuePanel.add(pres2);
      inputValuePanel.add(initVol);
      inputValuePanel.add(vol1);
      inputValuePanel.add(finalVol);
      inputValuePanel.add(vol2);
      inputValuePanel.add(initTemp);
      inputValuePanel.add(temp1);
      inputValuePanel.add(finalTemp);
      inputValuePanel.add(temp2);
      inputValuePanel.add(initMol);
      inputValuePanel.add(mol1);
      inputValuePanel.add(finalMol);
      inputValuePanel.add(mol2);
      inputValuePanel.add(firstRate);
      inputValuePanel.add(rate1);
      inputValuePanel.add(secondRate);
      inputValuePanel.add(rate2);
      inputValuePanel.add(firstMolMass);
      inputValuePanel.add(molMass1);
      inputValuePanel.add(secondMolMass);
      inputValuePanel.add(molMass2);
   
      frame.setJMenuBar(this.createMenuBar()); //makes the menu bar
     
      frame.getContentPane().add(inputValuePanel, BorderLayout.WEST);
      frame.getContentPane().add(instructPanel, BorderLayout.CENTER);
      frame.getContentPane().add(calcSimPanel, BorderLayout.EAST);
      
      frame.setSize(WIN_HEIGHT, WIN_WIDTH); //resets the window size so the menubar always shows without having to resize (bug)
      frame.setSize(WIN_WIDTH, WIN_HEIGHT);
   }   
   
   public static void main(String[] args)
   {
      gasLawCalc = new GasLaws();
      pS = new ParticleSimulator();
      kS = new KineticSimulator();
      kS.createAndShowGUI();
   }
   
   public void itemStateChanged(ItemEvent e)
   {   
      if(e.getItem() == bLaw)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(true);
            pres2.setEditable(true);
            vol1.setEditable(true);
            vol2.setEditable(true);
            temp1.setEditable(false);
            temp2.setEditable(false);
            mol1.setEditable(false);
            mol2.setEditable(false);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 3 known variables into the text fields if you want to calculate the value of the fourth and simulate the equation. Otherwise, put all 4 variables in to simulate the equation.\n\nPressure and Volume share an inverse relationship, meaning that as the volume of a system decreases or increases, the pressure of the system does the opposite.");
         } 
      }     
      if(e.getItem() == cLaw)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(false);
            pres2.setEditable(false);
            vol1.setEditable(true);
            vol2.setEditable(true);
            temp1.setEditable(true);
            temp2.setEditable(true);
            mol1.setEditable(false);
            mol2.setEditable(false);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 3 known variables into the text fields if you want to calculate the value of the fourth and simulate the equation. Otherwise, put all 4 variables in to simulate the equation.\n\nVolume and Temperature share a direct relationship, meaning that as the volume of a system decreases or increases, the temperature of the system does the same.");
         }
      }
      if(e.getItem() == gLLaw)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(true);
            pres2.setEditable(true);
            vol1.setEditable(false);
            vol2.setEditable(false);
            temp1.setEditable(true);
            temp2.setEditable(true);
            mol1.setEditable(false);
            mol2.setEditable(false);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 3 known variables into the text fields if you want to calculate the value of the fourth and simulate the equation. Otherwise, put all 4 variables in to simulate the equation.\n\nPressure and Temperature share a direct relationship, meaning that as the temperature of a system decreases or increases, the pressure does the same.");
         }
      }   
      if(e.getItem() == cGE)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(true);
            pres2.setEditable(true);
            vol1.setEditable(true);
            vol2.setEditable(true);
            temp1.setEditable(true);
            temp2.setEditable(true);
            mol1.setEditable(false);
            mol2.setEditable(false);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 5 known variables into the text fields if you want to calculate the value of the sixth and simulate the equation. Otherwise, put all 6 variables in to simulate the equation.\n\nThis equation combines Boyle's Law, Charles' Law, and Gay-Lussac's Law. ");
         }
      }   
      if(e.getItem() == aLaw)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(false);
            pres2.setEditable(false);
            vol1.setEditable(true);
            vol2.setEditable(true);
            temp1.setEditable(false);
            temp2.setEditable(false);
            mol1.setEditable(true);
            mol2.setEditable(true);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 3 known variables into the text fields if you want to calculate the value of the fourth and simulate the equation. Otherwise, put all 4 variables in to simulate the equation.\n\nVolume and the number of moles of gas in a system have a direct relationship, meaning that as the number of moles of gas in the system increases or decreases, the volume does the same.");
         }
      }   
      if(e.getItem() == iGE)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(true);
            pres2.setEditable(false);
            vol1.setEditable(true);
            vol2.setEditable(false);
            temp1.setEditable(true);
            temp2.setEditable(false);
            mol1.setEditable(true);
            mol2.setEditable(false);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 3 known variables into the text fields if you want to calculate the value of the fourth and simulate the equation. Otherwise, put all 4 variables in to simulate the equation.\n\nThe Ideal Gas Equation allows one to determine almost any information about the system. The Ideal Gas Constant (0.0821L*atm/mol*K) is used to accomplish this.");
         }
      }   
      if(e.getItem() == dLPP)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(true);
            pres2.setEditable(true);
            vol1.setEditable(false);
            vol2.setEditable(false);
            temp1.setEditable(false);
            temp2.setEditable(false);
            mol1.setEditable(false);
            mol2.setEditable(false);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("The total pressure of the system should be inputted in the top textfield, and the partial pressures of the system should be listed in the second field like:\n\n1.4atm + 3.5atm + 432mmHg + xatm\n\nWhere xatm is the unknown partial pressure that is to be solved for. There is no simulation for this equation.");
         }
      }   
      if(e.getItem() == gLED)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(false);
            pres2.setEditable(false);
            vol1.setEditable(false);
            vol2.setEditable(false);
            temp1.setEditable(false);
            temp2.setEditable(false);
            mol1.setEditable(false);
            mol2.setEditable(false);
            rate1.setEditable(true);
            rate2.setEditable(true);
            molMass1.setEditable(true);
            molMass2.setEditable(true);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 3 known variables into the text fields if you want to calculate the value of the fourth and simulate the equation. Otherwise, put all 4 variables in to simulate the equation.\n\nGraham's Law of Effusion/Diffusion allows one to determine how much faster/slower a gas is than another gas at effusing/diffusing into/out-of a system.");
         }
      }
      if(e.getItem() == vDWE)
      {
         if(e.getStateChange() == ItemEvent.SELECTED)
         {
            pres1.setEditable(true);
            pres2.setEditable(false);
            vol1.setEditable(true);
            vol2.setEditable(false);
            temp1.setEditable(true);
            temp2.setEditable(false);
            mol1.setEditable(true);
            mol2.setEditable(false);
            rate1.setEditable(false);
            rate2.setEditable(false);
            molMass1.setEditable(false);
            molMass2.setEditable(false);
            
            calcAndSim.setEnabled(true);
            
            instruct.setText("Put 3 known variables into the text fields if you want to calculate the value of the fourth and simulate the equation. Otherwise, put all 4 variables in to simulate the equation.\n\nThe Van der Waals Equation is a modification of the Ideal Gas Equation. This accounts for the attraction between the gas particles, and the volume of the particles themselves. 2 gas constants are needed to perform this equation, and the Van der Waals equation approaches the Ideal Gas Law PV=nRT as the values of these constants approach zero. ");
         }
      }   
   }  
   
   public void actionPerformed(ActionEvent e)
   {
      for(int i = 0; i < 9; i++)
      {
         switch(i)
         {
            case 0:  
               if(bLaw.isSelected())
               {
                  gasLawCalc.gasLawBoyle(pres1.getText().toLowerCase(), pres2.getText().toLowerCase(), vol1.getText().toLowerCase(), vol2.getText().toLowerCase());
               }   
               break;
                        
            case 1: 
               if(cLaw.isSelected())
               {
                  vol1.getText().toLowerCase();
                  vol2.getText().toLowerCase();
                  temp1.getText().toLowerCase();
                  temp2.getText().toLowerCase();
               }
               break;
                        
            case 2:
               if(gLLaw.isSelected())
               {
                  pres1.getText().toLowerCase();
                  pres2.getText().toLowerCase();
                  temp1.getText().toLowerCase();
                  temp2.getText().toLowerCase();
               }
               break;
                        
            case 3:
               if(cGE.isSelected())
               {
                  pres1.getText().toLowerCase();
                  pres2.getText().toLowerCase();
                  vol1.getText().toLowerCase();
                  vol2.getText().toLowerCase();
                  temp1.getText().toLowerCase();
                  temp2.getText().toLowerCase();
               }
               break;
                        
            case 4:
               if(aLaw.isSelected())
               {
                  vol1.getText().toLowerCase();
                  vol2.getText().toLowerCase();
                  mol1.getText().toLowerCase();
                  mol2.getText().toLowerCase();
               }   
               break;
                        
            case 5:
               if(iGE.isSelected())
               {
                  pres1.getText().toLowerCase();
                  vol1.getText().toLowerCase();
                  mol1.getText().toLowerCase();
                  temp1.getText().toLowerCase();
               }   
               break;
                        
            case 6:
               if(dLPP.isSelected())
               {
                  pres1.getText().toLowerCase();
                  pres2.getText().toLowerCase();
               }   
               break;
                        
            case 7:
               if(gLED.isSelected())
               {
                  rate1.getText().toLowerCase();
                  rate2.getText().toLowerCase();
                  molMass1.getText().toLowerCase();
                  molMass2.getText().toLowerCase();
               }   
               break;
                        
            case 8: 
               if(vDWE.isSelected())
               {
                  pres1.getText().toLowerCase();
                  vol1.getText().toLowerCase();
                  mol1.getText().toLowerCase();
                  temp1.getText().toLowerCase();
               }   
               break;
         }
      }   
   }     
}
