package tv.novy.revizor2.data.user;

import java.util.ArrayList;

/**
 * Created by NickNb on 22.09.2016.
 */
public class responses_photo {
    ArrayList<responses_photo_published> responses_photo_published = new ArrayList<responses_photo_published>();
    ArrayList<responses_photo_unpublished> responses_photo_unpublished = new ArrayList<responses_photo_unpublished>();

    public ArrayList<responses_photo_published> getResponses_photo_published() {
        return responses_photo_published;
    }

    public void setResponses_photo_published(ArrayList<responses_photo_published> responses_photo_published) {
        this.responses_photo_published = responses_photo_published;
    }

    public ArrayList<responses_photo_unpublished> getResponses_photo_unpublished() {
        return responses_photo_unpublished;
    }

    public void setResponses_photo_unpublished(ArrayList<responses_photo_unpublished> responses_photo_unpublished) {
        this.responses_photo_unpublished = responses_photo_unpublished;
    }
}