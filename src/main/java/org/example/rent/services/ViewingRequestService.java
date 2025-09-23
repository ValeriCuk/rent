package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.entity.ViewingRequest;
import org.example.rent.exceptions.ExcelExportException;
import org.example.rent.exceptions.InvalidStatusException;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.ViewingRequestStatus;
import org.example.rent.repositories.interfaces.ViewingRequestRepository;
import org.example.rent.services.mappers.ViewingRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewingRequestService {

    private final ViewingRequestRepository viewingRequestRepository;
    private final ViewingRequestMapper viewingRequestMapper;
    private final Logger log = CustomLogger.getLog();

    public ViewingRequestService(ViewingRequestRepository viewingRequestRepository, ViewingRequestMapper viewingRequestMapper) {
        this.viewingRequestRepository = viewingRequestRepository;
        this.viewingRequestMapper = viewingRequestMapper;
    }

    //getById(Long id)
    public ViewingRequestDTO getById(Long id) {
        ViewingRequest viewingRequest = viewingRequestRepository.findById(id).orElseThrow(() -> new NotFoundException("Viewing Request with id: " + id + " not found"));
        log.info("Get viewingRequest with id: " + id);
        return viewingRequestMapper.toDtoWithRelations(viewingRequest);
    }

    //getAll()
    public List<ViewingRequestDTO> getAll() {
        List<ViewingRequest> viewingRequests = viewingRequestRepository.findAll();
        log.info("Get all viewingRequests, size: " + viewingRequests.size());
        return viewingRequests.stream().map(viewingRequestMapper::toDtoWithRelations).collect(Collectors.toList());
    }

    //save(DTO dto)
    @Transactional
    public void save(ViewingRequestDTO viewingRequestDTO) {
        ViewingRequest viewingRequest = viewingRequestMapper.toEntityWithRelations(viewingRequestDTO);
        viewingRequestRepository.save(viewingRequest);
        log.info("Save viewingRequest with id: " + viewingRequest.getId());
    }

    //deleteAll()
    @Transactional
    public void deleteAll(){
        viewingRequestRepository.deleteAll();
        log.info("Delete all viewingRequests");
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!viewingRequestRepository.existsById(id))
            throw new NotFoundException("Viewing Request with id: " + id + " not found");
        viewingRequestRepository.deleteById(id);
        log.info("Delete viewing request with id: " + id);
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, ViewingRequestDTO viewingRequestDTO) {
        if (!viewingRequestRepository.existsById(id))
            throw new NotFoundException("Viewing Request with id: " + id + " not found");
        ViewingRequest viewingRequest = viewingRequestMapper.toEntityWithRelations(viewingRequestDTO);
        viewingRequest.setId(id);
        viewingRequestRepository.save(viewingRequest);
        log.info("Update viewing request with id: " + id);
    }

    //updateStatus(Long id, String status)
    @Transactional
    public void updateStatus(Long id, String status){
        ViewingRequestStatus newStatus = parseStatus(status);
        ViewingRequest viewingRequest = viewingRequestRepository.findById(id).orElseThrow(() -> new NotFoundException("Viewing Request with id: " + id + " not found"));
        viewingRequest.setStatus(newStatus);
        viewingRequestRepository.save(viewingRequest);
        log.info("Update status of viewing request with id: " + id);
    }

    private ViewingRequestStatus parseStatus(String status){
        try {
            return ViewingRequestStatus.valueOf(status.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new InvalidStatusException("Invalid viewing request status: " + status);
        }
    }

    //exportToExcel()
    public byte[] exportToExcel() {
        try(XSSFWorkbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()){
            XSSFSheet sheet = workbook.createSheet("Viewing Requests");

            createHeaderRow(sheet);
            fillDataRows(sheet);

            workbook.write(out);

            return out.toByteArray();
        }catch (IOException e){
            throw new ExcelExportException("Помилка при створенні Excel-файлу", e);
        }
    }

    private void createHeaderRow(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Phone");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Comments");
        headerRow.createCell(5).setCellValue("Date");
        headerRow.createCell(6).setCellValue("Status");
    }

    private void fillDataRows(XSSFSheet sheet) {
        List<ViewingRequest> requests = viewingRequestRepository.findAll();
        int rowIndex = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        for (ViewingRequest vr : requests) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(vr.getId());
            row.createCell(1).setCellValue(vr.getUser().getUsername());
            row.createCell(2).setCellValue(vr.getUser().getPhone());
            row.createCell(3).setCellValue(vr.getUser().getEmail());
            row.createCell(4).setCellValue(vr.getComments());
            //date formater
            LocalDateTime localDateTime = vr.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            row.createCell(5).setCellValue(localDateTime.format(formatter));
            row.createCell(6).setCellValue(vr.getStatus().toString());
        }
    }

}
