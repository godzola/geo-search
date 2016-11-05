
public class FakeMain {
    public static void main(String[] args) {
        //Kabul
        double lat = 34.553;
        double lon = 69.057;
        GeoSearchManager gsm = new GeoSearchManager();
        System.out.println("Афганистан --> " + gsm.NameLookup("Афганистан"));
        System.out.println("阿富汗 --> " + gsm.NameLookup("阿富汗"));
        System.out.println("Afganistán --> " + gsm.NameLookup("Afganistán"));
        System.out.println("Afghanistan --> " + gsm.NameLookup("Afghanistan"));
        System.out.println("Afghanistan --> " + gsm.NameLookup("Afghanistan"));
        System.out.println("Афганистан --> " + gsm.NameLookup("Афганистан"));

        // funny R2L printing...
        System.out.print("أفغانستان");
        System.out.print(" --> ");
        System.out.println(gsm.NameLookup("أفغانستان"));

        System.out.println("Lat Lon Query --> " + gsm.LatLonLookup(lat, lon));

    }
}
