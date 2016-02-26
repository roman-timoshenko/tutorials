package name.roman.tutorial.ioc.model;

/**
 *
 */
public class Group {

    private final int id;
    private final String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
