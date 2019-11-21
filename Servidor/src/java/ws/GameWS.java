
package ws;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;



@Path("2048")
public class GameWS extends Files{

    @Context
    private UriInfo context;

    public GameWS() {
    }

    @GET
    @Produces("application/json")
    public String getDir() {
        String dir = loadDir();
        Gson g = new Gson();
        String aux = g.toJson(dir);
        setDir("xxx");
        return aux;
    }
    
    @POST
    @Consumes("application/json")
    @Path("direcao")
    public void putInserir(String content) {
        setDir(content);
        //Var = content;
    }
}
