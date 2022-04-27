package com.xyf.show_service.aphorism.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyf.show_pai.domain.aphorism.CelebratedDictum;
import com.xyf.show_pai.domain.college.SyCollege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * SyCollege接口
 *
 * @author birancloud
 * @date 2021-04-18
 */
@Mapper
public interface AphorismMapper extends BaseMapper<CelebratedDictum> {


    @Select("SELECT\n" +
            "  *\n" +
            "FROM\n" +
            "  `celebrated_dictum` AS t1\n" +
            "  JOIN (\n" +
            "    SELECT\n" +
            "      ROUND(\n" +
            "        RAND() * (\n" +
            "          (\n" +
            "            SELECT\n" +
            "              MAX(id)\n" +
            "            FROM\n" +
            "              `celebrated_dictum`\n" +
            "          ) -(\n" +
            "            SELECT\n" +
            "              MIN(id)\n" +
            "            FROM\n" +
            "              `celebrated_dictum`\n" +
            "          )\n" +
            "        ) +(\n" +
            "          SELECT\n" +
            "            MIN(id)\n" +
            "          FROM\n" +
            "            `celebrated_dictum`\n" +
            "        )\n" +
            "      ) AS id\n" +
            "  ) AS t2\n" +
            "WHERE\n" +
            "  t1.id >= t2.id\n" +
            "ORDER BY\n" +
            "  t1.id\n" +
            "LIMIT\n" +
            "  1;")
    CelebratedDictum getAphorismOne();

}
