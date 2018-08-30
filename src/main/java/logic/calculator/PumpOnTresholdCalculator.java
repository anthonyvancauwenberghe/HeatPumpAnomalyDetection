package logic.calculator;

import abstracts.DataPoint;
import models.HeatPumpDataPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PumpOnTresholdCalculator {

    private List<HeatPumpDataPoint> dataPoints = new ArrayList<>();

    public PumpOnTresholdCalculator(List<HeatPumpDataPoint> dataPoints) {
        this.dataPoints.addAll(dataPoints);
        dataPoints.sort(Comparator.comparingInt(dataPoint -> dataPoint.value));
    }

    public int calculate() {

        Collections.reverse(dataPoints);

        int averageSum = 0;
        int size = dataPoints.size() / 5;
        for (int i = 0; i < size; i++) {
            averageSum += dataPoints.get(i).value;
        }


        double highestTwentyPercentileAverage = averageSum / size;
        int[] tresholdArray = new int[100];

        Collections.reverse(dataPoints);

        int[] lowestPercentileAmountArray = new int[1 + (int) highestTwentyPercentileAverage / 5];

        for (DataPoint dataPoint : dataPoints) {
            double value = (double) (dataPoint.value);

            double indexValue = value / ((highestTwentyPercentileAverage / 5));
            int index = (int) Math.round(indexValue);

            if (value < (highestTwentyPercentileAverage / 5)) {
                lowestPercentileAmountArray[(int) value]++;
            }

            tresholdArray[index]++;
        }

        int tresholdValue = 0;
        int totalSumAmount = 0;
        for (int i = 0; i < lowestPercentileAmountArray.length; i++) {
            totalSumAmount += lowestPercentileAmountArray[i];
        }

        double amountToExceed = totalSumAmount * 0.995;
        double amountCounter = 0;
        for (int i = 0; i < lowestPercentileAmountArray.length; i++) {
            amountCounter += lowestPercentileAmountArray[i];
            if (amountCounter >= amountToExceed) {
                tresholdValue = i;
                break;
            }
        }

        return tresholdValue;
    }
}