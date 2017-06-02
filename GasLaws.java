public class GasLaws
{
   private corNumUnit pressure1, pressure2, volume1, volume2, temperature1, temperature2, moles1, moles2, rate1, rate2, molMass1, molMass2;
   private String tempStr, unit;
   private int tempInt;
   private double tempDbl, tempAnswer;
   private corNumUnit corAnswer;
   final double RCONSTANT = 0.0821;
   
   public GasLaws()
   {
      pressure1 = new corNumUnit(0.0, "atm");
      pressure2 = new corNumUnit(0.0, "atm");
      volume1 = new corNumUnit(0.0, "l");
      volume2 = new corNumUnit(0.0, "l");
      temperature1 = new corNumUnit(0.0, "k");
      temperature2 = new corNumUnit(0.0, "k");
      moles1 = new corNumUnit(0.0, "mol");
      moles2 = new corNumUnit(0.0, "mol");
      rate1 = new corNumUnit(0.0, "r");
      rate2 = new corNumUnit(0.0, "r");
      molMass1 = new corNumUnit(0.0, "g/mol");
      molMass2 = new corNumUnit(0.0, "g/mol");
   }
   
   public corNumUnit separate(String str)  //separates the input from KinematicSimulator into a usable form of numbers and units
   {
      tempInt = 0;
      tempAnswer = 0;
      
      for(int i = 0; i < str.length(); i++)
      {
         if((str.charAt(i) <= 57 && str.charAt(i) >= 46) && str.charAt(i) != 47)
         {
            tempInt++;
         }
      }
      tempAnswer = Double.parseDouble(str.substring(0, tempInt));
      tempStr = str.substring(tempInt, str.length());
      
      return this.convert(tempAnswer, tempStr);
   }
   
   public corNumUnit convert(Double before, String temp) //converts numbers to standard units if needed
   {
      tempAnswer = 0;
      tempDbl = before;
      tempStr = temp;
      
      switch (tempStr) {
         case "atm": //pressure
            unit = tempStr;
            tempAnswer = tempDbl;
            break;
         case "pa":
            tempAnswer = tempDbl / 101325;
            unit = "atm";
            break;
         case "kpa":
            tempAnswer = tempDbl / 101.325;
            unit = "atm";
            break;
         case "torr":
            tempAnswer = tempDbl / 760.0;
            unit = "atm";
            break;
         case "mmhg":
            tempAnswer = tempDbl / 760.0;
            unit = "atm";
            break;
         case "psi":
            tempAnswer = tempDbl / 14.6959;
            unit = "atm";
            break;
         case "ml": //volume
            tempAnswer = tempDbl / 1000;
            unit = "l";
            break;
         case "l" :
            tempAnswer = tempDbl;
            unit = tempStr;
            break;   
         case "c": //temperature
            tempAnswer = tempDbl + 273.15;
            unit = "k";
            break;
         case "f":
            tempAnswer = (((tempDbl - 32.0) * 5.0) / 9.0) + 273.15;
            unit = "k";   
            break;
         case "k":
            tempAnswer = tempDbl;
            unit = tempStr;
            break;   
         case "mol": //moles
            tempAnswer = tempDbl;
            unit = tempStr;
            break;
         case "r": //rates
            tempAnswer = tempDbl;
            unit = tempStr;
            break;
         case "g/mol": //molar mass
            tempAnswer = tempDbl;
            unit = tempStr;
            break;   
         default:
            tempAnswer = -1;
            unit = ": error in unit choice";  
            break;
      }
      
      corAnswer = new corNumUnit(tempAnswer, unit);
      System.out.println(corAnswer.toString());
      return corAnswer;
   }
   
   public corNumUnit gasLawBoyle(String p1, String p2, String v1, String v2)
   {
      if(p1 != "" && p2 != "" && v1 != "" && v2 != "")
      {
      
         pressure1 = this.separate(p1);
         pressure2 = this.separate(p2);
         volume1 = this.separate(v1);
         volume2 = this.separate(v2);
      }
      else if(p1 == "" && (p2 != "" && v1 != "" && v2 != ""))
      {
      
      }
      else if(p2 == "" && (p1 != "" && v1 != "" && v2 != ""))
      {
      
      }
      else if(v1 == "" && !(p1 != "" && p2 != "" && v2 != ""))
      {
      
      }
      else if(v2 == "" && !(p1 != "" && p2 != "" && v1 != ""))
      {
         System.out.println("fucku");
      }
      else
      {
      
      }
      
      //corAnswer = pressure1 * volume1;
      return corAnswer;
   }
}