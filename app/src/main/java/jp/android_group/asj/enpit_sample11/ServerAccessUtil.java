package jp.android_group.asj.enpit_sample11;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import jp.android_group.asj.enpit_sample11.model.EntryModel;
import jp.android_group.asj.enpit_sample11.model.UserModel;

public class ServerAccessUtil {
    /**
     * ログ出力用
     */
    private static final String TAG = CheckUtil.class.getName().toString();

    /**
     * Get text from inputstream.
     *
     * @param is InputStream
     * @return Text
     * @throws IOException Exception
     */
    private static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        if (result.length() == 0) {
            return null;
        } else {
            return result.toString();
        }
    }


    /**
     * @param context
     * @param accessType
     * @param server
     * @param target_name
     * @param target_mail
     * @return
     */
    public static UserModel AccessServerUser(Context context, String accessType, String server, String target_name, String target_mail) {
        try {
            HttpURLConnection con = null;
            URL url = null;

            if (String.valueOf(accessType).equals(Constants.ACCESS_TYPE_GET_USER)) {
                String url_sv = "http://" + server + "/api/users/";
                String token = context.getString(R.string.token);

                // URLの作成
                url = new URL(url_sv);
                // 接続用HttpURLConnectionオブジェクト作成
                con = (HttpURLConnection) url.openConnection();
                // トークンセット
                con.addRequestProperty("authorization", "Token " + token);
                // リクエストメソッドの設定
                con.setRequestMethod("GET");
                // リダイレクトを自動で許可しない設定
                con.setInstanceFollowRedirects(false);
                // URL接続からデータを読み取る場合はtrue
                con.setDoInput(true);
                // URL接続にデータを書き込む場合はtrue
                con.setDoOutput(false);

                // 接続
                con.connect();
                int responsCode = con.getResponseCode();
                if (responsCode == 200) {
                    JSONArray jsonArray;
                    InputStream in = con.getInputStream();
                    String jsonText = inputStreamToString(in);
                    jsonArray = new JSONArray(jsonText);

                    if (jsonText.length() == 0) {
                        return null;
                    }

                    int size = jsonArray.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String name = item.getString("name");
                        String mail = item.getString("mail");
                        if (name.equals(target_name) && (mail.equals(target_mail))) {
                            UserModel userModel = new UserModel(id, name, mail);
                            if (BuildConfig.DEBUG) {
                                Log.i(TAG, "userModel:" + userModel.toString());
                            }
                            return userModel;
                        }
                    }
                    return null;
                } else {
                    return null;
                }
//            } else if (String.valueOf(accessType).equals(ACCESS_TYPE_PROFILE)) {
//                String url_sv = "http://" + SERVER_URL + "/profile?account_id=" + id;
//
//                // URLの作成
//                url = new URL(url_sv);
//                // 接続用HttpURLConnectionオブジェクト作成
//                con = (HttpURLConnection) url.openConnection();
//                // リクエストメソッドの設定
//                con.setRequestMethod("GET");
//                // リダイレクトを自動で許可しない設定
//                con.setInstanceFollowRedirects(false);
//                // URL接続からデータを読み取る場合はtrue
//                con.setDoInput(true);
//                // URL接続にデータを書き込む場合はtrue
//                con.setDoOutput(false);
//
//                // 接続
//                con.connect();
//                int responsCode = con.getResponseCode();
//                if (responsCode == 200) {
//                    JSONArray jsonArray;
//                    InputStream in = con.getInputStream();
//                    String jsonText = inputStreamToString(in);
//                    jsonArray = new JSONArray(jsonText);
//                    JSONObject json = jsonArray.getJSONObject(0);
//
//                    Log.i(TAG, "json data:" + json.toString());
//
//                    if (jsonText.length() > 0) {
//                        String name = json.getString("name");
//                        String address = json.getString("address");
//                        editFullName.setText(name);
//                        editAddress.setText(address);
//                    }
//                }
            }
        } catch (Exception e) {
            Log.i(TAG, "AccessServerUser() exception:" + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * @param context
     * @param accessType
     * @param server
     * @param authUserID
     * @return
     */
    public static ArrayList<EntryModel> AccessServerEntry(Context context, String accessType, String server, String authUserID) {
        try {
            HttpURLConnection con = null;
            URL url = null;
            ArrayList<EntryModel> entryModels = new ArrayList<>();

            if (String.valueOf(accessType).equals(Constants.ACCESS_TYPE_GET_ENTRY)) {
                String url_sv = "http://" + server + "/api/entries/";
                String token = context.getString(R.string.token);

                // URLの作成
                url = new URL(url_sv);
                // 接続用HttpURLConnectionオブジェクト作成
                con = (HttpURLConnection) url.openConnection();
                // トークンセット
                con.addRequestProperty("authorization", "Token " + token);
                // リクエストメソッドの設定
                con.setRequestMethod("GET");
                // リダイレクトを自動で許可しない設定
                con.setInstanceFollowRedirects(false);
                // URL接続からデータを読み取る場合はtrue
                con.setDoInput(true);
                // URL接続にデータを書き込む場合はtrue
                con.setDoOutput(false);

                // 接続
                con.connect();
                int responsCode = con.getResponseCode();
                if (responsCode == 200) {
                    JSONArray jsonArray;
                    InputStream in = con.getInputStream();
                    String jsonText = inputStreamToString(in);
                    jsonArray = new JSONArray(jsonText);

                    if (jsonText.length() == 0) {
                        return null;
                    }

                    int size = jsonArray.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String title = item.getString("title");
                        String body = item.getString("body");
                        String created_at = item.getString("created_at");
                        String status = item.getString("status");
                        String author = item.getString("author");
                        if (author.equals(authUserID)) {
                            EntryModel entryModel = new EntryModel(id, title, body, created_at, status, Integer.parseInt(author));
                            if (BuildConfig.DEBUG) {
                                Log.i(TAG, "entryModel:" + entryModel.toString());
                            }
                            entryModels.add(entryModel);
                        }
                    }
                    return entryModels;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "AccessServerEntry() exception:" + e.getMessage());
            return null;
        }
        return null;
    }
}
