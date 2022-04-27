package com.xyf.show_pai.domain.aphorism;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author JiaHY
 * @Description //TODO
 * @Date 2022/4/7 14:01
 * 名言
 **/
@Data
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@TableName("celebrated_dictum")
public class CelebratedDictum implements Serializable {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 文本内容
     */
    private String cdText;
    /**
     * 出处
     */
    private String cdFrom;
}
