package se.magnus.util.http;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import se.magnus.api.exceptions.BadRequestException;
import se.magnus.api.exceptions.InvalidInputException;
import se.magnus.api.exceptions.NotFoundException;

/*
 * I have created a set of Java exceptions in the util project that are used by both the API implementations and the API clients, initially InvalidInputException and NotFoundException. Look into the
Java package se.magnus.util.exceptions for details.
To separate protocol-specific error handling from the business logic in the REST controllers, that is,
the API implementations, I have created a utility class, GlobalControllerExceptionHandler.java,
in the util project, which is annotated as @RestControllerAdvice.
For each Java exception that the API implementations throw, the utility class has an exception handler
method that maps the Java exception to a proper HTTP response, that is, with a proper HTTP status
and HTTP response body.
For example, if an API implementation class throws InvalidInputException, the utility class will
map it to an HTTP response with the status code set to 422 (UNPROCESSABLE_ENTITY). 
 */
@RestControllerAdvice
class GlobalControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

  /*
   * To allow springdoc-openapi to also correctly document 400 (BAD_REQUEST)
   * errors that
   * Spring WebFlux generates when it discovers incorrect input arguments in a
   * request, we have also added
   * an @ExceptionHandler for 400 (BAD_REQUEST) errors
   */
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public @ResponseBody HttpErrorInfo handleBadRequestExceptions(
      ServerHttpRequest request, BadRequestException ex) {

    return createHttpErrorInfo(BAD_REQUEST, request, ex);
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public @ResponseBody HttpErrorInfo handleNotFoundExceptions(
      ServerHttpRequest request, NotFoundException ex) {

    return createHttpErrorInfo(NOT_FOUND, request, ex);
  }

  @ResponseStatus(UNPROCESSABLE_ENTITY)
  @ExceptionHandler(InvalidInputException.class)
  public @ResponseBody HttpErrorInfo handleInvalidInputException(
      ServerHttpRequest request, InvalidInputException ex) {

    return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
  }

  private HttpErrorInfo createHttpErrorInfo(
      HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {

    final String path = request.getPath().pathWithinApplication().value();
    final String message = ex.getMessage();

    LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
    return new HttpErrorInfo(httpStatus, path, message);
  }
}
