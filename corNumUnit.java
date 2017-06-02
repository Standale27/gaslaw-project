public class corNumUnit
{
   public double corNumber;
   public String corUnit;
      
   public corNumUnit(Double num, String un)
   {
      this.corNumber = num;
      this.corUnit = un;
   } 
   
   public String toString()
   {
      return corNumber + corUnit;
   }
}