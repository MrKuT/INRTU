package sample.models;

public class Train extends Transport {
    public int wagons;//кол-во вагонов

    public Train() {
    }

    public Train(String name, int speed, int distance, int wagons) {
        super(name, speed, distance);
        this.setWagons(wagons);
    }

    public int getWagons() {
        return wagons;
    }

    public void setWagons(int wagons) {
        this.wagons = wagons;
    }

    @Override
    public String getDescription() {
        return String.format("В составе %s вагона(-ов)", this.wagons);
    }
}
