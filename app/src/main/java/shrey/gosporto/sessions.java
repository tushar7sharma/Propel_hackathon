package shrey.gosporto;

/**
 * Created by tusharsharma on 27/02/16.
 */
public class sessions {

    private String start_time;
    private String end_time;
    private String steps;


    public sessions(String start_time, String end_time, String steps) {
        this.start_time = start_time;

        //this.cost = cost;
        this.end_time = end_time;
        this.steps = steps;
    }

    // getter
    public String getStart_time() { return start_time; }
    //public String getCost() { return cost; }
    public String getEnd_time() {return end_time;}
    public String getSteps() {return steps;}

}
