package Perceptron;

import Parser.CSVParser;
import Recources.DataSet;

import java.io.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        System.out.println("Choose mode" +
                "\n1: create new perceptron" +
                "\n2: train perceptron" +
                "\n3: get answers");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String mode_str = null;
        try {
            mode_str = reader.readLine();
        } catch (IOException e) {
            System.out.println("Trounbles with mode_str");
            e.printStackTrace();
        }
        int mode = Integer.parseInt(mode_str);

        switch(mode){
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;


        }


        try {

            File fin = new File("training_set.csv");
            File fout = new File("out.txt");
            FileWriter writer = new FileWriter(fout, true);
            DataSet training_dataset = CSVParser.parseCSV(fin, 2);
            //System.out.println(dataset);
            training_dataset.normalizeAll();
            training_dataset.print();
            //training_dataset.normalize();
            //System.out.println(training_dataset);

            fin = new File("test_ыуе.csv");
            DataSet test_set = CSVParser.parseCSV(fin, 2);

            test_set.normalizeAllOnAnotherSet(training_dataset);
            //test_set.normalizeClasses();
            test_set.print();
            //System.out.println(test_set);
            System.out.println("Normalizeing done");


            System.out.println("START!!!32 neurons!!!");
            writer.write("START!!!32 neurons!!!");
            Perceptron perceptron = new Perceptron(training_dataset, 32);
            int gen_num = 10;
            while(true){

                writer.write("!!generations_num : " + gen_num);
                gen_num += 10;
                perceptron.evolve(10);
                writer.write("\nTraining entities\n");
                perceptron.testOnNormalizedDataSet(training_dataset, writer);
                writer.write("\nTest entities\n");
                perceptron.testOnNormalizedDataSet(test_set, writer);

                writer.write("\n\n");
                writer.flush();

            }




        } catch (IOException e) {
            System.out.println("!!!Troubles with file!!!");
            e.printStackTrace();
        }

    }

    


}
