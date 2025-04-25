package com.liuqi.machineroomrepairsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuqi.machineroomrepairsystem.dto.trainingBuilding.QueryTrainingBuildingDTO;
import com.liuqi.machineroomrepairsystem.dto.trainingBuilding.UpdateTrainingBuildingDTO;
import com.liuqi.machineroomrepairsystem.mapper.LabMapper;
import com.liuqi.machineroomrepairsystem.mapper.TrainingBuildingMapper;
import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import com.liuqi.machineroomrepairsystem.service.LabService;
import com.liuqi.machineroomrepairsystem.service.TrainingBuildingService;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service("trainingBuildingService")
public class TrainingBuildingServiceImpl implements TrainingBuildingService {

    @Autowired
    private LabService labService;

    @Autowired
    private TrainingBuildingMapper trainingBuildingMapper;

    @Autowired
    private LabMapper labMapper;

    @Override
    public PagingVO<TrainingBuilding> getTrainingBuildings(QueryTrainingBuildingDTO queryTrainingBuildingDTO) {

        PageHelper.startPage(queryTrainingBuildingDTO.getCurrentPage(),queryTrainingBuildingDTO.getPageSize());

        List<TrainingBuilding> trainingBuildings = trainingBuildingMapper.getTrainingBuildings(queryTrainingBuildingDTO.getBuildingName());

        PageInfo<TrainingBuilding> pageInfo =new PageInfo<>(trainingBuildings);

        // 封装 分页查询结果集
        PagingVO<TrainingBuilding> pagingVO = new PagingVO<>();
        pagingVO.setTotal(pageInfo.getTotal());
        pagingVO.setPageSize(pageInfo.getPageSize());
        pagingVO.setCurrentPage(pageInfo.getPageNum());
        pagingVO.setTotalPages(pageInfo.getPages());
        pagingVO.setData(pageInfo.getList());
        return pagingVO;
    }

    @Override
    public List<TrainingBuilding> getAllTrainingBuildings() {
        return trainingBuildingMapper.getAllTrainingBuildings();
    }

    @Override
    public boolean addTrainingBuilding(String buildingName) {
        TrainingBuilding trainingBuilding = new TrainingBuilding();
        String id = UUID.randomUUID().toString().replace("-","");
        trainingBuilding.setId(id);
        trainingBuilding.setBuildingName(buildingName);
        int effectedRows = trainingBuildingMapper.addTrainingBuilding(trainingBuilding);
        if(effectedRows > 0) return true;
        return false;
    }

    @Override
    public boolean delTrainingBuilding(String id) {
        int deletedTbRows = trainingBuildingMapper.deleteTrainingBuildingById(id);
        if(deletedTbRows > 0){
            List<String> labIdList = labMapper.getLabIdByBuildingId(id);
            labIdList.forEach(labId -> labService.delLab(labId)); // 调用labService的删除方法删除所有实训室
        }
        return true;
    }

    @Override
    public boolean updateTrainingBuilding(UpdateTrainingBuildingDTO updateTrainingBuildingDTO) {
        TrainingBuilding trainingBuilding = new TrainingBuilding();
        BeanUtils.copyProperties(updateTrainingBuildingDTO,trainingBuilding);
        int effectedRows = 0;
        if(Objects.nonNull(trainingBuilding)){
            effectedRows = trainingBuildingMapper.updateTrainingBuildingById(trainingBuilding);
        }
        if(effectedRows>0) return  true;
        return false;
    }
}
