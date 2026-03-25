package co.edu.escuelaing.parcial.list.controller;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.escuelaing.parcial.list.service.ListService;

@RestController
@RequestMapping("/api/list")
public class ListController {

    private final ListService listService;
    private final String instance;
    public ListController(co.edu.escuelaing.parcial.list.service.ListService listService, @Value("${instance.name:list}") String instance) {
        this.listService = listService;
        this.instance = instance;
    }
    
    @GetMapping("/")
    public ResponseEntity<?> linearSearch(@RequestParam ArrayList list){
        String reult = listService.linearSearch(list);
        try {
            return HttpResponse.ok(Http)
        } catch (Exception e) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(Map.of("Server","bad"));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> binarySearch(){

    }
    
}
