package android.skmaury.com.onlinequiz.Model;

/**
 * Created by kurosaki on 28/02/18.
 */

public class Category {
    private String Name;
    private String Image;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Category(String name, String image) {

        Name = name;
        Image = image;
    }

    public Category() {

    }
}
