package com.yingdou.www.newpermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class NewPermissionActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_permission);

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
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        Log.e("ccccccc", permissionCheck + "===========");
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
//        }
//    }

    /**
     * google官方库easyPermission
     */
    private void easyPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            //具备权限 直接进行操作
            Toast.makeText(this, "我本来具有权限", Toast.LENGTH_SHORT).show();

        } else {
            //权限拒绝 申请权限
            EasyPermissions.requestPermissions(this, "xxxxxxxxxhkgkkgk与会加快；干红辣椒第一条复古看好了xxxxxxx", 0x11, Manifest.permission.CALL_PHONE);
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
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {

//            startActivityForResult(
//                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                            .setData(Uri.fromParts("package", getPackageName(), null)),
//                    0x101);//自己打开本APP的设置页面，注意小米三星可用，但是vivo oppo不好用


            new AppSettingsDialog.Builder(this).setTitle("需要申请权限").setRationale("XXXXXXXXXXXXXXXXXX").build().show();
        }
    }

}
