package se.simjarr.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.simjarr.model.CurrencyStash;
import se.simjarr.repository.CurrencyStashRepository;
import se.simjarr.repository.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
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
    private final UserRepository userRepository;

    @Autowired
    public StashResource(CurrencyStashRepository stashRepository, UserRepository userRepository) {
        this.stashRepository = stashRepository;
        this.userRepository = userRepository;
    }

    @GET
    public Response getAll() {
        List<CurrencyStash> hehe = (List<CurrencyStash>) stashRepository.findAll();
        return Response.ok(hehe.size()).build();
    }

    @GET
    @Path("/user/{username}")
    public Response getStashTab(@PathParam("username") String username) {
        List<CurrencyStash> stashes = stashRepository.findByUserUsername(username);
        return Response.ok(stashes).build();
    }
}
