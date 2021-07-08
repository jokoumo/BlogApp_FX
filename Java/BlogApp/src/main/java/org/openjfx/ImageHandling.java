package org.openjfx;

import java.nio.file.Files;
import java.nio.file.Path;

public class ImageHandling {

        public static String addImage(String imageUrl) {
            Path imagePath;
            Path newPath_src = Path.of("src/main/resources/org/openjfx/images");
            //String strPath_src;
            Path newPath_target = Path.of("target/classes/org/openjfx/images");  // Weil der Build in einem anderen Verzeichnis ausgeführt wird
            String strPath_target;

            // Bildauswahl kopieren und neuen Pfad speichern
            try {
                if(!imageUrl.isEmpty()) {
                    imagePath = Path.of(imageUrl);
                    newPath_src = newPath_src.resolve(imagePath.getFileName());
                    newPath_target = newPath_target.resolve(imagePath.getFileName());
                    Files.copy(imagePath, newPath_src);
                    Files.copy(imagePath, newPath_target);

    //                strPath_src = newPath_src.toString();
    //                strPath_src = strPath_src.replace("\\", "/");
    //                strPath_src = strPath_src.substring(strPath_src.lastIndexOf("images/"));

                    strPath_target = newPath_target.toString();
                    strPath_target = strPath_target.replace("\\", "/");
                    strPath_target = strPath_target.substring(strPath_target.lastIndexOf("images/"));
                } else {
                    //strPath_src = "images/default.jpg";
                    strPath_target = "images/default.jpg";
                }
            } catch(Exception e) {
                System.out.println("Bildfehler: " + e.getMessage());
                //strPath_src = "images/default.jpg";
                strPath_target = "images/default.jpg";
            }
            return strPath_target;
    }
}
