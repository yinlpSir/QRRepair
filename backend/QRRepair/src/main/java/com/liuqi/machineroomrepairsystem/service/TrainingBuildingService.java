package com.liuqi.machineroomrepairsystem.service;

import com.liuqi.machineroomrepairsystem.dto.trainingBuilding.QueryTrainingBuildingDTO;
import com.liuqi.machineroomrepairsystem.dto.trainingBuilding.UpdateTrainingBuildingDTO;
import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;

import java.util.List;

public interface TrainingBuildingService {
    public PagingVO<TrainingBuilding> getTrainingBuildings(QueryTrainingBuildingDTO queryTrainingBuildingDTO);

    /**
     * 获取所有 实训楼
     * @return
     */
    List<TrainingBuilding> getAllTrainingBuildings();
    public boolean addTrainingBuilding(String buildingName);
    public boolean delTrainingBuilding(String id);
    public boolean updateTrainingBuilding(UpdateTrainingBuildingDTO updateTrainingBuildingDTO);
}
