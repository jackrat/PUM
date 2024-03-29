package com.jackrat.a11_b_threadssync;

import android.util.Log;

import java.util.Date;


/*
Zmienne volatile
Kompilator może podjąć decyzję, by aktualna wartość zmiennej przez pewien czas była przechowywana w rejestrze.
Może też zdecydować o zmianie kolejności operacji na zmiennych. Nie wpływa to na wynik programu sekwencyjnego,
ale może zmienić wynik programu, w którym ze zmiennej korzystają współbieżne wątki.

Zadeklarowanie zmiennej jako volatile informuje kompilator, że wątki odczytujące wartość takiej zmiennej
zawsze powinny widzieć jej aktualną wartość i że powinna być zachowana kolejność operacji
na tej zmiennej wynikająca z kodu programu.

Można powiedzieć, że zmienne volatile wprowadzają do programów współbieżnych słabą synchronizację
- bez możliwości zawieszenia wątku.

Model pamięci języka Java gwarantuje atomowość operacji odczytu i zapisu zmiennych,
ale tylko dla typów o rozmiarze do 32-bitów włącznie. W przypadku typów 64-bitowych,
jak double lub long odczyt i zapis zmiennej bywa dzielony na dwie operacje 32-bitowe,
co może wpłynąć na wynik programu współbieżnego.
Zastosowanie modyfikatora volatile w deklaracji zmiennej gwarantuje, że do tego nie dojdzie.
 */


public class VolatileExample {

    //OK
    //private volatile boolean isDone = false;

    //niepewne działanie
    private boolean isDone = false;

    public void exampleStart() {

        Thread backgroundJob = new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        isDone = false;
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        isDone = true;
                        Log.d("THR_THR", "Thr 1: Koniec pracy!");
                    }
                });


        Thread heavyWorker = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Date start = new Date();
                        while (!isDone) {
                            //
                        }
                        long durationInMillis = (new Date()).getTime() - start.getTime();
                        Log.d("THR_THR", "Thr2 : Koniec pracy po " + durationInMillis + " ms");
                    }
                });

        heavyWorker.start();
        backgroundJob.start();
    }
}
