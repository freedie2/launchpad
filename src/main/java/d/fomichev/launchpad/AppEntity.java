package d.fomichev.launchpad;

public class AppEntity {
    private final String name;
    private final String iconPath;


    public AppEntity(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public String getName() { return name; }
    public String getIconPath() { return iconPath; }
}
