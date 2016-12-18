package entities;

import DAO.Identified;

/**
 * Created by Admin on 17.12.2016.
 */
public class Place implements Identified<Integer> {
    private int id;
   private String place_name;
   private String description;

    public Place(String place_name, String description) {
        this.place_name = place_name;
        this.description = description;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
