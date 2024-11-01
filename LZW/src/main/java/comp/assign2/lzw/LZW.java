package comp.assign2.lzw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mahmoud
 */

public class LZW {

    private static ArrayList<String> dec = new ArrayList<>();
    private static ArrayList<Integer> tags = new ArrayList<>();
    
        private static void fillDec() {
                for(int i = 65; i < 123; i++) {

                    if (i > 90 && i <= 96) {
                        continue;
                    }

                    String letter = "";
                    letter += ((char)i);
                    dec.add(letter);
            }
    
            // for(int i = 0; i < 52; i++) {
            //     System.out.println(dec.get(i));
            // }
        }

        public static String readString() {
            String inputtxt = "";
            try {
                File input = new File("src/input.txt");
                Scanner inputScanner = new Scanner(input);

                inputtxt = inputScanner.nextLine();
                inputScanner.close();

                return inputtxt;
            } catch (FileNotFoundException e) {
                System.out.println("Error: File not found");
                return "";
            }
        }

        public static void WriteString(String outputtxt, String path) {
            try {
                File output = new File("src/" + path);
                output.createNewFile();

                FileWriter outputWriter = new FileWriter(output);
                outputWriter.write(outputtxt);
                outputWriter.close();

            } catch (IOException e) {
                System.out.println("Error: Failed to write output");
            }
        }

        public static void compress(String txt) {

            int i = 0;
            while (i < txt.length()-1) {
                int j = 0;
                int lastmatchLocationInDec = 0;
                int matchlength;
                String searchtxtPart = "";
                String lastmatchedString = "";

                searchtxtPart += txt.charAt(i+j);

                for (int k = 0; k < dec.size(); k++) {
                    if (searchtxtPart.equals(dec.get(k))) {
                        lastmatchedString = searchtxtPart;

                        if(i + j + 1 > txt.length()-1) {
                            lastmatchLocationInDec = k;
                            break;
                        }

                        searchtxtPart += txt.charAt(i + ++j);
                        lastmatchLocationInDec = k;
                        
                    }
                }

                matchlength = lastmatchedString.length();
                if ( i+j+1 < txt.length() ) {
                    dec.add(lastmatchedString + txt.charAt(i+j));
                    
                }
                tags.add(lastmatchLocationInDec);

                i += matchlength;
            }
        }

        public static String decompress() {
            String decompressedtxt = "";

            // decompress here

            return decompressedtxt;
        }


        public static void main(String[] args) {
            fillDec();
            String txtTOCompress = readString();

            compress(txtTOCompress);

            // for(int i = 0; i < tags.size(); i++) {
            //     if(tags.get(i)+65 > 100) {
            //         System.out.println(tags.get(i)+65+11);
            //     } else {
            //         System.out.println(tags.get(i)+65);
            //     }
            // }
            // System.out.println("-----------------------");
            // for(int i = 52; i < dec.size(); i++) {
            //     System.out.println(dec.get(i));
            // }
            
            String PrintString = "";
            for (Integer tag : tags) {
                PrintString += tag.toString() + "\n";
            }
            WriteString(PrintString, "tagsOutput.txt");

            PrintString = "";
            for (String dec : dec) {
                PrintString += dec + "\n";
            }
            WriteString(PrintString, "decOutput.txt");

            // dec.clear();
            // fillDec();

            // String decompressedtxt = decompress();
            // WriteString(decompressedtxt, "DecompressedOutput.txt");
    }
}
