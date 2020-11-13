package com.gusi.androidx.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;

/**
 * @author Ylw
 * @since 2020/8/7 21:51
 */
public class ContactUtils {
    public static void insertContact() {
        String name = RandomValueUtil.getChineseName();
        String phone = RandomValueUtil.getTelephone();
        String email = RandomValueUtil.getEmail(10, 20);
        String address = RandomValueUtil.getRoad();
        //把数据插入到数据库  联系人数据库也是通过内容提供者暴露出来的
        // 定义uri
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri parse = Uri.parse("content://com.android.contacts/data");
        //先查询一下raw_contact表中一共有几条数据 行数+1就是contact_id的插入值
        Cursor query = Utils.getApp().getContentResolver().query(uri, null, null, null, null);

        int count = query.getCount();
        int contact_id = count + 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact_id", contact_id);
        Utils.getApp().getContentResolver().insert(uri, contentValues);
        //将控件里面的数据插入到data表中的data1字段中
        //将姓名插入到data表
        ContentValues nameData = new ContentValues();
        nameData.put("data1", name);//把数据插入到data1字段中
        nameData.put("raw_contact_id", contact_id);//告诉数据库插入的数据库属于哪个联系人
        nameData.put("mimetype", "vnd.android.cursor.item/name");//告诉数据库插入的数据类型
        Utils.getApp().getContentResolver().insert(parse, nameData);

        //将联系人号码添加到data表中
        ContentValues phoneData = new ContentValues();
        phoneData.put("data1", phone);
        phoneData.put("raw_contact_id", contact_id);
        phoneData.put("mimetype", "vnd.android.cursor.item/phone_v2");
        Utils.getApp().getContentResolver().insert(parse, phoneData);

        //将联系人邮箱添加到data表中
        ContentValues emailData = new ContentValues();
        emailData.put("data1", email);
        emailData.put("raw_contact_id", contact_id);
        emailData.put("mimetype", "vnd.android.cursor.item/email_v2");
        Utils.getApp().getContentResolver().insert(parse, emailData);

        //将联系人地址添加到data表中
        ContentValues addressData = new ContentValues();
        addressData.put("data1", address);
        addressData.put("raw_contact_id", contact_id);
        addressData.put("mimetype", "vnd.android.cursor.item/postal-address_v2");
        Utils.getApp().getContentResolver().insert(parse, addressData);
    }
}
