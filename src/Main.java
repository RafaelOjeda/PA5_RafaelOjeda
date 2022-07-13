import java.util.*;
import java.io.*;
public class Main {
    // Directories of INPUT and OUTPUT no need to change
    private static final String INPUT = System.getProperty("user.dir")+"/input/";
    private static final String OUTPUT = System.getProperty("user.dir")+"/output/";

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
        Scanner in = new Scanner(new File(src));
        PrintWriter out = new PrintWriter(trg);
        if(srcExt.equals(trgExt)) {// Same format IE .TXT to .TXT or .CSV to .CSV
            if(srcExt.equals("txt")) {// Converts .TXT to .TXT txt->txt
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
            } else {// Converts .CSV to .CSV csv->csv
                while(in.hasNextLine()) {
                    String[] cells = in.nextLine().split(",");
                    for(int i = 0; i < cells.length;i++) { // Loops through each word per line and checks how it will be formatted.
                        String seperator = ","; // Default format is tab
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
            }
        }else {
            if(srcExt.equals("txt")) {
                //complete here
            }else {//csv->txt
                //complete here
            }
        }
        out.close();
        in.close();
    }
    // Convert command ends here

    // Normalize command starts here
    public static void normalize(String fileName) throws Exception{
        String delimiter = fileName.endsWith("txt")?"\t": ",";
        ArrayList<String>content = new ArrayList<String>();
        Scanner in = new Scanner(new File(fileName));
        while(in.hasNextLine())
            content.add(in.nextLine());
        in.close();
        PrintWriter out = new PrintWriter(fileName);
        int rows = content.size();
        for(String line: content) {
            String[] cells = line.split(delimiter);
            int cols = cells.length;
            for(String cell: cells){
                //work on th next few lines...
                //process each cell
                //and print it using printf
                cols--;
                out.print(cell);
                if(cols != 0)
                    out.print(delimiter);
            }
            rows--;
            if(rows != 0)
                out.println();
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