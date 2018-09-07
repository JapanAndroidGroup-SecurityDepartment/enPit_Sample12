package jp.android_group.asj.enpit_sample12.model;


import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    // DB定義
    private int id;
    private String name;
    private String mail;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(mail);
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    private UserModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mail = in.readString();
    }

    public UserModel(int id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

    public UserModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "UserModel [id=" + id + ", name=" + name + ", mail=" + mail + "]";
    }
}

