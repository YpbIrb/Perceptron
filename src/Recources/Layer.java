package Recources;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Layer implements Serializable {
    List<Neuron> neurons;
    int number;

    public Layer(int number, int nn){
        neurons = new ArrayList<>();
        for (int i = 0; i < nn; i++){
            neurons.add(new Neuron());
        }
        this.number = number;
    }

    public Layer(int number, int nn, List<Link> links){
        neurons = new ArrayList<>();
        for (int i = 0; i < nn; i++){
            neurons.add(new Neuron(links));
        }
        this.number = number;
    }

    public void setError(DataEntity dataEntity){
        for (int i = 0; i < dataEntity.getClass_val().size(); i++){
            if (dataEntity.getClass_val().get(i) == null || neurons.get(i).equals(null)){
                neurons.get(i).disable();
            }
            else {
                neurons.get(i).setError(dataEntity.getClass_val().get(i) - neurons.get(i).getOutput());
            }
        }
    }

    public float getLayerError(){
        float res = 0;
        for (int i = 0; i < neurons.size(); i++){
            res += neurons.get(i).getError() * neurons.get(i).getError();
        }
        return res / 2;
    }

    public void resetErrors(){
        neurons.forEach(neuron -> {
            neuron.setError(0);
        });
    }

    public void setFirstInputs(DataEntity entity){
        for (int i = 0; i < neurons.size(); i++){
            if (entity.getAttr_val().get(i) == null){
                //System.out.println("!!!!");
                neurons.get(i).disable();
            }
            else {
                neurons.get(i).setInput_sum(entity.getAttr_val().get(i));
                neurons.get(i).setOutput(entity.getAttr_val().get(i));
            }

        }
    }

    public void resetInputSumm(){
        neurons.forEach(neuron -> {
            neuron.setInput_sum(0);
        });
    }

    public void pushAll(){
        neurons.forEach(neuron -> {
            neuron.pushSignals();
        });
    }

    public void activateAll(){
        neurons.forEach(neuron -> {
            neuron.activate();
        });
    }

    public List<Neuron> getNeurons(){
        return neurons;
    }

}
