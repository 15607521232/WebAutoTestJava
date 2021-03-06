package day27.InterfaceOriented.Day27Domain;

public class Day27Student {
    private String number;
    private String name;
    private int age;
    private String sex;
    private Day27Teacher day27Teacher;

    public Day27Teacher getDay27Teacher() {
        return day27Teacher;
    }

    public void setDay27Teacher(Day27Teacher day27Teacher) {
        this.day27Teacher = day27Teacher;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Day27Student() {
    }

    public Day27Student(String number, String name, int age, String sex) {
        this.number = number;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Day27Student{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
