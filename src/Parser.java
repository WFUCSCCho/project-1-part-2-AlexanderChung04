/*
        ∗ @file: Parser.java
∗ @description: This program implements process method/operate_BST/writeToFile, parses input from file, creates bst of Ramen from Ramen.csv, writes to result.txt
        ∗ @author: Alexander Chung
∗ @date: September 25, 2025
                                              */


import java.io.*;
import java.util.Scanner;

public class Parser {

    //Create a BST tree of Ramen type
    private BST<Ramen> mybst = new BST<>();

    public Parser(String filename) throws FileNotFoundException {
        process(new File(filename));
    }

    // Implement the process method
    // scans the input file parameter, also scans the downloaded data file to add to mybst of ramen, Remove redundant spaces for each input command, ignores blank lines, passes commands to operate_BST
    public void process(File input) throws FileNotFoundException {

        File dataFile = new File("./Ramen.csv");
        Scanner dataScanner = new Scanner(dataFile);
        dataScanner.nextLine(); // skip header

        while (dataScanner.hasNextLine()){
            String line = dataScanner.nextLine().trim();
            if(!line.isEmpty()){
                String[] data = line.split(",");

                int reviewNum = Integer.parseInt(data[0].trim());
                String brand = data[1].trim();
                String variety = data[2].trim();
                String style = data[3].trim();
                String country = data[4].trim();
                double stars = Double.parseDouble(data[5].trim());

                Ramen ramen = new Ramen(reviewNum, brand, variety, style, country, stars);
                mybst.insertVal(ramen); //fills mybst with ramen

            }
        }

        Scanner inScan = new Scanner(input);
        while(inScan.hasNextLine()){
            String ln = inScan.nextLine().trim();
            if(!ln.isEmpty()){
               String[] command = ln.split(",");
               operate_BST(command);//call operate_BST method
            }
        }

    }

    // Implement the operate_BST method
    // Determine the incoming command and operate on the BST, supports insertVal/removeVal/searchVal/print command and invalid command, writes to result.txt
    public void operate_BST(String[] command) {
        switch (command[0]) {
            // add your cases here 
            // call writeToFile
            case "insert" -> {
                if (command.length < 7) {
                    writeToFile("Invalid Command", "./result.txt");
                    return;
                }
                try {
                    int reviewNum = Integer.parseInt(command[1]);
                    String brand = command[2];
                    String variety = command[3];
                    String style = command[4];
                    String country = command[5];
                    double stars = Double.parseDouble(command[6]);

                    Ramen ramen = new Ramen(reviewNum, brand, variety, style, country, stars);
                    mybst.insertVal(ramen);
                    Node<Ramen> insertNode = mybst.searchVal(ramen);
                    writeToFile("insert " + insertNode.getValue(), "./result.txt");
                } catch(NumberFormatException e){
                    writeToFile("Invalid Command" , "./result.txt");
                }
            }

            case "search" -> { //search by review number
                if (command.length < 2) {
                    writeToFile("Invalid Command", "./result.txt");
                    return;
                }
                try {
                    int searchReviewNum = Integer.parseInt(command[1]);
                    Ramen ramSearch = new Ramen(searchReviewNum,"","","","",0.0);
                    Node<Ramen> searchNode = mybst.searchVal(ramSearch);
                    if(mybst.searchVal(ramSearch) != null){
                        writeToFile("found " + searchNode.getValue(), "./result.txt");
                    }
                    else{
                        writeToFile("Ramen review number " + searchReviewNum + " not found","./result.txt");
                    }
                }catch (NumberFormatException e) {
                    writeToFile("Invalid command", "./result.txt");
                }
            }

            case "remove" -> { //remove by review number
                if (command.length < 2){
                    writeToFile("Invalid Command", "./result.txt");
                    return;
                }
                try{
                    int removeReviewNum = Integer.parseInt(command[1]);
                    Ramen ramRemove = new Ramen(removeReviewNum, "", "", "", "",0.0);
                    Node<Ramen> removeNode = mybst.removeVal(ramRemove);
                    if(removeNode != null){
                        writeToFile("Removed " + removeNode.getValue(),"./result.txt");
                    } else{
                        writeToFile("Ramen review number " + removeReviewNum + " not found", "./result.txt");
                    }
                } catch (NumberFormatException e){
                    writeToFile("Invalid command", "./result.txt");
                }
            }

            case "print" -> { //just prints review numbers instead of entire dataset
                String out = "";
                for(Ramen value : mybst) {
                    out += value.getReviewNum() + " ";
                }
                writeToFile(out,"./result.txt");
            }


            // default case for Invalid Command
            default -> writeToFile("Invalid Command", "./result.txt");
        }
    }

    // Implement the writeToFile method
    // Generate the result file, writes to file, throws IO exception if it can't write to file
    public void writeToFile(String content, String filePath) {
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(filePath, true))) {
            fileWriter.println(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
