import com.vividsolutions.jts.geom.Geometry;
//import java.security.MessageDigest;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class GeographicArea  {


    // byte[] ptext = String.getBytes("UTF-8")
    private String id;
    private String englishName;
    private String spanishName;
    private String frenchName;
    private String russianName;
    private String chineseName;
    private String arabicName;

    private Double pop2000;
    private Geometry WKTgeom = null;

    // TODO: make this GeoJSON?
    public String JSONString(){
        JsonObjectBuilder value = Json.createObjectBuilder()
                .add("english", englishName)
                .add("spanish", spanishName)
                .add("french", frenchName)
                .add("russian", russianName)
                .add("chinese", chineseName)
                .add("arabic", arabicName)
                .add("population", pop2000)
                .add("wkt", WKTgeom.toString());


        return value.toString();
    }

    public String ID(){
        return id;
    }

    public String EnglishName() {
        return englishName;
    }

    public String SpanishName() {
        return spanishName;
    }

    public String FrenchName() {
        return frenchName;
    }

    public String RussianName() {
        return russianName;
    }

    public String ChineseName() {
        return chineseName;
    }

    public String ArabicName() {
        return arabicName;
    }

    public Geometry Geom() {
        return WKTgeom;
    }

    public Double Population(){
        return pop2000;
    }

    public GeographicArea Population(Double pop){
        this.pop2000 = pop;
        return this;
    }

    public GeographicArea ID(String id){
        this.id = id;
        return this;
    }

    public GeographicArea EnglishName(String name) throws UnsupportedEncodingException {
        this.englishName = new String(name.getBytes("UTF-8"));
        return this;
    }

    public GeographicArea SpanishName(String name) throws UnsupportedEncodingException {
        this.spanishName = new String(name.getBytes("UTF-8"));
        return this;
    }

    public GeographicArea FrenchName(String name) throws UnsupportedEncodingException {
        this.frenchName = new String(name.getBytes("UTF-8"));
        return this;
    }

    public GeographicArea RussianhName(String name) throws UnsupportedEncodingException {
        this.russianName = new String(name.getBytes("UTF-8"));
        return this;
    }

    public GeographicArea ChineseName(String name) throws UnsupportedEncodingException {
        this.chineseName = new String(name.getBytes("UTF-8"));
        return this;
    }

    public GeographicArea ArabicName(String name) throws UnsupportedEncodingException {
        this.arabicName = new String(name.getBytes("UTF-8"));
        return this;
    }

    public GeographicArea Geom(Geometry geom) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("SHA");
        this.WKTgeom = geom;
        return this;
    }
    
}
