package com.example.superproject;

public class Details {
    private int code;
    private String msg;
    public data data;

    public static class data {

        private String key;
        private String phone;
        private String name;
        private String passwd;
        private String text;
        private String img;
        private String other;
        private String other2;
        private String createTime;

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public void setImg(String img) {
            this.img = img;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setOther(String other) {
            this.other = other;
        }
        public void setOther2(String other2) {
            this.other2 = other2;
        }
        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }
        public void setText(String text) {
            this.text = text;
        }
        public void setKey(String key) {
            this.key = key;
        }

        public String getText() {
            return text;
        }
        public String getPhone() {
            return phone;
        }
        public String getCreateTime() {
            return createTime;
        }
        public String getPasswd() {
            return passwd;
        }
        public String getOther2() {
            return other2;
        }
        public String getOther() {
            return other;
        }
        public String getName() {
            return name;
        }
        public String getKey() {
            return key;
        }
        public String getImg() {
            return img;
        }
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg() { this.msg = msg; }

}
