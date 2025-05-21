package se.reky.hakan.insecure.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/*

REST-controller som ansvarar för att visa upp en fil i applikationen.
Endpointen /readFile i denna REST-controller är inte säker. Den kan användas
för att komma åt filer på din dator, utanför denna webbapplikation.
1.  Testa att med hjälp av endpointen /readFile nedan visa upp filen 'file.txt'
som ligger i foldern 'files' i roten av denna applikationen.
2.  Använd informationen i file.txt för att skapa en fil utanför din webbapplikation
och som är tänkt att innehålla känslig information
3.  Försök att åtgärda problemet med att filer på din dator kan nås. Tips på
implementation: försök att begränsa åtkomsten till endast foldern 'files'.
 */
@RestController
@RequestMapping("/readFile")
public class FileController {
    private static final String SAFE_DIR = "files";

    @GetMapping
    public String readFile(@RequestParam (name="filePath") String filePath) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }

        return fileContent.toString();
    }
}

