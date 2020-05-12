package cc.js.sora.cfg;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cc.js.sora.ErrorMessage;
import cc.js.sora.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorMessageHandler {

	@ExceptionHandler(ErrorMessage.class)
	public ResponseMessage apiExceptionHandler(ErrorMessage ex) {
		log.error("Error meesage:"+ex.getMessage());
		return ResponseMessage.errorMessage(ex);
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseMessage apiExceptionHandler(Exception ex) {
		log.error("Error meesage:"+ex, ex);
		return new ResponseMessage(99999, "Internal server error", false);
	}
}
