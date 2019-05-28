
import java.io.Serializable;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bartek
 */
public class komunikat implements Serializable {
    Date date;
    String data;
    
    public komunikat(String info) {
        date = new Date();
        data = info;
    }

    @Override
    public String toString() {
        return "data: " + date + ", info: " + data;
    }
    
    
}
