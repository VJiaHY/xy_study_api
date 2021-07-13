package com.xyf.show_pai.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * user对象 user
 *
 * @author birancloud
 * @date 2021-04-18
 */
@Data
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sy_college")
public class  SyCollege implements Serializable {

private static final long serialVersionUID=1L;

    private Long id;
    private String name;
    private String levelName;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String address;
    private String type;
    private String natureName;
    private String lng;
    private String lat;

}
