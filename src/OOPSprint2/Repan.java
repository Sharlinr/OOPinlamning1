package OOPSprint2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Repan {


    public LocalDate datum = LocalDate.now().minusYears(1);
    public LocalDate medlemsDatum;
    public LocalDate inpasseringTidpunkt = LocalDate.now();

    public void giltigaMedelemmar(String namnPers) {

        try (BufferedReader reader = Files.newBufferedReader(Path.of(
                "C:\\Users\\sharl\\OneDrive\\Skrivbord\\customers.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(
                     "GiltigaMedlemmar.txt", true))) {
            {
                String personUppgifter;
                boolean aktivKund = false;
                boolean inaktivKund = false;

                while ((personUppgifter = reader.readLine()) != null) {
                    List<String> personNamn = List.of(personUppgifter.split(","));

                    if (personNamn.size() == 2) {
                        if (personNamn.get(0).equals(namnPers)
                                || personNamn.get(1).strip().equalsIgnoreCase(namnPers)) {
                            medlemsDatum = LocalDate.parse(reader.readLine());
                            if (datum.isBefore(medlemsDatum)) {
                                System.out.println("Medlem, aktiv");
                                aktivKund = true;
                                inpasseringar(personNamn, inpasseringTidpunkt);
                            } else if (datum.isAfter(medlemsDatum)) {
                                System.out.println("Medlem, inaktiv");
                                inaktivKund = true;
                            }
                            if (!Files.exists(Path.of("GiltigaMedlemmar.txt"))) {
                                Files.createFile(Path.of("GiltigaMedlemmar.txt"));
                            }
                            writer.write(personUppgifter + " " + medlemsDatum);
                            writer.newLine();
                        }
                    }
                }
                if (aktivKund) {
                    System.out.println("Aktiv kund");
                } else if (inaktivKund) {
                    System.out.println("Inaktiv kund");
                } else {
                    System.out.println("Ej medlem");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Filen kunde inte hittas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inpasseringar(List<String> personNamn, LocalDate inpasseringTidpunkt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDate = formatter.format(inpasseringTidpunkt);
        String namn = personNamn.get(0);
        String personnummer = personNamn.get(1);

        try {
            if (!Files.exists(Path.of("Inpasseringar.txt"))) {
                Files.createFile(Path.of("Inpasseringar.txt"));
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("Inpasseringar.txt", true));
            writer.write(namn + " " + personnummer + " " + "Besökte anläggningen senast" + " " + localDate);
            writer.newLine();
            writer.flush();
            System.out.println(namn + " " + personnummer + " " + "Besökte anläggningen senast" + " " + localDate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


