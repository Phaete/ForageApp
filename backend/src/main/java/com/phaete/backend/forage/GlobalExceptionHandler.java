package com.phaete.backend.forage;

import com.phaete.backend.forage.model.ForageMapItemNotFoundException;
import com.phaete.backend.forage.model.ForageWikiItemNotFoundException;
import com.phaete.backend.forage.model.InvalidAuthenticationException;
import com.phaete.backend.forage.model.MarkerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	/**
	 * Handles exceptions of type {@link MarkerNotFoundException}, {@link ForageWikiItemNotFoundException}, and
	 * {@link ForageMapItemNotFoundException}.
	 * <p>
	 * These exceptions are thrown when a marker, forage wiki item, or forage map item with the given id is not found.
	 * <p>
	 * A {@link ResponseEntity} containing a string with name of the exception class, the error message and the
	 * stack trace is returned.
	 * The HTTP status code is set to 404 (NOT FOUND).
	 *
	 * @param e the exception to be handled
	 * @return a ResponseEntity containing the error message and stack trace
	 */
	@ExceptionHandler(
			{MarkerNotFoundException.class, ForageWikiItemNotFoundException.class, ForageMapItemNotFoundException.class}
	)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleNotFoundExceptions(Exception e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(
				String.format("%s: %s with Stacktrace:%n %s",
					e.getClass().getSimpleName(),
					e.getMessage(),
					Arrays.toString(e.getStackTrace())
				), org.springframework.http.HttpStatus.NOT_FOUND);
		logger.error(responseEntity.getBody());
		return responseEntity;
	}

	/**
	 * Handles exceptions of type {@link InvalidAuthenticationException}.
	 * <p>
	 * These exceptions are thrown when a user tries to access a resource that does not belong to them.
	 * <p>
	 * A {@link ResponseEntity} containing a string with name of the exception class, the error message and the
	 * stack trace is returned.
	 * The HTTP status code is set to 401 (UNAUTHORIZED).
	 *
	 * @param e the exception to be handled
	 * @return a ResponseEntity containing the error message and stack trace
	 */
	@ExceptionHandler(InvalidAuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<String> handleInvalidAuthenticationException(InvalidAuthenticationException e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(String.format("%s: %s with Stacktrace:%n %s",
				e.getClass().getSimpleName(),
				e.getMessage(),
				Arrays.toString(e.getStackTrace())), HttpStatus.UNAUTHORIZED);
		logger.error(responseEntity.getBody());
		return responseEntity;
	}
}
