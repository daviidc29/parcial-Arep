package co.edu.escuelaing.parcial.list.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ListService {

    public String linearSearch(ArrayList list, String value) {
  
        for (int i = 0; i < list.size(); i++) {
            if (list.contains(value)) {
                return String.valueOf(i);
            }
        }
        return "-1";

    }

    public String binarySearch(ArrayList list, String value) {

        
        String result;
        try {
            result= String.valueOf(list.indexOf(Integer.getInteger(value)));
        } catch (Exception e) {
             result = "-1";
        }
        return result;
    }
    
}
