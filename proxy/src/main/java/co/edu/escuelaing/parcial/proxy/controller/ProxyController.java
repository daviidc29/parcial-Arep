package co.edu.escuelaing.parcial.proxy.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

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
        return delegate(()-> proxyService.flinearSearch(value));
    }

    @GetMapping("/linearSearch")
    public ResponseEntity<String> binarySearch( String value){
        return delegate(()-> proxyService.fbinarySearch(value));
    }

    public ResponseEntity<String> delegate(){
        try {
            return 
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    

    
}
