package app.com.example.shalan.popualrmovies.Model;

/**
 * Created by noura on 16/04/2017.
 */

public class Trailer {
    String name ;
    String Key ;
    String Site ;

    public Trailer(String name, String key) {
        this.name = name;
        Key = key;
    }

    public Trailer(String name, String key, String site) {
        this.name = name;
        Key = key;
        Site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }
}
