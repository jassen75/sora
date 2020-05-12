package cc.js.sora;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
	protected int code;
	protected String message;
	protected boolean success;
	
	
	public static ResponseMessage successMessage() {
		return new ResponseMessage(0, "operation success", true);
		
	}
	
	public static ResponseMessage errorMessage(ErrorMessage error) {
		return new ResponseMessage(error.getCode(), error.getMessage(), false);
		
	}

}
