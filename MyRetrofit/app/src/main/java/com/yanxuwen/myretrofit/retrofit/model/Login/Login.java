package com.yanxuwen.myretrofit.retrofit.model.Login;

import com.google.gson.annotations.SerializedName;
import com.yanxuwen.myretrofit.retrofit.MyBaseModel;
import com.yanxuwen.retrofit.Annotation.Description;

/**
 * Created by yanxuwen on 2017/7/21.
 */
@Description("登录")
public class Login extends MyBaseModel {
    /**
     * data : {"id":32,"username":"15060568265","nickname":"默默嗯","realname":"","email":"","mobile":"15060568265","signature":"嗯噢噢噢哦哦","thumb":"http://59.57.240.50/storage/avatars/default.png","temple_follows":"","monk_follows":"","birthday":"0000-00-00","source":0,"gender":1,"generation":"80","pious":74,"level":1,"province_id":"820000","city_id":"652700","district_id":"652723","balance":"0.00","last_login":"0000-00-00 00:00:00","status":0,"created_at":"2017-05-26 19:57:06","updated_at":"2017-07-21 17:42:03","location_name":"澳门特别行政区博尔塔拉蒙古自治州","points_difference":26,"is_check_in":1,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjMyLCJpc3MiOiJodHRwOlwvXC9jbXMuc2R3aGNuLmNvbVwvYXBpXC9tZW1iZXJcL2xvZ2luIiwiaWF0IjoxNTAwNjMwMTY5LCJleHAiOjE1MDE4Mzk3NjksIm5iZiI6MTUwMDYzMDE2OSwianRpIjoiYjdud1BUVjBNdTVLc1M5diJ9.PUpyLYi55RpOM9-b6vvy6dX7_KoQB_FcWwnXSmKVCuQ"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 32
         * username : 15060568265
         * nickname : 默默嗯
         * realname :
         * email :
         * mobile : 15060568265
         * signature : 嗯噢噢噢哦哦
         * thumb : http://59.57.240.50/storage/avatars/default.png
         * temple_follows :
         * monk_follows :
         * birthday : 0000-00-00
         * source : 0
         * gender : 1
         * generation : 80
         * pious : 74
         * level : 1
         * province_id : 820000
         * city_id : 652700
         * district_id : 652723
         * balance : 0.00
         * last_login : 0000-00-00 00:00:00
         * status : 0
         * created_at : 2017-05-26 19:57:06
         * updated_at : 2017-07-21 17:42:03
         * location_name : 澳门特别行政区博尔塔拉蒙古自治州
         * points_difference : 26
         * is_check_in : 1
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjMyLCJpc3MiOiJodHRwOlwvXC9jbXMuc2R3aGNuLmNvbVwvYXBpXC9tZW1iZXJcL2xvZ2luIiwiaWF0IjoxNTAwNjMwMTY5LCJleHAiOjE1MDE4Mzk3NjksIm5iZiI6MTUwMDYzMDE2OSwianRpIjoiYjdud1BUVjBNdTVLc1M5diJ9.PUpyLYi55RpOM9-b6vvy6dX7_KoQB_FcWwnXSmKVCuQ
         */

        private int id;
        private String username;
        private String nickname;
        private String realname;
        private String email;
        private String mobile;
        private String signature;
        private String thumb;
        private String temple_follows;
        private String monk_follows;
        private String birthday;
        private int source;
        private int gender;
        private String generation;
        private int pious;
        private int level;
        private String province_id;
        private String city_id;
        private String district_id;
        private String balance;
        private String last_login;
        @SerializedName("status")
        private int statusX;
        private String created_at;
        private String updated_at;
        private String location_name;
        private int points_difference;
        private int is_check_in;
        private String token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTemple_follows() {
            return temple_follows;
        }

        public void setTemple_follows(String temple_follows) {
            this.temple_follows = temple_follows;
        }

        public String getMonk_follows() {
            return monk_follows;
        }

        public void setMonk_follows(String monk_follows) {
            this.monk_follows = monk_follows;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getGeneration() {
            return generation;
        }

        public void setGeneration(String generation) {
            this.generation = generation;
        }

        public int getPious() {
            return pious;
        }

        public void setPious(int pious) {
            this.pious = pious;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(String district_id) {
            this.district_id = district_id;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getLocation_name() {
            return location_name;
        }

        public void setLocation_name(String location_name) {
            this.location_name = location_name;
        }

        public int getPoints_difference() {
            return points_difference;
        }

        public void setPoints_difference(int points_difference) {
            this.points_difference = points_difference;
        }

        public int getIs_check_in() {
            return is_check_in;
        }

        public void setIs_check_in(int is_check_in) {
            this.is_check_in = is_check_in;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
