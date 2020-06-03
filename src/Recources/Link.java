package Recources;

import org.w3c.dom.ls.LSOutput;

import java.io.Serializable;
import java.util.Random;

public class Link implements Serializable {

    private static Random random;

    private float getNewWeight(){
        float eps = 0.00001f;
        //float rand = random.nextFloat()*0.12f - 0.06f;
        float rand = (float)Math.random()*1.2f - 0.6f;
        while(Math.abs(rand - 0.0f) < eps){
            rand = (float)Math.random()*1.2f - 0.6f;
        }
        //System.out.println("rand = " + rand);
        return rand;
    }

    Neuron start;
    Neuron end;
    float weight;

    public Link(Neuron start, Neuron end) {
        random = new Random();
        this.start = start;
        this.end = end;
        weight = getNewWeight();
    }

    public void deltaRule(float n){
        if (end.isEnabled() && start.isEnabled()){
            //System.out.println("Old weight = " + weight);
            //System.out.println("Startid = " + start.getId() + " Endid = " +end.getId() + " In DelteRule weight = " + weight + " end.localGrad = " + end.localGradient() + " start.getOutput = " + start.getOutput());
            weight += n * end.localGradient() * start.getOutput();
            //System.out.println("new weight = " + weight);
        }
    }

    public Neuron getStart() {
        return start;
    }

    public Neuron getEnd() {
        return end;
    }

    public float getWeight() {
        return weight;
    }
}
