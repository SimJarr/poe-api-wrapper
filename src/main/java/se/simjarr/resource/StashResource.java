package se.simjarr.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.simjarr.model.CurrencyStash;
import se.simjarr.repository.CurrencyStashRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Path("currency-stash")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StashResource {

    @Context
    private HttpHeaders httpHeaders;

    @Context
    private UriInfo uriInfo;

    private final CurrencyStashRepository stashRepository;

    @Autowired
    public StashResource(CurrencyStashRepository stashRepository) {
        this.stashRepository = stashRepository;
    }

    @GET
    public Response getAll() {
        List<CurrencyStash> stashes = (List<CurrencyStash>) stashRepository.findAll();
        return Response.ok(stashes).build();
    }

    @GET
    @Path("/size")
    public Response getSize() {
        List<CurrencyStash> stashes = (List<CurrencyStash>) stashRepository.findAll();
        return Response.ok(stashes.size()).build();
    }

    @GET
    @Path("/user/{username}")
    public Response getUserTabs(@PathParam("username") String username) {
        List<CurrencyStash> stashes = stashRepository.findByUserUsername(username);
        return Response.ok(stashes).build();
    }
}
