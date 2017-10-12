package com.zhou.test.util;

import com.zhou.test.model.LiveShowTiezhiBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author huz
 * @since 2016/3/14 18:16
 */
public class LiveShowConstants {
    public static final String SYSTEM_USER_ID = "513305e8ae76a102e94617ac";

    public static final String COMMENT_REQUEST_URL = "live_comment";
    public static final String LIVE_VIEW_URL = "live_view";
    public static final String LIVE_VIEW_USERS = "live_view_users";
    public static final String LIVE_SHOW_PAY_URL = "place_order";
    public static final String LIVE_SHOW_MANAGEMENT_URL = "impromptu_management";
    public static final String DIAMOND_PRICE_URL = "purchase_diamond_list";
    public static final String DIAMOND_PAYPAL_PRICE_URL = "paypal_web_order";
    public static final String LIVE_SHOW_USER_PROFILE_REQUEST_URL = "profile";
    public static final String LA_BI_CONTRIBUTION_URL = "live_contribute_list";
    public static final String RTC_ROOM_RIGHTS = "rtc_room_rights";
    public static final String REPORT_ANCHOR_URL = "report_anchor";
    public static final String REPORT_AUDIENCE_URL = "report_audience";
    public static final String KICKING_USER_URL = "kicking_user";
    public static final String LIVE_SILENCE_USER_URL = "live_silence_user";
    public static final String LIVE_AUDIENCE_REENTER_ROOM_URL = "live_audience_reenter_room";
    public static final String LIVE_ANCHOR_STAR_RANKING = "live_anchor_star_ranking";

    public static final int LIVE_SHOW_ONLINE_REQUEST_NUM = 200;

    public static final int PROFILE_TYPE_NORMAL = 0;
    public static final int PROFILE_TYPE_LIVE_SHOW = 1;

    public static final int LIVE_COMMENT_TYPE_COMMENT = 1;  // 评论(前台)
    public static final int LIVE_COMMENT_TYPE_GIFT = 2;  // 礼物
    public static final int LIVE_COMMENT_TYPE_SYSMSG = 3;  // 系统消息
    public static final int LIVE_COMMENT_TYPE_LIKE = 4;  // 点赞消息(前台)
    public static final int LIVE_COMMENT_TYPE_VIP_IN = 5;  // VIP进入房间
    public static final int LIVE_COMMENT_TYPE_FOLLOW = 6;  // 关注主播(前台)
    public static final int LIVE_COMMENT_TYPE_INIT_HEART = 7;  // 点亮事件（进入房间第一次点赞）(前台)
    public static final int LIVE_COMMENT_TYPE_END_SHOW = 8;  // 直播结束z
    public static final int LIVE_COMMENT_TYPE_DANMU = 9;  // 弹幕
    public static final int LIVE_COMMENT_TYPE_INIT_VIP_HEART = 10; // VIP点亮事件（进入房间第一次点赞）(前台)
    public static final int LIVE_COMMENT_TYPE_VIP_LIKE = 11; // VIP点赞消息(前台)
    public static final int LIVE_COMMENT_TYPE_USER_LIST = 12; // 在线列表
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI_APPLY = 13; // 连麦请求
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI_CONNECT = 14; // 同意连接
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI_IGNORE = 15; // 拒绝连接
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI_CANCEL = 16; // 取消连接
    public static final int LIVE_COMMENT_TYPE_ANCHOR_UPGRADE = 17; // 主播升级消息
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI_STATUS_SUCCESS = 18; // 通知观众连麦成功
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI_STATUS_CLOSE = 19; // 通知观众连麦关闭
    public static final int LIVE_COMMENT_TYPE_FESTIVAL_GIFT = 20; // 特殊节日礼物类型
    public static final int LIVE_COMMENT_TYPE_FLOAT_DECORATE = 21; // 直播间装饰
    public static final int LIVE_COMMENT_TYPE_TEXT_DECORATE = 27; // 直播间文本装饰
    public static final int LIVE_COMMENT_TYPE_GUARD_COME = 22; // 守护进入
    public static final int LIVE_COMMENT_TYPE_NOBLE_STAR = 23; // 贵族升级
    public static final int LIVE_COMMENT_TYPE_NOBLE_COME = 24; // 贵族进入
    public static final int LIVE_COMMENT_TYPE_SHARE_LIVE = 25; // 分享直播消息
    public static final int LIVE_COMMENT_TYPE_CHANGE_URL = 26; // cnd切换通知观众
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI = 28; // 连麦消息类型
    public static final int LIVE_COMMENT_TYPE_GAME = 29; // 直播间游戏
    public static final int LIVE_COMMENT_TYPE_HORN = 31; // 大喇叭消息
    public static final int LIVE_COMMENT_TYPE_LIAN_MAI_PK = 50; // 直播间游戏

    //消息类型 BEGIN
    public static final int LPLiveLianMaiTypeApply  = 1;    // 观众 申请连麦
    public static final int LPLiveLianMaiTypeAccept = 2;    // 主播 同意连麦
    public static final int LPLiveLianMaiTypeReject = 3;    // 主播 拒绝连麦
    public static final int LPLiveLianMaiTypeCancel = 4;    // 观众 取消连麦申请
    public static final int LPLiveLianMaiTypeKick   = 5;    // 主播踢除某个副主播
    public static final int LPLiveLianMaiTypeConnected    = 6; // 其他观众显示 副主播名字
    public static final int LPLiveLianMaiAgoraUid    = 7; // 副主播同步声网uid
    public static final int LPLiveLianMaiTypeBusy    = 8; // 主播忙，给副主播发稍等
    public static final int LPLiveLianMaiTypeAudienceJoin = 9; //观众进入连麦主播的房间
    //消息类型 END

    public static final int LPLiveLianMaiTypePkRequest = 1; //主播 连麦pk 申请
    public static final int LPLiveLianMaiTypePkResponse = 2; // 应答
    public static final int LPLiveLianMaiTypePkCancel = 3; // 取消pk
    public static final int LPLiveLianMaiTypePkBegin = 5; // 开始pk
    public static final int LPLiveLianMaiTypePkSendGift = 6; // 送礼信息
    public static final int LPLiveLianMaiTypePkResult = 7; // 送礼信息
    public static final int LPLiveLianMaiTypePkExit = 8; // 送礼信息
    public static final int LPLiveLianMaiTypePkAudienceJoin = 20;
    public static final int LPLiveLianMaiTypePkAudienceJoinEnd = 21;
    public static final int LPLiveLianMaiTypePkRunAway = 30;

    public static final int LPLiveLianMaiTypePkRoleAudience = 1;

    public static final int LIVE_SHOW_LIANMAI_STATUS_SUCCESS = 0;

    public static final int LIVE_SHOW_LIANMAI_STATUS_IGNORE = 1;
    public static final int LIVE_SHOW_LIANMAI_STATUS_BUSY = 2;


    public static final int LIVE_SHOW_LINAMAI_SUCCESS = 1;  // 连麦中
    public static final int LIVE_SHOW_LINAMAI_CANCEL = 0;  // 不在连麦中



    //如果手动取消或者30s自动取消需要等待时间
    public static final int lianmaiWaitTime = 3 * 60 * 1000;

    public static final int LIVE_COMMENT_TYPE_LIVE_TOPIC = 10000; // 在本地评论区显示直播间标题

    public static final String MYSTERY_USER_ID_APPEND = "_m";

    public static final int PAY_TYPE_ALIPAY = 1;
    public static final int PAY_TYPE_WECHAT = 2;
    public static final int PAY_TYPE_GOOGLE = 100;
    public static final int PAY_TYPE_PAYPAL = 101;

    public static final int LIVE_SHOW_TYPE_NORMAL = 1; //普通直播
    public static final int LIVE_SHOW_TYPE_MIX = 2; //连麦直播

    public static final int LIVE_SHOW_TYPE_NEW = 1;
    public static final int LIVE_SHOW_TYPE_HOT = 2;
    public static final int LIVE_SHOW_TYPE_FOLLOWED = 3;
    public static final int LIVE_SHOW_TYPE_TOPIC = 4;
    public static final int LIVE_SHOW_TYPE_PLAY_BACK = 5;

    public static final int LIVE_SHOW_RANKING_LIST_DAY = 1;
    public static final int LIVE_SHOW_RANKING_LIST_WEEK = 2;
    public static final int LIVE_SHOW_RANKING_LIST_TOTAL = 3;
    public static final int LIVE_SHOW_RANKING_LIST_MONTH = 4;

    //系统消息子类型 LIVE_COMMENT_TYPE_SYSMSG = 3
    public static final int LIVE_COMMENT_MSG_TYPE_SHARE = 1;//分享得钻消息

    public static final int REQUEST_MAX_RETRY_TIMES = 5;
    public static final int GET_DIAMOND_REQUEST_DELAY_TIME = 2000;
    public static final int COMMENT_REQUEST_DELAY_TIME = 2000;
    public static final int COMMENT_COUNT_PER_REQUEST = 10;
    public static final int ONLINE_LIST_REQUEST_DELAY_TIME = 10000;

    public static final int REQUEST_START_PURCHASE_DIAMOND_PAGE = 1;

    // 礼物ID
    public static final int LIVE_SHOW_GIFT_ROSE = 1;
    public static final int LIVE_SHOW_GIFT_KISS = 2;
    public static final int LIVE_SHOW_GIFT_HONGBAO = 3;
    public static final int LIVE_SHOW_GIFT_BAG = 4;
    public static final int LIVE_SHOW_GIFT_DIAMOND_RING = 5;
    public static final int LIVE_SHOW_GIFT_FIRE_BALLOON = 6;
    public static final int LIVE_SHOW_GIFT_CAR = 7;
    public static final int LIVE_SHOW_GIFT_VILLA = 8;
    public static final int LIVE_SHOW_GIFT_BANANA = 9;
    public static final int LIVE_SHOW_GIFT_FINGER_STALL = 10;
    public static final int LIVE_SHOW_GIFT_CHEERS = 11;
    public static final int LIVE_SHOW_GIFT_PISTOL = 12;
    public static final int LIVE_SHOW_GIFT_WHIP = 13;
    public static final int LIVE_SHOW_GIFT_UNDERWER = 14;
    public static final int LIVE_SHOW_GIFT_PLANE = 15;
    public static final int LIVE_SHOW_GIFT_CASTLE = 16;
    public static final int LIVE_SHOW_GIFT_EGGPLANT = 17;
    public static final int LIVE_SHOW_GIFT_CLEANER = 18;
    public static final int LIVE_SHOW_GIFT_HUG = 19;
    public static final int LIVE_SHOW_GIFT_CAKE = 20;
    public static final int LIVE_SHOW_GIFT_DOG = 21;
    public static final int LIVE_SHOW_GIFT_CHAMPION_SINGER = 22;
    public static final int LIVE_SHOW_GIFT_PORSCHE = 23;
    public static final int LIVE_SHOW_GIFT_YACHT = 24;
    public static final int LIVE_SHOW_GIFT_520 = 45;
    public static final int LIVE_SHOW_GIFT_999 = 46;
    public static final int LIVE_SHOW_GIFT_1314 = 47;
    public static final int LIVE_SHOW_GIFT_RAINBOW = 52;
    public static final int LIVE_SHOW_GIFT_VIP = 54;
    public static final int LIVE_SHOW_GIFT_MID_AUTUMN = 55;
    public static final int LIVE_SHOW_GIFT_FIREWORKS = 62;
    public static final int LIVE_SHOW_GIFT_CHRISTMAS = 71;
    public static final int LIVE_SHOW_GIFT_RED_PACKET_1 = 75;
    public static final int LIVE_SHOW_GIFT_RED_PACKET_2 = 76;
    public static final int LIVE_SHOW_GIFT_RED_PACKET_3 = 77;
    public static final int LIVE_SHOW_GIFT_RED_PACKET_4 = 78;
    public static final int LIVE_SHOW_GIFT_VALENTINE = 85;
    public static final int LIVE_SHOW_GIFT_CEREMONY = 109;
    public static final int LIVE_SHOW_GIFT_GUARD = 133;
    public static final int LIVE_SHOW_GIFT_GUARD_COMPLEX = 118;
    public static final int LIVE_SHOW_GIFT_GUARD_SIMPLE = 119;
    public static final int LIVE_SHOW_GIFT_BIG_CAKE = 142;
    public static final int LIVE_SHOW_GIFT_SHIT = 143;
    public static final int LIVE_SHOW_GIFT_FINGER_GUESS = 202;
    public static final int LIVE_SHOW_GIFT_FUJI_MOUNT = 216;
    public static final int LIVE_SHOW_GIFT_EXCLUSIVE = 223;

    public static final int LIVE_SHOW_GIFT_TIE_ZHI_1 = 1;
    public static final int LIVE_SHOW_GIFT_TIE_ZHI_2 = 2;
    public static final int LIVE_SHOW_GIFT_TIE_ZHI_3 = 3;
    public static final int LIVE_SHOW_GIFT_TIE_ZHI_4 = 4;
    public static final int LIVE_SHOW_GIFT_TIE_ZHI_5 = 5;


    // 礼物价格
    public static final int POPUP_GIFT_ROSE_DIAMOND = 1;
    public static final int POPUP_GIFT_KISS_DIAMOND = 5;
    public static final int POPUP_GIFT_HONGBAO_DIAMOND = 10;
    public static final int POPUP_GIFT_BAG_DIAMOND = 50;
    public static final int POPUP_GIFT_DIAMOND_RING_DIAMOND = 99;
    public static final int POPUP_GIFT_FIRE_BALLOON_DIAMOND = 299;
    public static final int POPUP_GIFT_CAR_DIAMOND = 666;
    public static final int POPUP_GIFT_CASTLE_DIAMOND = 1999;
    public static final int POPUP_GIFT_BANANA_DIAMOND = 1;
    public static final int POPUP_GIFT_FINGER_STALL_DIAMOND = 2;
    public static final int POPUP_GIFT_CHEERS_DIAMOND = 5;
    public static final int POPUP_GIFT_PISTOL_DIAMOND = 10;
    public static final int POPUP_GIFT_WHIP_DIAMOND = 20;
    public static final int POPUP_GIFT_UNDERWER_DIAMOND = 99;
    public static final int POPUP_GIFT_PLANE_DIAMOND = 3999;
    public static final int POPUP_GIFT_TOWER_DIAMOND = 6999;
    public static final int POPUP_GIFT_EGGPLANT_DIAMOND = 2;
    public static final int POPUP_GIFT_CLEANER_DIAMOND = 2;
    public static final int POPUP_GIFT_HUG_DIAMOND = 5;
    public static final int POPUP_GIFT_CAKE_DIAMOND = 10;
    public static final int POPUP_GIFT_DOG_DIAMOND = 20;
    public static final int POPUP_GIFT_CHAMPION_SINGER_DIAMOND = 99;
    public static final int POPUP_GIFT_PORSCHE_DIAMOND = 1666;
    public static final int POPUP_GIFT_YACHT_DIAMOND = 3666;
    public static final int POPUP_GIFT_520_DIAMOND = 520;
    public static final int POPUP_GIFT_999_DIAMOND = 999;
    public static final int POPUP_GIFT_1314_DIAMOND = 1314;
    public static final int POPUP_GIFT_RAINBOW_DIAMOND = 799;
    public static final int POPUP_GIFT_MID_AUTUMN = 1888;
    public static final int POPUP_GIFT_VIP_DIAMOND = 1;
    public static final int VIP_FREE_GIFT = 1;
    public static final int POPUP_GIFT_CHRISTMAS_DIAMOND = 39999;

    // 从礼物ID到icon res id的mapping
//    private static SparseArray<Integer> sGiftResIdMap = new SparseArray<>();
//    static {
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_ROSE, R.drawable.live_show_gift_rose);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_KISS, R.drawable.live_show_gift_kiss);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_HONGBAO, R.drawable.live_show_gift_red_packet);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_BAG, R.drawable.live_show_gift_bag);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_DIAMOND_RING, R.drawable.live_show_gift_ring);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_FIRE_BALLOON, R.drawable.live_show_gift_balloon);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_CAR, R.drawable.live_show_gift_car);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_VILLA, R.drawable.live_show_gift_villa);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_BANANA, R.drawable.live_show_gift_banana);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_FINGER_STALL, R.drawable.live_show_gift_stall);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_CHEERS, R.drawable.live_show_gift_cheers);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_PISTOL, R.drawable.live_show_gift_pistol);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_WHIP, R.drawable.live_show_gift_whip);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_UNDERWER, R.drawable.live_show_gift_underwer);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_PLANE, R.drawable.live_show_gift_plane);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_CASTLE, R.drawable.live_show_gift_tower);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_EGGPLANT, R.drawable.live_show_gift_eggplant);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_CLEANER, R.drawable.live_show_gift_cleaner);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_HUG, R.drawable.live_show_gift_hug);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_CAKE, R.drawable.live_show_gift_cake);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_DOG, R.drawable.live_show_gift_dog);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_CHAMPION_SINGER, R.drawable.live_show_gift_singer);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_PORSCHE, R.drawable.live_show_gift_porsche);
//        sGiftResIdMap.put(LIVE_SHOW_GIFT_YACHT, R.drawable.live_show_gift_yacht);
//    }

    //观看过的直播间
    public static ArrayList<String> showLiveShows = new ArrayList<>();

    /**
     * 不要直接访问這个变量，而要通过LiveShowUtils.getGiftList()来访问
     *
//     * @see LiveShowUtils#getGiftList()
     */
//    public static ArrayList<LiveShowPopupGiftItem> sGiftList = new ArrayList<>();
//    public static ArrayList<LiveShowPopupGiftItem> sVIPGiftList = new ArrayList<>();
    /**
     * 不要直接访问這个变量，而要通过LiveShowUtils.getGiftListSpecial()来访问
     *
//     * @see LiveShowUtils#getGiftListSpecial()
     */
//    public static ArrayList<LiveShowPopupGiftItem> sGiftListSpecial = new ArrayList<>();
    /**
     * 不要直接访问這个变量，而要通过LiveShowUtils.getGiftTypeList()来访问
     *
//     * @see LiveShowUtils#getGiftTypeList()
     */
    public static ArrayList<Integer> sGiftTypeList = new ArrayList<>();
    /**
     * 不要直接访问這个变量，而要通过LiveShowUtils.getGiftTypeList()来访问
     *
//     * @see LiveShowUtils#getGiftTypeList()
     */
    public static ArrayList<Integer> sGiftTypeListSpecial = new ArrayList<>();

//    public static ArrayList<LiveShowPopupGiftItem> sTiezhiGiftList = new ArrayList<>();

//    public static MysteriousManBean mysteriousManBean = new MysteriousManBean();

//    public static LiveShowPopupGiftItem vipLiveShowGift = null;
    public static ArrayList<LiveShowTiezhiBean> tiezhiList = new ArrayList<>();
    public static ArrayList<LiveShowTiezhiBean> giftTiezhiList = new ArrayList<>();
//    public static ArrayList<LiveShowPopupGiftItem> sGiftGif = new ArrayList<>();
    public static ArrayList<String> countryList = new ArrayList<>();


    // 礼物动画相关的常量
    public static final int LIVE_SHOW_GIFT_LEFT_IN_ANIM_START_OFFSET = 400;
    public static final int LIVE_SHOW_GIFT_NUM_ANIM_START_OFFSET = 1500;
    public static final int LIVE_SHOW_GIFT_NUM_ANIM_DURATION = 500;

    public static final int LIVE_SHOW_SENT_TYPE_SERVER = 0;
    public static final int LIVE_SHOW_SENT_TYPE_RONGCLOUD = 1;

    public static final String TOPIC_URL = "banner_430";
    public static final String LIVE_TOPIC = "live_topic";

    public static final String ONLINE_SHOW_LIST_URL = "user_online_lives";
    public static final String ONLINE_SHOW_RECOMMEND = "v2/live_recommd";
    public static final String BEST_ANCHOR_LIST = "love_bean_rank_list";
    public static final String PLAY_BACK_LIST = "/v2/live_record";

    public static final String ARGUMENT_LIVE_VIEW_TYPE = "live_view_type";
    public static final String ARGUMENT_VIEW_TYPE = "live_type";
    public static final String ARGUMENT_LIVE_VIEW_STYLE = "live_view_style";
    public static final String LIVE_TAG = "live_tag";

    public static String liveLocal = "";

    public static final int STYLE_LIST = 0;
    public static final int STYLE_GRID = 1;

    public static final int spanCount = 2;

    public static final int PAGE_INDEX_FOLLOWED = 0;
    public static final int PAGE_INDEX_HOT = 1;
    public static final int PAGE_INDEX_NEW = 2;

//    public static final ArrayList<TencentTIMMessage> timMsgList = new ArrayList<>();

    public static int GIFT_VERSION = 0;
    public static boolean CAN_SEND_CUSTOM = false;
    public static String CUSTOM_GIFT_IMG = "";
    public static String CUSTOM_GIFT_IMG_CACHE = "";

    public static int LianMaiUsePos = -1;
    public static HashMap<String,Integer> liveShowLikes = new HashMap<>();


    public static final int LIVE_COMMENT_SYSTEM_CONTENT_TYPE_DEFAULT = 0;
    public static final int LIVE_COMMENT_SYSTEM_CONTENT_TYPE_GAME = 1;//主播获胜系统消息发送后或游戏中断消息发送后
    public static final int LIVE_COMMENT_SYSTEM_CONTENT_TYPE_CONTRIBUTION_LIST = 2;//小榜一空缺
    public static final int LIVE_COMMENT_SYSTEM_CONTENT_TYPE_GUARD = 3;//1.守护位空缺 2.主播等级大于等于5级 3.已关注该主播4.观看时间超过5min
    public static final int LIVE_COMMENT_SYSTEM_CONTENT_TYPE_FIRST_LIVE_SHOW = 4;//首次进入直播间
    public static final int LIVE_COMMENT_SYSTEM_CONTENT_TYPE_SHARE_COME = 5;//有用户成功分享
    public static final int LIVE_COMMENT_SYSTEM_CONTENT_TYPE_ACTIVITY = 6;//有活动
    public static boolean isFirstLiveShow = true;

    public static final int LIVE_SHOW_GAME_FINGER_GUESS = 1; //猜拳游戏

    public static final int FINGER_GUESS_TYPE_SCISSORS = 1;
    public static final int FINGER_GUESS_TYPE_ROCK = 2;
    public static final int FINGER_GUESS_TYPE_PAPER = 3;

    public static final int GAME_NOT_START = 0;      // 游戏未开始
    public static final int GAME_WAIT_START = 1;     // 游戏等待开始
    public static final int GAME_IS_START = 2;       // 游戏进行中
    public static final int GAME_SHOW_RESULT = 3;    // 游戏展示结果中

    public static final int LIVE_GAME_RESULT_TYPE_REQUEST = 1;     // 观众向主播邀请游戏(主播接收)
    public static final int LIVE_GAME_RESULT_TYPE_AGREE = 2;       // 同意游戏(游戏观众和主播接收)
    public static final int LIVE_GAME_RESULT_TYPE_REJECT = 3;      // 主播已经开始游戏，拒绝(第二游戏观众接收)
    public static final int LIVE_GAME_RESULT_TYPE_CHOICE = 4;      // 观众发送选择项给主播(主播接收)
    public static final int LIVE_GAME_RESULT_TYPE_DRAWED = 5;      // 平局 (游戏观众接收)
    public static final int LIVE_GAME_RESULT_TYPE_ANCHORWIN = 6;   // 主播赢(游戏观众接收)
    public static final int LIVE_GAME_RESULT_TYPE_ANCHORLOSE = 7;  // 主播输(游戏观众接收)
    public static final int LIVE_GAME_RESULT_TYPE_GAMING = 8;      // 主播游戏中(观众接收)
    public static final int LIVE_GAME_RESULT_TYPE_AUDIENCE_RUN = 9;      // 主播赢，游戏观众逃跑(观众接收)

    public static final int WAIT_FINGER_GUESS_GAME_TIME = 16;    //真实预留时间
    public static final int WAIT_DELAY_FINGER_GUESS_GAME_RESULT_TIME = 6;//真实预留时间中等待延时的时间
    public static final int SHOW_FINGER_GUESS_GAME_RESULT_TIME = 5; //展示游戏结果的时间

    public static boolean isNotifying = false;//连麦窗口正在刷新中
//    public static LuckGameBean luckGameBean = null;
    public static String luckGameImg = null;
}
