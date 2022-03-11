package com.example.RentingBooks.components;

import com.example.RentingBooks.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class FileStorageComponent {
    public ResponseDto storeFile(MultipartFile multipartFile) throws IOException {
        String directoryPath=System.getProperty("user.home")+ File.separator+"RentingBook";
    File directoryFile=new File(directoryPath);
    if(!directoryFile.exists()){
        directoryFile.mkdir();
    }else {
        log.info("Already Exists");
    }
    String ext= FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    if(ext.equalsIgnoreCase("jpg")||
            ext.equalsIgnoreCase("png")||
            ext.equalsIgnoreCase("jpeg")){
        UUID uuid=UUID.randomUUID();
        String filePath=directoryPath+File.separator+uuid+"_"+multipartFile.getOriginalFilename();
        File fileToStore=new File(filePath);
        multipartFile.transferTo(fileToStore);
        return ResponseDto.builder().status(true).message(filePath).build();
    }else if(ext==""){
        return ResponseDto.builder().status(false).message("No file selected").build();
    }else
    {
        return ResponseDto.builder().status(false).message("Invalid Extensions FIle").build();
    }
    }
    public String returnFileAsBase64(String filepath) throws IOException{
        File file=new File(filepath);
        byte[] bytes= Files.readAllBytes(file.toPath());
        String base64EncodedImage="data:image/png;base64,"+ Base64.getEncoder().encodeToString(bytes);
        return base64EncodedImage;
    }
}
