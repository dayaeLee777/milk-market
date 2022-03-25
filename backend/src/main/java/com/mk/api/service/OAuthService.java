package com.mk.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mk.api.dto.request.SignUpReq;
import com.mk.api.dto.request.UserDTO;
import com.mk.config.JwtTokenProvider;
import com.mk.db.entity.User;
import com.mk.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class OAuthService {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public OAuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = passwordEncoder;
    }

    public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=86e8297334bfc2e93495d3bc96ccee21"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/api/oauth/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    @Transactional
    public SignUpReq createKakaoUser(String token) throws Exception {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송
            System.out.println(conn.getResponseMessage());
            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            String nickname ="";
            String password ="";
            String profileImage="";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
                nickname = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();
                password = tempKey();
                profileImage = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("thumbnail_image_url").getAsString();
            }
            //pwd, nick, email
            SignUpReq signUpReq = SignUpReq.builder().password(password).nickname(nickname).email(email).profileImage(profileImage).build();
            System.out.println(signUpReq.toString());
            System.out.println("id : " + id);
            System.out.println("email : " + email);
            System.out.println("nickname : "+nickname);
            log.info("profileImage"+profileImage);

            br.close();
            return signUpReq;

        } catch (IOException e) {
            e.printStackTrace();
            log.info("회원정보가 다 등록되어있지 않습니다.");
            return null;
        }


    }

    public String getNaverAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://nid.naver.com/oauth2.0/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=mzc8ntfJYjQ9Mh1MjJnb"); // TODO REST_API_KEY 입력
            sb.append("&client_secret=_nQzYLabgF");
            sb.append("&redirect_uri=http://localhost:8080/api/oauth/naver"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            sb.append("&state=url_parameter");
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    @Transactional
    public SignUpReq createNaverUser(String token) throws Exception {

        String reqURL = "https://openapi.naver.com/v1/nid/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);


            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);



            System.out.println(element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString());
            String email = "";
            String password = "";
            String nickname ="";
            email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
            password = tempKey();
            nickname = element.getAsJsonObject().get("response").getAsJsonObject().get("nickname").getAsString();



            System.out.println("email : " + email);

            SignUpReq signUpReq = SignUpReq.builder().password(password).nickname(nickname).email(email).build();
            System.out.println(signUpReq.toString());

            System.out.println("email : " + email);
            System.out.println("nickname : "+nickname);
            br.close();
            return signUpReq;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("계정정보가 올바르게 저장되지 않았습니다.");
            return null;
        }

    }

    public String tempKey(){
        String key="";
        Random random=new Random();  //난수 생성을 위한 랜덤 클래스
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }

        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key+=numIndex;
        return key;
    }

    //Login 데이터를 받고, JWT를 반환하는 메소드
    public String getToken(SignUpReq signUpReq) {

        String email = signUpReq.getEmail();
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("Optional"+user);

        //기존 가입이 아닐 때
        if(!user.isPresent()){

            log.info("회원정보가 없을 때 "+signUpReq.toString());
            UserDTO userDTO = new UserDTO(signUpReq);
            User entitiy = modelMapper.map(userDTO, User.class);
            entitiy.setPassword(passwordEncoder.encode(entitiy.getPassword()));
            userRepository.save(entitiy);
            user = userRepository.findByEmail(entitiy.getEmail());

        }

        return jwtTokenProvider.createToken(user.get(), user.get().getRoles());
    }

}