package com.nmf.assessment.q1.provided;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;

class Helper {

    static final String Disney = "DIS";
    static final String Ibm = "IBM";
    static final String Nike = "NKE";
    static final String ExxonMobil = "XOM";
    static final String JohnsonJohnson = "JNJ";

    static final double tickSize = 0.01;

    public static Map<String, Double> startingPrices = new HashMap<>();
    static {
        startingPrices.put(Disney, 129.79);
        startingPrices.put(Ibm, 124.64);
        startingPrices.put(Nike, 111.51);
        startingPrices.put(ExxonMobil, 40.88);
        startingPrices.put(JohnsonJohnson, 152.06);
    }

    static double random(double floor, double ceil) {
        return floor + (Math.random() * (ceil - floor));
    }

    static int randomInt(int floor, int ceil) {
        return (int)Math.round(random(floor, ceil));
    }

    static double roundTo6SF(double num) {
        double roundingFactor = pow(10, 5 - Math.floor(Math.log10(Math.abs(num))));
        return Math.round(num * roundingFactor) / roundingFactor;
    }

    static double roundToTick(double price) {
        double quotient = price / tickSize;
        return roundTo6SF(Math.floor(quotient) * tickSize);
    }

    static double[] getBidAskFromMid(double mid) {
        return new double[] {roundToTick(mid-tickSize), roundToTick(mid+tickSize)};
    }


}

