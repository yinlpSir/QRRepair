package com.liuqi.machineroomrepairsystem.service;

import com.liuqi.machineroomrepairsystem.dto.lab.AddAndUpdateLabDTO;
import com.liuqi.machineroomrepairsystem.dto.lab.QueryLabDTO;
import com.liuqi.machineroomrepairsystem.pojo.Lab;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;

import java.util.List;

public interface LabService {
    public PagingVO<Lab> getLabs(QueryLabDTO queryLabDTO);
    public List<Lab> getLabByBuildingName(String buildingName);
    public Lab getLabByLabName(String labName);
    /**
     *  添加 实训室
     * @param addLabDto
     * @return
     */
    public boolean addLab(AddAndUpdateLabDTO addLabDto);
    public boolean updateLab(AddAndUpdateLabDTO addLabDTO);
    public int delLab(String labId);
}
