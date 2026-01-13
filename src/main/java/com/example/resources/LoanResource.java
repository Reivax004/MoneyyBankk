package com.example.resources;

import com.example.service.LoanService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/loans")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoanResource {

    @Inject
    private LoanService loanService;

    public static class LoanRequestBody {
        public Integer userId;
        public Double amount;
    }

    @POST
    @Path("/request")
    public Response requestLoan(LoanRequestBody body) {
        if (body == null || body.userId == null || body.amount == null) {
            throw new BadRequestException("userId and amount are required");
        }
        return Response.ok(loanService.requestLoan(body.userId, body.amount)).build();
    }
}
