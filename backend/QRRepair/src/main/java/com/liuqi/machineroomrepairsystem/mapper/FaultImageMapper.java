package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.FaultImage;
import org.apache.ibatis.annotations.*;

import java.io.InputStream;
import java.util.List;

@Mapper
public interface FaultImageMapper {
    @Insert("insert into fault_image values (#{id},#{image},#{repairId});")
    int insertImage(@Param("id") String id,@Param("image") InputStream image,@Param("repairId") String repairId);

    @Select("select * from fault_image where id = #{id}")
    FaultImage getImageById(String id);

    @Select("select * from fault_image where repair_record_id = #{repairId}")
    List<FaultImage> getImageByRepairId(String repairId);

    @Delete("delete from fault_image where repair_record_id = #{id}")
    int delImageByRepairId(String id);
}
