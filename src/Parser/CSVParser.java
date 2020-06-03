package Parser;


import Recources.DataEntity;
import Recources.DataSet;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser {

    public static DataSet parseCSV(File fin, int class_amount) throws IOException {
        List<DataEntity> entities = new ArrayList<>();
        List<String> attrs = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        FileReader freader = new FileReader(fin);
        BufferedReader reader = new BufferedReader(freader);
        String line = reader.readLine();
        String[] str_arr = line.split(",");
        List<String> str_list;
        str_list = Arrays.asList(str_arr);

        int i = 0;
        for (; i < str_list.size() - class_amount; i++){
            attrs.add(str_list.get(i));
        }
        for (; i < str_list.size(); i++){
            classes.add(str_list.get(i));
        }

        line = reader.readLine();
        while(line != null){
            str_arr = line.split(",");
            str_list = Arrays.asList(str_arr);
            //System.out.println(str_list);
            entities.add(new DataEntity(str_list, class_amount));

            line = reader.readLine();
        }

        return new DataSet(attrs, classes, entities);
    }
}