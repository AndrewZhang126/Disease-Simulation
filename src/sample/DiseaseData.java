package sample;

public class DiseaseData {
    private String name;
    private int alive;
    private int dead;
    public DiseaseData(String n, int a, int d) {
        name = n;
        alive = a;
        dead = d;
    }
    public String getName() {
        return name;
    }
    public int getCurrent() {
        return alive - dead;
    }
    public int getAlive() {
        return alive;
    }
    public int getDead() {
        return dead;
    }
}
