package shrey.gosporto;

public class Card {
    private String title;
    private String venue;
    private String time;
    private String cost;
    private String sport_id;
    //private String id;

    public Card(String title, String venue, String time, String cost, String sport_id) {

        this.title = title;
        this.venue = venue;
        this.time = time;
        this.cost = cost;
        this.sport_id = sport_id;
        //this.id = id;
    }

    public String getTitle() { return title; }
    public String getVenue() { return venue; }
    public String getTime() { return time; }
    public String getCost() { return cost; }
    public String getSport_id() {return sport_id ;}
    //public String getId() {return id;}
}
