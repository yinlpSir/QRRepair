package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.dto.trainingBuilding.QueryTrainingBuildingDTO;
import com.liuqi.machineroomrepairsystem.pojo.Lab;
import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import com.liuqi.machineroomrepairsystem.service.TrainingBuildingService;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest
public class TrainingBuildingMapperTest {
    @Autowired
    private TrainingBuildingMapper trainingBuildingMapper;
    @Autowired
    private LabMapper labMapper;
    @Autowired
    private TrainingBuildingService trainingBuildingService;

    @BeforeEach
    public void init(){
        System.out.println("init...");
    }
    @AfterEach
    public void destroy(){
        System.out.println("destroy...");
    }

    @Test
    public void getTrainingBuildingByNameTest(){
        String buildingName = "敏行楼";
        TrainingBuilding trainingBuilding = trainingBuildingMapper.getTrainingBuildingByName(buildingName);
//        System.out.println(trainingBuilding.getBuildingName());
        assertEquals(trainingBuilding.getBuildingName(),buildingName);
        assertNotNull(trainingBuilding);
    }

    @Test
    public void getAllTrainingBuildingsTest(){
        QueryTrainingBuildingDTO trainingBuildingDTO = new QueryTrainingBuildingDTO();
        trainingBuildingDTO.setBuildingName("");
        List<TrainingBuilding> trainingBuildings = trainingBuildingMapper.getTrainingBuildings2(
                trainingBuildingDTO.getCurrentPage(),
                trainingBuildingDTO.getPageSize(),
                trainingBuildingDTO.getBuildingName()
        );
        assertNotNull(trainingBuildings);
//        System.out.println(trainingBuildings.getTotal());
//        System.out.println(trainingBuildings.getData().size());
//        trainingBuildings.getData().forEach(System.out::println);

        System.out.println(trainingBuildings.size());
        trainingBuildings.forEach(System.out::println);
    }
    @Test
    public void addTrainingBuildingTest(){
        TrainingBuilding trainingBuilding = new TrainingBuilding();
        String id = UUID.randomUUID().toString().replace("-","");
        trainingBuilding.setId(id);
        trainingBuilding.setBuildingName("博文楼");
        int effectedRows = trainingBuildingMapper.addTrainingBuilding(trainingBuilding);
        assertEquals(effectedRows,1);
    }

    @Test
//    @Transactional
    public void deleteTrainingBuilding(){
        String id="t2";
        List<Lab> labs = labMapper.getLabByBuildingId(id);
        labs.forEach(System.out::println);

        int deletedRows = trainingBuildingMapper.deleteTrainingBuildingById(id);
        assertEquals(deletedRows,1);
        int effectedRows = labMapper.deleteLabByBuildingId(id);
        System.out.println(effectedRows);
    }

    @Test
    public void updateTrainingBuildingById(){
        TrainingBuilding trainingBuilding = new TrainingBuilding();
        trainingBuilding.setId("t4");
        trainingBuilding.setBuildingName("xxx");
        int effectedRows = trainingBuildingMapper.updateTrainingBuildingById(trainingBuilding);
        assertEquals(effectedRows,1);
    }
}
