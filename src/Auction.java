import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Auction {

    public Dog dogInAuction;
    public int auctionID;
    private static int instanceCount;
    private int highestBid;
    private Bid[] bids = new Bid[1];

    Auction(Dog dog){
        instanceCount++;
        auctionID = instanceCount;
        dogInAuction = dog;
        //register.registerDogToAuction(dog); //could be static??
    }

    public void addBid(Bid bid){
        bids[bids.length-1] = bid;
        bids = Arrays.copyOf(bids, bids.length+1);
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

    public int getHighestBid(){
        System.out.println(getBidsAsString());
        sortBids();
        System.out.println(getBidsAsString());
        if (bids.length > 1){
            highestBid = bids[0].getBidAmount();
        }else {
            highestBid = 0;
        }
        return highestBid;
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
            sb.append(bids[i].getBidAmount());
            if (i < bids.length -2){
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
