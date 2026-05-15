package com.example.testapplication;

import android.content.Context;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Reads dog weight data based on type (csv) and converts it into an arrayList of dogBreed
 */
public class DogReader {
    private static ArrayList<DogBreed> dogBreeds = new ArrayList<>();

    public static String header;

    private static String fileName = "DogBreedWeightData.csv";
    private static Scanner scanner;

    /**
     * Method to setup scanner object and read csv and parse into dodBreeds list
     */
    public static void initialize(Context context){
        try {
            InputStream is = context.getAssets().open(fileName);
            scanner = new Scanner(is);
        }
        catch (Exception e){
            scanner = null;
            return;
        }
        if (scanner.hasNextLine()) {
            header = scanner.nextLine();
        }
        while (scanner != null && scanner.hasNext()){
            String[] parts = scanner.nextLine().split(",");
            DogBreed dogBreed = new DogBreed(
                    parts[0],
                    new int[]{
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2])},
                    new int[]{
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4])}
                );
            if (Arrays.equals(dogBreed.getfWeights(), dogBreed.getmWeights())){
                dogBreed.setfWeights(
                    new int[] {
                        (int)(dogBreed.getmWeights()[0]*0.9),
                        (int)(dogBreed.getmWeights()[1]*0.9)
                    }
                );
            }
            dogBreeds.add(dogBreed);
        }
    }
    /**
     * prints a string representation of each of the dog in the dogBreed list
     */
    public static void printList(){
        for (DogBreed dog : dogBreeds){
            dog.print();
        }
    }
    /**
     * @return ArrayList<DogBreed> list of all dogBreeds read from DogBreedWeightData.csv
     */
    public static ArrayList<DogBreed> getDogBreeds() {
        return dogBreeds;
    }
    /**
     * Given name of dog breed, finds and returns dog breed from list
     * @param name
     * @return
     */
    public static DogBreed findDogBreed(String name){
        for (DogBreed breed : dogBreeds){
            if (breed.getName().equalsIgnoreCase(name)){
                return breed;
            }
        }
        for (DogBreed breed : dogBreeds){
            if (breed.getName().toUpperCase().contains(name.toUpperCase())){
                return breed;
            }
        }
        return null;
    }
}
