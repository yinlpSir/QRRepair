package com.liuqi.machineroomrepairsystem.controller;

import com.liuqi.machineroomrepairsystem.dto.trainingBuilding.QueryTrainingBuildingDTO;
import com.liuqi.machineroomrepairsystem.dto.trainingBuilding.UpdateTrainingBuildingDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import com.liuqi.machineroomrepairsystem.service.TrainingBuildingService;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 实训楼管理控制器
 */
@RestController
@RequestMapping("/TBuilding")
public class TrainingBuildingController {
    @Autowired
    private TrainingBuildingService trainingBuildingService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTrainingBuildings(){
        List<TrainingBuilding> trainingBuildings = trainingBuildingService.getAllTrainingBuildings();
        return ResponseEntity.ok(this.buildResult("查询成功!",trainingBuildings));
    }

    @GetMapping
    public ResponseEntity<Object> getTrainingBuildings(QueryTrainingBuildingDTO queryTrainingBuildingDTO){
        PagingVO<TrainingBuilding> trainingBuildings = trainingBuildingService.getTrainingBuildings(queryTrainingBuildingDTO);
        return ResponseEntity.ok(this.buildResult("查询成功!",trainingBuildings));
    }

    @PostMapping
    public ResponseEntity<Object> addTrainingBuilding(@RequestParam String buildingName) throws MachineRoomRepairException {
        if(Objects.isNull(buildingName) || buildingName.trim().length() == 0) throw new IllegalArgumentException("实训楼名称不能为空!");
        if(trainingBuildingService.addTrainingBuilding(buildingName)){
            return ResponseEntity.ok(this.buildResult("添加成功!",null));
        }
        throw new MachineRoomRepairException("添加失败");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delTrainingBuilding(@PathVariable String id) throws MachineRoomRepairException {
        if(trainingBuildingService.delTrainingBuilding(id)){
            return ResponseEntity.ok(this.buildResult("删除成功!",null));
        }
        throw new MachineRoomRepairException("删除失败,请检查您传入的id!");
    }

    @PutMapping
    public ResponseEntity<Object> updateTrainingBuilding(@RequestBody @Valid UpdateTrainingBuildingDTO updateTrainingBuildingDTO) throws MachineRoomRepairException {
        if(trainingBuildingService.updateTrainingBuilding(updateTrainingBuildingDTO)){
            return ResponseEntity.ok(this.buildResult("更新成功!",null));
        }
        throw new MachineRoomRepairException("更新失败,请检查您传入的id!");
    }

    private HashMap<String,Object> buildResult(String message,@Nullable Object data){
        var result = new HashMap<String,Object>();
        result.put("msg",message);
        result.put("data",data);
        return result;
    }
}
