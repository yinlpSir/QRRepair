package com.liuqi.machineroomrepairsystem.service;

import com.liuqi.machineroomrepairsystem.dto.repair.AddRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.dto.repair.AppletRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.dto.repair.QueryRepairRecordDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RepairService {
    /**
     * 二维码报修
     * @param addRepairRecordDTO
     * @return
     * @throws MachineRoomRepairException
     * @throws IOException
     */
    public ResponseEntity<Object> addRepairRecord(AddRepairRecordDTO addRepairRecordDTO) throws MachineRoomRepairException, IOException;

    /**
     * 小程序报修
     * @param appletRepairRecordDTO
     * @return
     */
    public ResponseEntity<Object> appletRepairRecord(AppletRepairRecordDTO appletRepairRecordDTO);

    /**
     * 上传故障图片
     * @param repairId 故障图片所属的报修ID
     * @param file 报修图片
     * @return
     */
    public ResponseEntity<Object> uploadFaultImg(String repairId, MultipartFile file) throws IOException;
    public ResponseEntity<Object> getRepairRecord(QueryRepairRecordDTO queryRepairRecordDTO);
    public ResponseEntity<Object> getRepairHistory(QueryRepairRecordDTO queryRepairRecordDTO);
    public ResponseEntity<Object> updateRepairRecord(String repairId,byte status);
    public ResponseEntity<Object> delRepairRecord(String id);
}
