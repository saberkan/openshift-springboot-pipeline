package saberkan.com.springboot.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.TimedSet;

@RestController
@RequestMapping("/rest")
public class EnvironmentVarsController {

	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${server.port}")
	private String serverPort;	
	
	@Value("${environment}")
	private String environment;
	
	@Value("${version}")
	private String version;
	
	
    @RequestMapping("/vars")
    public ResponseEntity<Map<String, String>> getVars() {
    	@SuppressWarnings("serial")
    	Map<String, String> vars = new HashMap<String, String>(){{
    	    put("applicationName", applicationName);
    	    put("serverPort", serverPort);
    	    put("environment", environment);
    	    put("version", version);
    	}};
    	System.out.println("ICIICI" + vars.toString());

    	return ResponseEntity.status(HttpStatus.OK)
                .body(vars);
    }
    
    @RequestMapping("/vars/{key}")
    public ResponseEntity<String> getVar(@PathVariable(value="key") String key) {
    	@SuppressWarnings("serial")
    	Map<String, String> vars = new HashMap<String, String>(){{
    	    put("applicationName", applicationName);
    	    put("serverPort", serverPort);
    	    put("environment", environment);
    	    put("version", version);
    	}};
    	
    	if(!vars.containsKey(key))
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found key : " + key);
    	return ResponseEntity.status(HttpStatus.OK)
                .body(vars.get(key));
    }


}
	