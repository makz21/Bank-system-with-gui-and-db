package sample;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RoundDownMoney {

    public double roundTwoDecimal(double doubleToRound){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        String newDouble = String.valueOf(doubleToRound);
        Double newValue = Double.parseDouble(newDouble);
        newDouble = df.format(newValue);
        newDouble = newDouble.replace(",", ".");
        newValue = Double.parseDouble(newDouble);
        return newValue;
    }
}
