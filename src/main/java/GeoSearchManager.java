import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKTReader;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import java.util.TreeSet;

public class GeoSearchManager {

    private HashMap<String, GeographicArea> areaCache;
    private NameSearcher nameSearcher;
    private STRTreeSearcher strTreeSearcher;
    private GeometryFactory gf;

    private WKBReader wkbReader;

    private final static String bigQuery =
            "select gid, " +
            "       pop2000, " +
            "       name_engli, " +
            "       name_local, " +
            "       name_frenc, " +
            "       name_spani, " +
            "       name_russi, " +
            "       name_arabi, " +
            "       name_chine, " +
            "       ST_AsBinary(geom) as wkb " +
            "from gadm0 " +
            "limit 10";

    public GeoSearchManager(){
        areaCache = new HashMap<String, GeographicArea>();
        nameSearcher = new NameSearcher();
        strTreeSearcher = new STRTreeSearcher();
        gf = new GeometryFactory(new PrecisionModel(), 4326);
        wkbReader = new WKBReader(gf);
        LoadData();
    }

    private void LoadData(){
        String url = "jdbc:postgresql://localhost/gadm0?user=scot";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            System.out.println(bigQuery);
            conn = DriverManager.getConnection(url);
            st = conn.createStatement();
            rs = st.executeQuery(bigQuery);

            while(rs.next()){
                GeographicArea geo = new GeographicArea()
                                            .ID(rs.getString("gid"))
                                            .ArabicName(rs.getString("name_arabi"))
                                            .ChineseName(rs.getString("name_chine"))
                                            .EnglishName(rs.getString("name_engli"))
                                            .FrenchName(rs.getString("name_frenc"))
                                            .RussianhName(rs.getString("name_russi"))
                                            .SpanishName(rs.getString("name_spani"))
                                            .Population(rs.getDouble("pop2000"))
                                            .Geom(wkbReader.read(rs.getBytes("wkb")));

                //
                // Odd state of affairs,
                // the STRTree search will return a list of areas, but
                // the name search will return a list of IDs which need to
                // be looked up in the cache to get the areas.
                //
                // We might want to make smaller objects to store in
                // the data structures, and only return the full info if the
                // caller asks for it, once we go to a REST interface.
                //
                strTreeSearcher.InsertGeographicArea(geo);

                nameSearcher.AddNameIDPair(geo.ArabicName(), geo.ID());
                nameSearcher.AddNameIDPair(geo.EnglishName(), geo.ID());
                nameSearcher.AddNameIDPair(geo.FrenchName(), geo.ID());
                nameSearcher.AddNameIDPair(geo.ChineseName(), geo.ID());
                nameSearcher.AddNameIDPair(geo.RussianName(), geo.ID());
                nameSearcher.AddNameIDPair(geo.SpanishName(), geo.ID());

                areaCache.put(geo.ID(), geo);
            }
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
            System.exit(1);
        }
        catch(UnsupportedEncodingException uee){
            uee.printStackTrace();
            System.exit(2);
        }
        catch(ParseException pe){
            pe.printStackTrace();
            System.exit(3);
        }
        catch(NoSuchAlgorithmException nsae){
            nsae.printStackTrace();
            System.exit(4);
        }


    }

    public String LatLonLookup(double lat, double lon){
        StringBuilder sb = new StringBuilder();
        ArrayList<GeographicArea> resp = strTreeSearcher.LatLonQuery(lat, lon);
        if(resp != null && resp.size() > 0) {
            for (GeographicArea geo : resp) {
                sb.append(geo.JSONString()).append("\n");
            }
            return sb.toString();
        }
        return "No Results";
    }

    public String NameLookup(String name){
        StringBuilder sb = new StringBuilder();
        TreeSet<String> resp = nameSearcher.NameQuery(name);
        if(resp != null && resp.size() > 0) {
            for (String id : resp) {
                sb.append(areaCache.get(id).JSONString()).append("\n");
            }
            return sb.toString();
        }
        return "No Results";
    }
}
