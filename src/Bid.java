public class Bid {

    /**
     * Karl Gustafsson kagu9654
     * Har ej samarbetat med någon
     **/

    private Auction auction; //användning?
    private User bidder;
    private int bidAmount;

    Bid(Auction auction, User bidder, int bidAmount){
        this.auction = auction;
        this.bidder = bidder;
        this.bidAmount = bidAmount;
    }

    Bid(){
        this.bidAmount = 0;
    }

    public User getBidder(){
        return bidder;
    }

    public int getBidAmount(){
        return bidAmount;
    }

    public void setBidAmount(int bidAmount){
        this.bidAmount = bidAmount;
    }
}
