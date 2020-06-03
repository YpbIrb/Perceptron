package Recources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataSet implements Serializable {
    private List<String> attributes;
    private List<String> classses;
    List<DataEntity> entities;
    List<Float> max_class_vals = new ArrayList<>();
    List<Float> min_class_vals = new ArrayList<>();
    List<Float> max_attr_vals = new ArrayList<>();
    List<Float> min_attr_vals = new ArrayList<>();


    public DataSet(List<String> attributes, List<String> classses, List<DataEntity> entities) {
        this.attributes = attributes;
        this.classses = classses;
        this.entities = entities;
    }

    public void normalizeClassesOnAnotherSet(DataSet dataSet){
        this.max_class_vals = dataSet.max_class_vals;
        this.min_class_vals = dataSet.min_class_vals;

        for (int i = 0; i < entities.size(); i++){
            for (int j = 0; j < classses.size(); j++){
                if (entities.get(i).getClass_val().get(j) != null){
                    float val = entities.get(i).getClass_val().remove(j);
                    float min = min_class_vals.get(j);
                    float max = max_class_vals.get(j);
                    entities.get(i).getClass_val().add(j, (val - min)/(max - min));
                }

            }
        }

    }

    public void normalizeAttributesOnAnotherSet(DataSet dataSet){
        this.max_attr_vals = dataSet.max_attr_vals;
        this.min_attr_vals = dataSet.min_attr_vals;

        for (int i = 0; i < entities.size(); i++){
            for (int j = 0; j < attributes.size(); j++){
                if (entities.get(i).getAttr_val().get(j) != null){
                    float val = entities.get(i).getAttr_val().remove(j);
                    float min = min_attr_vals.get(j);
                    float max = max_attr_vals.get(j);
                    entities.get(i).getAttr_val().add(j, (val - min)/(max - min));
                }

            }
        }

    }

    public void normalizeClasses(){

        for (int i = 0;  i < classses.size(); i++){
            max_class_vals.add(Float.MIN_VALUE);
            min_class_vals.add(Float.MAX_VALUE);
        }


        for (int i = 0;  i < classses.size(); i++){
            for (int j = 0; j < entities.size(); j++){
                if (entities.get(j).getClass_val().get(i) != null){
                    if (entities.get(j).getClass_val().get(i) > max_class_vals.get(i)){
                        max_class_vals.remove(i);
                        max_class_vals.add(i, entities.get(j).getClass_val().get(i));

                    }

                    if (entities.get(j).getClass_val().get(i) < min_class_vals.get(i)){
                        min_class_vals.remove(i);
                        min_class_vals.add(i, entities.get(j).getClass_val().get(i));

                    }
                }
            }
        }
        //System.out.println("Final min vals : " + min_vals);
        //System.out.println("Final max vals : " + max_vals);

        for (int i = 0; i < entities.size(); i++){
            for (int j = 0; j < classses.size(); j++){
                 if (entities.get(i).getClass_val().get(j) != null){
                     float val = entities.get(i).getClass_val().remove(j);
                     float min = min_class_vals.get(j);
                     float max = max_class_vals.get(j);
                     entities.get(i).getClass_val().add(j, (val - min)/(max - min));
                 }

            }
        }


    }

    public void normalizeAttributes(){
        for (int i = 0;  i < attributes.size(); i++){
            max_attr_vals.add(Float.MIN_VALUE);
            min_attr_vals.add(Float.MAX_VALUE);
        }


        for (int i = 0;  i < attributes.size(); i++){
            for (int j = 0; j < entities.size(); j++){
                if (entities.get(j).getAttr_val().get(i) != null){
                    if (entities.get(j).getAttr_val().get(i) > max_attr_vals.get(i)){
                        max_attr_vals.remove(i);
                        max_attr_vals.add(i, entities.get(j).getAttr_val().get(i));

                    }

                    if (entities.get(j).getAttr_val().get(i) < min_attr_vals.get(i)){
                        min_attr_vals.remove(i);
                        min_attr_vals.add(i, entities.get(j).getAttr_val().get(i));

                    }
                }
            }
        }
        //System.out.println("Final min vals : " + min_vals);
        //System.out.println("Final max vals : " + max_vals);

        for (int i = 0; i < entities.size(); i++){
            for (int j = 0; j < attributes.size(); j++){
                if (entities.get(i).getAttr_val().get(j) != null){
                    float val = entities.get(i).getAttr_val().remove(j);
                    float min = min_attr_vals.get(j);
                    float max = max_attr_vals.get(j);
                    entities.get(i).getAttr_val().add(j, (val - min)/(max - min));
                }

            }
        }

    }

    public void normalizeAll(){

        normalizeAttributes();
        normalizeClasses();

    }

    public void normalizeAllOnAnotherSet(DataSet dataSet){
        normalizeAttributesOnAnotherSet(dataSet);
        normalizeClassesOnAnotherSet(dataSet);
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<String> getClassses() {
        return classses;
    }

    public List<DataEntity> getEntities() {
        return entities;
    }


    public void print(){
        System.out.println("attributes= " + attributes +
                "\nclassses= " + classses +"\nentities : ");
        for (int i = 0; i < entities.size(); i++){
            System.out.println(entities.get(i));
        }
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "attributes=" + attributes +
                ", classses=" + classses +
                ", entities= ";

    }
}
