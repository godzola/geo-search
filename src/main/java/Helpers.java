import java.io.UnsupportedEncodingException;

/**
 * Created by scot on 11/5/16.
 */
public class Helpers {

    public static String UTF8Normalize(String in){
        String out = null;
        if(in != null) {
            try {
//                System.out.println("UTF8 Normalize: >>" + in + "<<");
                out = new String(in.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                out = null;
            }
        }
        return out;
    }
}
