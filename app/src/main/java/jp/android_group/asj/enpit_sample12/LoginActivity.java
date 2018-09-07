package jp.android_group.asj.enpit_sample12;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.android_group.asj.enpit_sample12.BuildConfig;
import jp.android_group.asj.enpit_sample12.R;
import jp.android_group.asj.enpit_sample12.model.UserModel;

public class LoginActivity extends Activity {
    /**
     * ログ出力用
     */
    private static final String TAG = LoginActivity.class.getName().toString();

    private EditText mServerIP;
    private EditText mLoginName;
    private EditText mMailAddress;
    private Button mLoginBtn;
    private static int mAutherUserID = 0;
    private static String mAutherUserName = "";
    private static String mIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mServerIP = findViewById(R.id.edt_server_ip);
        mLoginName = findViewById(R.id.edt_login_name);
        mMailAddress = findViewById(R.id.edt_mail_address);
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoginName.getText().length() == 0) {
                    CheckUtil.showAlertDialog(LoginActivity.this, getString(R.string.msg_login_name_err));
                }
                if (mMailAddress.getText().length() == 0) {
                    CheckUtil.showAlertDialog(LoginActivity.this, getString(R.string.msg_mail_address_err));
                }
                //Todo ログイン処理呼び出し
                GetUserModel();
            }
        });

    }


    /**
     * Backキーを無視します
     */
    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            return false;
        }
    }

    private void GetUserModel() {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "GetUserModel() start");
        }
        mIP = mServerIP.getText().toString();
        String name = mLoginName.getText().toString();
        String mail = mMailAddress.getText().toString();

        Connecting2Server connecting2Server = new Connecting2Server(LoginActivity.this);
        connecting2Server.setOnCallBack(new Connecting2Server.CallBackTask() {
            @Override
            public void CallBack(int result) {
                super.CallBack(result);
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "GetUserModel()  Call Back Success.");
                    Log.i(TAG, "GetUserModel()  mAutherUserID:" + String.valueOf(mAutherUserID)
                            + ", mAutherUserName:" + mAutherUserName);
                }

                Intent data = new Intent();
                data.putExtra(Constants.EXTRA_USER_ID, mAutherUserID);
                data.putExtra(Constants.EXTRA_USER_NAME, mAutherUserName);
                data.putExtra(Constants.EXTRA_SERVER_IP, mIP);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        connecting2Server.execute(Constants.ACCESS_TYPE_GET_USER, mIP, name, mail);
        return;
    }

    /**
     * サーバへの問い合わせ非同期クラス
     */
    private static class Connecting2Server extends AsyncTask<String, Integer, Boolean> {
        private Context context;
        private ProgressDialog mProgressDialog;
        private Boolean isShowProgress;
        private CallBackTask callbacktask;
        private int id = 0;

        /**
         * コンストラクタ
         *
         * @param context
         */
        public Connecting2Server(Context context) {
            this.context = context;
        }

        /**
         * getIsShowProgress
         * プログレスが表示中かどうかを返却
         *
         * @return
         */
        public Boolean getIsShowProgress() {
            return isShowProgress;
        }

        /**
         * onPreExecute
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isShowProgress = true;
            showDialog();
        }

        /**
         * doInBackground
         *
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(String... params) {
            UserModel userModel = ServerAccessUtil.AccessServerUser(context, params[0], params[1], params[2], params[3]);
            if (userModel == null)
                return false;
            mAutherUserID = userModel.getId();
            mAutherUserName = userModel.getName();
            return true;
        }

        /**
         * onPostExecute
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            dismissDialog();
            isShowProgress = false;
            callbacktask.CallBack(-1);

            if (result) {
                Toast.makeText(context, "認証成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "エラー", Toast.LENGTH_LONG).show();
            }
        }

        /**
         * showDialog
         * ダイアログ表示
         */
        public void showDialog() {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getResources().getString(R.string.connect_Server));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        /**
         * dismissDialog
         */
        public void dismissDialog() {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        /**
         * @param callBackTask
         */
        public void setOnCallBack(CallBackTask callBackTask) {
            callbacktask = callBackTask;
        }

        /**
         * コールバック用のstaticなclass
         */
        public static class CallBackTask {
            public void CallBack(int result) {
            }
        }
    }
}
