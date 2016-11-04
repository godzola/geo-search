import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.index.strtree.STRtree;

import java.util.ArrayList;
import java.util.List;

public class STRTreeSearcher {

    private STRtree strTree;
    private GeometryFactory gf;

    public STRTreeSearcher(){
        strTree = new STRtree();
        gf = new GeometryFactory();
    }

    public void InsertGeographicArea (GeographicArea geo){
        strTree.insert(geo.Geom().getEnvelopeInternal(), geo);
    }

    public ArrayList<GeographicArea> LatLonQuery(double lat, double lon){
        ArrayList<GeographicArea> areasContainingPoint = new ArrayList<GeographicArea>();
        Coordinate c = new Coordinate(lat, lon);
        Envelope e = new Envelope(c);
        final Point p = gf.createPoint(c);

        // TODO: make this a lambda?
        List<GeographicArea> candidates = strTree.query(e);
        for(GeographicArea candidate : candidates) {
            if(candidate.Geom().contains(p)) {
                areasContainingPoint.add(candidate);
            }
        }

        return areasContainingPoint;
    }

    public Integer NumAreas(){
        return strTree.size();
    }
}
