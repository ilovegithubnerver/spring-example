package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    public User getById(@Param("id") Long id);

    @Insert("INSERT INTO user(id,account,password) VALUE(#{id},#{account},#{password})")
    public Long insert(User user);

}
