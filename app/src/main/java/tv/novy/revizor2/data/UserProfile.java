package tv.novy.revizor2.data;

import java.util.ArrayList;

import tv.novy.revizor2.data.user.contest;
import tv.novy.revizor2.data.user.meta_checkin;
import tv.novy.revizor2.data.user.meta_comment;
import tv.novy.revizor2.data.user.meta_photo_published;
import tv.novy.revizor2.data.user.meta_photo_unpublished;
import tv.novy.revizor2.data.user.responses_checkin;
import tv.novy.revizor2.data.user.responses_comment;
import tv.novy.revizor2.data.user.responses_photo;
import tv.novy.revizor2.data.user.user_data;



/**
 * Created by NickNb on 22.09.2016.
 */
public class UserProfile {

    int status;
    boolean error;
    meta_comment meta_comment = new meta_comment();
    meta_checkin meta_checkin = new meta_checkin();

    meta_photo_published meta_photo_published = new meta_photo_published();
    meta_photo_unpublished meta_photo_unpublished = new meta_photo_unpublished();

    user_data user_data = new user_data();
    responses_photo responses_photo = new responses_photo();

    public ArrayList<responses_comment> getResponses_comment_array() {
        return responses_comment_array;
    }

    public void setResponses_comment_array(ArrayList<responses_comment> responses_comment_array) {
        this.responses_comment_array = responses_comment_array;
    }

    public ArrayList<responses_checkin> getResponses_checkin_array() {
        return responses_checkin_array;
    }

    public void setResponses_checkin_array(ArrayList<responses_checkin> responses_checkin_array) {
        this.responses_checkin_array = responses_checkin_array;
    }

    ArrayList<responses_comment> responses_comment_array = new ArrayList<responses_comment>();
    ArrayList<responses_checkin> responses_checkin_array = new ArrayList<responses_checkin>();

    contest contest = new contest();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public tv.novy.revizor2.data.user.meta_comment getMeta_comment() {
        return meta_comment;
    }

    public void setMeta_comment(tv.novy.revizor2.data.user.meta_comment meta_comment) {
        this.meta_comment = meta_comment;
    }

    public tv.novy.revizor2.data.user.meta_checkin getMeta_checkin() {
        return meta_checkin;
    }

    public void setMeta_checkin(tv.novy.revizor2.data.user.meta_checkin meta_checkin) {
        this.meta_checkin = meta_checkin;
    }

    public tv.novy.revizor2.data.user.meta_photo_published getMeta_photo_published() {
        return meta_photo_published;
    }

    public void setMeta_photo_published(tv.novy.revizor2.data.user.meta_photo_published meta_photo_published) {
        this.meta_photo_published = meta_photo_published;
    }

    public tv.novy.revizor2.data.user.meta_photo_unpublished getMeta_photo_unpublished() {
        return meta_photo_unpublished;
    }

    public void setMeta_photo_unpublished(tv.novy.revizor2.data.user.meta_photo_unpublished meta_photo_unpublished) {
        this.meta_photo_unpublished = meta_photo_unpublished;
    }

    public tv.novy.revizor2.data.user.user_data getUser_data() {
        return user_data;
    }

    public void setUser_data(tv.novy.revizor2.data.user.user_data user_data) {
        this.user_data = user_data;
    }

    public responses_photo getResponses_photo() {
        return responses_photo;
    }

    public void setResponses_photo(responses_photo responses_photo) {
        this.responses_photo = responses_photo;
    }

    public tv.novy.revizor2.data.user.contest getContest() {
        return contest;
    }

    public void setContest(tv.novy.revizor2.data.user.contest contest) {
        this.contest = contest;
    }

}
