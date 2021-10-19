package OOPSprint2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner medlemNamnNr = new Scanner(System.in);

        System.out.println("Ange namn eller personnummer");
        String input = medlemNamnNr.nextLine();
        Repan repan = new Repan();
        repan.giltigaMedlemmar(input);
    }
}
