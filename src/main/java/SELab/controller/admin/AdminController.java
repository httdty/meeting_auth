package SELab.controller.admin;

import SELab.config.RemoteServiceConfig;
import SELab.request.admin.ApplicationRatifyRequest;
import SELab.service.Service;
import SELab.utility.response.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);
    private Service service;

    @Autowired
    private RemoteServiceConfig remote;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public AdminController(Service service){this.service = service;}


    @GetMapping("/admin/queueingApplication")
    public ResponseEntity<?> getqueueingApplication( @RequestHeader("authorization") String token) {
        logger.debug("Get queuing applications by admin");
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.getqueueingApplication(token));
    }

    @GetMapping("/admin/alreadyApplication")
    public ResponseEntity<?> getalreadyApplication( @RequestHeader("authorization") String token) {
        logger.debug("Get dealed applications by admin");
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.getalreadyApplication(token));
    }

    @PostMapping("/admin/ratify")
    public ResponseEntity<?> applicationRatify(@RequestBody ApplicationRatifyRequest request,  @RequestHeader("authorization") String token) {
        logger.debug("Ratification for Meeting named "+ request.getMeetingName());
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.applicationRatify(request, token));
    }


}
