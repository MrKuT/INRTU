package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Transport {//транспорт
    public Integer id = null;//идентификатор
    public String name;//название транспорта
    public int speed;//скорость
    public int distance;//расстояние пути

    public Transport() {
    }

    public Transport(String name, int speed, int distance) {
        this.setName(name);
        this.setSpeed(speed);
        this.setDistance(distance);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return "";
    }

    public String getTime(float distance, float speed) {
        int hour = (int) (distance / speed);
        float min = ((distance / speed) - hour)*60;
        String time = String.valueOf(hour + ":" + (int)min);
        return time;
    }
}
