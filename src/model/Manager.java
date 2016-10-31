package model;

/**
 * Created by Lillian on 10/2/2016.
 */
public class Manager extends Worker{
    /**
    * no args constructor
     */
    public Manager() {
    }

    /**
     * view historical reports and trends of water purity
     */
    public void viewHistoryReports() {
        System.out.println("view history reports");
    }

    /**
     * view trends of water purity
     */
    public void viewWaterTrend() {
        System.out.println("view water trends");
    }

    /**
     * delete individual reports that they deem inaccurate
     * @param report report to be deleted
     */
    public void deleteReports(Report report) {
        System.out.println("report deleted");
    }
}
