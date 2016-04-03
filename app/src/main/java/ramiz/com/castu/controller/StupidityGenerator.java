package ramiz.com.castu.controller;

import java.util.ArrayList;
import java.util.Calendar;

import ramiz.com.castu.model.Stupidity;


public class StupidityGenerator {

    private ArrayList<String> typesList;
    private ArrayList<String> categoriesList;
    private Stupidity stupidity;

    private final String GREED = "Наглое жлобство";
    private final String JUNK = "Визуальный мусор";
    private final String ROADS = "Апокалиптические дороги";
    private final String ECOLOGY = "Экологические катастрофы";
    private final String ENERGY = "Энергетическое транжирство";
    private final String INVALID = "Издевательство над инвалидами";
    private final String BREAKDOWN = "Разруха на пороге";
    private final String MUNICIPAL = "Муниципальная деградация";
    private final String SEPARATIZM = "Сепаратизм и нетерпимость";

    private final String CITIZEN = "Исправимо гражданами";
    private final String GOVERNMENT = "Исправимо государством";
    private final String FIRE = "Неисправимо, господь жги";


    public StupidityGenerator (){
        typesList = new ArrayList<String>();
        categoriesList = new ArrayList<String>();
        stupidity = new Stupidity();
        typesList.add(GREED);
        typesList.add(JUNK);
        typesList.add(ROADS);
        typesList.add(ECOLOGY);
        typesList.add(ENERGY);
        typesList.add(INVALID);
        typesList.add(BREAKDOWN);
        typesList.add(MUNICIPAL);
        typesList.add(SEPARATIZM);

        categoriesList.add(CITIZEN);
        categoriesList.add(GOVERNMENT);
        categoriesList.add(FIRE);
    }

    public Stupidity generateStupidity (){
        stupidity.setAuthor_id("id237542");
        stupidity.setImage("BASE64HERE");
        return  stupidity;
    }

    public void parseTypes (ArrayList<Boolean> booleanList){
        ArrayList<String> result = new ArrayList<String>();
        int counter = 0;
        for(Boolean b: booleanList){
          if (b){
            result.add(typesList.get(counter));
          }
          counter++;
         }
        stupidity.setTypesOfStupidities(result);
    }

    public void parseCategory (int id){
        switch (id){
            case 0: stupidity.setCategory(categoriesList.get(0)); break;
            case 1: stupidity.setCategory(categoriesList.get(1)); break;
            case 2: stupidity.setCategory(categoriesList.get(2)); break;
            default: stupidity.setCategory ("Not selected");
        }
    }

    public void setComment (String comment){
        stupidity.setComment(comment);
    }
    public void setLongitude(float longitude) {stupidity.setLongitude(longitude);}
    public void setLatitude(float latitude) {stupidity.setLatitude(latitude);}

    public void setDate (){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get (Calendar.DAY_OF_MONTH);
        int hour = c.get (Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        String result = year + "/" + month + "/" + day
                + " " + hour + ":" + minute + ":" + seconds;
        stupidity.setDate(result);
    }


}
