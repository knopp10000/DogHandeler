import java.util.ArrayList;

/**
 * Karl Gustafsson kagu9654
 * Har ej samarbetat med någon
 **/

public class Register {

    private ArrayList<Dog> dogList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Dog> dogsInAuction = new ArrayList<>();
    private ArrayList<Auction> auctionList = new ArrayList<>();

    public void registerAuction(Auction auction){
        auctionList.add(auction);
    }

    public void registerDogToAuction(Dog dog){
        dogsInAuction.add(dog); //change
    }

    public void registerDog(Dog dog){
        dogList.add(dog);
    }

    public void registerUser(User user){
        userList.add(user);
    }

    public ArrayList<Dog> cloneDogList(){
        return new ArrayList<Dog>(dogList);
    }

    public ArrayList<Auction> cloneAuctionList(){
        return new ArrayList<Auction>(auctionList);
    }

    public ArrayList<User> cloneUserList(){
        ArrayList<User> clone = new ArrayList<>(userList.size());
        for (User user : userList) clone.add(user);
        return clone;
    }

    public void unregisterDogInAuction(Dog dog){
        if (dog != null){
            dogsInAuction.remove(dog);
        }else{
            System.out.println("error case insensitive: Hund med det namnet fanns ej i registret");
        }
    }

    public void unregisterDog(Dog dog){
        if (dog != null){
            dogList.remove(dog);
        }else{
            System.out.println("error case insensitive: Hund med det namnet fanns ej i registret");
        }
    }

    public void unregisterUser(User user){
        if (user != null){
            userList.remove(user);
            user = null;
        }else{
            System.out.println("error case insensitive: User med det namnet fanns ej i registret");
        }
    }

    public void unregisterAuction(Auction auction){
        if (auction != null){
            userList.remove(auction);
            auction = null;
        }else{
            System.out.println("error case insensitive: User med det namnet fanns ej i registret");
        }
    }

    public Auction getAuctionByDogName(String name){
        for (Auction auction : auctionList){
            if (auction.getDog().getName().equals(name)){
                return auction;
            }
            System.out.println(name + " is not in auction:" + auction.auctionID);
        }
        System.out.println("There are no auctions");
        return null;
    }

    public Dog getDogByName(String name){
        for (Dog dog : dogList){
            if (dog.getName().equals(name)){
                return dog;
            }
        }
        return null;
    }

    public User getUserByName(String name){
        for (User user : userList){
            if (user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }

    public boolean dogIsInAuction(Dog dog){
        if (dogsInAuction.contains(dog)){
            return true;
        } else {
            return false;
        }
    }
}
