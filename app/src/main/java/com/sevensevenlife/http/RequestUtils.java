package com.sevensevenlife.http;

/**
 * Created by 10237 on 2016/11/11.
 *
 * api 配置类
 */

public class RequestUtils {
    public static String FORMAL_URL = "http://111.8.133.24:7777/";

    public static String TEST_URL = "http://218.75.134.187:7777/";

    public static String TEST_URL_TWO = "http://192.168.1.101:8081/";

    public static String QEQUEST_URL = TEST_URL;//在这里修改正式or测试环境

    public static final String ZIXINGCHE_URL = "http://111.8.133.24:7776";

    public static final String PROTOCOL = "http://111.8.133.24:7777/yxsh-api/html/zc_protocol.html";//协议

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int POST_TWO = 4;
    public static final int UPFEIL = 2;
    public static final int DOWNLOAD = 3;


    public static class URI {

        public static final String USER_LOGIN = "yxsh-api/cust/userLogin.do";

        public static final String GER_VERSION = "yxsh-api/common/getVersion.do";

        public static final String ADD_BIKE_ERROR = "yxsh-api/bike/addBikeError.do";

        public static final String GET_LOST_BIKE = "yxsh-api/bike/getLostBike.do";

        public static final String GET_BALANCE = "yxsh-api/bike/getBalance.do";

        public static final String UP_LOAD = "yxsh-api/file/upload.do";

        public static final String EDIT_USER_INFO = "yxsh-api/cust/editUserInfo.do";

        public static final String UP_PASSWORD = "yxsh-api/cust/updatePassword.do";

        public static final String GET_BANNER = "yxsh-api/common/getBanner.do";

        public static final String FIRDT_LEVEL_PORJECT = "yxsh-api/project/getFirstLevelProject.do";

        public static final String GET_ORDER_LIST = "yxsh-api/order/getOrderList.do";

        public static final String ADD_INSTALL_INFO = "yxsh-api/common/addInstallInfo.do";

        public static final String GET_APPRAISAL_LIST = "yxsh-api/service/getAppraisalList.do";

        public static final String ADD_COLLECT = "yxsh-api/cust/addCollect.do";

        public static final String LINE_DOWN = "yxsh-api/order/updateRemainStatusOffline.do";

        public static final String ALL_PROJECT = "yxsh-api/project/getAllProject.do";

        public static final String INVITE_QR_CODE = "yxsh-api/cust/getInviteQrCode.do";

        public static final String GET_COLLEC_LIST = "yxsh-api/cust/getCollectList.do";

        public static final String DEL_COLLECT_LIST = "yxsh-api/cust/cancleCollect.do";

        public static final String GET_ORDER_DETAIL = "yxsh-api/order/getOrderDetail.do";

        public static final String GET_USER_COUPON = "yxsh-api/cust/getUserCoupon.do";

        public static final String ADD_CUST_ORDER = "yxsh-api/order/addCustOrder.do";

        public static final String GET_COMMON_COMMENT = "yxsh-api/order/getCommonComment.do";

        public static final String ADD_APPRAISAL = "yxsh-api/service/addAppraisal.do";

        public static final String CHECK_PHONE_NUMBER = "yxsh-api/cust/checkPhoneNumber.do";

        public static final String REGISTER = "yxsh-api/cust/register.do";

        public static final String RESET_PASSWORD = "yxsh-api/cust/resetPassword.do";

        public static final String GET_PROJECT_BY_PARENT_LEVEL = "yxsh-api/project/getProjectByParentLevel.do";

        public static final String CANCLE_ORDER = "yxsh-api/order/cancleOrder.do";

        public static final String ADD_SUGGESTIOIN = "yxsh-api/common/addSuggestioin.do";

        public static final String GET_SERVICE_DETAIL = "yxsh-api/service/getServiceDetail.do";

        public static final String GET_SERVICE_LIST = "yxsh-api/service/getServiceList.do";

        public static final String PUSh_MESSAGE = "yxsh-api/cust/v2/getPushMessage.do";

        public static final String UPDATE_FRIEND_LIST = "yxsh-api/bike/updateFriendList.do";

        public static final String GET_FRIEND_LIST = "yxsh-api/bike/getFriendList.do";

        public static final String GET_RIDE_TOP_LIST = "yxsh-api/bike/getRideTopList.do";

        public static final String GET_RLXH = "yxsh-api/bike/getRLXH.do";

        public static final String GET_MY_COUPON_LIST = "yxsh-api/coupon/getMyCouponList.do";

        public static final String APP_PHONE_ORDER = "yxsh-api/order/addPhoneOrder.do";

        public static final String DEL_MESSAGE = "yxsh-api/cust/deletePushMessage.do";

        public static final String DOWN_ORDER = "yxsh-api/order/pay.do";

        public static final String GET_CDYEE_NEWS = "yxsh-api/common/getCdyeeNews.do";

        public static final String WXPAY_ORDER_QUERY = "yxsh-api/order/wxpayOrderQuery.do";

        public static final String ADD_CDYEE_MESSAGE = "yxsh-api/common/addCdyeeMessage.do";

        public static final String CDYEE_MESSAGE = "yxsh-api/common/getCdyeeMessage.do";

        public static final String GET_CDYEE_MESSAGE_STAUS = "yxsh-api/common/getCdyeeMessageStatus.do";

        public static final String GET_VERIFY_CODE_V2 = "yxsh-api/cust/v2/getVerifyCode.do";

        public static final String GET_ALL_COMMUNITY = "yxsh-api/community/getAllCommunityInfoList.do";

        public static final String GET_MY_COMMUNITY = "yxsh-api/community/getMyCommunityInfoList.do";

        public static final String ADD_COMMUNITY = "yxsh-api/community/addMyCommunity.do";

        public static final String GET_NOTICE_LIST = "yxsh-api/community/getNoticeList.do";

        public static final String GET_VOTE_LIST = "yxsh-api/community/getVoteList.do";

        public static final String GET_VOTE_DETAIL = "yxsh-api/community/getVoteDetail.do";

        public static final String ADD_VOTE = "yxsh-api/community/addVote.do";

        public static final String ADD_COMMUNITY_SUGGESTIOIN = "yxsh-api/community/addSuggestioin.do";

        public static final String SECURITY_LIST = "yxsh-api/community/getSecurityList.do";

        public static final String GET_DICTIONARY = "yxsh-api/common/getDictionary.do";

        public static final String ADD_REPAIR_ORDER = "yxsh-api/community/addRepairOrder.do";

        public static final String ADD_BBS_TOPIC = "yxsh-api/community/addBbsTopic.do";

        public static final String BBS_LIST = "yxsh-api/community/getBbsTopicList.do";

        public static final String PRAISE_BBS = "yxsh-api/community/praiseBbsTopic.do";

        public static final String REPLY_BBS_TOPIC = "yxsh-api/community/replyBbsTopic.do";

        public static final String GET_BBS_DETAILS = "yxsh-api/community/getBbsTopicDetail.do";

        public static final String GET_BBS_REPLY_LIST = "yxsh-api/community/getBbsReplyList.do";

        public static final String GET_AD_MESSAGE = "yxsh-api/cust/getAdMessage.do";

        public static final String READ_AD_MESSAGE = "yxsh-api/cust/readAdMessage.do";

        public static final String USE_COUPON = "yxsh-api/coupon/useCoupon.do";



    }

}
