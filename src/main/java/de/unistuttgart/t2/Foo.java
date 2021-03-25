package de.unistuttgart.t2;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cart")
public class Foo {

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello from cart";
    }
    
    @GET
    @Path("get/{pid}")
    @Produces({ "application/json" })
    public Response getBlob(@PathParam("pid") final Long pid) {
    	// acces db, get the blob for given id
    
    	Map<String,Integer> products = new HashMap<String,Integer>();
    	
    	products.put("a", 2);
    	products.put("b", 3);
    	products.put("c", 5);
    	
        return Response.status(Response.Status.OK).entity(products).build();
    }
    
    @POST
    @Path("set/{pid}")
    @Produces({ "application/json" })
    @Consumes({ "application/json" })
    public Response setBlob(String blob, @PathParam("pid") final Long pid) {
    	// updated db 
        return Response.status(Response.Status.OK).build();
    }
}