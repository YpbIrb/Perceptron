package Recources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Neuron implements Serializable {

   private float output;
   private float input_sum;
   private float error;
   private boolean enabled;
   private static int idCounter = 0;
   private final int id = createID();
   List<Link> links;

   public Neuron(){
       output = 0;
       input_sum = 0;
       error = 0;
       enabled = true;
       links = new ArrayList<>();
   }

    public Neuron(List<Link> links){
        output = 0;
        input_sum = 0;
        error = 0;
        enabled = true;
        this.links = links;
    }

   private static int createID(){
       return (idCounter++);
   }

   public List<Link> getRightLinks(){
       //System.out.println("In getRightLinks");
       List<Link> res = new ArrayList<>();
       for (int i = 0; i < links.size();i++){
           if (links.get(i).getStart() == this)
               res.add(links.get(i));
       }
       //System.out.println("this.id = " + id + " res.size = " + res.size());
       return res;
   }

   public List<Link> getLeftLinks(){
       //System.out.println("In getLeftLinks");
       List<Link> res = new ArrayList<>();
       for (int i = 0; i < links.size();i++){
           if (links.get(i).getEnd() == this)
               res.add(links.get(i));
       }
       //System.out.println("LeftLinks.size = " + res.size());
       return res;
   }

   public void disable(){
       enabled = false;
   }

   public void enable(){
       enabled = true;
   }

   private float logActivateFunc(float sum, float a){
       return (float)(1/(1 + Math.exp(-1*a*sum)));
   }

   private float diffLogActivateFunc(float activSum, float a){
       return (a * activSum * (1 - activSum));
   }


   public void activate(){
       output = logActivateFunc(input_sum, 1);
       //System.out.println("In activate id= " + id + " input_sum = " + input_sum + " output = " + output);
   }

   public void recountError(float neaded_data){
       error = neaded_data - input_sum;
   }

    public float getOutput() {
        return output;
    }

    public void setOutput(float output) {
        this.output = output;
    }

    public float getInput_sum() {
        return input_sum;
    }

    public void setInput_sum(float input_sum) {
        this.input_sum = input_sum;
    }

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void pushSignals(){
       links.forEach(link -> {
           if(link.getStart() == this){
               link.getEnd().input_sum += this.output*link.getWeight();
           }
       });
   }


   public float localGradient(){
       if(isInLastLayer()){
           if(enabled = true)
               return error * diffLogActivateFunc(output, 1);
           else return 0;
       }
       else{
           float prev_sum = 0;
           List<Link> right_links = getRightLinks();
           for (int i = 0; i < right_links.size(); i++){
               prev_sum += right_links.get(i).getEnd().localGradient()*right_links.get(i).getWeight();
           }
           return prev_sum * diffLogActivateFunc(output, 1);
       }
   }

   public boolean isInLastLayer(){
       boolean res = true;
       for (int i = 0; i < links.size(); i++){
           if(links.get(i).getStart() == this)
               res = false;
       }
       return res;
   }


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "output=" + output +
                ", input_sum=" + input_sum +
                ", error=" + error +
                ", enabled=" + enabled +
                ", id=" + id +
                ", links=" + links +
                '}';
    }
}
