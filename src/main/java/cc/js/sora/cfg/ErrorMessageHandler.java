package cc.js.sora.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import cc.js.sora.ErrorMessage;
import cc.js.sora.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorMessageHandler {

	@ExceptionHandler(ErrorMessage.class)
	public ResponseMessage apiExceptionHandler(ErrorMessage ex) {
		log.error("Error meesage[" + ex.getCode() + "]:" + ex.getMessage());
		return ResponseMessage.errorMessage(ex);
	}

	@ExceptionHandler(Exception.class)
	public ResponseMessage apiExceptionHandler(Exception ex) {
		log.error("Error meesage:" + ex, ex);
		return new ResponseMessage(99999, "Internal server error", false);
	}

//	@Bean
//	public ObjectMapper objectMapper() {
//		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//
//	}
}
