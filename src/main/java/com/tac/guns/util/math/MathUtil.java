package com.tac.guns.util.math;

public class MathUtil {
    public static double magnificationToFovMultiplier(double magnification, double currentFov){
        double currentTan = Math.tan(Math.toRadians(currentFov / 2));
        double newTan = currentTan / magnification;
        double newFov = Math.toDegrees(Math.atan(newTan)) * 2;
        return newFov / currentFov;
    }
}
