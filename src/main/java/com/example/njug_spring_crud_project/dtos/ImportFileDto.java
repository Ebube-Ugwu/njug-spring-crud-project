package com.example.njug_spring_crud_project.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


public final class ImportFileDto {
    private final MultipartFile file;

    public ImportFileDto(
            MultipartFile file
    ) {
        this.file = file;
    }

    public MultipartFile file() {
        return file;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ImportFileDto) obj;
        return Objects.equals(this.file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }

    @Override
    public String toString() {
        return "ImportFileDto[" +
                "file=" + file + ']';
    }
}
