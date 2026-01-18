package com.example.resources;

import com.example.service.LoanService;
import com.example.responses.SuccessResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/loans")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoanResource {

    @Inject
    private LoanService loanService;

    @Context
    private SecurityContext securityContext;

    public static class LoanRequestBody {
        public Double amount;
    }

    @POST
    @Path("/request")
    public Response requestLoan(LoanRequestBody body) {

        if (body == null || body.amount == null) {
            throw new BadRequestException("Amount is required");
        }

        if (securityContext.getUserPrincipal() == null) {
            throw new NotAuthorizedException("Not authenticated");
        }

        String email = securityContext.getUserPrincipal().getName();

        Object loanResult = loanService.requestLoan(email, body.amount);

        return SuccessResponse.of("Loan requested successfully", loanResult);
    }
}
