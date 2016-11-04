import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.TreeSet;


public class NameSearcher {

    private HashMap<String,  TreeSet<GeographicArea>> nameToGeo;

    public NameSearcher(){
        nameToGeo = new HashMap<String, TreeSet<GeographicArea>>();
    }

    public Boolean AddNameIDPair(String n, String id)  {
        Boolean success = true;

        String name = UTF8Normalize(n);
        if(name != null) {
            TreeSet update = nameToGeo.get(name);
            update.add(id);
            nameToGeo.put(name, update);
        }
        else{
            success = false;
        }
        return success;
    }

    public TreeSet<GeographicArea> getIDsForName(String n){
        String name = UTF8Normalize(n);
        if(name != null){
           return nameToGeo.get(name);
        }
        return null;
    }

    private String UTF8Normalize(String in){
        String out;
        try {
            out = new String(in.getBytes("UTF-8"));
        }
        catch(UnsupportedEncodingException e){
            out = null;
        }
        return out;

    }
}
