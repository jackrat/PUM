package com.jackrat.a01_android_ui;

public class Pierwiastki {
    public double A;
    public double B;
    public double C;
    public double Delta;
    public double[] Wynik;

    public void Oblicz() {
        Delta = B * B - 4 * A * C;
        if (Delta < 0)
            Wynik = new double[0];
        else if (Delta == 0) {
            Wynik = new double[1];
            Wynik[0] = -B / (2 * A);
        } else{
            Wynik = new double[2];
            Wynik[0] = (-B - Math.sqrt(Delta)) / (2 * A);
            Wynik[1] = (-B + Math.sqrt(Delta)) / (2 * A);
        }
    }
}
