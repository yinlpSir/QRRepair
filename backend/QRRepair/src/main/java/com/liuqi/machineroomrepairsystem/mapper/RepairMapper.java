package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.RepairRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RepairMapper {

    @Insert("insert into repair_record (id, equipment_no, description,repair_time, repairman, phone_number,building_id,lab_id) value " +
                "(#{repair.id},#{repair.equipmentNo},#{repair.description},#{repair.repairTime},#{repair.repairman},#{repair.phoneNumber},#{repair.buildingId},#{repair.labId});")
    public int addRepairRecord(@Param("repair")RepairRecord repairRecord);

    @Select("select r.* from repair_record as r where r.status = 1 or r.status = 2")
    @Results({
            @Result(
                    property = "id",
                    column = "id",
                    id = true
            ),
            @Result(
                    property = "lab",
                    column = "lab_id",
                    one = @One(select = "com.liuqi.machineroomrepairsystem.mapper.LabMapper.getLabById")
            ),
            @Result(
                    property = "trainingBuilding",
                    column = "building_id",
                    one = @One(select = "com.liuqi.machineroomrepairsystem.mapper.TrainingBuildingMapper.getTrainingBuildingById")
            ),
            @Result(
                    property = "faultImages",
                    column = "id",
                    many = @Many(select = "com.liuqi.machineroomrepairsystem.mapper.FaultImageMapper.getImageByRepairId")
            )
    })
    public List<RepairRecord> getRepairRecord();

    @Select("select * from repair_record r join training_building tb on r.building_id = tb.id where tb.building_name like CONCAT('%',#{bn},'%') and r.status = 3")
    @Results({
            @Result(
                    property = "id",
                    column = "id",
                    id = true
            ),
            @Result(
                    property = "lab",
                    column = "lab_id",
                    one = @One(select = "com.liuqi.machineroomrepairsystem.mapper.LabMapper.getLabById")
            ),
            @Result(
                    property = "trainingBuilding",
                    column = "building_id",
                    one = @One(select = "com.liuqi.machineroomrepairsystem.mapper.TrainingBuildingMapper.getTrainingBuildingById")
            ),
            @Result(
                    property = "faultImages",
                    column = "id",
                    many = @Many(select = "com.liuqi.machineroomrepairsystem.mapper.FaultImageMapper.getImageByRepairId")
            )
    })
    public List<RepairRecord> getRepairHistory(@Param("bn") String buildingName);

    /**
     * 用来删除用的
     * @param labId
     * @return
     */
    @Select("select id from repair_record where lab_id = #{labId}")
    public List<String> getRepairIdByLabId(String labId);

    @Update("update repair_record set status = #{status} where id = #{id}")
    public int updateRepairRecord(String id,byte status);

    @Delete("delete from repair_record where id = #{id}")
    public int delRepairRecordById(String id);

    @Delete("delete from repair_record where lab_id = #{labId}")
    public int delRepairRecordByLabId(String labId);
}
