package com.grubstay.server.services;

import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FilenameUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class StorageService {

    /*private final String deploymentPath="target/classes/";
    private final String localWorkingPath="src/main/resources/";*/

    @Value("${storage.path}")
    private String storagePath;

    private final String rootLocation= Paths.get(storagePath+"static/image/").toString();
    private final String cityPath="/city";
    private final String pgPath="/pg";
    private final String landMarkPath="/landmark";

    public String getWorkingPath(){
        return storagePath;
    }

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

    public void storePg(MultipartFile file) throws Exception{
        try{
            File savePath = ResourceUtils.getFile(this.rootLocation+pgPath);
            Path path = Paths.get(savePath.getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    public void storeLandMark(MultipartFile file) throws Exception{
        try{
            File savePath = ResourceUtils.getFile(this.rootLocation+landMarkPath);
            Path path = Paths.get(savePath.getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<String> loadAllCityImage() throws Exception{
        List<String> imageList=new ArrayList<>();
        String cityImagePath=this.rootLocation+this.cityPath;
        File fileFolder=new File(cityImagePath);
        if(fileFolder!=null){
            for(final File file: fileFolder.listFiles()){
                if(!file.isDirectory()){
                    String encodedImage=null;
                    try{
                        String extension=FilenameUtils.getExtension(file.getName());
                        FileInputStream fileInputStream=new FileInputStream(file);
                        byte[] bytes= new byte[(int)file.length()];
                        fileInputStream.read(bytes);
                        encodedImage= Base64.getEncoder().encodeToString(bytes);
                        imageList.add("data:images/"+extension+";base64,"+encodedImage);
                        fileInputStream.close();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
        return imageList;
    }

    public String getImageSrc(File file) throws Exception{
        String encodedImage=null;
        try{
            String extension=FilenameUtils.getExtension(file.getName());
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[] bytes= new byte[(int)file.length()];
            fileInputStream.read(bytes);
            encodedImage= Base64.getEncoder().encodeToString(bytes);
            fileInputStream.close();
            return "data:images/"+extension+";base64,"+encodedImage;
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public String getCityRootPath(){
        String cityImagePath=this.rootLocation+this.cityPath;
        return cityImagePath;
    }
    public String getPgRootPath(){
        String pgImagesPath=this.rootLocation+this.pgPath;
        return pgImagesPath;
    }
    public String getLandMarkPath(){
        String landMarkImagePath=this.rootLocation+this.landMarkPath;
        return landMarkImagePath;
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
