import java.util.*;
import java.io.*;
public class Main {
    private static final String INPUT = System.getProperty("user.dir")+"/input/";
    private static final String OUTPUT = System.getProperty("user.dir")+"/output/";
    public static void convert(String src, String trg) throws Exception {
        String srcExt = src.substring(src.length()-3);
        String trgExt = trg.substring(trg.length()-3);
        if(!srcExt.equals("txt") && !srcExt.equals("csv"))
            throw new Exception("bad format");
        if(!trgExt.equals("txt") && !trgExt.equals("csv"))
            throw new Exception("bad format");
        if(src.equals(trg))
            throw new Exception("same i/o");
        Scanner in = new Scanner(new File(src));
        PrintWriter out = new PrintWriter(trg);
        if(srcExt.equals(trgExt)) {//same formats
            if(srcExt.equals("txt")) {//txt->txt
                while(in.hasNextLine()) {
                    String[] cells = in.nextLine().split("\t");
                    for(int i = 0; i < cells.length;i++) {
                        String seperator = "\t";
                        if(i == cells.length - 1){
                            if(in.hasNextLine())
                                seperator = "\n";
                            else
                                seperator = "";
                        }
                        out.print(cells[i] + seperator);
                    }
                    out.flush();
                }
            }else {//csv->csv
                //complete here
            }
        }else {
            if(srcExt.equals("txt")) {//txt->csv
                //complete here
            }else {//csv->txt
                //complete here
            }
        }
        out.close();
        in.close();
    }
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
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        while(true){
            String command = keyboard.nextLine();
            String[] words = command.split(" ");
            if(words.length == 0){
                System.out.println("Error: the command is empty! Please try again!");
            }else if(words[0].equals("quit"))
                break;
            else if(words[0].equals("convert")) {
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
            }else if(words[0].equals("normalize")){
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






    }

}