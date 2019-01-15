import java.util.ArrayList;

/**
 * Karl Gustafsson kagu9654
 * Har ej samarbetat med någon
 **/

public class User {

    private ArrayList<Dog> dogList = new ArrayList<>();
    private String name;

    public User(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Dog> getDogs(){
        return dogList;
    }

    public void addDog(Dog dog){
        dogList.add(dog);
    }

    public void removeDog(Dog dog){
        dogList.remove(dog);
    }

    public boolean hasDog(){
        if (!dogList.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

/*    public boolean hasDog(Dog dog){
        return dogList.contains(dog);
    }

    public boolean hasDog(String dogName){
        for (Dog dog : dogList){
            if (dog.getName().equals(dogName)){
                return true;
            }
        }
        return false;
    }*/

}
