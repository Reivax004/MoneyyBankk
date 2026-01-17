package com.example.resources;
import com.example.models.ConnectionHistory;
import com.example.service.ConnectionHistoriesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/connection-histories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConnectionHistoriesResource {

    @Inject
    private ConnectionHistoriesService connectionHistoriesService;

    @GET
    @Path("/all")
    public Response list() {
        List<ConnectionHistory> connectionHistoryList =
                connectionHistoriesService.findAllConnectionHistories();
        if (connectionHistoryList.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return Response.ok(connectionHistoryList).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        List<ConnectionHistory> connectionHistoryList = connectionHistoriesService.findAllConnectionHistoriesOfUser(id);
        if (connectionHistoryList.isEmpty()) {
            throw new NotFoundException("User %d not found".formatted(id));
        }
        return Response.ok(connectionHistoryList).build();
    }
}
