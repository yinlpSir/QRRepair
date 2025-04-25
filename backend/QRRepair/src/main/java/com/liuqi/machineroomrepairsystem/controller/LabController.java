package com.liuqi.machineroomrepairsystem.controller;

import com.liuqi.machineroomrepairsystem.dto.lab.AddAndUpdateLabDTO;
import com.liuqi.machineroomrepairsystem.dto.lab.QueryLabDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import com.liuqi.machineroomrepairsystem.pojo.Lab;
import com.liuqi.machineroomrepairsystem.service.LabService;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 实训室管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/lab")
public class LabController {
    @Autowired
    private LabService labService;

    @GetMapping
    public ResponseEntity<Object> getLabs(QueryLabDTO queryLabDTO){
        log.info(String.format("查询实训室参数:%s",queryLabDTO.toString()));

        PagingVO<Lab> labPagingVO = labService.getLabs(queryLabDTO);
        return ResponseEntity.ok(this.buildResult("查询成功!",labPagingVO));
    }

    @GetMapping("/byBuildingName")
    @Validated
    public ResponseEntity<Object> getLabByBuildingName(@NotBlank(message = "实训楼不能为空!") String buildingName){
        log.info(String.format("查询 %s 的所有实训室",buildingName));

        List<Lab> labs = labService.getLabByBuildingName(buildingName);
        return ResponseEntity.ok(this.buildResult("查询成功!",labs));
    }

    @GetMapping("/byLabName")
    @Validated
    public ResponseEntity<Object> getLabByLabName(@NotBlank(message = "实训室不能为空!") String labName){
        log.info(String.format("查询 %s 实训室",labName));

        Lab lab = labService.getLabByLabName(labName);
        lab.setBuildingId(null);
        return ResponseEntity.ok(this.buildResult("查询成功!",lab));
    }

    @PostMapping
    public ResponseEntity<Object> addLab(@RequestBody @Valid AddAndUpdateLabDTO addLabDto) throws MachineRoomRepairException {
        log.info(String.format("添加实训室：%s",addLabDto.toString()));

        if(labService.addLab(addLabDto)){
            var result = new HashMap<>();
            result.put("msg","添加成功!");
            return ResponseEntity.ok(result);
        }
        throw new MachineRoomRepairException("添加失败");
    }

    @PutMapping
    public ResponseEntity<Object> updateLab(@RequestBody @Valid AddAndUpdateLabDTO updateLabDto) throws MachineRoomRepairException {
        log.info(String.format("修改实训室参数：%s",updateLabDto.toString()));

        if(Objects.isNull(updateLabDto.getId()) || updateLabDto.getId().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResult("实训室ID不能为空!",null));
        }
        if(labService.updateLab(updateLabDto)){
            return ResponseEntity.ok(buildResult("修改成功!",null));
        }
        throw new MachineRoomRepairException("修改实训室失败!");
    }

    @DeleteMapping("/{labId}")
    public ResponseEntity<Object> delLab(@PathVariable String labId) {
        log.info(String.format("删除实训室,ID: %s",labId));

        if(Objects.isNull(labId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResult("实训室ID不能为空!",null));
        }
        labService.delLab(labId);
        return ResponseEntity.ok(this.buildResult("删除成功!",null));
    }

    private HashMap<String,Object> buildResult(String message, @Nullable Object data){
        var result = new HashMap<String,Object>();
        result.put("msg",message);
        result.put("data",data);
        return result;
    }
}
