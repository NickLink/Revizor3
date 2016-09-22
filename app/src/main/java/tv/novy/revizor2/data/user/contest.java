package tv.novy.revizor2.data.user;

/**
 * Created by NickNb on 22.09.2016.
 */
public class contest{
    int points;
    String award;
    long last_revision;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public long getLast_revision() {
        return last_revision;
    }

    public void setLast_revision(long last_revision) {
        this.last_revision = last_revision;
    }
}