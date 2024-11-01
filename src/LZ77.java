package commp.assign1.lz77;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mahmoud
 */

public class LZ77 {

    public static String readFromFile(String path) {
        String fileText = "";
        try (Scanner reader = new Scanner(new File("src/" + path))) {
            String line;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                // System.out.println(line);
                fileText += line + "\n";

            }
            return fileText;
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            return "File not found";
        }
    }

    public static ArrayList<Tag> readTagsFromFile(String path) {
        // System.out.println("xcccc");
        ArrayList<Tag> tagsArr = new ArrayList<>();
        String fileText = "";
        try (Scanner reader = new Scanner(new File("src/" + path))) {
            String line;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                Tag tag = new Tag((int)line.charAt(1)-48, ((int)line.charAt(4))-48, ((String.valueOf(line.charAt(7)).equals("$")) ? "\n" : String.valueOf(line.charAt(7))));
                tagsArr.add(tag);

            }
            return tagsArr;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // return "File not found";
            return tagsArr;
        }
    }

    public static void WriteToFile(String path, String txt) {
        try (FileWriter writer = new FileWriter(new File("src/" + path))) {
            // System.out.println("111");
            writer.write(txt);

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("File not found");

        }
    }

    public static ArrayList<Tag> compress(String txt) {
        ArrayList<Tag> tags = new ArrayList<>();
        int searchWinSize = 9;
        int lookAheadBuffSize = 9;

        int i = 0;

        while (i < txt.length()-1) {
            int matchOffset = 0;
            int matchLength = 0;
            String nextChar = "";
            
            int searchStart = Math.max(0, i - searchWinSize);
            int searchEnd = i;
            int lookAheadEnd = Math.min(i + lookAheadBuffSize, txt.length());

            int length = 0;
            int j = 0;
            for (j = searchStart; j < searchEnd; j++) {
                length = 0;

                while (txt.charAt(j + length) == txt.charAt(i + length) && j + length < searchEnd && i + length < lookAheadEnd) {
                    length++;
                }

                if (length >= matchLength) {
                    matchLength = length;
                    matchOffset = i - j;
                }
            }
            if (length == matchLength && matchLength == 0) {
                matchOffset = i - j;
            }

            if (i + matchLength < txt.length()) {
                if(txt.charAt(i + matchLength) == '\n') {
                    nextChar = String.valueOf('$');
                } else {
                    nextChar = String.valueOf(txt.charAt(i + matchLength));
                }

            } else {
                nextChar = " ";
            }

            Tag tag = new Tag(matchOffset, matchLength, nextChar);
            tags.add(tag);

            i += (matchLength > 0) ? matchLength + 1 : 1;
        }

        return tags;
    }

    public static String decompress(ArrayList<Tag> tags) {

        String result = "";

        for (Tag tag : tags) {
            int offset = tag.position;
            int length = tag.length;
            char nextChar = tag.nextChar.charAt(0);

            int j =  result.length() - offset;
            for (int i = 0; i < length; i++) {
                result += result.charAt(j);
                j++;
            }

            result += nextChar;

        }

        return result;
    }


    public static void main(String[] args) {

        String inputPath = "/input.txt";
        String outputPath = "/output.txt";
        String inputTagsPath = "/outputtags.txt";

        String PlainText = readFromFile(inputPath);
        ArrayList<Tag> tagsOut = compress(PlainText);

        String tagsOutS = "";
        for (Tag tag : tagsOut) {
            tagsOutS += tag.toString() + "\n";
        }
        WriteToFile(inputTagsPath, tagsOutS);

        ArrayList<Tag> tagsIn = readTagsFromFile(inputTagsPath);
        String decompressed = decompress(tagsIn);

        WriteToFile(outputPath, decompressed);

    }
}
