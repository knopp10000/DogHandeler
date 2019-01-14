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
        register.registerDog(new Dog("Luna", "Uraiser", 1, 20));
        User kalle = new User("Kalle");
        register.registerUser(kalle);
        register.registerUser(new User("Denise"));
        Auction auction = new Auction(rufus);
        register.registerAuction(auction);
        register.registerDogToAuction(rufus);
        Bid bid = new Bid(auction, kalle, 20);
        auction.addBid(bid);

    }

    private void runCommandLoop() {
        while (running) {
            System.out.println("Enter Command! To see available commands type \"help\"!");
            String input = getInput();
            handleCommand(input);
        }
    }

    private String getInput() {
        return scanner.nextLine().toLowerCase();
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

    private void makeBid() {
        System.out.println("Enter the name of the user");
        String userName = scanner.nextLine();
        User user = register.getUserByName(userName);
        if (user != null){
            System.out.println("Enter the name of the dog");
            String dogName = scanner.nextLine();
            Auction auction = register.getAuctionByDogName(dogName);
            if (auction != null){
                boolean bidTooLow = true;
                while (bidTooLow) {
                    System.out.printf("Amount to bid (min %d)", auction.getHighestBid() + 1);
                    int bidAmount = scanner.nextInt();
                    scanner.nextLine();
                    if (bidAmount > auction.getHighestBid()) {
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
        String dogName = scanner.nextLine();
        Dog dog = register.getDogByName(dogName);

        if (dog != null && !register.dogIsInAuction(dog) && !dog.hasOwner()) {
            Auction auction = new Auction(dog);
            register.registerAuction(auction);
            register.registerDogToAuction(dog);
            System.out.printf("%s has ben put up for auction in auction #%d\n", dog.getName(), auction.auctionID);
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
        String name = scanner.nextLine();
        register.registerUser(new User(name));
        System.out.printf("%s added to the register\n", name);
    }

    private void registerNewDog() {
        System.out.println("What's the dogs name?");
        String name = scanner.nextLine();
        System.out.println("What's the dogs breed?");
        String breed = scanner.nextLine();
        System.out.println("How old is the dog?");
        int age = scanner.nextInt();
        System.out.println("How many kilos does the dog weigh?");
        int weight = scanner.nextInt();
        register.registerDog(new Dog(name, breed, age, weight));
        scanner.nextLine();
        System.out.println("New dog registered");
    }

    private void increaseAge() {
        System.out.println("Which dog do you want to increase its age?");
        String name = scanner.nextLine();
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
        String name = scanner.nextLine();
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
                System.out.printf("Auction #%d: %s. Top bids: [%s]\n", auction.auctionID, auction.dogInAuction.getName(), auction.getBidsAsString());
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
                System.out.println(user.getName() + user.getDogs() + "\n");
            }
            System.out.println("list users was performed!");
        } else {
            System.out.println("Error: no users in register");
        }
    }

    private void removeDog() {
        System.out.println("What dog do you want to remove?");
        String name = scanner.nextLine();
        Dog dog = register.getDogByName(name);
        register.unregisterDog(dog);
    }

    private void removeUser() {
        System.out.println("Enter name of the user you want to remove.");
        String name = scanner.nextLine();
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
    } */
}

