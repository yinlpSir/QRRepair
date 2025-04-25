package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.RepairRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RepairMapperTest {
    @Autowired
    private RepairMapper repairMapper;

    @Test
    public void test(){
        List<RepairRecord> repairRecords = repairMapper.getRepairRecord();
        repairRecords.forEach(repairRecord -> System.out.println(repairRecord.getFaultImages()));
//        repairMapper.getRepairRecord()
    }
    @Test
    public void updateRepairRecordTest(){
        String repairId = "aa6c73f7e87c484ca0444d3eb8f6c89f";
        byte status = 2;
        int effectedRows = repairMapper.updateRepairRecord(repairId, status);
        System.out.println(effectedRows);
        assertEquals(1,effectedRows);
    }
    @Test
    public void test01(){
        String id = "dfc2e483c2de47cb803c412ebf5faaab";
        System.out.println(repairMapper.getRepairIdByLabId(id).toString());
    }
}
