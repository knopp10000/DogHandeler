public class Bid {

    private Auction auction; //användning?
    private User bidder;
    private int bidAmount;

    Bid(Auction auction, User bidder, int bidAmount){
        this.auction = auction;
        this.bidder = bidder;
        this.bidAmount = bidAmount;
    }

    public User getBidder(){
        return bidder;
    }

    public int getBidAmount(){
        return bidAmount;
    }
}
