import java.util.*;
import java.io.*;
public class Main {
    // Directories of INPUT and OUTPUT no need to change
    private static final String INPUT = System.getProperty("user.dir")+"/input/";
    private static final String OUTPUT = System.getProperty("user.dir")+"/output/";

    private static boolean isDbl(String dblString) {
        // Returns boolean if it is a double. This method is used in the normalize method.
        try {
            Double.parseDouble(dblString);
            if (dblString.contains(".")) { // If it does not contain a period it should return false as it is an int.
                return true;
            }
        } catch (NumberFormatException exp) {
            return false;
        }
        return false;
    }

    private static boolean isInt(String intString) {
        // Returns boolean if it is an int. This method is used in the normalize method.
        try {
            Integer.parseInt(intString);
            return true;
        } catch (NumberFormatException exp) {
            return false;
        }
    }

    // Convert command starts here
    public static void convert(String src, String trg) throws Exception {
        // Gets extension of files
        String srcExt = src.substring(src.length()-3);
        String trgExt = trg.substring(trg.length()-3);
        // Parameter handling
        if(!srcExt.equals("txt") && !srcExt.equals("csv"))
            throw new Exception("Incorrect format must be a .TXT to .CSV or vice versa file only. Try Again.");
        if(!trgExt.equals("txt") && !trgExt.equals("csv"))
            throw new Exception("Incorrect format must be a .TXT to .CSV or vice versa file only. Try Again.");
        if(src.equals(trg))
            throw new Exception("Same I/O. file cannot convert to the same file. Try again.");
        Scanner in = new Scanner(new File(INPUT + src));
        PrintWriter out = new PrintWriter(OUTPUT + trg);
        if(srcExt.equals(trgExt)) { // Same format file converter
            if(srcExt.equals("txt")) { // txt->txt
                while(in.hasNextLine()) {
                    String[] cells = in.nextLine().split("\t");
                    for(int i = 0; i < cells.length;i++) { // Loops through each word per line and checks how it will be formatted.
                        String seperator = "\t"; // Default format is tab
                        if(i == cells.length - 1) { // If it's the word check if there is a new line.
                            if(in.hasNextLine())
                                seperator = "\n";
                            else
                                seperator = "";
                        }
                        out.print(cells[i] + seperator);
                    }
                    out.flush();
                }
            } else { // csv->csv
                while(in.hasNextLine()) {
                    String[] cells = in.nextLine().split(",");
                    for(int i = 0; i < cells.length;i++) { // Loops through each word per line and checks how it will be formatted.
                        String seperator = ","; // Default format is tab
                        if(i == cells.length - 1) { // If it's the word check if there is a new line.
                            if(in.hasNextLine()) {
                                seperator = "\n";
                            } else {
                                seperator = "";
                            }
                        }
                        out.print(cells[i] + seperator);
                    }
                    out.flush();
                }
            }
        } else {
            if(srcExt.equals("txt") && trgExt.equals("csv")) { // txt->csv
                while(in.hasNextLine()) {
                    String[] cells = in.nextLine().split("\t");
                    for(int i = 0; i < cells.length;i++) { // Loops through each word per line and checks how it will be formatted.
                        String seperator = ","; // Default format is tab
                        if(i == cells.length - 1) { // If it's the word check if there is a new line.
                            if (in.hasNextLine()) {
                                seperator = "\n";
                            } else {
                                seperator = "";
                            }
                        }
                        out.print(cells[i] + seperator);
                    }
                    out.flush();
                }
            } else if (srcExt.equals("csv") && trgExt.equals("txt")) {// csv->txt
                while(in.hasNextLine()) {
                    String[] cells = in.nextLine().split(",");
                    for(int i = 0; i < cells.length;i++) { // Loops through each word per line and checks how it will be formatted.
                        String seperator = "\t"; // Default format is tab
                        if(i == cells.length - 1) { // If it's the word check if there is a new line.
                            if (in.hasNextLine()) {
                                seperator = "\n";
                            } else {
                                seperator = "";
                            }
                        }
                        out.print(cells[i] + seperator);
                    }
                    out.flush();
                }
            }
        }
        out.close();
        in.close();
    }
    // Convert command ends here

    // Normalize command starts here
    public static void normalize(String fileName) throws NumberFormatException, FileNotFoundException {
        String delimiter = fileName.endsWith("txt")?"\t": ","; // If its a .TXT delimeter equals \t else delimeter is ","
        ArrayList<String>content = new ArrayList<String>(); // This arrayList contains every line within the fileName file.
        Scanner in = new Scanner(new File(INPUT + fileName));
        while(in.hasNextLine())
            content.add(in.nextLine());
        in.close();
        PrintWriter out = new PrintWriter(INPUT + fileName);
        int rows = content.size();
        for(String line: content) {
            String[] cells = line.split(delimiter); // Splits the lines in the content array into cells using the delimiter
            int cols = cells.length; // Length of every word within each line.
            for(String cell: cells){
                //work on th next few lines...
                //process each cell
                //and print it using printf

                if (cell.equals("")) { // If the cell is empty
                    cols--;
                    out.print("N/A");
                } else if (isInt(cell)) { // If the cell contains an integer
                    cols--;
                    int intNum = Integer.parseInt(cell);

                    if (intNum > 0) {
                        out.printf("+%010d", intNum);
                    } else {
                        out.printf("%010d", intNum);
                    }
                } else if (isDbl(cell)) { // If the cell contains a double
                    cols--;
                    double dblNum = Double.parseDouble(cell);

                    if ( (dblNum > 100) || (dblNum < 0.01)) {
                        out.printf("%.2e", dblNum);

                    } else {
                        out.printf("%.2f", dblNum); // Only shows to the hundredth.

                    }
                } else { // Anything else
                    cols--;

                    if (cell.length() > 13) { // Adds ellipse after 13 character length
                        out.printf("%s. . .", cell.substring(0,11));
                    } else {
                        out.printf(cell); // Just prints the regular string
                    }
                }

                if(cols != 0) {
                    out.print(delimiter);
                }
            }
            rows--;
            if(rows != 0) {
                out.println();
            }
        }
        out.close();
    }
    // Normalize command ends here

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        // Input handling starts here
        while(true){
            String command = keyboard.nextLine();
            String[] words = command.split(" "); // Splits input to an array using each space character
            if(words.length == 0){ // Empty input handling
                System.out.println("Error: the command is empty! Please try again!");
            }else if(words[0].equals("quit")) // Quit command handling
                break;
            else if(words[0].equals("convert")) { // Convert command handling
                if(words.length != 3 ||
                        !words[1].endsWith(".txt") && !words[1].endsWith(".csv") ||
                        !words[2].endsWith(".txt") && !words[2].endsWith(".csv")){
                    System.out.println("Error: the convert command needs to get 2 arguments:" +
                            " convert source.xxx destination.yyy where xxx and yyy are csv or txt!" +
                            " Please try again!");
                    continue;
                }
                System.out.println("converting " + words[1] + " to " + words[2]);
                try{
                    convert(words[1], words[2]);
                }catch(Exception exp){
                    System.out.println("Error: conversion failed! " +
                            "Something is wrong w/ the format of the input file!" +
                            "Details: " + exp.getMessage() +
                            "Please enter a new command: ");
                }
            }else if(words[0].equals("normalize")){ // Normalize command handling
                if(words.length != 2 ||
                        !words[1].endsWith(".txt") && !words[1].endsWith(".csv")) {
                    System.out.println("Error: the convert command needs to get 1 arguments:" +
                            " normalize source.xxx where xxx is either csv or txt!" +
                            " Please try again!");
                    continue;
                }
                System.out.println("normalizing " + words[1]);
                try{
                    normalize(words[1]);
                }catch(Exception exp){
                    System.out.println("Error: normalize failed! " +
                            "Something is wrong w/ the format of the input file!" +
                            "Details: " + exp.getMessage() +
                            "Please enter a new command: ");
                }
            }
            else{
                System.out.println("Error: invalid command. Valid commands are quit, convert, normalize." +
                        "Please try again!");
            }
        }
        // End of input/while loop handling
    }

}