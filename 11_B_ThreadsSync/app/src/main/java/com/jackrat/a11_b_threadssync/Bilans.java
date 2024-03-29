package com.jackrat.a11_b_threadssync;

import java.util.concurrent.locks.Lock;

public class Bilans {
    private int liczba = 0;

    public void zeruj() {
        liczba = 0;
    }


    private final static Object lockObj1 = new Object();
    //Dla klasy Lock wymagana deklaracja wersji Javy >1.5 w File->Project Structure->Modules->Source/Target Compatibility
    private Lock lockObj2;

    public void setLock(Lock alock) {
        lockObj2 = alock;
    }


    //wersja całkiem zła
    public int bilansuj1() {
        liczba++;
        liczba--;
        return liczba;
    }


    //wersja zła bo wątek w sekcji kryt. może podmienić wartość wątkowi (lub wątkom) który robi już return
    public int bilansuj2() {
        synchronized (lockObj1) {
            liczba++;
            liczba--;
        }
        return liczba;
    }


    //wersja OK
    public synchronized int bilansuj3() {
        liczba++;
        liczba--;
        return liczba;
    }

    //wersja OK
    public int bilansuj4() {
        lockObj2.lock();
        try {
            liczba++;
            liczba--;
            return liczba;
        } finally {
            lockObj2.unlock();
        }

    }
}
