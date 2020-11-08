package SELab.controller.meeting;


import SELab.config.RemoteServiceConfig;
import SELab.domain.Meeting;
import SELab.domain.PCMemberRelation;
import SELab.exception.MeetingOfNoExistenceException;
import SELab.exception.MeetingStatusUnAvailableToReviewException;
//import SELab.repository.ArticleRepository;
import SELab.repository.MeetingRepository;
import SELab.repository.PCMemberRelationRepository;
import SELab.request.admin.ApplicationRatifyRequest;
import SELab.request.meeting.BeginSubmissionRequest;
import SELab.request.meeting.GetByNameRequest;
import SELab.service.Service;
import SELab.utility.response.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class MeetingSelfController {
    Logger logger = LoggerFactory.getLogger(MeetingArticleController.class);

    private Service service;

    @Autowired
    private PCMemberRelationRepository pcMemberRelationRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private RemoteServiceConfig remote;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    public MeetingSelfController(Service service) { this.service = service; }
//
//    @GetMapping("/meeting/queueingApplication")
//    public ResponseEntity<?> queueingApplication() {
//        logger.debug("Get queuing meeting by admin");
//        return ResponseEntity.ok(service.queueingApplication());
//    }
//
//    @GetMapping("/meeting/alreadyApplication")
//    public ResponseEntity<?> alreadyApplication() {
//        logger.debug("Get dealed metting by admin");
//        return ResponseEntity.ok(service.alreadyApplication());
//    }
//
//    @PostMapping("/meeting/ratify")
//    public ResponseEntity<?> mt_applicationRatify(@RequestBody ApplicationRatifyRequest request) {
//        logger.debug("Ratification for Meeting named "+ request.getMeetingName());
//        return ResponseEntity.ok(service.mt_applicationRatify(request));
//    }

/////////////////////////////////////////////////////////////////////
    public HttpStatus checkToken(String token){
        String checkApi = remote.getCheck(); //检查token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        return resp.getStatusCode();// 检查结束
    }

    @GetMapping("/meeting/getByName")
    public ResponseEntity<?> mt_getByName(String meetingName, @RequestHeader("authorization") String token) {
        logger.debug("Get Meeting By Name: " + meetingName);

        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查

        Meeting meeting = meetingRepository.findByMeetingName(meetingName);

        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/meeting/getById")
    public ResponseEntity<?> mt_getById(long meetingId, @RequestHeader("authorization") String token) {
        logger.debug("Get Meeting By Id: " + meetingId);
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        Meeting meeting = meetingRepository.findById(meetingId);
        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/meeting/getByChairName")
    public ResponseEntity<?> mt_getByChairName(String chairName, @RequestHeader("authorization") String token) {
        logger.debug("Get Meeting By chairName: " + chairName);
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        List<Meeting> meetingList = meetingRepository.findByChairName(chairName);
        return ResponseEntity.ok(meetingList);
    }


    @GetMapping("/meeting/getByStatusAndChairNameNot")
    public ResponseEntity<?> mt_getByStatusAndChairNameNot(String status, String chairName, @RequestHeader("authorization") String token) {
        logger.debug("Get Meeting By chairName NOT and status NOT: " + chairName);
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        List<Meeting> meetingList = meetingRepository.findByStatusAndChairNameNot(status, chairName);
        return ResponseEntity.ok(meetingList);
    }

    @PostMapping("/meeting/save")
    public ResponseEntity<?> mt_save_post(@RequestBody Meeting meeting, @RequestHeader("authorization") String token) {
        logger.debug("mt_save");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        meetingRepository.save(meeting);
        return ResponseEntity.ok(meeting);
    }

    @PutMapping("/meeting/save")
    public ResponseEntity<?> mt_save_put(@RequestBody Meeting meeting, @RequestHeader("authorization") String token) {
        logger.debug("mt_save");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        meetingRepository.save(meeting);
        return ResponseEntity.ok(meeting);
    }


    @GetMapping("/meeting/pcMemberRelation/getByMeetingId")
    public ResponseEntity<?> mt_pcrelation_getByMeetingId(long meetingId, @RequestHeader("authorization") String token) {
        logger.debug("Get PCMemberRelation By MeetingId: " + meetingId);
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        List<PCMemberRelation> pcMemberRelations = pcMemberRelationRepository.findByMeetingId(meetingId);
        return ResponseEntity.ok(pcMemberRelations);
    }

    @GetMapping("/meeting/pcMemberRelation/getByPcmemberIdAndStatus")
    public ResponseEntity<?> mt_pcrelation_getByPcmemberIdAndStatus(long pcmemberId, String status, @RequestHeader("authorization") String token) {
        logger.debug("getByPcmemberIdAndStatus");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        List<PCMemberRelation> pcMemberRelations = pcMemberRelationRepository.findByPcmemberIdAndStatus(pcmemberId, status);
        return ResponseEntity.ok(pcMemberRelations);
    }

    @GetMapping("/meeting/pcMemberRelation/getByPcmemberIdAndMeetingId")
    public ResponseEntity<?> mt_pcrelation_getByPcmemberIdAndMeetingId(long pcmemberId, long meetingId, @RequestHeader("authorization") String token) {
        logger.debug("getByPcmemberIdAndMeetingId");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        List<PCMemberRelation> pcMemberRelations = pcMemberRelationRepository.findByPcmemberIdAndMeetingId(pcmemberId, meetingId);
        return ResponseEntity.ok(pcMemberRelations);
    }

    @GetMapping("/meeting/pcMemberRelation/getByMeetingIdAndStatus")
    public ResponseEntity<?> mt_pcrelation_getByMeetingIdAndStatus(long meetingId,String status, @RequestHeader("authorization") String token) {
        logger.debug("getByMeetingIdAndStatus");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        List<PCMemberRelation> pcMemberRelations = pcMemberRelationRepository.findByMeetingIdAndStatus(meetingId, status);
        return ResponseEntity.ok(pcMemberRelations);
    }

    @GetMapping("/meeting/pcMemberRelation/getByPcmemberIdAndStatusNot")
    public ResponseEntity<?> mt_pcrelation_getByPcmemberIdAndStatusNot(long pcmemberId, String status, @RequestHeader("authorization") String token) {
        logger.debug("getByPcmemberIdAndStatusNot");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        List<PCMemberRelation> pcMemberRelations = pcMemberRelationRepository.findByPcmemberIdAndStatusNot(pcmemberId, status);
        return ResponseEntity.ok(pcMemberRelations);
    }
    @PutMapping("/meeting/pcMemberRelation/save")
    public ResponseEntity<?> mt_pcrelation_save_put(@RequestBody PCMemberRelation relation, @RequestHeader("authorization") String token) {
        logger.debug("pcMemberRelation save");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        pcMemberRelationRepository.save(relation);
        return ResponseEntity.ok(relation);
    }
    @PostMapping("/meeting/pcMemberRelation/save")
    public ResponseEntity<?> mt_pcrelation_save_post(@RequestBody PCMemberRelation relation, @RequestHeader("authorization") String token) {
        logger.debug("pcMemberRelation save");
        if(this.checkToken(token)!= HttpStatus.OK){ return ResponseEntity.badRequest().build(); } // 检查
        pcMemberRelationRepository.save(relation);
        return ResponseEntity.ok(relation);
    }





}
