package com.zhupeiting.bisheproject.mapper;

import com.zhupeiting.bisheproject.model.Ad;
import com.zhupeiting.bisheproject.model.AdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    long countByExample(AdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int deleteByExample(AdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int insert(Ad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int insertSelective(Ad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    List<Ad> selectByExampleWithRowbounds(AdExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    List<Ad> selectByExample(AdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    Ad selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int updateByExampleSelective(@Param("record") Ad record, @Param("example") AdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int updateByExample(@Param("record") Ad record, @Param("example") AdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int updateByPrimaryKeySelective(Ad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AD
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    int updateByPrimaryKey(Ad record);
}