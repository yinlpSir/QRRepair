package com.liuqi.machineroomrepairsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuqi.machineroomrepairsystem.dto.repair.AddRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.dto.repair.AppletRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.dto.repair.QueryRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import com.liuqi.machineroomrepairsystem.mapper.FaultImageMapper;
import com.liuqi.machineroomrepairsystem.mapper.LabMapper;
import com.liuqi.machineroomrepairsystem.mapper.RepairMapper;
import com.liuqi.machineroomrepairsystem.mapper.TrainingBuildingMapper;
import com.liuqi.machineroomrepairsystem.pojo.FaultImage;
import com.liuqi.machineroomrepairsystem.pojo.Lab;
import com.liuqi.machineroomrepairsystem.pojo.RepairRecord;
import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import com.liuqi.machineroomrepairsystem.service.RepairService;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service("repairService")
public class RepairServiceImpl implements RepairService {
    @Autowired
    private TrainingBuildingMapper trainingBuildingMapper;

    @Autowired
    private LabMapper labMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private FaultImageMapper faultImageMapper;

    @Override
    @Transactional // 注意：默认是发生 运行时异常 才回滚.所以可以将自定义异常继承 运行时异常
    public ResponseEntity<Object> addRepairRecord(AddRepairRecordDTO addRepairRecordDTO) throws MachineRoomRepairException, IOException {

//        if(addRepairRecordDTO.getFiles()[0].getOriginalFilename().endsWith(".png")){}

        TrainingBuilding trainingBuilding = trainingBuildingMapper.getTrainingBuildingByName(addRepairRecordDTO.getBuildingName());
        if(Objects.isNull(trainingBuilding)) {
            throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"未知的实训楼");
        }

        Lab lab = labMapper.getLabByLabNameAndBuildingId(addRepairRecordDTO.getLabName(),trainingBuilding.getId());
        if(Objects.isNull(lab)) {
            throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"未知的实训室");
        }else if(addRepairRecordDTO.getEquipmentNo() > lab.getAvailableEquipment()){
            throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"无效的电脑编号");
        }

        RepairRecord repairRecord = new RepairRecord();
        BeanUtils.copyProperties(addRepairRecordDTO,repairRecord);
        String repairId = UUID.randomUUID().toString().replace("-", "");
        repairRecord.setId(repairId);
        repairRecord.setBuildingId(trainingBuilding.getId());
        repairRecord.setLabId(lab.getId());

        log.info(String.format(" %s 添加报修记录：%s", LocalDateTime.now(),repairRecord.toString()));
        int addRepairRecordEffectedRow = repairMapper.addRepairRecord(repairRecord);
        if(addRepairRecordEffectedRow>0){
            // 添加故障图片
            MultipartFile[] files = addRepairRecordDTO.getFiles();
            for (MultipartFile file : files){
                log.info(String.format("报修故障图片名:%s 大小:%d 文件类型:%s",file.getOriginalFilename(),file.getSize(),file.getContentType()));
                String imageId = UUID.randomUUID().toString().replace("-", "");
//                file.transferTo(new File("d:\\test-data",file.getOriginalFilename()));
                int rows = faultImageMapper.insertImage(imageId,file.getInputStream(),repairRecord.getId());
            }
            return ResponseEntity.ok(buildResult("报修成功!",null));
        }
        throw new MachineRoomRepairException("因未知错误而报修失败,请联系管理员!");
    }

    @Override
    public ResponseEntity<Object> appletRepairRecord(AppletRepairRecordDTO appletRepairRecordDTO) {
        TrainingBuilding trainingBuilding = trainingBuildingMapper.getTrainingBuildingByName(appletRepairRecordDTO.getBuildingName());
        if(Objects.isNull(trainingBuilding)) {
            throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"未知的实训楼");
        }

        Lab lab = labMapper.getLabByLabNameAndBuildingId(appletRepairRecordDTO.getLabName(),trainingBuilding.getId());
        if(Objects.isNull(lab)) {
            throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"未知的实训室");
        }else if(appletRepairRecordDTO.getEquipmentNo() > lab.getAvailableEquipment()){
            throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"无效的电脑编号");
        }

        RepairRecord repairRecord = new RepairRecord();
        BeanUtils.copyProperties(appletRepairRecordDTO,repairRecord);
        String repairId = UUID.randomUUID().toString().replace("-", "");
        repairRecord.setId(repairId);
        repairRecord.setBuildingId(trainingBuilding.getId());
        repairRecord.setLabId(lab.getId());

        log.info(String.format(" %s 添加报修记录：%s", LocalDateTime.now(),repairRecord.toString()));
        int addRepairRecordEffectedRow = repairMapper.addRepairRecord(repairRecord);
        var result = new HashMap<String,String>();
        result.put("repairId",repairId);
        if(addRepairRecordEffectedRow > 0) return ResponseEntity.status(HttpStatus.OK).body(buildResult("报修成功!",result));
        throw new MachineRoomRepairException("因未知错误而报修失败,请联系管理员处理!");
    }

    @Override
    public ResponseEntity<Object> uploadFaultImg(String repairId, MultipartFile file) throws IOException {
        log.info(String.format("报修故障图片名:%s 大小:%d 文件类型:%s",file.getOriginalFilename(),file.getSize(),file.getContentType()));
        String imageId = UUID.randomUUID().toString().replace("-", "");
        // 插入故障图片
        int effectedRow = faultImageMapper.insertImage(imageId,file.getInputStream(),repairId);
        if(effectedRow > 0) return ResponseEntity.ok(buildResult("添加图片成功!",null));
        throw new MachineRoomRepairException("添加图片失败");
    }

    @Override
    public ResponseEntity<Object> getRepairRecord(QueryRepairRecordDTO queryRepairRecordDTO) {
        List<RepairRecord> repairRecords = repairMapper.getRepairRecord();
        repairRecords.forEach(repairRecord -> {
            List<FaultImage> faultImages = repairRecord.getFaultImages();
            if(Objects.nonNull(faultImages)){
                faultImages.forEach(image -> {
                    try {
                        // 将 数据库中读取出的二进制图片 Base64 编码为 字符串类型
                        String imageToString = Base64.getEncoder().encodeToString(image.getImage().readAllBytes());
                        image.setImageToString(imageToString);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        return ResponseEntity.ok(buildResult("查询成功！",repairRecords));
    }

    @Override
    public ResponseEntity<Object> getRepairHistory(QueryRepairRecordDTO queryRepairRecordDTO) {
        PageHelper.startPage(queryRepairRecordDTO.getCurrentPage(),queryRepairRecordDTO.getPageSize());

        List<RepairRecord> repairRecords = repairMapper.getRepairHistory(queryRepairRecordDTO.getBuildingName());

        PageInfo<RepairRecord> pageInfo = new PageInfo<>(repairRecords);

        pageInfo.getList().forEach(repairRecord -> {
            List<FaultImage> faultImages = repairRecord.getFaultImages();
            if(Objects.nonNull(faultImages)){
                faultImages.forEach(image -> {
                    try {
                        // 将 数据库中读取出的二进制图片 Base64 编码为 字符串类型
                        String imageToString = Base64.getEncoder().encodeToString(image.getImage().readAllBytes());
                        image.setImageToString(imageToString);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });

        PagingVO<RepairRecord> recordPagingVO=new PagingVO<>();
        recordPagingVO.setTotalPages(pageInfo.getPages());
        recordPagingVO.setCurrentPage(pageInfo.getPageNum());
        recordPagingVO.setPageSize(pageInfo.getPageSize());
        recordPagingVO.setTotal(pageInfo.getTotal());
        recordPagingVO.setData(pageInfo.getList());
        return ResponseEntity.ok(buildResult("查询成功！",recordPagingVO));
    }

    @Override
    public ResponseEntity<Object> updateRepairRecord(String repairId, byte status) {
        String msg = "";
        if(status == 1){
            // 维修中
            int effectedRows = repairMapper.updateRepairRecord(repairId, (byte) 2);
            msg = "操作成功!";
        }else {
            // 维修完成
            int effectedRows = repairMapper.updateRepairRecord(repairId, (byte) 3);
            msg = "维修完成!";
        }
        return ResponseEntity.ok(buildResult(msg,null));
    }

    @Override
    public ResponseEntity<Object> delRepairRecord(String id) {
        int repairEffectedRow = repairMapper.delRepairRecordById(id);
        int imageEffectedRow = faultImageMapper.delImageByRepairId(id);
        return ResponseEntity.ok(buildResult("删除成功!",null));
    }

    private HashMap<String,Object> buildResult(String message, @Nullable Object data){
        var result = new HashMap<String,Object>();
        result.put("msg",message);
        result.put("data",data);
        return result;
    }
}
