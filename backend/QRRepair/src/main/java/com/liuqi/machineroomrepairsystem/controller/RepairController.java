package com.liuqi.machineroomrepairsystem.controller;

import com.liuqi.machineroomrepairsystem.dto.repair.AddRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.dto.repair.AppletRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.dto.repair.QueryRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import com.liuqi.machineroomrepairsystem.service.RepairService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 报修控制器
 */
@Slf4j
@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    /**
     * 由于一些原因，这里变为 二维码报修接口
     * @param addRepairRecordDTO
     * @return
     * @throws MachineRoomRepairException
     * @throws IOException
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addRepair(@Valid AddRepairRecordDTO addRepairRecordDTO) throws MachineRoomRepairException, IOException {
        log.info("二维码报修!");
        return repairService.addRepairRecord(addRepairRecordDTO);
    }

    /**
     * 小程序报修接口
     * @param appletRepairRecordDTO
     * @return
     */
    @PostMapping("/appletRepair")
    public ResponseEntity<Object> appletRepair(@RequestBody @Valid AppletRepairRecordDTO appletRepairRecordDTO){
        log.info("小程序报修!");
        return repairService.appletRepairRecord(appletRepairRecordDTO);
    }

    /**
     *  微信小程序端只能只支持单文件上传，所以需要反复调用这个接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @Validated
    public ResponseEntity<Object> uploadRepairImg(@RequestParam @NotBlank(message = "报修ID不能为空") String repairId,
                                                  @RequestParam @NotNull(message = "文件不能为空") MultipartFile file) throws IOException {
        if(repairId.trim().equals(""))  throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"报修ID不能为空");
        return repairService.uploadFaultImg(repairId,file);
    }

    @GetMapping
    public ResponseEntity<Object> getRepair(QueryRepairRecordDTO queryRepairRecordDTO) {
        log.info("查询报修记录!");
        return repairService.getRepairRecord(queryRepairRecordDTO);
    }

    @GetMapping("/repairHistory")
    public ResponseEntity<Object> getRepairHistory(QueryRepairRecordDTO queryRepairRecordDTO) {
        log.info("分页条件查询报修历史记录!");
        return repairService.getRepairHistory(queryRepairRecordDTO);
    }

    /**
     * 报修受理接口
     * @param repairId 报修ID
     * @param status 报修记录的状态
     * @return
     */
    @PutMapping
    @Validated
    public ResponseEntity<Object> updateRepair(@NotBlank(message = "ID不能为空") String repairId,
                                               @Range(min = 1,max = 2,message = "状态错误!") byte status) {
        log.info("受理报修!");
        return repairService.updateRepairRecord(repairId,status);
    }

    @DeleteMapping("/{id}")
    @Validated
    public ResponseEntity<Object> deleteRepair(@PathVariable @NotBlank(message = "ID不能为空") String id) {
        log.info(String.format("删除ID为:%s的报修记录!",id));
        return repairService.delRepairRecord(id);
    }

}
