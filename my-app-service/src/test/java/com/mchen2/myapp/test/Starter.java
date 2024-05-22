package com.mchen2.myapp.test;

public class Starter extends Thread {

    private int x = 2;
    public static void main(String[] args) throws Exception {
        String o = "";

       for (int x = 0; x < 3; x++) {
               for (int y = 0; y < 2; y++) {
             	             if(x==1) break;
                           if(x==2 && y==1) break;
             	             o = o + x + y;
                	      }
           	}
       	System.out.println(o);

    }
    public Starter(){
        x = 4;
        start();
    }
    public void makeItSo() throws Exception {
        join();
        x = x - 1;
        System.out.println(x);
    }
    public void run() { x *= 2; }


}
