package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.other.ServicesStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServicesDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String description;
    @NotNull
    private LocalDateTime date;
    private MultipartFile banner;
    private MultipartFile preview;
    private List<PhotoDTO> photos = new ArrayList<>();
    private ServicesStatus status;
    private PhotoDTO bannerPhotoDTO;
    private PhotoDTO previewPhotoDTO;
}
