package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.Lab;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LabMapper {
    @Insert("insert into lab values (#{lab.id},#{lab.labName},#{lab.labAlias},#{lab.availableEquipment},#{lab.damageEquipment},#{lab.buildingId});")
    public int insertLab(@Param("lab") Lab lab);

    @Select("select * from lab where building_id = #{buildingId}")
    public List<Lab> getLabByBuildingId(String buildingId);

    @Select("select * from lab where lab_name = #{labName}")
    public Lab getLabByLabName(String labName);

    @Select("select id,lab_name,lab_alias,available_equipment,damage_equipment from lab where id = #{id}")
    public Lab getLabById(String id);

    @Select("select * from lab where lab_name = #{ln} and building_id = #{bi}")
    public Lab getLabByLabNameAndBuildingId(@Param("ln") String labName,@Param("bi") String buildingId);

    @Select("select l.*,tb.* from lab l join training_building tb on l.building_id = tb.id where tb.building_name like CONCAT('%',#{bn},'%') and l.lab_name like CONCAT('%',#{ln},'%')")
    @Results({
            @Result(
                    property = "trainingBuilding",
                    column = "building_id",
                    one = @One(select = "com.liuqi.machineroomrepairsystem.mapper.TrainingBuildingMapper.getTrainingBuildingById")
            )
    })
    public List<Lab> getLabs(@Param("bn") String buildingName,@Param("ln") String labName);

    @Update("update lab set lab_name = #{lab.labName},lab_alias = #{lab.labAlias},available_equipment = #{lab.availableEquipment},damage_equipment = #{lab.damageEquipment},building_id = #{lab.buildingId} where id = #{lab.id}")
    public int updateLabById(@Param("lab") Lab lab);

    @Select("select id from lab where building_id = #{buildingId}")
    public List<String> getLabIdByBuildingId(String buildingId);

    @Delete("delete from lab where building_id = #{buildingId}")
    public int deleteLabByBuildingId(String buildingId);

    @Delete("delete from lab where id = #{labId}")
    public int delLabById(String labId);
}
