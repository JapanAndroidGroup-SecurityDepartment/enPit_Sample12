package jp.android_group.asj.enpit_sample11;

import android.app.AlertDialog;
import android.content.Context;

public class CheckUtil {
    /**
     * ログ出力用
     */
    private static final String TAG = CheckUtil.class.getName().toString();

    public static void showAlertDialog(Context context, String msg) {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}
