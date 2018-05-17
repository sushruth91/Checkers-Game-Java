package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


import static java.lang.Integer.parseInt;

/**
 * Constructor reads input from the file and stores it in the form of row,column matrix for further processing.
 * Invalid data in the file is handled at source.
 */

public class InputReader {

    private HashMap<String,Integer> newHashMap;
    private ArrayList<String> movesFromFile;

        InputReader() {
        HashMap<Integer,String> hashMap;
        movesFromFile = new ArrayList<>();
        hashMap= new HashMap<>();
        newHashMap= new HashMap<>();
        int count = 0;
        for(int i=0;i<8;i++){
            for(int k=0; k<8;k++){
                if(i%2==0 && k%2==0){
                    count++;
                    hashMap.put(count,i+"-"+k);
                    newHashMap.put(i+"-"+k,count);
                }
                else if(i%2!=0 && k%2!=0){
                    ++count;
                    hashMap.put(count,i+"-"+k);
                    newHashMap.put(i+"-"+k,count);

                }

            }
        }

        final String gameMovesFile= "/Users/sushruthradhakrishna/IdeaProjects/OOAD-CheckersGame/src/com/company/checkersInput.txt";
        File file;
        file = new File(gameMovesFile);
        Scanner inputData;

        try{
            inputData = new Scanner(file);
            while ((inputData.hasNext())){
                String line = inputData.next();
                String[] values = line.split("-");
                if(values.length>2){
                    System.out.println("Invalid Input");
                }
                else{

                    int fromCoordinates = parseInt(values[0]);
                    int toCoordinates = parseInt(values[1]);

                    String getValueofCordinatesFrom = hashMap.get(fromCoordinates);
                    String getValueofCordinatesTo = hashMap.get(toCoordinates);
                    movesFromFile.add(getValueofCordinatesFrom+","+getValueofCordinatesTo);

                    }
            }




        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList getmovesFromFile() {
        return this.movesFromFile;
    }

    public HashMap getnewHashMap(){
        return this.newHashMap;
    }






}


