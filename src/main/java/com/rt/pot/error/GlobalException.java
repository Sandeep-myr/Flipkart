package com.rt.pot.error;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalException {

	// =======================================================================================
	// IF EMAIL ID IS ALREADY EXIST THEN THIS TYPE EXCEPTION HANDLED BY THIS METHOD

	@ExceptionHandler(EmailIdAreAlreadyExist.class)
	public ResponseEntity<ExceptionModel> emailIdAreAlreadyExist(Exception ex) {

		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.BAD_REQUEST);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
	}

	// =================================================================================================

	// AT THE TIME OF LOGIN IF EMAIL ID IS DOES NOT EXIST THEN THIS EXCEPTION IS
	// THROW

	@ExceptionHandler(EmailIdDoesNotExist.class)
	public ResponseEntity<ExceptionModel> emailIdDoesNotExist(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.NOT_FOUND);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
	}

	// =============================================================================================

	// AT THE TIME OF LOGIN IF USERNAME AND PASSWORD DOES NOT EXIST THEN THIS
	// EXCEPTION IS THROWN

	@ExceptionHandler(InvalidCredentials.class)
	public ResponseEntity<ExceptionModel> invalidCredentials(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.UNAUTHORIZED);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.UNAUTHORIZED);
	}

	// =========================================================================================

	// WHEN ADMIN DATA ARE NOT STORED INTO THE SESSION THEN THIS METHOD HANDLE THE
	// EXCEPTION

	@ExceptionHandler(NotLogin.class)
	public ResponseEntity<ExceptionModel> notLogin(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.UNAUTHORIZED);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.UNAUTHORIZED);
	}

	// =========================================================================================

	// CUSTOMER SEARCHING THE PRODUCT WITH SOME KEY WORD IF IT IS NOT FETCH ANY
	// PRODUCT WITH THAT KEYWORD THEN THIS EXCEPTION IS HANDLE THE SITUATION
	@ExceptionHandler(NoProductFoundWithThisName.class)
	public ResponseEntity<ExceptionModel> noProductFoundWithThisName(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.NOT_FOUND);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
	}

	// =================================================================================================

	// AT THE TIME OF SAVING THE DATA ANY EXCEPTION OCCURED HANDLED BY THIS METHOD

	@ExceptionHandler(SavingException.class)
	public ResponseEntity<ExceptionModel> savingDataException(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.BAD_REQUEST);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
	}

//=======================================================================================================

	// AT THE TIME OF GETTING THE DATA ANY EXCEPTION IS OCCURED THEN THIS METHOD
	// HANDLE THE EXCEPTION

	@ExceptionHandler(DataGettingException.class)
	public ResponseEntity<ExceptionModel> dataGettingException(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.BAD_REQUEST);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
	}

//=======================================================================================================

	// IF SUCH TYPE OF URL DOES NOT EXIST IN CONTROLLER THEN IT IS THROW THAT TYPE
	// EXCEPTION
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ExceptionModel> noUrlFound(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.NOT_FOUND);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
	}

//=======================================================================================================

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ExceptionModel> missingServletRequestParameterException(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.NOT_FOUND);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
	}

//=======================================================================================================

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<ExceptionModel> invalidDataAccessApiUsageException(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.NOT_FOUND);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
	}

	// =======================================================================================================

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionModel> httpRequestMethodNotSupportedException(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.NOT_FOUND);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
	}

	// =======================================================================================================

	@ExceptionHandler(PotException.class)
	public ResponseEntity<ExceptionModel> controllerException(Exception ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setHttpStatus(HttpStatus.BAD_REQUEST);
		exceptionModel.setMessage(ex.getMessage());
		return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
	}

}
