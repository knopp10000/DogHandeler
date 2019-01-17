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

    public Auction(Dog dog){
        instanceCount++;
        auctionID = instanceCount;
        dogInAuction = dog;
    }

    public void addBid(Bid bid){
        removeBidsFromUser(bid.getBidder());
        bids[bids.length-1] = bid;
        if (bids[0] == null){
            bids[0] = bids[1];
        }
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

    public String getThreeBiggestBidsAsString(){
        sortBids();
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<bids.length-1 && i < 3;i++){
            sb.append(bids[i].getBidder().getName() + " " + bids[i].getBidAmount() + " kr");
            if (i < bids.length -2){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void removeBidsFromUser(User user){
        boolean bidIsFound = false;
        int counter = 0;
        Bid[] temp = new Bid[bids.length];
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] != null) {
                if (bids[i].getBidder().equals(user) && !bidIsFound) {
                    bidIsFound = true;
                } else {
                    temp[counter] = bids[i];
                    counter++;
                }
            }
        }
        if (bidIsFound) {
            bids = Arrays.copyOf(temp, temp.length - 1);
        }
    }
}
