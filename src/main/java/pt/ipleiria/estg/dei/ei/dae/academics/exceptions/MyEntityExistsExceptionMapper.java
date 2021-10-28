package pt.ipleiria.estg.dei.ei.dae.academics.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyEntityExistsExceptionMapper implements ExceptionMapper<MyEntityExistsException> {
    private static final Logger logger = Logger.getLogger("exceptions.MyEntityExistsExceptionMapper");

    @Override
    public Response toResponse(MyEntityExistsException e){
        String errorMessage = e.getMessage();
        logger.warning("ERROR: " + errorMessage);
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
    }
}
