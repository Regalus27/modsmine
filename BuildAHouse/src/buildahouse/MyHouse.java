package buildahouse;
public class MyHouse {
    public static void build_me() {
        int width = 7;
        int height = 7;
        int count = 10;

        while (count > 0 ) {
            BuildAHouse.buildMyHouse(width, height);
            count = count -1;
        }
    }
}