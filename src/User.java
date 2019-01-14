import java.util.ArrayList;

public class User {

    private ArrayList<Dog> dogList = new ArrayList<>();
    private String name;

    public User(String name){
        this.name = name;
    }

    String getName() {
        return name;
    }

    ArrayList getDogs(){
        return dogList;
    }

}
