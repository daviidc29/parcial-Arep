package co.edu.escuelaing.parcial.proxy.controller;

import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.escuelaing.parcial.proxy.service.ProxyService;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {
    private final ProxyService proxyService;

    public ProxyController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/linearSearch")
    public ResponseEntity<String> linearSearch( String value){
        return delegate(()-> {
            try {
                return proxyService.flinearSearch(value);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @GetMapping("/linearSearch")
    public ResponseEntity<String> binarySearch( String value){
        return delegate(()-> {
            try {
                return proxyService.fbinarySearch(value);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public ResponseEntity<String> delegate(ProxyCall proxy){
        try {
            return ResponseEntity.ok()
                    .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(proxy.execute());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error: ",e.getMessage()).toString());
        }

    }

    @FunctionalInterface
    private interface ProxyCall {
        String execute();
        
    }
    

    
}
