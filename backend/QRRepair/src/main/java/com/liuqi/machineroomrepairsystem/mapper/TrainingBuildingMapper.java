package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.TrainingBuilding;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainingBuildingMapper {
    @Select("select id,building_name as buildingName from training_building where building_name = #{buildingName}")
    public TrainingBuilding getTrainingBuildingByName(String buildingName);

    @Select("select building_name from training_building where id = #{id}")
    public TrainingBuilding getTrainingBuildingById(String id);

    @Select("select * from training_building where building_name like CONCAT('%',#{buildingName},'%')")
    public List<TrainingBuilding> getTrainingBuildings(@Param("buildingName") String buildingName);

    /**
     * 测试用例
     * @param pageNum
     * @param pageSize
     * @param buildingName
     * @return
     */
    @Select("select * from training_building where building_name like CONCAT('%',#{buildingName},'%')")
    public List<TrainingBuilding> getTrainingBuildings2(@Param("pageNum") int pageNum,
                                                           @Param("pageSize") int pageSize,
                                                           @Param("buildingName") String buildingName);

    @Select("select building_name from training_building")
    public List<TrainingBuilding> getAllTrainingBuildings();

    @Insert("insert into training_building values (#{tb.id},#{tb.buildingName})")
    public int addTrainingBuilding(@Param("tb") TrainingBuilding trainingBuilding);

    @Delete("delete from training_building where id = #{id}")
    public int deleteTrainingBuildingById(String id);

    @Update("update training_building set building_name = #{tb.buildingName} where id = #{tb.id}")
    public int updateTrainingBuildingById(@Param("tb") TrainingBuilding trainingBuilding);
}
