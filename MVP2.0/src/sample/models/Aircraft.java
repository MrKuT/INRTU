package sample.models;

public class Aircraft extends Transport {
    public int range;//максимально возможное расстояние полета
    public int load;//грузоподъемность самолета
    public Aircraft(){}
    public Aircraft(String name, int speed, int distance, int range, int load) {
        super(name, speed, distance);
        this.setLoad(load);
        this.setRange(range);
    }
    public int getRange(){return range;}
    public void setRange(int range){this.range=range;}
    public int getLoad(){return load;}
    public void setLoad(int load){this.load=load;}
    @Override
    public String getDescription(){
        return String.format("Самолет может пролететь без подзаправки %s км., а его максимальная грузоподъемность %s тонн", this.range, this.load);
    }
}
