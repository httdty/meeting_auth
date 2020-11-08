package SELab.controller.meeting;

import SELab.config.RemoteServiceConfig;
import SELab.request.meeting.BeginReviewRequest;
import SELab.request.meeting.BeginSubmissionRequest;
import SELab.request.meeting.ResultPublishRequest;
import SELab.request.meeting.ReviewRequest;
import SELab.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class MeetingArticleController {
    Logger logger = LoggerFactory.getLogger(MeetingArticleController.class);

    private Service service;

    @Autowired
    public MeetingArticleController(Service service) { this.service = service; }

    @Autowired
    private RemoteServiceConfig remote;

    @Autowired
    private RestTemplate restTemplate;



    @PostMapping("/meeting/beginSubmission")
    public ResponseEntity<?> beginSubmission(@RequestBody BeginSubmissionRequest request, @RequestHeader("authorization") String token) {
        logger.debug("Begin Submission: " + request.toString());
        String checkApi = remote.getCheck();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.beginSubmission(request, token));
    }

    @GetMapping("/meeting/reviewArticles")
    public ResponseEntity<?> getInfoOfReview(String pcMemberName,String meetingName, @RequestHeader("authorization") String token) {
        logger.debug("Get review information: " + meetingName + " " + pcMemberName);
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.getInfoOfReview(pcMemberName,meetingName, token));
    }

    @GetMapping("/meeting/reviewArticle")
    public ResponseEntity<?> getInfoOfArticleToReview(String pcMemberName,String articleId, @RequestHeader("authorization") String token) {
        logger.debug("Get Article information: " + articleId + " Reviewer: " + pcMemberName);
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.getInfoOfArticleToReview(pcMemberName,articleId, token));
    }

    @PostMapping("/meeting/reviewer")
    public ResponseEntity<?> review(@RequestBody ReviewRequest request, @RequestHeader("authorization") String token) {
        logger.debug("Review: " + request.toString());
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.review(request, token));
    }

    @GetMapping("/meeting/alreadyReviewedInfo")
    public ResponseEntity<?> getAlreadyReviewedInfo(String pcMemberName,String articleId, @RequestHeader("authorization") String token) {
        logger.debug("Get Review information: " + articleId + " Reviewer: " + pcMemberName);
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.getAlreadyReviewedInfo(pcMemberName,articleId, token));
    }

    @PostMapping("/meeting/beginReview")
    public ResponseEntity<?> beginReview(@RequestBody BeginReviewRequest request, @RequestHeader("authorization") String token) {
        logger.debug("Begin Review: " + request.toString());
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.beginReview(request, token));
    }

    @PostMapping("/meeting/publish")
    public ResponseEntity<?> reviewPublish(@RequestBody ResultPublishRequest request, @RequestHeader("authorization") String token) {
        logger.debug("Review Request to Publish: " + request.toString());
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if(resp.getStatusCode()!= HttpStatus.OK){
            return ResponseEntity.badRequest().build();
        }// 检查结束
        return ResponseEntity.ok(service.reviewPublish(request, token));
    }
}
