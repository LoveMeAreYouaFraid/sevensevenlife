package com.sevensevenlife.model.httpmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class ServiceDeatil extends PublicMode {


    /**
     * rows : {"id":"","phone":"18610177152","password":"","realName":"肖师傅","nickName":"","sex":"","headPic":"http://218.75.134.187:7777/yxsh-api/static/images/201702/3ca44520-bc6b-4372-8fde-0658bea82cf7.jpg","birthday":"","companyName":"","companyLogo":"","cardType":"","cardNo":"","cardPica":"","cardPicb":"","provinceId":"","cityId":"","areaId":"","address":"","addressDetail":"","hasLicense":"","licensePic":"","certificatePic":"","userType":1,"auditStatus":"","regStep":"","authenticate":1,"String roduction":"从北京回来的，普通话带北京腔调","bankCard":"","bankType":"","bankUserName":"","auditRemark":"","createDate":"","updateDate":"","activeFlag":"","projectIds":"","currentAddress":"金地名苑","workStatus":"3","longitude":"","latitude":"","distance":147,"appraisalTotal":2.1666667,"attitude":1.7,"profession":2.5,"punctual":2.3,"appraisalCount":6,"orderCount":6,"String roductPhotos":["http://218.75.134.187:7777/yxsh-api/static/images/201702/28512829-ddae-445e-a449-5ba101639d81.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/54d1f747-c09c-4e4d-97d8-0f86d28d2125.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/b93119ec-5057-44cb-b9fa-4a8f4f3a3a8e.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/84646aa8-9030-449b-a616-94e2ed2782dd.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/31f052a2-67f7-4e79-8e1a-2edca68a6e5f.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/b8067b54-7717-4daa-9956-d3bf497e9916.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/860e2935-f112-40a7-87b9-5e50142d5c5f.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/505b437e-fbda-4712-9b16-cd7a4164e14b.jpg"],"appraisalList":[{"id":63,"serviceId":54,"custId":24288,"custPic":"http://218.75.134.187:7777/yxsh-api/static/images/201702/e83d6d6d-97b2-49bc-ac2d-e1f86d15462d.png","custName":"jssjsj","orderId":239,"attitude":5,"profession":5,"punctual":5,"total":5,"content":"一般","isAnonymity":0,"photoa":"","photob":"","photoc":"","createDate":"2017-02-24 08:50:20.0","replyContent":"","replyTime":""}],"car_type":"","box_length":"","load_weight":"","car_address":""}
     */

    private RowsBean rows;

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id :
         * phone : 18610177152
         * password :
         * realName : 肖师傅
         * nickName :
         * sex :
         * headPic : http://218.75.134.187:7777/yxsh-api/static/images/201702/3ca44520-bc6b-4372-8fde-0658bea82cf7.jpg
         * birthday :
         * companyName :
         * companyLogo :
         * cardType :
         * cardNo :
         * cardPica :
         * cardPicb :
         * provinceId :
         * cityId :
         * areaId :
         * address :
         * addressDetail :
         * hasLicense :
         * licensePic :
         * certificatePic :
         * userType : 1
         * auditStatus :
         * regStep :
         * authenticate : 1
         * String roduction : 从北京回来的，普通话带北京腔调
         * bankCard :
         * bankType :
         * bankUserName :
         * auditRemark :
         * createDate :
         * updateDate :
         * activeFlag :
         * projectIds :
         * currentAddress : 金地名苑
         * workStatus : 3
         * longitude :
         * latitude :
         * distance : 147
         * appraisalTotal : 2.1666667
         * attitude : 1.7
         * profession : 2.5
         * punctual : 2.3
         * appraisalCount : 6
         * orderCount : 6
         * String roductPhotos : ["http://218.75.134.187:7777/yxsh-api/static/images/201702/28512829-ddae-445e-a449-5ba101639d81.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/54d1f747-c09c-4e4d-97d8-0f86d28d2125.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/b93119ec-5057-44cb-b9fa-4a8f4f3a3a8e.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/84646aa8-9030-449b-a616-94e2ed2782dd.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/31f052a2-67f7-4e79-8e1a-2edca68a6e5f.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/b8067b54-7717-4daa-9956-d3bf497e9916.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/860e2935-f112-40a7-87b9-5e50142d5c5f.jpg","http://218.75.134.187:7777/yxsh-api/static/images/201702/505b437e-fbda-4712-9b16-cd7a4164e14b.jpg"]
         * appraisalList : [{"id":63,"serviceId":54,"custId":24288,"custPic":"http://218.75.134.187:7777/yxsh-api/static/images/201702/e83d6d6d-97b2-49bc-ac2d-e1f86d15462d.png","custName":"jssjsj","orderId":239,"attitude":5,"profession":5,"punctual":5,"total":5,"content":"一般","isAnonymity":0,"photoa":"","photob":"","photoc":"","createDate":"2017-02-24 08:50:20.0","replyContent":"","replyTime":""}]
         * car_type :
         * box_length :
         * load_weight :
         * car_address :
         */

        private String id;
        private String phone;
        private String password;
        private String realName;
        private String nickName;
        private String sex;
        private String headPic;
        private String birthday;
        private String companyName;
        private String companyLogo;
        private String cardType;
        private String cardNo;
        private String cardPica;
        private String cardPicb;
        private String provinceId;
        private String cityId;
        private String areaId;
        private String address;
        private String addressDetail;
        private String hasLicense;
        private String licensePic;
        private String certificatePic;
        private String userType;
        private String auditStatus;
        private String regStep;
        private String authenticate;
        private String introduction;
        private String bankCard;
        private String projectId;
        private String bankType;
        private String bankUserName;
        private String auditRemark;
        private String createDate;
        private String updateDate;
        private String activeFlag;
        private String projectIds;
        private String currentAddress;
        private String workStatus;
        private String longitude;
        private String latitude;
        private String distance;
        private String appraisalTotal;
        private String attitude;
        private String profession;
        private String punctual;
        private String appraisalCount;
        private String orderCount;
        private String car_type;
        private String box_length;
        private String load_weight;
        private String car_address;
        private List<String> introductPhotos;
        private List<AppraisalListBean> appraisalList;

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardPica() {
            return cardPica;
        }

        public void setCardPica(String cardPica) {
            this.cardPica = cardPica;
        }

        public String getCardPicb() {
            return cardPicb;
        }

        public void setCardPicb(String cardPicb) {
            this.cardPicb = cardPicb;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getHasLicense() {
            return hasLicense;
        }

        public void setHasLicense(String hasLicense) {
            this.hasLicense = hasLicense;
        }

        public String getLicensePic() {
            return licensePic;
        }

        public void setLicensePic(String licensePic) {
            this.licensePic = licensePic;
        }

        public String getCertificatePic() {
            return certificatePic;
        }

        public void setCertificatePic(String certificatePic) {
            this.certificatePic = certificatePic;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getRegStep() {
            return regStep;
        }

        public void setRegStep(String regStep) {
            this.regStep = regStep;
        }

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public List<String> getIntroductPhotos() {
            return introductPhotos;
        }

        public void setIntroductPhotos(List<String> introductPhotos) {
            this.introductPhotos = introductPhotos;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public String getBankUserName() {
            return bankUserName;
        }

        public void setBankUserName(String bankUserName) {
            this.bankUserName = bankUserName;
        }

        public String getAuditRemark() {
            return auditRemark;
        }

        public void setAuditRemark(String auditRemark) {
            this.auditRemark = auditRemark;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getActiveFlag() {
            return activeFlag;
        }

        public void setActiveFlag(String activeFlag) {
            this.activeFlag = activeFlag;
        }

        public String getProjectIds() {
            return projectIds;
        }

        public void setProjectIds(String projectIds) {
            this.projectIds = projectIds;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
        }

        public String getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(String workStatus) {
            this.workStatus = workStatus;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getAppraisalTotal() {
            return appraisalTotal;
        }

        public void setAppraisalTotal(String appraisalTotal) {
            this.appraisalTotal = appraisalTotal;
        }

        public String getAttitude() {
            return attitude;
        }

        public void setAttitude(String attitude) {
            this.attitude = attitude;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getPunctual() {
            return punctual;
        }

        public void setPunctual(String punctual) {
            this.punctual = punctual;
        }

        public String getAppraisalCount() {
            return appraisalCount;
        }

        public void setAppraisalCount(String appraisalCount) {
            this.appraisalCount = appraisalCount;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(String orderCount) {
            this.orderCount = orderCount;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getBox_length() {
            return box_length;
        }

        public void setBox_length(String box_length) {
            this.box_length = box_length;
        }

        public String getLoad_weight() {
            return load_weight;
        }

        public void setLoad_weight(String load_weight) {
            this.load_weight = load_weight;
        }

        public String getCar_address() {
            return car_address;
        }

        public void setCar_address(String car_address) {
            this.car_address = car_address;
        }


        public List<AppraisalListBean> getAppraisalList() {
            return appraisalList;
        }

        public void setAppraisalList(List<AppraisalListBean> appraisalList) {
            this.appraisalList = appraisalList;
        }

        public static class AppraisalListBean {
            /**
             * id : 63
             * serviceId : 54
             * custId : 24288
             * custPic : http://218.75.134.187:7777/yxsh-api/static/images/201702/e83d6d6d-97b2-49bc-ac2d-e1f86d15462d.png
             * custName : jssjsj
             * orderId : 239
             * attitude : 5
             * profession : 5
             * punctual : 5
             * total : 5
             * content : 一般
             * isAnonymity : 0
             * photoa :
             * photob :
             * photoc :
             * createDate : 2017-02-24 08:50:20.0
             * replyContent :
             * replyTime :
             */

            private String id;
            private String serviceId;
            private String custId;
            private String custPic;
            private String custName;
            private String orderId;
            private String attitude;
            private String profession;
            private String punctual;
            @SerializedName("total")
            private String totalX;
            private String content;
            private String isAnonymity;
            private String photoa;
            private String photob;
            private String photoc;
            private String createDate;
            private String replyContent;
            private String replyTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public String getCustId() {
                return custId;
            }

            public void setCustId(String custId) {
                this.custId = custId;
            }

            public String getCustPic() {
                return custPic;
            }

            public void setCustPic(String custPic) {
                this.custPic = custPic;
            }

            public String getCustName() {
                return custName;
            }

            public void setCustName(String custName) {
                this.custName = custName;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getAttitude() {
                return attitude;
            }

            public void setAttitude(String attitude) {
                this.attitude = attitude;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public String getPunctual() {
                return punctual;
            }

            public void setPunctual(String punctual) {
                this.punctual = punctual;
            }

            public String getTotalX() {
                return totalX;
            }

            public void setTotalX(String totalX) {
                this.totalX = totalX;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIsAnonymity() {
                return isAnonymity;
            }

            public void setIsAnonymity(String isAnonymity) {
                this.isAnonymity = isAnonymity;
            }

            public String getPhotoa() {
                return photoa;
            }

            public void setPhotoa(String photoa) {
                this.photoa = photoa;
            }

            public String getPhotob() {
                return photob;
            }

            public void setPhotob(String photob) {
                this.photob = photob;
            }

            public String getPhotoc() {
                return photoc;
            }

            public void setPhotoc(String photoc) {
                this.photoc = photoc;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getReplyContent() {
                return replyContent;
            }

            public void setReplyContent(String replyContent) {
                this.replyContent = replyContent;
            }

            public String getReplyTime() {
                return replyTime;
            }

            public void setReplyTime(String replyTime) {
                this.replyTime = replyTime;
            }
        }
    }
}
