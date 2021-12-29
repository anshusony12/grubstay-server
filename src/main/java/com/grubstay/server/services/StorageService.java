package com.grubstay.server.services;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    private final String deploymentPath="target/classes/";
    private final String localWorkingPath="src/main/resources/";
    private final String rootLocation= Paths.get(localWorkingPath+"static/image/").toString();
    private final String cityPath="/city";


    public void storeCity(MultipartFile file) throws Exception{
        try{
            File savePath = ResourceUtils.getFile(this.rootLocation+cityPath);
            Path path = Paths.get(savePath.getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    public Resource getCityFile(String filename) throws Exception{
        try{
            Path path = Paths.get(this.rootLocation+cityPath);
            Path file = path.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
