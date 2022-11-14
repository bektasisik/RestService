package TYS.RestService.domain;

public class Student {
    private final int id;
    private final String name;
    private final String surname;
    private int absent;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAbsent() {
        return absent;
    }


    public Student(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.absent = 0;
    }

    public void increaseAbsent() {
        this.absent = absent + 1;
    }

    public void decraseAbsent() {
        this.absent = absent - 1;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", absent=" + absent +
                '}';
    }
}
