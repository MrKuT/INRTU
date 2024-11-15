/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treads;

import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;

/**
 *
 * @author stud
 */
class Streams {

    final Random random = new Random();
    private int max = 0;
    private int sch2 = 0;
    private int sch1 = 0;
    private int position = 0;
    private int chislo = 100000;
    private int i = 0;
    private int j = 0;
    private int g = 0;
    private int schetmax = 1;
    ArrayList<Integer> buf1 = new ArrayList<>();
    ArrayList<Integer> buf2 = new ArrayList<>();

    public synchronized void putInBuf1() {
        while (sch1 >= 10 && !Thread.interrupted()) {//следит за переполнением первого буфера, при достяжении макс значения ждет
            try {                
                wait();
            } catch (InterruptedException e) {
                System.out.println(" has been interrupted");
            }
        }
        i = g;
        i++;
        buf1.add(sch1, chislo);
        chislo = chislo - 1000;
        String s1 = String.format("%d", buf1.get(sch1));
        sch1++;
        System.out.print("Первый поток добавил " + i + " элемент в первый буфер: " + s1);
        System.out.println(" || Чисел в буфере №1: " + sch1 + " || Чисел в буфере №2: " + sch2);
        g = i;
        notify();

    }

    public synchronized void putInBuf2() {
        while (sch1 < 1 || sch2 >= 10) {//следит за переполнением второго буфера, при достяжении макс значения ждет
            try {                      //при пустом 1 буфере так же ждет               
                wait();
            } catch (InterruptedException e) {
            }
        }
        j = i;
        sch1--;
        position = 0;       
        buf2.add(buf1.get(sch1) / 1000);       
        System.out.print("Второй поток добавил " + (sch2 + 1) + "-ое число из буфера 1 во второй буфер: " + buf2.get(sch2));
        sch2++;
        System.out.println(" || Чисел в буфере №1: " + (sch1) + " || Чисел в буфере №2: " + sch2);
        j--;
        i = j;
        notifyAll();
    }

    public synchronized void getFromBuf2() {
        while (sch2 < 1) {//при пустом 2 буфере ждет его заполнения хотя бы на 1 элемент
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(" has been interrupted");
            }
        }
        j = i;
        max = 0;
        //перменная для хранения макс значения
        for (i = 0; i < buf2.size(); i++) {//поиск большего числа в буфере и запоминанияе его позиции
            if (max < buf2.get(i)) {
                max = buf2.get(i);
                position = i;
            }
        }
        buf2.remove(position);//чистим буфер от этого значения
        System.out.println("Третий поток выписал "+schetmax + "-ое максимальное число из буфера 2 число на позиции " + (position + 1) + " : " + max);
        sch2--;
        schetmax++;
        j--;
        i = j;
        notifyAll();
    }

}
