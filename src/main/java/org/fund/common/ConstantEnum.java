package org.fund.common;

public class ConstantEnum {

    public static enum AuthType {
        NORMAL_USER("普通用户", 1), VIP_USER("会员用户", 2), SVIP("终生会员", 3);

        private String text;
        private Integer id;

        private AuthType(String text, int id) {
            this.text = text;
            this.id = id;
        }

        public static boolean checkIdeaType(int id) {
            for (AuthType e : AuthType.values()) {
                if (e.getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }

        public static String getText(int typeId) {
            for (AuthType en : AuthType.values()) {
                if (en.getId().equals(typeId)) {
                    return en.text;
                }
            }
            return null;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
