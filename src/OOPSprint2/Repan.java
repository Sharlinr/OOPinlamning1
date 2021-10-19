package OOPSprint2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Repan {

    public LocalDate datumMinusEttAr = LocalDate.now().minusYears(1);
    public LocalDate medlemsBetalDatum;
    public LocalDate inpasseringTidpunkt = LocalDate.now();

    public void giltigaMedlemmar(String medlem) {

        try (BufferedReader reader = Files.newBufferedReader(Path.of(
                "C:\\Users\\sharl\\OneDrive\\Skrivbord\\customers.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(
                     "GiltigaMedlemmar.txt", true))) {

            String personUppgifter;
            boolean aktivKund = false;
            boolean inaktivKund = false;

            while ((personUppgifter = reader.readLine()) != null) {
                List<String> personNamn = List.of(personUppgifter.split(","));

                if (personNamn.size() == 2) {
                    if (personNamn.get(0).equals(medlem)
                            || personNamn.get(1).strip().equalsIgnoreCase(medlem)) {
                        medlemsBetalDatum = LocalDate.parse(reader.readLine());
                        if (datumMinusEttAr.isBefore(medlemsBetalDatum)) {
                            System.out.println("Medlem, aktiv");
                            aktivKund = true;
                            inpasseringar(personNamn, inpasseringTidpunkt);
                        } else if (datumMinusEttAr.isAfter(medlemsBetalDatum)) {
                            System.out.println("Medlem, inaktiv");
                            inaktivKund = true;
                        } else {
                            System.out.println("Ej medlem");
                        }
                        if (!Files.exists(Path.of("GiltigaMedlemmar.txt"))) {
                            Files.createFile(Path.of("GiltigaMedlemmar.txt"));
                        }
                        writer.write(personUppgifter + " " + medlemsBetalDatum);
                        writer.newLine();
                    }
                }
            }
            if (aktivKund) {
                System.out.println("Registrerar passage");
            } else if (inaktivKund) {
                System.out.println("Inaktiv kund");
            } else {
                System.out.println("Ej medlem");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Filen kunde inte hittas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inpasseringar(List<String> personNamn, LocalDate inpasseringTidpunkt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDate = formatter.format(inpasseringTidpunkt);
        String personnummer = personNamn.get(0);
        String namn = personNamn.get(1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Inpasseringar.txt", true))) {
            if (!Files.exists(Path.of("Inpasseringar.txt"))) {
                Files.createFile(Path.of("Inpasseringar.txt"));
            }
            writer.write(personnummer + " " + namn + " " + "Besökte anläggningen senast" + " " + localDate);
            writer.newLine();
            writer.flush();
            System.out.println(personnummer + " " + namn + " " + "Besökte anläggningen senast" + " " + localDate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}