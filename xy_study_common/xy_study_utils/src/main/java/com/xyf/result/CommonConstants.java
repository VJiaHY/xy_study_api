package com.xyf.result;

/**
 * @author lengleng
 * @date 2017/10/29
 */
public interface CommonConstants {

    /**
     * header 中租户ID
     */
    String TENANT_ID = "TENANT-ID";

    /**
     * header 中版本信息
     */
    String VERSION = "VERSION";

    /**
     * 租户ID
     */
    Integer TENANT_ID_1 = 1;

    /**
     * 删除
     */
    String STATUS_DEL = "1";

    /**
     * 正常
     */
    String STATUS_NORMAL = "0";


    /**
     * 删除
     */
    String BAN_DEL = "1";

    /**
     * 正常
     */
    String BAN_NORMAL = "0";

    /**
     * 可编辑
     */
    String EDITABLE = "1";

    /**
     * 不可编辑
     */
    String UNEDITABLE = "0";

    /**
     * 锁定
     */
    String STATUS_PROHIBIT = "1";

    /**
     * 未锁定
     */
    String STATUS_ALLOW = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 菜单树根节点
     */
    Integer MENU_TREE_ROOT_ID = -1;

    /**
     * 根系统根节点（管理系统）
     */
    Integer SAAS_ADMIN_ID = 1;

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * 前端工程名
     */
    String FRONT_END_PROJECT = "pigx-ui";

    /**
     * 后端工程名
     */
    String BACK_END_PROJECT = "pigx";

    /**
     * 公共参数
     */
    String PIG_PUBLIC_PARAM_KEY = "PIG_PUBLIC_PARAM_KEY";

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;

    /**
     * 失败标记
     */
    Integer FAIL = 1;

    /**
     * 默认存储bucket
     */
    String BUCKET_NAME = "lengleng";

    /**
     * 滑块验证码
     */
    String IMAGE_CODE_TYPE = "blockPuzzle";

    /**
     * 管理员角色ID
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 管理员角色ID
     */
    Integer MANAGER_ROLE_ID = 66;

    /**
     * 不显示TopMenu
     */
    String NO_TOP_MENU_TYPE = "-1";

    /**
     * 逗号
     */
    String COMMA = ",";

    /**
     * 终端类型：APP
     */
    String APP_CLIENT = "app";

    enum DISTRICT_LEVEL {
        QU_CODE("qu_code"), XIANG_CODE("xiang_code"), CUN_CODE("cun_code");

        private final String value;

        private DISTRICT_LEVEL(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    enum DISTRICT_LEVEL_NAME {
        QU_NAME("quName"), XIANG_NAME("xiangName"), CUN_NAME("cunName");

        private final String value;

        private DISTRICT_LEVEL_NAME(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }
}
