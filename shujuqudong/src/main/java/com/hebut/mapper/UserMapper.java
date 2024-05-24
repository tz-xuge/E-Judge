package com.hebut.mapper;


import com.hebut.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * (User)表数据库访问层
 */
@Mapper
public interface UserMapper {

    /**
     * 通过登录查询单条数据
     *
     * @param user
     * @return 实例对象
     */
    User selectByUsername(User user);

    /**
     * 修改数据
     *
     * @param user
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(User user);

}
