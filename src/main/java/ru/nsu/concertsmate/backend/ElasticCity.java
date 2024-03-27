package ru.nsu.concertsmate.backend;

public class ElasticCity {
    private ElasticCoords coords;
    private String district;

    private String name;

    private int population;

    private String subject;

    public ElasticCity(){

    }

    public ElasticCity(ElasticCoords coords, String district, String name, int population, String subject) {
        this.coords = coords;
        this.district = district;
        this.name = name;
        this.population = population;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "ElasticCity{" +
                "coords=" + coords +
                ", district='" + district + '\'' +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", subject='" + subject + '\'' +
                '}';
    }

    public ElasticCoords getCoords() {
        return coords;
    }

    public void setCoords(ElasticCoords coords) {
        this.coords = coords;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
