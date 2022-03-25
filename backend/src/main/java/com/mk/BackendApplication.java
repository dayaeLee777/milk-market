package com.mk;



import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class BackendApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(BackendApplication.class, args);
		
//		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
//        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
//        pbeEnc.setPassword("password");
//
//        String enc = pbeEnc.encrypt("webfirewood");
//        System.out.println("enc = " + enc);
//
//        String des = pbeEnc.decrypt(enc);
//        System.out.println("des = " + des);
	}
}
