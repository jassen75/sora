package cc.js.sora.space.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/systemInfo")
public class SystemInfoController {
	
	@Value("${sora.version}")
	private String version;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	Map getSystemInfo()
	{
		Map systemInfo = new HashMap();
		systemInfo.put("version", version);
		return systemInfo;
	}

}
