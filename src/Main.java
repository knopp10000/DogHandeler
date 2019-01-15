import java.util.ArrayList;
import java.util.Scanner;

/**
 * Karl Gustafsson kagu9654
 * Har ej samarbetat med någon
 **/

public class Main {

    private static final String[] COMMANDS = {"register new dog", "increase age", "list dogs", "remove dog", "start auction", "exit", "help"};
    private Scanner scanner = new Scanner(System.in);
    private Register register = new Register();
    private boolean running = true;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        initialize();
        runCommandLoop();
        shutDown();
    }

    private void initialize() {
        System.out.println("Welcome to The DogHandler2000 v2.0");

        //Add instances for testing
        Dog rufus = new Dog("Rufus", "Golden Retriever", 11, 40);
        register.registerDog(rufus);
        Dog arufus = new Dog("Arufus", "Golden Retriever", 11, 40);
        register.registerDog(arufus);
        Dog brufus = new Dog("Brufus", "Golden Retriever", 12, 40);
        register.registerDog(brufus);
        register.registerDog(new Dog("Luna", "Uraiser", 1, 20));
        User kalle = new User("kalle");
        register.registerUser(kalle);
        register.registerUser(new User("denise"));
        Auction auction = new Auction(rufus);
        register.registerAuction(auction);
        register.registerDogToAuction(rufus);
        Bid bid = new Bid(auction, kalle, 20);
        auction.addBid(bid);

    }

    private void runCommandLoop() {
        while (running) {
            System.out.println("Enter Command! To see available commands type \"help\"!");
            String input = scanner.nextLine();
            input = input.toLowerCase();
            handleCommand(input);
        }
    }

    private String getInput() {
        boolean nameIsNotValid = true;
        String output = "";
        while (nameIsNotValid){
            String input = scanner.nextLine().toLowerCase().trim();
            if (input.length() > 2){
                output = input.substring(0, 1).toUpperCase() + input.substring(1);
            }
            if (isValidName(output)){
                nameIsNotValid = false;
            } else {
                System.out.println("Error: the name can’t be empty");
            }
        }
        return output;
    }

    private void handleCommand(String input) {
        switch (input) {
            case "register new dog": {
                registerNewDog();
                break;
            }
            case "increase age": {
                increaseAge();
                break;
            }
            case "list dogs": {
                listDogs();
                break;
            }
            case "remove dog": {
                removeDog();
                break;
            }
            case "register new user": {
                registerNewUser();
                break;
            }
            case "list users": {
                listUsers();
                break;
            }
            case "remove user": {
                removeUser();
                break;
            }
            case "start auction": {
                startAuction();
                break;
            }
            case "list auctions": {
                listAuctions();
                break;
            }
            case "close auction": {
                closeAuction();
                break;
            }
            case "make bid": {
                makeBid();
                break;
            }
            case "list bids": {
                listBids();
                break;
            }
            case "exit": {
                running = false;
                break;
            }
            case "help": {
                printCommands();
                break;
            }
            default: {
                System.out.println("error case insensitive. Type help for commands!");
            }
        }
    }

    private void closeAuction() {
        System.out.println("Enter the name of the dog");
        String dogName = getInput();
        Auction auction = register.getAuctionByDogName(dogName);
        if (auction != null && auction.hasBids()){
            //Bid highestBid = auction.getHighestBid();
            //System.out.println("Highest Bid = " + highestBid.getBidAmount());
            //System.out.println("Winner = " + winner.getName());
            //System.out.println("Dog = " + dogInAuction.getName());
            Dog dogInAuction = auction.getDog();
            User winner = auction.getHighestBid().getBidder();
            winner.addDog(dogInAuction);
            dogInAuction.setOwner(winner);
            System.out.printf("The auction is closed. The winning bid was %dkr and was made by %s\n", auction.getHighestBid().getBidAmount(), winner.getName());
            register.unregisterDogInAuction(dogInAuction); //
            register.unregisterAuction(auction); //make into method?
        }else if (auction == null) {
            System.out.println("Error: this dog is not up for auction");
        }else if (!auction.hasBids()){
            Dog dogInAuction = auction.getDog();
            System.out.println("The auction is closed. No bids where made for" + dogInAuction.getName());
            register.unregisterDogInAuction(dogInAuction);
            register.unregisterAuction(auction);
        }else{
            System.out.println("This should not occur");
        }
    }

    private void makeBid() {
        System.out.println("Enter the name of the user");
        String userName = getInput();
        User user = register.getUserByName(userName);
        if (user != null){
            System.out.println("Enter the name of the dog");
            String dogName = getInput();
            Auction auction = register.getAuctionByDogName(dogName);
            if (auction != null){
                boolean bidTooLow = true;
                while (bidTooLow) {
                    System.out.printf("Amount to bid (min %d)", auction.hasBids() ? auction.getHighestBid().getBidAmount() + 1 : 1); //why this no work
                    int bidAmount = scanner.nextInt();
                    scanner.nextLine();
                    if (bidAmount > (auction.hasBids() ? auction.getHighestBid().getBidAmount() : 0)) {
                        Bid bid = new Bid(auction, user, bidAmount);
                        auction.addBid(bid);
                        System.out.println("Bid made");
                        bidTooLow = false;
                    } else {
                        System.out.println("Too low bid!");
                    }
                }
            }else {
                System.out.println("Error: this dog is not up for auction");
            }
        }else {
            System.out.println("Error: no such user");
        }
    }

    private void startAuction() {
        System.out.println("What's the dogs name?");
        String dogName = getInput();
        Dog dog = register.getDogByName(dogName);

        if (dog != null && !register.dogIsInAuction(dog) && !dog.hasOwner()) {
            Auction auction = new Auction(dog);

            register.registerAuction(auction);
            register.registerDogToAuction(dog);
            System.out.printf("%s has ben put up for auction in auction #%d\n", dog.getName(), auction.getAuctionID());
        } else if (dog == null) {
            System.out.println("Error: no such dog");
        } else if (dog.hasOwner()) {
            System.out.println("Error: this dog already has an owner");
        } else {
            System.out.println("Error: this dog is already up for auction");
        }
    }

    private void registerNewUser() {
        System.out.println("What's the users name?");
        String name = getInput();
        register.registerUser(new User(name));
        System.out.printf("%s added to the register\n", name);
    }

    private void registerNewDog() {
        System.out.println("What's the dogs name?");
        String name = getInput();
        System.out.println("What's the dogs breed?");
        String breed = getInput();
        System.out.println("How old is the dog?");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("How many kilos does the dog weigh?");
        int weight = scanner.nextInt();
        scanner.nextLine();
        register.registerDog(new Dog(name, breed, age, weight));
        System.out.println("New dog registered");
    }

    private void increaseAge() {
        System.out.println("Which dog do you want to increase its age?");
        String name = getInput();
        Dog dog = register.getDogByName(name);
        if (dog != null) {
            dog.birthday();
            System.out.println("increase age was performed!");
        } else {
            System.out.println("fel case insensitive : hund med det namnet fanns ej i registret");
        }
    }

    private void listBids(){
        System.out.println("Enter the name of the dog in the auction that you're looking for");
        String name = getInput();
        Auction auction = register.getAuctionByDogName(name);
        if (auction != null && auction.hasBids()){
            System.out.println("Here are the bids for this auction");
            Bid[] bids = auction.getBids();
            for (int i = 0; i < bids.length -1; i++){
                System.out.printf("%s %d kr\n",bids[i].getBidder().getName(), bids[i].getBidAmount());
            }
        } else if (auction == null){
            System.out.println("Error: this dog is not up for auction");
        } else if (auction.getBids().length == 0){
            System.out.println("No bids registered yet for this auction");
        }else {
            System.out.println("This should not occur");
        }

    }

    private void listAuctions() {
        ArrayList<Auction> auctionList = register.cloneAuctionList();
        if (!auctionList.isEmpty()) {
            for (Auction auction : auctionList) {
                auction.sortBids();
                System.out.printf("Auction #%d: %s. Top bids: [%s]\n", auction.getAuctionID(), auction.getDog().getName(), auction.getBidsAsString());
            }
        } else {
            System.out.println("Error: no auctions in progress");
        }
    }

    private void listDogs() {
        ArrayList<Dog> dogList = register.cloneDogList();
        if (!dogList.isEmpty()) {
            System.out.println("minsta svanslängd att visa?");
            double tailLength = scanner.nextDouble();
            scanner.nextLine();

            for (Dog dog : dogList) {
                if (dog.getTailLength() >= tailLength) {
                    System.out.printf("%s (%s, %d år, %d kilo, %.1f cm svans) \n", dog.getName().toLowerCase(), dog.getBreed(), dog.getAge(), dog.getWeight(), dog.getTailLength());
                    //System.out.println(dog.hasOwner() ? "has owner" : "Does not have owner");
                }
            }
            System.out.println("list dogs was performed!");
        } else {
            System.out.println("Error: no dogs in register");
        }
        System.out.println("Följande hundar har sådana stora svansar:");
    }

    private void listUsers() {
        ArrayList<User> userList = register.cloneUserList();
        if (!userList.isEmpty()) {
            for (User user : userList) {
                StringBuffer dogList = new StringBuffer();
                if (user.hasDog()){
                    for (Dog dog: user.getDogs()) {
                        dogList.append(dog.getName());
                    }
                }
                System.out.println(user.getName() + dogList);
            }
            System.out.println("list users was performed!");
        } else {
            System.out.println("Error: no users in register");
        }
    }

    private void removeDog() {
        System.out.println("What dog do you want to remove?");
        String name = getInput();
        Dog dog = register.getDogByName(name);
        register.unregisterDog(dog);
    }

    private void removeUser() {
        System.out.println("Enter name of the user you want to remove.");
        String name = getInput();
        User user = register.getUserByName(name);
        register.unregisterUser(user);
    }

    private void printCommands() {
        for (String command : COMMANDS) {
            System.out.println(command);
        }
    }

    private void shutDown() {
        scanner.close();
        System.out.println("Closing down...");
    }

    private boolean isValidName(String string){
        return !string.trim().isEmpty();
    }

    /*
    private void listAllDogs() {
        ArrayList<Dog> dogList = register.cloneDogList();
        if (!dogList.isEmpty()) {
            for (Dog dog : dogList) {
                System.out.printf("%s (%s, %d år, %d kilo, %.1f cm svans) \n", dog.getName().toLowerCase(), dog.getBreed(), dog.getAge(), dog.getWeight(), dog.getTailLength());
            }
            System.out.println("list dogs was performed!");
        } else {
            System.out.println("Error: no dogs in register");
        }
    }
    */
}

