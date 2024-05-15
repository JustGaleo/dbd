package com.justgaleo.dbd.ldn;

import com.justgaleo.dbd.models.entity.PerkCat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ImageProcessorPerks {
	

    public List<PerkCat> processImages(String folderPath) {
        List<PerkCat> entityList = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            Arrays.stream(files)
                    .filter(file -> file.isFile() && isImage(file.getName()))
                    .forEach(file -> {
                    	PerkCat entity = processImageFile(file);
                        if (entity.getNombre() != null) {
                            entityList.add(entity);
                        }
                    });
        } else {
            System.out.println("Folder is empty or does not exist.");
        }
        return entityList;
    }

    private PerkCat processImageFile(File imageFile) {
        try {
            String name = imageFile.getName();
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            name = name.substring(0, name.lastIndexOf('.'));
            PerkCat entity = new PerkCat();
            entity.setId(1L);
            entity.setNombre(name);
            entity.setPicture(imageBytes);

            System.out.println("Saved entity: " + name);
            return entity;
        } catch (IOException e) {
            System.err.println("Failed to process image file: " + imageFile.getName());
            e.printStackTrace();
            return null;
        }
    }

    private boolean isImage(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
    }
}