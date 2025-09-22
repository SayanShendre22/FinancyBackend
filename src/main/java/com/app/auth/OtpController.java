package com.app.auth;
import org.apache.catalina.loader.ResourceEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8082")
public class OtpController {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    OtpRepo otpRepo;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        String otp = String.format("%06d", new Random().nextInt(999999)); // 6-digit OTP

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\nIt will expire in 5 minutes.");

        mailSender.send(message);
        
        // TODO: Save OTP in DB or in-memory store (Redis) with expiry time
        if(otpRepo.findByEmail(email)!=null) {
        	//update otp
        	OtpModel entity =otpRepo.findByEmail(email);
            entity.setOtp(otp);
            entity.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP valid 5 mins
            otpRepo.save(entity);
        }else {
        	//create otp
        	OtpModel entity = new OtpModel();
            entity.setEmail(email);
            entity.setOtp(otp);
            entity.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP valid 5 mins
            otpRepo.save(entity);
        }
        
       
        return new ResponseEntity<String>("sended" + email,HttpStatus.OK);
    }
    
    @PostMapping("/validateOtp")
    public ResponseEntity<Boolean> validateOtp(@RequestBody OtpModel enteredOTP) {
    	OtpModel entity =otpRepo.findByEmail(enteredOTP.getEmail());
    	System.out.println("here"+enteredOTP);
        if (entity!=null) {
            if (entity.getExpiryTime().isBefore(LocalDateTime.now())) return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Boolean>(entity.getOtp().equals(enteredOTP.getOtp()),HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        
    }
}
