package tv.novy.revizor2.data.user;

/**
 * Created by NickNb on 22.09.2016.
 */
public class meta_photo{
    meta_photo_published meta_photo_published = new meta_photo_published();
    meta_photo_unpublished meta_photo_unpublished = new meta_photo_unpublished();

    public meta_photo_published getMeta_photo_published() {
        return meta_photo_published;
    }

    public void setMeta_photo_published(meta_photo_published meta_photo_published) {
        this.meta_photo_published = meta_photo_published;
    }

    public meta_photo_unpublished getMeta_photo_unpublished() {
        return meta_photo_unpublished;
    }

    public void setMeta_photo_unpublished(meta_photo_unpublished meta_photo_unpublished) {
        this.meta_photo_unpublished = meta_photo_unpublished;
    }
}