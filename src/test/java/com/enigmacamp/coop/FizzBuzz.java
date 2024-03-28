package com.enigmacamp.coop;

import org.junit.jupiter.api.Test;

public class FizzBuzz {

    public void fizzAndBuuzz(int a){
        for (int i = 1; i <= a; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("EnigmaCamp");
            } else if (i % 3 == 0) {
                System.out.println("enigma");
            } else if (i % 5 == 0) {
                System.out.println("camp");
            } else {
                System.out.println(i);
            }
        }
    }

    @Test
    void testFizzBuzz(){
        fizzAndBuuzz(100);
    }

}
