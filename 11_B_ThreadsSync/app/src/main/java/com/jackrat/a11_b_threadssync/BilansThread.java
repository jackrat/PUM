package com.jackrat.a11_b_threadssync;

import android.util.Log;

public class BilansThread  extends  Thread{
    private Bilans b;
    private int count;
    private int wariant;

    public BilansThread(String name, Bilans aB, int aCount, int aWariant) {
        super(name);
        b = aB;
        count = aCount;
        wariant = aWariant;
        start();
    }

    public void run()
    {
        int wynik = 0;
        for (int i = 0; i < count; i++) {
            switch (wariant) {
                case 1:
                    wynik = b.bilansuj1();
                    break;
                case 2:
                    wynik = b.bilansuj2();
                    break;
                case 3:
                    wynik = b.bilansuj3();
                    break;
                case 4:
                    wynik = b.bilansuj4();
                    break;
            }
            if (wynik != 0) {
                Log.d("THR_N", getName() + " skończył po " + (i+1) + " cyklach z niezerowym wynikiem.");
                break;
            }
        }
        if(wynik == 0)
            Log.d("THR_N", getName() + " OK - zakończył się zerowym wynikiem.");
    }

}
