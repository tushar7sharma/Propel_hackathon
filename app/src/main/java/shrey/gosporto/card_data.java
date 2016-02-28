package shrey.gosporto;

/**
 * Created by tusharsharma on 07/07/15.
 */
public class card_data {

    private String title;
    private String venue;
    private String time;
    private String cost;
    private String sport_id;
    private String id;


    // constructor
    public card_data(String title, String venue, String time, String cost, String sport_id, String id) {
        this.title = title;
        this.venue = venue;
        this.time = time;
        this.cost = cost;
        this.sport_id = sport_id;
        this.id = id;
    }

    // getter
    public String getTitle() { return title; }
    public String getVenue() { return venue; }
    public String getTime() { return time; }
    public String getCost() { return cost; }
    public String getSport_id() {return sport_id ;}
    public String getId() {return id;}
    // setter

    public void setTitle() { this.title = title; }
    public void setVenue() { this.venue = venue; }
    public void setTime() { this.time = time; }
    public void setCost() { this.cost = cost; }
    public void setSport_id() {this.sport_id = sport_id;}
    public void setid() {this.id = id;}


}
