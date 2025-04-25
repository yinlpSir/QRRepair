package com.liuqi.machineroomrepairsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuqi.machineroomrepairsystem.dto.lab.AddAndUpdateLabDTO;
import com.liuqi.machineroomrepairsystem.dto.lab.QueryLabDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import com.liuqi.machineroomrepairsystem.mapper.FaultImageMapper;
import com.liuqi.machineroomrepairsystem.mapper.LabMapper;
import com.liuqi.machineroomrepairsystem.mapper.RepairMapper;
import com.liuqi.machineroomrepairsystem.mapper.TrainingBuildingMapper;
import com.liuqi.machineroomrepairsystem.pojo.Lab;
import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import com.liuqi.machineroomrepairsystem.service.LabService;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service("labService")
public class LabServiceImpl implements LabService {
    @Autowired
    private TrainingBuildingMapper trainingBuildingMapper;

    @Autowired
    private LabMapper labMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private FaultImageMapper faultImageMapper;

    @Override
    public PagingVO<Lab> getLabs(QueryLabDTO queryLabDTO) {
        PageHelper.startPage(queryLabDTO.getCurrentPage(),queryLabDTO.getPageSize());
        List<Lab> labs = labMapper.getLabs(queryLabDTO.getBuildingName(),queryLabDTO.getLabName());
        PageInfo<Lab> pageInfo = new PageInfo<>(labs);

        PagingVO<Lab> labPagingVO=new PagingVO<>();
        labPagingVO.setTotalPages(pageInfo.getPages());
        labPagingVO.setCurrentPage(pageInfo.getPageNum());
        labPagingVO.setPageSize(pageInfo.getPageSize());
        labPagingVO.setTotal(pageInfo.getTotal());
        labPagingVO.setData(pageInfo.getList());
        return labPagingVO;
    }

    @Override
    public List<Lab> getLabByBuildingName(String buildingName) {
        TrainingBuilding trainingBuilding = trainingBuildingMapper.getTrainingBuildingByName(buildingName);
        if(Objects.isNull(trainingBuilding)) throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,"未找到 "+buildingName);
        return labMapper.getLabByBuildingId(trainingBuilding.getId());
    }

    @Override
    public Lab getLabByLabName(String labName) {
        return labMapper.getLabByLabName(labName);
    }

    @Override
    public boolean addLab(AddAndUpdateLabDTO addLabDto) {
        int effectedRow = 0;
        TrainingBuilding trainingBuilding = trainingBuildingMapper.getTrainingBuildingByName(addLabDto.getBuildingName());
        if(Objects.nonNull(trainingBuilding)){
            String labId = UUID.randomUUID().toString().replace("-", "");
            Lab lab = new Lab();
            BeanUtils.copyProperties(addLabDto,lab);
            lab.setId(labId);
            lab.setBuildingId(trainingBuilding.getId());
            effectedRow = labMapper.insertLab(lab);
        }
        return effectedRow > 0;
    }

    @Override
    public boolean updateLab(AddAndUpdateLabDTO updateLabDto) {
        TrainingBuilding trainingBuilding = trainingBuildingMapper.getTrainingBuildingByName(updateLabDto.getBuildingName());
        if(Objects.nonNull(trainingBuilding)){
            Lab lab = new Lab();
            BeanUtils.copyProperties(updateLabDto,lab);
            lab.setBuildingId(trainingBuilding.getId());
            int effectedRow = labMapper.updateLabById(lab);
            return true;
        }
        return false;
    }

    @Override
    public int delLab(String labId) {
        // 获取要删除的报修记录id，用来删图片用的
        List<String> repairId = repairMapper.getRepairIdByLabId(labId);
        repairId.forEach(id -> {
            faultImageMapper.delImageByRepairId(id); // 删除报修记录对应的图片
        });
        // 删除该实训室对应的 报修记录
        repairMapper.delRepairRecordByLabId(labId);
        // 删除实训室
        return labMapper.delLabById(labId);
    }
}
