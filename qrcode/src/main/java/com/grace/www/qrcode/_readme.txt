>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>配置文件Config.java>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


阅读confg/Config.java,
在这里配置文件，修改常用的UI


>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>功能：（核心功能都在Util.java这个类）>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




1.摄像头扫描二维码
 >不能太近距离扫描二维码（实验证明可以通过抓取分辨率较小的图片，改善近距离扫描不出二维码的问题，但是会引起二维码变形）

2.扫描相册里的二维码
 >摄像头照片代码内部进行了小图优化，不然直接oom  参照：https://blog.csdn.net/qq_28057541/article/details/52119896
 >这也是仅仅适用于这个二维码占据照片篇幅较大的情况，当一张图分辨率很大，但是其中的二维码占据一小部分时，为了不oom就要把大照片缩小
       * 但是缩小后，二维码就更小了，就识别不出来
 >还是有点问题（截图的肯定能扫描，拍照的大图有时候扫不出来）
3.指定字符串，生成二维码

4.打开、关闭闪光灯



>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>读取示例代码，直接复制即可使用了>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




    private static final int REQUEST_QRCODE = 0x01;

    // 一、打开CaptureActivity页面
    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_QRCODE);

    // 二、在Activity里按照下面方式，打开扫描页面，处理扫描结果

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                // TODO: 2018/7/6 处理扫码结果
                if (resultCode == Activity.RESULT_OK) {
                    String code = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(MainActivity.this,code,Toast.LENGTH_LONG).show();
                }
                break;
        }

        // TODO: 2018/7/6 处理扫相册图片结果

        if (resultCode==CaptureActivity.ALBUM_RESULT_CODE){
            String code = data.getStringExtra(CaptureActivity.RESULT_ALBUM_DATA);
            Toast.makeText(MainActivity.this,code,Toast.LENGTH_LONG).show();

        }
    }
