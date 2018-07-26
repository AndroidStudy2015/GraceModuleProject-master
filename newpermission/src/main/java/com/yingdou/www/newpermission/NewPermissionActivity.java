package com.yingdou.www.newpermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class NewPermissionActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks
{

//    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_permission);
//        createDialog();
        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nativePermission();

            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyPermission();

            }
        });
    }

    /**
     * 原生动态权限申请方法
     */
    private void nativePermission() {


//        第一次打开app，直接请求权限
//        第二次请求权限，上次用户点击了拒绝，但没点击不再询问，直接请求权限
//        第二次请求权限，上次用户点击了不再询问，直接请求权限是不会弹出请求权限对话框的，而是相当于直接点了拒绝，onRequestPermissionsResult里会接收到未授权的回调
//        此时需要弹出一个对话框，打开本app的设置去手动打开权限

//        原生处理的麻烦之处在于，你手动点击拒绝和点击拒绝+不再询问，在onRequestPermissionsResult里都会得到未授权的结果，、
//        但是，如果如果仅仅是拒绝，我们不需要特殊处理，但是如果是拒绝+不再询问，我们需要弹出一个去系统设置的对话框
//        我们无法判断是属于那种情况（虽然我们可以通过记录在sharepreferenc里判断，但是很麻烦）

//        这一点在google的easyPermission是有方法可以判断的 permissionPermanentlyDenied，随意推荐使用permissionPermanentlyDenied


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        // 被授权
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "授权了。。。", Toast.LENGTH_SHORT).show();
        }
        //没有授权
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "没有授权了", Toast.LENGTH_SHORT).show();

            //第一次打开App时，为false
            //上次弹出权限点击了禁止（但没有勾选“下次不在询问”）为true
            //上次选择禁止并勾选：下次不在询问，为false
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "解释解释及为积极第三方的双丰收", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, 0x00);

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, 0x00);
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (grantResults.length > 0
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "你点击了授权", Toast.LENGTH_SHORT).show();
//
//        }else
//        {
//            Toast.makeText(this, "你点击了不再询问", Toast.LENGTH_SHORT).show();
//
//        }
//    }

    /**
     * google官方库easyPermission
     */
    private void easyPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE,Manifest.permission.CAMERA)) {
            //具备权限 直接进行操作
            Toast.makeText(this, "我本来具有权限", Toast.LENGTH_SHORT).show();

        } else {
            //没有权限 申请权限
            //requestPermissions，内部做了处理
            //1.如果 首次请求权限，则直接请求权限
            //2.如果 上次拒绝（没点击不再提醒），会弹出解释对话框，点击确定按钮会再次弹出请求权限对话框
            //3.如果 上次拒绝（点击不再提醒），这里返回不授权，在onPermissionsDenied里处理后续操作，例如打开对话框，让他去设置
            EasyPermissions.requestPermissions(this, "向用户解释为什么需要这个权限", 0x11, Manifest.permission.CALL_PHONE,Manifest.permission.CAMERA);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



/**
     * 请求权限成功。
     * 可以弹窗显示结果，也可执行具体需要的逻辑操作
     *
     * @param requestCode
     * @param perms
     */

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "用户授权成功。。。", Toast.LENGTH_SHORT).show();

    }

/**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "用户授权失败。。。", Toast.LENGTH_SHORT).show();

/**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */


     if(  EasyPermissions.permissionPermanentlyDenied(this,Manifest.permission.CAMERA)){//permissionPermanentlyDenied判断单个权限是不是被拒绝
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {//somePermissionPermanentlyDenied判断多个权限是不是被拒绝
            new AppSettingsDialog.Builder(this).setTitle("需要申请权限-->摄像头").setRationale("解释需要权限的原因").build().show();
        }

        if(  EasyPermissions.permissionPermanentlyDenied(this,Manifest.permission.CALL_PHONE)){//permissionPermanentlyDenied判断单个权限是不是被拒绝
            new AppSettingsDialog.Builder(this).setTitle("需要申请权限-->打电话").setRationale("解释需要权限的原因").build().show();
        }
    }






}
