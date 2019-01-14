import java.util.ArrayList;

/**
 * Karl Gustafsson kagu9654
 * Har ej samarbetat med någon
 */

public class Dog {

    private String name;
    private String breed;
    private int age;
    private int weight;
    private User owner;

    public Dog(String name, String breed, int age, int weight){

        this.age = age;
        this.breed = breed;
        this.weight = weight;
        this.name = name;
    }

    public Dog(Dog dog){
        this.age = dog.getAge();
        this.breed = dog.getBreed();
        this.weight = dog.getWeight();
        this.name = dog.getName();
    }

    //bulletproof?
    public double getTailLength(){
        if (breed.toLowerCase().equals("tax") || breed.toLowerCase().equals("dachshund")){
            return 3.7;
        } else {
            return (age * weight) / 10.0;
        }
    }

    //kan anroppas en gång varje år automatiskt.
    public void birthday(){
        age++;
        //System.out.printf("Yay! It's %s's birthday! %s is now %d yeas old!", getName(), getName(), getAge());
    }

    @Override
    public String toString(){
        return String.format("name=%s breed=%s age=%d weight=%d tailLength=%f", getName(), getBreed(), getAge(), getWeight(), getTailLength());
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public Boolean hasOwner(){
        return owner != null;
    }
}
