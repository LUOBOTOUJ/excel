package com.vitality;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class Controller {

    @PostMapping("/reserve/add")
    public void add() {
            ExcelService excelService = new ExcelService();
            try {
                excelService.insertByExcel("/Users/egoshiten/Desktop/aaa");
            }catch (Exception e){

            }
    }
}

