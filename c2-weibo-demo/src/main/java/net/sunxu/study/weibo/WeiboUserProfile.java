package net.sunxu.study.weibo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 微博的用户数据, 通过 https://api.weibo.com/2/users/show.json 获得的结果
 * 这个是从sdk 压缩包拿来的数据修改了一一下.
 */
@Data
@ToString
public class WeiboUserProfile implements Serializable {
    private Long id;                      //用户UID, 有 id, 和 idstr 分别表示数据
    private String screenName;            //微博昵称
    private String name;                  //友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
    private int province;                 //省份编码（参考省份编码表）
    private int city;                     //城市编码（参考城市编码表）
    private String location;              //地址
    private String description;           //个人描述
    private String url;                   //用户博客地址
    private String profileImageUrl;       //自定义图像
    private String userDomain;            //用户个性化URL
    private String gender;                //性别,m--男，f--女,n--未知
    private int followersCount;           //粉丝数
    private int friendsCount;             //关注数
    private int statusesCount;            //微博数
    private int favouritesCount;          //收藏数
    private Date createdAt;               //创建时间
    private boolean following;            //保留字段,是否已关注(此特性暂不支持)
    private boolean verified;             //加V标示，是否微博认证用户
    private int verifiedType;             //认证类型
    private boolean allowAllActMsg;       //是否允许所有人给我发私信
    private boolean allowAllComment;      //是否允许所有人对我的微博进行评论
    private boolean followMe;             //此用户是否关注我
    private String avatarLarge;           //大头像地址
    private int onlineStatus;             //用户在线状态
    private String status;                //用户最新一条微博, 这个原先是一个status 类, 因为引用太多我给改成String 类型了
    private int biFollowersCount;         //互粉数
    private String remark;                //备注信息，在查询用户关系时提供此字段。
    private String lang;                  //用户语言版本
    private String verifiedReason;        //认证原因
    private String weihao;                //微號
    private String statusId;
}
