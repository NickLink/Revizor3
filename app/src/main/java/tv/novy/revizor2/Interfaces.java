package tv.novy.revizor2;

/**
 * Created by NickNb on 12.09.2016.
 */
public interface Interfaces {
    void OnRegionSelected(int region_id, String region_name);
    void OnCitySelected(int region_id, String keyword);
    void BackToCity();
    void BackToRegion();
    void OpenClose();
    void BackToPlacesP1();
    void Places_selectes(int i);

    void SocialToken(int net, String token);
}
