package com.framework.app.common.service;

import com.framework.app.common.exception.BizException;
import com.framework.app.common.service.dto.FileInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class CommService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadImagePath;

    public FileInfoDto uploadFile(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String mimeType = file.getContentType();

        //최대용량 체크
//        if (file.getSize() > FileConstant.MAX_FILE_SIZE) {
//            throw new FileUploadException("10MB 이하 파일만 업로드 할 수 있습니다.");
//        }


        //MIMETYPE 체크
//        if (!FileUtil.isImageFile(mimeType)) {
//            throw new FileUploadException("이미지 파일만 업로드할 수 있습니다.");
//        }

        //저장 파일명을 중복방지 고유명으로 변경
        String newFileName = generateUniqueFileName(originalFileName);
        Path filePath = Paths.get(uploadImagePath, newFileName);


        //서버 내부 스토리지에 업로드
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new BizException("File upload exception. ");
        }

        FileInfoDto FileInfoDto = new FileInfoDto();
        FileInfoDto.setOriginalFilename(originalFileName);
        FileInfoDto.setFilePath("/images/" + newFileName);

        if(log.isInfoEnabled()) {
            log.debug("******************************************************");
            log.debug("* 파일 업로드 !");
            log.debug("* originalFileName => {}", originalFileName);
            log.debug("* New file => {}", FileInfoDto.getFilePath());
            log.debug("******************************************************");
        }

        return FileInfoDto;
    }

    /**
     * [중복방지를 위한 파일 고유명 생성]
     * @return String 파일 고유이름
     */
    private String generateUniqueFileName(String originalFileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // Random 객체 생성
        Random random = new Random();
        // 0 이상 100 미만의 랜덤한 정수 반환
        String randomNumber = Integer.toString(random.nextInt(Integer.MAX_VALUE));
        String timeStamp = dateFormat.format(new Date());
        return timeStamp + randomNumber + originalFileName;
    }

}
