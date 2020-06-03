package Recources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataEntity implements Serializable {

    List<Float> attr_val;
    List<Float> class_val;

    public DataEntity(List<String> vals, int class_amount){
        attr_val = new ArrayList<>();
        class_val = new ArrayList<>();
        int i = 0;
        for (; i < vals.size() - class_amount; i++){
            try {
                float f = Float.parseFloat(vals.get(i));
                attr_val.add(f);
            }
            catch (NumberFormatException e){
                attr_val.add(null);
            }
        }

        for (; i < vals.size(); i++){
            try {
                float f = Float.parseFloat(vals.get(i));
                class_val.add(f);
            }
            catch (NumberFormatException e){
                class_val.add(null);
            }
        }

    }

    public List<Float> getAttr_val() {
        return attr_val;
    }

    public List<Float> getClass_val() {
        return class_val;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "attr_val=" + attr_val +
                ", class_val=" + class_val +
                '}';
    }
}
