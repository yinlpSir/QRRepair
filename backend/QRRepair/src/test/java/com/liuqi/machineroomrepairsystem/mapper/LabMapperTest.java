package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.Lab;
import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest
public class LabMapperTest {
    @Autowired
    private LabMapper labMapper;
    @Autowired
    private TrainingBuildingMapper trainingBuildingMapper;

    @BeforeEach
    public void init(){
        System.out.println("init...");
    }
    @AfterEach
    public void destroy(){
        System.out.println("destroy...");
    }
    @Test
    public void insertLabTest(){
        String buildingName = "敏行楼";
        TrainingBuilding trainingBuilding = trainingBuildingMapper.getTrainingBuildingByName(buildingName);
        assertNotNull(trainingBuilding);
        String labId = UUID.randomUUID().toString().replace("-", "");
//        Lab lab = new Lab(labId,"10320",trainingBuilding.getId(),"人工智能开发室",trainingBuilding);
//        int effectedRow = labMapper.insertLab(lab);
//        assertEquals(effectedRow,1);
    }
    @Test
    public void test01(){
//        if(0 || 1) System.out.println("sdf");
        List<Lab> labs = labMapper.getLabs("", "");
        labs.forEach(System.out::println);
    }
}
