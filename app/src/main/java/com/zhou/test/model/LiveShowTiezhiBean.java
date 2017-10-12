package com.zhou.test.model;

/**
 * @author dyj
 * @since 2017/1/11.
 */

public class LiveShowTiezhiBean implements Cloneable {
    public String tiezhi_item;
    public String tiezhi_avatar;
    public String tiezhi_name;
    public boolean isDownload = false;
    public int anchor_level;
    public boolean isUsing = false;
    public boolean isEnable = false;
    public int currentProgress = 0;
    public boolean isGift = false;
    public long length;

    public Object clone() {
        LiveShowTiezhiBean o = null;
        try {
            o = (LiveShowTiezhiBean) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
