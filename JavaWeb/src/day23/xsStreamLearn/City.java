package day23.xsStreamLearn;

public class City {
    private String name;
    private String description;

    public City(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
