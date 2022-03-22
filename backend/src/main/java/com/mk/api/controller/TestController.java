package com.mk.api.controller;

import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.api.dto.response.BaseResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Controller
@RestController
@RequestMapping("/api/test")
public class TestController {

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<? extends BaseResponseDto> regist(
            @RequestPart(value = "file", required = false) List<MultipartFile> file,
            @RequestPart(value ="dto", required = true) ItemRegisterRequestDto dto){
        log.info("dto = " + dto);
        log.info("file =" + file.get(0));

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));

    }
}
