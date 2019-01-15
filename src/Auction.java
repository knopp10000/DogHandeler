import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Karl Gustafsson kagu9654
 * Har ej samarbetat med någon
 **/

public class Auction {

    private Dog dogInAuction;
    private int auctionID;
    private static int instanceCount;
    private Bid[] bids = new Bid[1];

    Auction(Dog dog){
        instanceCount++;
        auctionID = instanceCount;
        dogInAuction = dog;
    }

    public void addBid(Bid bid){
        bids[bids.length-1] = bid;
        bids = Arrays.copyOf(bids, bids.length+1);
    }

    public int getAuctionID(){
        return auctionID;
    }

    public Dog getDog(){
        return dogInAuction;
    }

    public Bid[] getBids(){
        return bids.clone();
    }

    public boolean hasBids(){
        return bids.length > 1;
    }

    public Bid getHighestBid(){
        sortBids();
        if (this.hasBids()){
            return bids[0];
        }
        return new Bid(this, new User("Fuck"), 9999); //correct?
    }

    public void sortBids() {
        for (int indexToSort = 1; indexToSort < bids.length -1; indexToSort++){
            if (bids[indexToSort].getBidAmount() > bids[indexToSort -1].getBidAmount()){
                Bid bid = bids[indexToSort];
                bids[indexToSort] = bids[indexToSort-1];
                bids[indexToSort-1] = bid;
            }
        }
    }

    public String getBidsAsString(){
        sortBids();
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<bids.length-1;i++){
            sb.append(bids[i].getBidder().getName() + " " + bids[i].getBidAmount() + " kr");
            if (i < bids.length -2){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void removeBidsFromUser(User user){
        //System.out.println("Bid list for this auction:" + getBidsAsString());
        boolean usersBeenRemoved = false;
        while (!usersBeenRemoved){
            usersBeenRemoved = true;
            boolean bidIsFound = false;
            int counter = 0;
            Bid[] temp = new Bid[bids.length-1];
            for (int i = 0; i < bids.length-1; i++){
                if (bids[i].getBidder().equals(user) && !bidIsFound){
                    bidIsFound = true;
                    usersBeenRemoved = false;
                } else {
                    temp[counter] = bids[i];
                    counter++;
                }
            }
            bids = temp;
        }
        //System.out.println("Bid list for this auction after remove:" + getBidsAsString());
    }
}
