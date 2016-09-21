package tv.novy.revizor2.data;

import java.util.ArrayList;

/**
 * Created by NickNb on 12.09.2016.
 */
public class Inv_RegionFull{
    int total;
    ArrayList<Inv_RegionData> region_list = new ArrayList<Inv_RegionData>();
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public ArrayList<Inv_RegionData> getRegion_list() {
        return region_list;
    }
    public void setRegion_list(ArrayList<Inv_RegionData> region_list) {
        this.region_list = region_list;
    }
}
