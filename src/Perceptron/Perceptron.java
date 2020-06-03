package Perceptron;

import Recources.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.ls.LSOutput;

import javax.xml.crypto.Data;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Perceptron implements Serializable {
    private List<Link> links;
    private List<Layer> layers;
    private List<Float> errors;
    private DataSet dataSet;
    private float n;

    public Perceptron(@NotNull DataSet dataSet, int hidden_count){
        this.dataSet = dataSet;
        int inputs_count = dataSet.getAttributes().size();
        int classes_count = dataSet.getClassses().size();
        n = 0.405f;
        links = new ArrayList<>();
        layers = new ArrayList<>();
        layers.add(new Layer(0, inputs_count, links));
        layers.add(new Layer(1, hidden_count, links));
        layers.add(new Layer(2, hidden_count, links));
        layers.add(new Layer(3, classes_count, links));

        for (int i = 0; i < layers.size() - 1; i++){
            List<Neuron> this_layer_neurons = layers.get(i).getNeurons();
            List<Neuron> next_layer_neurons = layers.get(i + 1).getNeurons();
            for (int j = 0; j < this_layer_neurons.size(); j++)
                for (int k = 0; k < next_layer_neurons.size(); k++)
                    links.add(new Link(this_layer_neurons.get(j), next_layer_neurons.get(k)));

        }



        errors = new ArrayList<>();

    }


    @Nullable
    private Layer getInputLayer(){
        if(layers.isEmpty())
            return null;

        else
            return layers.get(0);

    }

    @Nullable
    private Layer getFirstHiddenLayer(){
        if(layers.size() >= 2)
            return layers.get(1);
        else
            return null;
    }

    @Nullable
    private Layer getExitLayer(){
        if(layers.size() >= 3)
            return layers.get(layers.size() - 1);
        else
            return null;
    }

    private float costFunction(){
        float err_sum = 0;
        for (int i = 0; i < errors.size(); i++){
            err_sum += errors.get(i);
        }
        return err_sum / errors.size();
    }

    @Nullable
    private Layer getLayer(int id) {
        if (id < layers.size())
            return layers.get(id);
        return null;
    }


    public void evolve(int gen_num){
        //System.out.println(layers.get(0).getNeurons());

        for (int i = 0; i < gen_num; i++){
            Collections.shuffle(dataSet.getEntities());
            dataSet.getEntities().forEach(this::iterate);
            System.out.println("epoch: " + i + " Cost funk: " + costFunction());
            //System.out.println(errors);
            errors.clear();
       }
    }

    public void forvardPropagation(DataEntity entity){
        getExitLayer().resetErrors();
        getInputLayer().setFirstInputs(entity);
        for (int i = 0; i < layers.size() - 1; i++){
            layers.get(i + 1).resetInputSumm();
            layers.get(i).pushAll();
            layers.get(i + 1).activateAll();
        }
        getExitLayer().setError(entity);
        float error = getExitLayer().getLayerError();
        errors.add(error);
        //System.out.println("Error = " + error);
    }


    public void backPropagation(){
        //System.out.println("Back prop start");
        for (int i = layers.size() - 1; i > 0; i--){
            layers.get(i).getNeurons().forEach(neuron ->
                    neuron.getLeftLinks().forEach(link ->
                            link.deltaRule(n)) );
        }
    }

    public void iterate(DataEntity entity){
        forvardPropagation(entity);
        //System.out.println("NetError: " + getExitLayer().getLayerError());
        backPropagation();
        for (int i = layers.size() - 1; i < 0; i--){
            layers.get(i).getNeurons().forEach(neuron -> neuron.enable());
        }
    }

    public List<Float> find_classes(DataEntity entity){
        List<Float> res = new ArrayList<>();
        forvardPropagation(entity);
        getExitLayer().getNeurons().forEach(neuron -> {
            res.add(neuron.getOutput());
        });
        return res;
    }

    public void testOnNormalizedDataSet(DataSet dataSet){
        List<List<Float>> errors = new ArrayList<>();
        for (int i = 0; i < dataSet.getClassses().size(); i++)
            errors.add(new ArrayList<>());

        for (int i = 0; i < dataSet.getEntities().size(); i++){
            List<Float> from_neuro = find_classes(dataSet.getEntities().get(i));
            List<Float> from_set = dataSet.getEntities().get(i).getClass_val();
            for (int j = 0; j < from_set.size(); j++){
                if(from_set.get(j) != null){
                    errors.get(j).add((from_set.get(j) - from_neuro.get(j)));
                    //System.out.println("Class № " + j + " error = " + (from_set.get(j) - from_neuro.get(j)));
                }

            }

        }

        for (int i = 0; i < errors.size(); i++){
            float sqr_sum_err = 0;
            for (int j = 0; j < errors.get(i).size(); j++){
                sqr_sum_err += (errors.get(i).get(j) * errors.get(i).get(j));
            }
            double rmse = Math.sqrt(sqr_sum_err/ errors.get(i).size());
            System.out.println("Class № " + i + " RMSE = " + rmse);
        }
    }


    public void testOnNormalizedDataSet(DataSet dataSet, FileWriter writer) throws IOException {
        List<List<Float>> errors = new ArrayList<>();
        for (int i = 0; i < dataSet.getClassses().size(); i++)
            errors.add(new ArrayList<>());

        for (int i = 0; i < dataSet.getEntities().size(); i++){
            List<Float> from_neuro = find_classes(dataSet.getEntities().get(i));
            List<Float> from_set = dataSet.getEntities().get(i).getClass_val();
            for (int j = 0; j < from_set.size(); j++){
                if(from_set.get(j) != null){
                    errors.get(j).add((from_set.get(j) - from_neuro.get(j)));
                    //System.out.println("Class № " + j + " error = " + (from_set.get(j) - from_neuro.get(j)));
                }

            }

        }

        for (int i = 0; i < errors.size(); i++){
            float sqr_sum_err = 0;
            for (int j = 0; j < errors.get(i).size(); j++){
                sqr_sum_err += (errors.get(i).get(j) * errors.get(i).get(j));
            }
            double rmse = Math.sqrt(sqr_sum_err/ errors.get(i).size());
            String formattedDouble = new DecimalFormat("#0.00").format(rmse);
            writer.write("Class № " + i + " RMSE = " + formattedDouble + "\n");
        }
    }


    public void testOnNonNormalizedDataSet(DataSet dataSet){
        List<List<Float>> errors = new ArrayList<>();
        for (int i = 0; i < dataSet.getClassses().size(); i++)
            errors.add(new ArrayList<>());

        for (int i = 0; i < dataSet.getEntities().size(); i++){
            List<Float> from_neuro = find_classes(dataSet.getEntities().get(i));
            List<Float> from_set = dataSet.getEntities().get(i).getClass_val();
            for (int j = 0; j < from_set.size(); j++)
                if(from_set.get(j) != null)
                    errors.get(j).add((from_set.get(j) - from_neuro.get(j)));
            //System.out.println("Class № " + j + " error = " + (from_set.get(j) - from_neuro.get(j)));
        }

        for (int i = 0; i < errors.size(); i++){
            float sum_err = 0;
            for (int j = 0; j < errors.get(i).size(); j++){
                sum_err += errors.get(i).get(j);
            }
            double rmse = Math.sqrt(sum_err * sum_err / errors.get(i).size());
            //System.out.println("Class № " + i + " RMSE = " + rmse);
        }
    }




}
