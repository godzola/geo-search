import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.sql.*;

public class GeoSearchManager {

    private HashMap<String, GeographicArea> areaCache;
    private NameSearcher nameSearcher;
    private STRTreeSearcher rstreeSearcher;

    private final static String bigQuery =
            "select gid, " +
            "       name_engli, " +
            "       name_local, " +
            "       name_frenc, " +
            "       name_spani, " +
            "       name_russi, " +
            "       name_arabi, " +
            "       name_chine, " +
            "       ST_AsEWKT(geom) as wkt" +
            "from gadm0 " +
            "limit 10";

    public GeoSearchManager(){
        nameSearcher = new NameSearcher();
        rstreeSearcher = new STRTreeSearcher();
        LoadData();
    }

    private void LoadData(){
        String url = "jdbc:postgresql://localhost/gadm0?user=scot";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        WKTReader reader = new WKTReader();

        try{
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
                                            .Geom(reader.read(rs.getString("wkt")));


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

    public static void main(String[] args) {

    }

}
